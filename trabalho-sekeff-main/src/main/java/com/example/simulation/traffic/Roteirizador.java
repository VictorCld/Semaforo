package com.example.simulation.traffic;

import java.io.Serializable;

import com.example.simulation.datastructure.Fila;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;

public class Roteirizador implements Serializable {
    private static final long serialVersionUID = 1L;

    private Grafo grafo;

    public Roteirizador(Grafo grafo) {
        this.grafo = grafo;
    }

    public LinkedList<Integer> calcularRota(int origemId, int destinoId) {
        Intersecao origem = grafo.obterIntersecaoPorId(origemId);
        Intersecao destino = grafo.obterIntersecaoPorId(destinoId);

        LinkedList<Integer> caminho = Dijkstra.encontrarMenorCaminho(grafo, origem, destino);
        LinkedList<Integer> rota = new LinkedList<>();

        while (!caminho.isEmpty()) {
            Integer elem = caminho.getPrimeiro();
            caminho.removeFirst();
            rota.add(elem);
        }

        return rota;
    }
}