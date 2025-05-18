package com.example.simulation.traffic.semaforo;

import java.io.Serializable;


import com.example.model.Semaforo;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.Node;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;

public class ControladorSemaforos implements Serializable {
    private static final long serialVersionUID = 1L;
    private Grafo grafo;

    public ControladorSemaforos(Grafo grafo) {
        this.grafo = grafo;
    }

    public void atualizarSemaforos() {
        Node<Intersecao> atual = grafo.vertices.head;
        while (atual != null) {
            Semaforo semaforo = atual.data.getSemaforo();
            if (semaforo != null) {
                semaforo.attSemaforo();
            }
            atual = atual.next;
        }
    }

    public LinkedList<Semaforo> getSemaforos() {
        LinkedList<Semaforo> semaforos = new LinkedList<>();
        Node<Intersecao> atual = grafo.vertices.head;
        while (atual != null) {
            Semaforo semaforo = atual.data.getSemaforo();
            if (semaforo != null) {
                semaforos.add(semaforo);
            }
            atual = atual.next;
        }
        return semaforos;
    }
}


