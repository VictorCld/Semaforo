package com.example.model;

import com.example.simulation.datastructure.LinkedList;

public class Veiculo {
    private long id;
    private LinkedList<Integer> caminho;
    private Integer posicaoAtual;

    public Veiculo(long id, LinkedList<Integer> caminho) {
        this.id = id;
        this.caminho = caminho;
        this.posicaoAtual = caminho.getPrimeiro();
    }

    public Integer getProximo() {
        return caminho.getProximo(posicaoAtual);
    }

    public void avancar() {
        posicaoAtual = getProximo();
    }

    public boolean chegouAoDestino() {
        return getProximo() == null;
    }

    public Integer getPosicaoAtual() {
        return posicaoAtual;
    }

    public long getId() {
        return id;
    }

}
