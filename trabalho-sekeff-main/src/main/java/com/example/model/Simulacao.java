package com.example.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Scanner;

import javax.swing.JOptionPane;

import com.example.VisualizacaoSimulador;
import com.example.simulation.datastructure.Fila;
import com.example.simulation.datastructure.HashMap;
import com.example.simulation.datastructure.HashSet;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.Node;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;
import com.example.simulation.traffic.semaforo.ControladorSemaforos;
import com.example.simulation.traffic.semaforo.TrafficLightState;
import com.example.simulation.traffic.veiculo.GeradorVeiculos;

public class Simulacao implements Serializable {
    private static final long serialVersionUID = 1L;

    private Grafo grafo;
    private GeradorVeiculos geradorVeiculos;
    private Fila<Veiculo> filaVeiculos;
    private ControladorSemaforos controladorSemaforos;
    private transient boolean rodando;
    private transient boolean pausado;

    public Simulacao(Grafo grafo, GeradorVeiculos geradorVeiculos) {
        this.grafo = grafo;
        this.geradorVeiculos = geradorVeiculos;
        this.filaVeiculos = new Fila<>();
        this.controladorSemaforos = new ControladorSemaforos(grafo);
    }

    public void iniciar(int ciclos, int veiculosPorCiclo, int modoOperacao) {
        rodando = true;
        for (int i = 0; i < ciclos && rodando; i++) {
            System.out.println("\nCiclo: " + (i + 1));

            controladorSemaforos.atualizarSemaforos();
            mostrarEstadoSemaforos();

            geradorVeiculos.gerarMultiplosVeiculos(veiculosPorCiclo, filaVeiculos);
            simularMovimentoVeiculos();

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void mostrarEstadoSemaforos() {
        System.out.println("Estado dos semáforos:");
        for (Semaforo s : controladorSemaforos.getSemaforos()) {
            if (s.getIntersecao() != null) {
                System.out.println("Semáforo na interseção " + s.getIntersecao().getId() + ": " + s.getEstadoAtual());
            } else {
                System.out.println("Semáforo: " + s.getEstadoAtual());
            }
        }
    }

    public static void menuSimulacao(Grafo grafo, GeradorVeiculos geradorVeiculos,
            com.example.VisualizacaoSimulador painel) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("===============================================");
        System.out.println("         SIMULADOR DE TRÁFEGO URBANO           ");
        System.out.println("===============================================");
        System.out.print("Digite a quantidade de veículos para simular: ");
        int quantidadeDeVeiculos = scanner.nextInt();

        System.out.println("\nEscolha o modelo de controle de semáforo:");
        System.out.println("1 - Ciclo fixo");
        System.out.println("2 - Otimização do tempo de espera");
        System.out.println("3 - Otimização do consumo de energia");
        System.out.print("Modelo: ");
        int modelo = scanner.nextInt();

        Simulacao sim = new Simulacao(grafo, geradorVeiculos);
        sim.testarVeiculosESemaforosComModelo(quantidadeDeVeiculos, modelo, painel);
    }

    public void testarVeiculosESemaforosComModelo(int quantidadeDeVeiculos, int modelo, VisualizacaoSimulador painel) {
    System.out.println("===============================================");
    System.out.println(" INÍCIO DA SIMULAÇÃO DE VEÍCULOS E SEMÁFOROS ");
    System.out.println("===============================================");

    rodando = true;

    geradorVeiculos.gerarMultiplosVeiculos(quantidadeDeVeiculos, filaVeiculos);

    // Exibe as rotas completas dos veículos
    System.out.println("\n========== ROTAS DOS VEÍCULOS ==========");
    for (int i = 0; i < filaVeiculos.size(); i++) {
        Veiculo v = filaVeiculos.get(i);
        System.out.println();
        System.out.printf(" [Veículo %d] = ", v.getNumeroSimulacao());
        LinkedList<Long> rota = v.getCaminho();
        Node<Long> atual = rota.head;
        while (atual != null) {
            System.out.print(atual.data);
            if (atual.next != null) {
                System.out.print("  ->  ");
            }
            atual = atual.next;
        }
        System.out.println();
    }
    System.out.println("========================================\n");

    // Estruturas para estatísticas
    HashSet<Long> veiculosQueChegaram = new HashSet<>();
    HashMap<Long, Integer> tempoViagem = new HashMap<>();
    HashMap<Long, Integer> tempoEspera = new HashMap<>();
    int totalVeiculosCongestionados = 0;
    int ciclo = 0;

    // Marca o tempo de início da simulação
    long inicioSimulacao = System.currentTimeMillis();

    // --- AJUSTE: Controle de pausa e parada ---
    while (veiculosQueChegaram.tamanho() < quantidadeDeVeiculos && rodando) {
        // Controle de pausa
        synchronized (this) {
            while (pausado) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
        // ----------------------------------------

        System.out.println("\n-----------------------------------------------");
        System.out.printf("CICLO %d\n", ciclo);
        System.out.println("-----------------------------------------------");

        int tamanho = filaVeiculos.size();
        for (int i = 0; i < tamanho; i++) {
            Veiculo v = filaVeiculos.desenfileirar();
            int numeroVeiculo = v.getNumeroSimulacao();

            // Estatística: conta ciclos de viagem
            Integer viagemAtual = tempoViagem.get(v.getId());
            if (viagemAtual == null)
                viagemAtual = 0;
            tempoViagem.put(v.getId(), viagemAtual + 1);

            if (v.chegouAoDestino()) {
                if (!veiculosQueChegaram.contem(v.getId())) {
                    veiculosQueChegaram.adicionar(v.getId());
                    System.out.printf("[Veículo %d]  Chegou ao destino final: %s\n", numeroVeiculo, v.getDestino());
                }
                continue;
            }

            Intersecao proxima = v.getProximaIntersecao(grafo);
            Intersecao atualInter = v.getIntersecaoAtual(grafo);

            if (proxima == null) {
                if (v.estaNoDestino() && !veiculosQueChegaram.contem(v.getId())) {
                    v.marcarChegou();
                    veiculosQueChegaram.adicionar(v.getId());
                    System.out.printf("[Veículo %d]  Chegou ao destino final: %s\n", numeroVeiculo, v.getDestino());
                } else {
                    filaVeiculos.enfileirar(v);
                }
                continue;
            }

            Semaforo semaforo = proxima.getSemaforo();
            if (semaforo == null) {
                v.avancar();
                System.out.printf("[Veículo %d]  Avançou: %s -> %s (sem semáforo)\n", numeroVeiculo,
                        atualInter.getId(), proxima.getId());
            } else {
                semaforo.atualizarCicloModelo(modelo, filaVeiculos);

                TrafficLightState estado = semaforo.getEstadoAtual();
                switch (estado) {
                    case VERDE:
                        v.avancar();
                        System.out.printf("[Veículo %d]  Passou no sinal VERDE: %s -> %s\n", numeroVeiculo,
                                atualInter.getId(), proxima.getId());
                        break;
                    case AMARELO:
                        Integer esperaAmarelo = tempoEspera.get(v.getId());
                        if (esperaAmarelo == null)
                            esperaAmarelo = 0;
                        tempoEspera.put(v.getId(), esperaAmarelo + 1);
                        System.out.printf("[Veículo %d]  Aguardando sinal AMARELO em %s (próximo: %s)\n",
                                numeroVeiculo, atualInter.getId(), proxima.getId());
                        break;
                    case VERMELHO:
                        Integer esperaVermelho = tempoEspera.get(v.getId());
                        if (esperaVermelho == null)
                            esperaVermelho = 0;
                        tempoEspera.put(v.getId(), esperaVermelho + 1);
                        System.out.printf("[Veículo %d]  Aguardando sinal VERMELHO em %s (próximo: %s)\n",
                                numeroVeiculo, atualInter.getId(), proxima.getId());
                        break;
                }
            }

            if (!v.chegouAoDestino()) {
                filaVeiculos.enfileirar(v);
            } else if (!veiculosQueChegaram.contem(v.getId())) {
                veiculosQueChegaram.adicionar(v.getId());
                System.out.printf("[Veículo %d]  Chegou ao destino final: %s\n", numeroVeiculo, v.getDestino());
            }
        }

        // Índice de congestionamento: veículos que não avançaram neste ciclo
        totalVeiculosCongestionados += filaVeiculos.size();

        // Atualiza a interface gráfica após cada ciclo
        if (painel != null) {
            // Monta lista de veículos ativos usando sua LinkedList
            LinkedList<Veiculo> veiculosAtivos = new LinkedList<>();
            for (int i = 0; i < filaVeiculos.size(); i++) {
                veiculosAtivos.add(filaVeiculos.get(i));
            }
            painel.setVeiculos(veiculosAtivos);
            painel.repaint();
        }

        controladorSemaforos.atualizarSemaforos();
        ciclo++;

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    // Marca o tempo de fim da simulação
    long fimSimulacao = System.currentTimeMillis();
    double duracaoSegundos = (fimSimulacao - inicioSimulacao) / 1000.0;

    System.out.println("\n===============================================");
    System.out.println("    TODOS OS VEÍCULOS CHEGARAM AO DESTINO!     ");
    System.out.println("===============================================");

    // Estatísticas finais
    double somaViagem = 0;
    double somaEspera = 0;
    Iterable<Long> chaves = tempoViagem.keySet();
    for (Long id : chaves) {
        somaViagem += tempoViagem.get(id);
        Integer espera = tempoEspera.get(id);
        if (espera != null) {
            somaEspera += espera;
        }
    }
    double tempoMedioViagem = somaViagem / quantidadeDeVeiculos;
    double tempoMedioEspera = somaEspera / quantidadeDeVeiculos;
    double indiceCongestionamento = (double) totalVeiculosCongestionados / ciclo;

    String estatisticas = String.format(
            "=============== ESTATÍSTICAS DA SIMULAÇÃO ===============\n" +
                    "Tempo médio de viagem: %.2f ciclos\n" +
                    "Tempo médio de espera em semáforo: %.2f ciclos\n" +
                    "Índice médio de congestionamento: %.2f veículos/ciclo\n" +
                    "Quantidade de ciclos: %d\n" +
                    "Tempo total de simulação: %.2f segundos\n" +
                    "=========================================================",
            tempoMedioViagem, tempoMedioEspera, indiceCongestionamento, ciclo, duracaoSegundos);

    System.out.println(estatisticas);

    // Mostra na tela (Swing)
    javax.swing.SwingUtilities.invokeLater(() -> {
        JOptionPane.showMessageDialog(null, estatisticas, "Estatísticas da Simulação",
                JOptionPane.INFORMATION_MESSAGE);
    });
}

    private void simularMovimentoVeiculos() {
        int tamanho = filaVeiculos.size();
        System.out.println("Tamanho da fila de veículos: " + tamanho);

        for (int i = 0; i < tamanho; i++) {
            Veiculo v = filaVeiculos.desenfileirar();

            System.out.println("Veículo " + v.getId() + " posição atual: " + v.getIntersecaoAtual(grafo).getId());

            if (v.chegouAoDestino()) {
                System.out.println("Veículo " + v.getId() + " chegou ao destino.");
                continue;
            }

            Intersecao proxima = v.getProximaIntersecao(grafo);

            if (proxima == null) {
                if (v.chegouAoDestino()) {
                    System.out.println("Veículo " + v.getId() + " chegou ao destino.");
                } else {
                    System.out.println("Veículo " + v.getId()
                            + " sem próxima interseção, porém não chegou ao destino. Verificar rota.");
                    filaVeiculos.enfileirar(v);
                }
                continue;
            }

            Semaforo s = proxima.getSemaforo();
            TrafficLightState estado = (s != null) ? s.getEstadoAtual() : TrafficLightState.VERDE;

            System.out.println("Veículo " + v.getId() + " -> Próxima: " + proxima.getId() + " | Semáforo: " + estado);

            if (estado == TrafficLightState.VERDE) {
                v.avancar();
                System.out.println("Veículo " + v.getId() + " avançou para " + v.getIntersecaoAtual(grafo).getId());

                if (v.estaNoDestino()) {
                    System.out.println("Veículo " + v.getId() + " está no destino.");
                    continue;
                }
            } else {
                System.out.println("Veículo " + v.getId() + " aguardando sinal verde em " + proxima.getId());
            }

            filaVeiculos.enfileirar(v);
        }
    }

    public void pausar() {
        pausado = true;
        System.out.println("Simulação pausada.");
    }

    public void continuar() {
        pausado = false;
        synchronized (this) {
            notifyAll();
        }
        System.out.println("Simulação retomada.");
    }

    public void parar() {
        rodando = false;
        pausado = false;
        synchronized (this) {
            notifyAll();
        }
        System.out.println("Simulação encerrada.");
    }

    public boolean isPausado() {
        return pausado;
    }

    public void salvar(String nomeArquivo) {
        File dir = new File("saves");
        if (!dir.exists())
            dir.mkdirs();

        if (!nomeArquivo.endsWith(".dat")) {
            nomeArquivo += ".dat";
        }

        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("saves/" + nomeArquivo))) {
            out.writeObject(this);
            System.out.println("Simulação salva em saves/" + nomeArquivo);
        } catch (IOException e) {
            System.out.println("Erro ao salvar simulação: " + e.getMessage());
        }
    }

    public static Simulacao carregar(String nomeArquivo) {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream("saves/" + nomeArquivo))) {
            Simulacao sim = (Simulacao) in.readObject();
            System.out.println("Simulação carregada de saves/" + nomeArquivo);
            return sim;
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Erro ao carregar simulação: " + e.getMessage());
            return null;
        }
    }
}
