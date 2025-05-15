package com.example.model;

import java.io.Serializable;

import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;

public class Veiculo implements Serializable {
    private static final long serialVersionUID = 1L;
    private long id;
    private LinkedList<Integer> caminho;
    private int posicaoAtual; // índice, não valor do nó
    private boolean chegou = false;

    public Veiculo(long id, LinkedList<Integer> caminho) {
        this.id = id;
        this.caminho = caminho;
        this.posicaoAtual = 0; // começa no índice 0
    }

    public Intersecao getProximaIntersecao(Grafo grafo) {
        if (posicaoAtual + 1 >= caminho.getSize()) {
            return null;
        }
        Integer proximaIntersecaoId = caminho.get(posicaoAtual + 1);
        if (proximaIntersecaoId == null) {
            return null;
        }
        return grafo.obterIntersecaoPorId(proximaIntersecaoId);
    }

    public Integer getOrigem() {
        return caminho.getPrimeiro();
    }

    public Integer getDestino() {
        if (caminho.getSize() > 0) {
            return caminho.get(caminho.getSize() - 1);
        }
        return null;
    }

    public Integer getProximo() {
        int proximoIndice = posicaoAtual + 1;
        if (proximoIndice < caminho.getSize()) {
            return caminho.get(proximoIndice);
        }
        return null;
    }

    public void avancar() {
        if (chegou)
            return;

        if (posicaoAtual >= caminho.getSize() - 1) {
            chegou = true;
            System.out.println("Veículo " + id + " chegou ao destino.");
            return;
        }

        posicaoAtual++;
    }

    public LinkedList<Integer> getCaminho() {
        return caminho;
    }

    public boolean chegouAoDestino() {
        return posicaoAtual == caminho.getSize() - 1;
    }

    public boolean estaNoDestino() {
        return posicaoAtual == caminho.getSize() - 1;
    }

    public Intersecao getIntersecaoAtual(Grafo grafo) {
        if (posicaoAtual < caminho.getSize()) {
            return grafo.obterIntersecaoPorId(caminho.get(posicaoAtual));
        }
        return null;
    }

    public int getPosicaoAtual() {
        return posicaoAtual;
    }

    public long getId() {
        return id;
    }
}
