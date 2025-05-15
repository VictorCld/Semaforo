package com.example.model;

import java.io.Serializable;

import com.example.simulation.traffic.semaforo.TrafficLightState;

public class Semaforo implements Serializable {
    private static final long serialVersionUID = 1L;

    private TrafficLightState estadoAtual;
    private int tempoVerde;
    private int tempoAmarelo;
    private int tempoVermelho;
    private int tempoAtual;

    public void setEstadoAtual(TrafficLightState estadoAtual) {
        this.estadoAtual = estadoAtual;
        this.tempoAtual = 0;
    }

    public Semaforo(int tempoVerde, int tempoAmarelo, int tempoVermelho) {
        this.estadoAtual = TrafficLightState.VERMELHO;
        this.tempoVerde = tempoVerde;
        this.tempoAmarelo = tempoAmarelo;
        this.tempoVermelho = tempoVermelho;
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
        return "Semáforo está " + estadoAtual;
    }

    public boolean podeAvancar(){
        return true;
    }

}
