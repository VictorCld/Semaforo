package com.example.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import com.example.simulation.datastructure.Fila;
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

    public Simulacao(Grafo grafo, GeradorVeiculos geradorVeiculos) {
        this.grafo = grafo;
        this.geradorVeiculos = geradorVeiculos;
        this.filaVeiculos = new Fila<>();
        this.controladorSemaforos = new ControladorSemaforos(grafo);
    }

    private LinkedList<Semaforo> coletarSemaforos() {
        LinkedList<Semaforo> semaforos = new LinkedList<>();
        for (Intersecao intersecao : grafo.vertices) {
            if (intersecao.getSemaforo() != null) {
                semaforos.add(intersecao.getSemaforo());
            }
        }
        return semaforos;
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

    public void testarVeiculosESemaforos(int quantidadeDeVeiculos) {
        System.out.println("===============================================");
        System.out.println(" INÍCIO DA SIMULAÇÃO DE VEÍCULOS E SEMÁFOROS ");
        System.out.println("===============================================");

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

        com.example.simulation.datastructure.HashSet<Long> veiculosQueChegaram = new com.example.simulation.datastructure.HashSet<>();
        int ciclo = 0;
        while (veiculosQueChegaram.tamanho() < quantidadeDeVeiculos) {
            System.out.println("\n-----------------------------------------------");
            System.out.printf("CICLO %d\n", ciclo);
            System.out.println("-----------------------------------------------");

            int tamanho = filaVeiculos.size();
            for (int i = 0; i < tamanho; i++) {
                Veiculo v = filaVeiculos.desenfileirar();
                int numeroVeiculo = v.getNumeroSimulacao();

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
                    TrafficLightState estado = semaforo.getEstadoAtual();
                    switch (estado) {
                        case VERDE:
                            v.avancar();
                            System.out.printf("[Veículo %d]  Passou no sinal VERDE: %s -> %s\n", numeroVeiculo,
                                    atualInter.getId(), proxima.getId());
                            break;
                        case AMARELO:
                            System.out.printf("[Veículo %d]  Aguardando sinal AMARELO em %s (próximo: %s)\n",
                                    numeroVeiculo, atualInter.getId(), proxima.getId());
                            break;
                        case VERMELHO:
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

            controladorSemaforos.atualizarSemaforos();
            ciclo++;

            try {
                Thread.sleep(400);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        System.out.println("\n===============================================");
        System.out.println("    TODOS OS VEÍCULOS CHEGARAM AO DESTINO!     ");
        System.out.println("===============================================");
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
        rodando = false;
        System.out.println("Simulação pausada.");
    }

    public void parar() {
        rodando = false;
        System.out.println("Simulação encerrada.");
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
