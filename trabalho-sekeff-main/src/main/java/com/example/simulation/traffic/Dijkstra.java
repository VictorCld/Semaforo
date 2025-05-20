package com.example.simulation.traffic;

import java.io.Serializable;

import com.example.simulation.datastructure.Fila;
import com.example.simulation.datastructure.HashMap;
import com.example.simulation.datastructure.HashSet;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.PilhaEncadeada;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;
import com.example.simulation.graph.Rua;

public class Dijkstra implements Serializable {
    private static final long serialVersionUID = 1L;

    public static LinkedList<Long> encontrarMenorCaminho(Grafo grafo, Intersecao origem, Intersecao destino) {
        HashMap<Intersecao, Integer> distancias = new HashMap<>();
        HashMap<Intersecao, Intersecao> anteriores = new HashMap<>();
        HashSet<Intersecao> visitados = new HashSet<>();

        LinkedList<Intersecao> todosVertices = grafo.vertices;
        for (int i = 0; i < todosVertices.tamanho(); i++) {
            Intersecao v = todosVertices.obter(i);
            distancias.put(v, Integer.MAX_VALUE);
            anteriores.put(v, null);
        }
        distancias.put(origem, 0);

        while (visitados.tamanho() < todosVertices.tamanho()) {
            Intersecao atual = encontrarMenorVertice(distancias, visitados);
            if (atual == null)
                break;
            if (atual.equals(destino))
                break; 

            visitados.adicionar(atual);

            LinkedList<Rua> adjacentes = grafo.obterArestasDe(atual);
            for (int i = 0; i < adjacentes.tamanho(); i++) {
                Rua aresta = adjacentes.obter(i);
                Intersecao vizinho = grafo.obterIntersecaoPorId(aresta.destino);
                if (!visitados.contem(vizinho)) {
                    int novaDist = distancias.get(atual) + aresta.tempoTravessia;
                    if (novaDist < distancias.get(vizinho)) {
                        distancias.put(vizinho, novaDist);
                        anteriores.put(vizinho, atual);
                    }
                }
            }
        }

        Fila<Intersecao> caminhoFila = construirCaminho(anteriores, origem, destino);
        LinkedList<Long> caminhoIds = new LinkedList<>();
        while (!caminhoFila.isEmpty()) {
            Intersecao intersecao = caminhoFila.desenfileirar();
            caminhoIds.add(intersecao.getId()); 
        }
        return caminhoIds;
    }

    private static Intersecao encontrarMenorVertice(HashMap<Intersecao, Integer> distancias,
            HashSet<Intersecao> visitados) {
        Intersecao menor = null;
        int menorDistancia = Integer.MAX_VALUE;
        for (Intersecao v : distancias.keySet()) {
            if (!visitados.contem(v) && distancias.get(v) < menorDistancia) {
                menor = v;
                menorDistancia = distancias.get(v);
            }
        }
        return menor;
    }

    private static Fila<Intersecao> construirCaminho(HashMap<Intersecao, Intersecao> anteriores, Intersecao origem,
            Intersecao destino) {
        PilhaEncadeada<Intersecao> pilha = new PilhaEncadeada<>();
        Intersecao atual = destino;

        
        while (atual != null) {
            pilha.empilhar(atual);
            atual = anteriores.get(atual);
        }

        
        if (pilha.estaVazia() || !pilha.getTopo().equals(origem)) {
            
            return new Fila<>();
        }

        Fila<Intersecao> caminho = new Fila<>();
        while (!pilha.estaVazia()) {
            caminho.enfileirar(pilha.desempilhar());
        }

        return caminho;
    }
}
