package com.example.simulation.datastructure;

import java.io.Serializable;

public class HashSet<T> implements Serializable {
    private static final long serialVersionUID = 1L;
     private static final int CAPACIDADE = 100;
    private No<T>[] tabela;

    @SuppressWarnings("unchecked")
    public HashSet() {
        tabela = new No[CAPACIDADE];
    }

    private static class No<T> {
        T valor;
        No<T> proximo;

        No(T valor) {
            this.valor = valor;
            this.proximo = null;
        }
    }

    private int hash(T valor) {
        return Math.abs(valor.hashCode()) % CAPACIDADE;
    }

    public void adicionar(T valor) {
        int indice = hash(valor);
        No<T> atual = tabela[indice];
        while (atual != null) {
            if (atual.valor.equals(valor)) {
                return; // Já existe
            }
            atual = atual.proximo;
        }
        No<T> novo = new No<>(valor);
        novo.proximo = tabela[indice];
        tabela[indice] = novo;
    }

    public boolean contem(T valor) {
        int indice = hash(valor);
        No<T> atual = tabela[indice];
        while (atual != null) {
            if (atual.valor.equals(valor)) {
                return true;
            }
            atual = atual.proximo;
        }
        return false;
    }

    public int tamanho() {
        int count = 0;
        for (No<T> no : tabela) {
            No<T> atual = no;
            while (atual != null) {
                count++;
                atual = atual.proximo;
            }
        }
        return count;
    }
}
