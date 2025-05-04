package com.example.simulation.traffic;

import com.example.simulation.datastructure.Node;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;

public class ControladorSemaforos {
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
}
