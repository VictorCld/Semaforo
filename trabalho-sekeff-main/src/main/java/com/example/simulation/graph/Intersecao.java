package com.example.simulation.graph;

import java.io.Serializable;

import com.example.model.Semaforo;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.Node;
import com.example.simulation.traffic.semaforo.GrupoSemaforo;
import com.example.simulation.traffic.semaforo.TrafficLightState;

public class Intersecao implements Serializable {
    private static final long serialVersionUID = 1L;

    public long id;
    public LinkedList<Rua> adjacentes;
    public GrupoSemaforo gs;
    private Semaforo semaforo;

    public Intersecao(long id) {
        this.id = id;
        this.adjacentes = new LinkedList<>();
        this.gs = new GrupoSemaforo(2, 1, 1, 1);

        if (gs.semaforos.head != null) {
            this.semaforo = gs.semaforos.head.data;
        }
    }

    public void mudar() {
        this.gs.click();
    }

    public void alternarEstadosOpostos() {
        if (gs.semaforos.head == null) {
            throw new IllegalArgumentException("O grupo precisa ter pelo menos 2 semáforos.");
        }
        Node<Semaforo> primeiro = gs.semaforos.head;
        Node<Semaforo> segundo = primeiro.next;

        if (primeiro != null && segundo != null) {
            if (primeiro.data.getEstadoAtual() == TrafficLightState.VERMELHO) {
                primeiro.data.setEstadoAtual(TrafficLightState.VERDE);
                segundo.data.setEstadoAtual(TrafficLightState.VERMELHO);
            } else if (primeiro.data.getEstadoAtual() == TrafficLightState.VERDE) {
                primeiro.data.setEstadoAtual(TrafficLightState.AMARELO);
                segundo.data.setEstadoAtual(TrafficLightState.VERMELHO);
            } else if (primeiro.data.getEstadoAtual() == TrafficLightState.AMARELO) {
                primeiro.data.setEstadoAtual(TrafficLightState.VERMELHO);
                segundo.data.setEstadoAtual(TrafficLightState.VERDE);
            }
        }
    }

    public Integer getProximo() {
        if (adjacentes != null && adjacentes.head != null) {
            Rua proximaRua = adjacentes.head.data;
            if (proximaRua != null) {
                return (int) proximaRua.destino;
            }
        }
        return null;
    }

    public Integer getProximaIntersecaoId() {
        return getProximo();
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Intersecao " + id;
    }

    public Semaforo getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(Semaforo semaforo) {
        this.semaforo = semaforo;
    }
}
