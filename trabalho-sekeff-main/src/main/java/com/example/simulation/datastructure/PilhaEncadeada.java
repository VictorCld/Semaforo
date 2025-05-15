package com.example.simulation.datastructure;

import java.io.Serializable;

public class PilhaEncadeada<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private No<T> topo;

    public PilhaEncadeada() {
        this.topo = null;
    }

    public void empilhar(T elemento) {
        No<T> novo = new No<>(elemento);
        novo.proximo = topo;
        topo = novo;
    }

    public T desempilhar() {
        if (estaVazia()) {
            return null;
        }
        T dado = topo.dado;
        topo = topo.proximo;
        return dado;
    }

    public boolean estaVazia() {
        return topo == null;
    }

    public T getTopo() {
        if (topo != null) {
            return topo.dado;
        }
        return null;
    }

    private static class No<T> {
        T dado;
        No<T> proximo;

        No(T dado) {
            this.dado = dado;
            this.proximo = null;
        }
    }
}
