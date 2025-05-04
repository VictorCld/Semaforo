package com.example.simulation.graph;

import com.example.model.Semaforo;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.Node;
import com.example.simulation.traffic.GrupoSemaforo;
import com.example.simulation.traffic.TrafficLightState;

public class Intersecao {

    public long id;
    public LinkedList<Rua> adjacentes;
    public GrupoSemaforo gs;


    public Intersecao(long id) {
        this.id = id;
        this.adjacentes = new LinkedList<>();
        this.gs = new GrupoSemaforo(2, 1, 1, 1);
    }

    public void mudar() {
        this.gs.click();
    }

    public void alternarEstadosOpostos(){
        if(gs.semaforos.head == null){
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

}
