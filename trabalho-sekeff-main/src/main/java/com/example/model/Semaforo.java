package com.example.model;

import java.io.Serializable;
import java.util.Random;

import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;
import com.example.simulation.traffic.semaforo.TrafficLightState;

public class Semaforo implements Serializable {
    private static final long serialVersionUID = 1L;

    private TrafficLightState estadoAtual;
    private int tempoVerde;
    private int tempoAmarelo;
    private int tempoVermelho;
    private int tempoAtual;

    private Intersecao intersecao;

    public Semaforo(Intersecao intersecao, int tempoVerde, int tempoAmarelo, int tempoVermelho) {
        this.intersecao = intersecao;
        this.estadoAtual = TrafficLightState.VERMELHO;
        this.tempoVerde = tempoVerde;
        this.tempoAmarelo = tempoAmarelo;
        this.tempoVermelho = tempoVermelho;
        // Começa em tempo aleatório para não sincronizar todos
        this.tempoAtual = new Random().nextInt(tempoVermelho + 1);
    }

    public Semaforo(int tempoVerde, int tempoAmarelo, int tempoVermelho) {
        this(null, tempoVerde, tempoAmarelo, tempoVermelho);
    }

    public Intersecao getIntersecao() {
        return intersecao;
    }

    public void setEstadoAtual(TrafficLightState estadoAtual) {
        this.estadoAtual = estadoAtual;
        this.tempoAtual = 0;
    }

    public void attSemaforo() {
        tempoAtual++;
        switch (estadoAtual) {
            case VERDE:
                if (tempoAtual >= tempoVerde) {
                    estadoAtual = TrafficLightState.AMARELO;
                    tempoAtual = 0;
                }
                break;
            case AMARELO:
                if (tempoAtual >= tempoAmarelo) {
                    estadoAtual = TrafficLightState.VERMELHO;
                    tempoAtual = 0;
                }
                break;
            case VERMELHO:
                if (tempoAtual >= tempoVermelho) {
                    estadoAtual = TrafficLightState.VERDE;
                    tempoAtual = 0;
                }
                break;
        }
    }

    public TrafficLightState getEstadoAtual() {
        return estadoAtual;
    }

    @Override
    public String toString() {
        if (intersecao != null) {
            return "Semáforo na interseção " + intersecao.getId() + ": " + estadoAtual;
        } else {
            return "Semáforo: " + estadoAtual;
        }
    }

    public boolean podeAvancar() {
        return estadoAtual == TrafficLightState.VERDE;
    }

    public void atualizarCicloModelo(int modelo, com.example.simulation.datastructure.Fila<Veiculo> filaVeiculos) {
        switch (modelo) {
            case 1:
                // Modelo 1: Ciclo fixo (exemplo: 10s verde, 3s amarelo, 10s vermelho)
                this.setTempoVerde(10);
                this.setTempoAmarelo(3);
                this.setTempoVermelho(10);
                break;
            case 2:
                // Modelo 2: Otimização do tempo de espera (ajusta verde conforme fila)
                int filaNaIntersecao = contarVeiculosNaIntersecao(this.getIntersecao(), filaVeiculos);
                int tempoVerde = 5 + filaNaIntersecao * 2; // Exemplo: cada veículo na fila aumenta 2s
                if (tempoVerde > 30)
                    tempoVerde = 30; // Limite máximo
                if (tempoVerde < 5)
                    tempoVerde = 5; // Limite mínimo
                this.setTempoVerde(tempoVerde);
                this.setTempoAmarelo(3);
                this.setTempoVermelho(10);
                break;
            case 3:
                // Modelo 3: Otimização do consumo de energia (verde só se houver fila, ciclos
                // longos fora de pico)
                int filaAtual = contarVeiculosNaIntersecao(this.getIntersecao(), filaVeiculos);
                if (filaAtual == 0) {
                    this.setTempoVerde(2); // Verde curto se não há veículos
                } else {
                    this.setTempoVerde(20); // Verde longo se há veículos
                }
                this.setTempoAmarelo(3);
                this.setTempoVermelho(20);
                break;
            default:
                // Padrão: ciclo fixo
                this.setTempoVerde(10);
                this.setTempoAmarelo(3);
                this.setTempoVermelho(10);
        }
    }

    private int contarVeiculosNaIntersecao(Intersecao intersecao,
            com.example.simulation.datastructure.Fila<Veiculo> filaVeiculos) {
        int count = 0;
        for (int i = 0; i < filaVeiculos.size(); i++) {
            Veiculo v = filaVeiculos.get(i);
            // Verifica se a próxima interseção do veículo é igual à deste semáforo
            Intersecao proxima = v.getProximaIntersecao(intersecao.getGrafo());
            if (proxima != null && proxima.equals(intersecao)) {
                count++;
            }
        }
        return count;
    }

    public void setTempoVerde(int tempoVerde) {
        this.tempoVerde = tempoVerde;
    }

    public void setTempoAmarelo(int tempoAmarelo) {
        this.tempoAmarelo = tempoAmarelo;
    }

    public void setTempoVermelho(int tempoVermelho) {
        this.tempoVermelho = tempoVermelho;
    }

}