package com.example.simulation.traffic.semaforo;

import java.io.Serializable;
import java.util.List;

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
            atual.data.gs.click(); // Atualiza os tempos e estados dos semáforos
            atual = atual.next;
        }
    }

    public void alternarSemaforos() {
        Node<Intersecao> atual = grafo.vertices.head;
        while (atual != null) {
            atual.data.alternarEstadosOpostos(); // Alterna verde/amarelo/vermelho
            atual = atual.next;
        }
    }

    public List<Semaforo> getSemaforos() {
        LinkedList<Semaforo> semaforos = new LinkedList<>();
        for (Intersecao intersecao : grafo.vertices) {
            if (intersecao.getSemaforo() != null) {
                semaforos.add(intersecao.getSemaforo());
            }
        }
        // Convert custom LinkedList to java.util.List (ArrayList)
        java.util.List<Semaforo> result = new java.util.ArrayList<>();
        for (Semaforo s : semaforos) {
            result.add(s);
        }
        return result;
    }
}
