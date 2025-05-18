package com.example.model;

import java.io.Serializable;
import java.util.Random;

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
}