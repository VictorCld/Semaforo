package com.example.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.LinkedList;

import com.example.simulation.datastructure.Fila;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;
import com.example.simulation.traffic.semaforo.ControladorSemaforos;
import com.example.simulation.traffic.semaforo.GeradorDeSemaforos;
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
                Thread.sleep(1000); // 1 segundo = 1 minuto simulado
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void mostrarEstadoSemaforos() {
        for (Semaforo s : controladorSemaforos.getSemaforos()) {
            System.out.println(s);
        }
    }

    public void testarVeiculosESemaforos(int quantidadeDeVeiculos) {
        System.out.println("=== INÍCIO DO TESTE DE VEÍCULOS E SEMÁFOROS ===");

        GeradorDeSemaforos geradorDeSemaforos = new GeradorDeSemaforos();
        geradorDeSemaforos.configurarSemaforos(grafo);

        geradorVeiculos.gerarMultiplosVeiculos(quantidadeDeVeiculos, filaVeiculos);
        // Supondo que Fila<Veiculo> tenha um método size() e get(int)
        for (int i = 0; i < filaVeiculos.size(); i++) {
            Veiculo v = filaVeiculos.get(i);
            System.out.println("Rota veículo " + v.getId() + ": " + v.getCaminho());
        }

        for (int ciclo = 0; ciclo < 20; ciclo++) {
            System.out.println("=== CICLO " + ciclo + " ===");

            int tamanho = filaVeiculos.size();
            for (int i = 0; i < tamanho; i++) {
                Veiculo v = filaVeiculos.desenfileirar();

                if (v.chegouAoDestino()) {
                    System.out.println("Veículo " + v.getId() + " chegou ao destino.");
                    continue;
                }

                Intersecao proxima = v.getProximaIntersecao(grafo);
                if (proxima == null) {
                    if (v.chegouAoDestino()) {
                        System.out.println("Veículo " + v.getId() + " chegou ao destino (sem próxima interseção).");
                    } else {
                        System.out.println("Veículo " + v.getId()
                                + " sem próxima interseção, mas ainda não chegou ao destino.");
                        filaVeiculos.enfileirar(v);
                    }
                    continue;
                }

                Semaforo semaforo = proxima.getSemaforo();
                TrafficLightState estado = (semaforo != null) ? semaforo.getEstadoAtual() : TrafficLightState.VERDE;

                System.out
                        .println("Veículo " + v.getId() + " -> Próxima: " + proxima.getId() + " | Semáforo: " + estado);

                if (estado == TrafficLightState.VERDE) {
                    v.avancar();
                    System.out.println("Veículo " + v.getId() + " avançou para " + v.getIntersecaoAtual(grafo).getId());
                } else {
                    System.out.println("Veículo " + v.getId() + " aguardando sinal verde.");
                }

                if (!v.chegouAoDestino()) {
                    filaVeiculos.enfileirar(v);
                } else {
                    System.out.println("Veículo " + v.getId() + " chegou ao destino.");
                }
            }

            controladorSemaforos.atualizarSemaforos();
            System.out.println();
        }

        System.out.println("=== FIM DO TESTE ===");
    }

    private void simularMovimentoVeiculos() {
        int tamanho = filaVeiculos.size();
        System.out.println("Tamanho da fila de veículos: " + tamanho);

        for (int i = 0; i < tamanho; i++) {
            Veiculo v = filaVeiculos.desenfileirar();

            System.out.println("Veículo " + v.getId() + " posição atual: " + v.getIntersecaoAtual(grafo).getId());

            if (v.chegouAoDestino()) {
                System.out.println("Veículo " + v.getId() + " chegou ao destino.");
                // Não reenfileirar, pois chegou ao destino
                continue;
            }

            Intersecao proxima = v.getProximaIntersecao(grafo);

            if (proxima == null) {
                if (v.chegouAoDestino()) {
                    System.out.println("Veículo " + v.getId() + " chegou ao destino.");
                    // Não reenfileira, pois já chegou
                } else {
                    System.out.println("Veículo " + v.getId()
                            + " sem próxima interseção, porém não chegou ao destino. Verificar rota.");
                    filaVeiculos.enfileirar(v); // se quiser manter na fila para investigar
                }
                continue;
            }

            Semaforo s = proxima.getSemaforo();
            TrafficLightState estado = (s != null) ? s.getEstadoAtual() : TrafficLightState.VERDE;

            System.out.println("Veículo " + v.getId() + " -> Próxima: " + proxima.getId() + " | Semáforo: " + estado);

            if (estado == TrafficLightState.VERDE) {
                v.avancar();
                System.out.println("Veículo " + v.getId() + " avançou para " + v.getIntersecaoAtual(grafo).getId());

                // Verifica se chegou ao destino após avançar
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
