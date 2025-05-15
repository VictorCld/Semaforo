package com.example.simulation.datastructure;

import java.io.Serializable;

public class HashMap<K, V> implements Serializable {
    private static final long serialVersionUID = 1L;
    private static class Entrada<K, V> {
        K chave;
        V valor;
        Entrada<K, V> proximo;

        Entrada(K chave, V valor) {
            this.chave = chave;
            this.valor = valor;
            this.proximo = null;
        }
    }

    private static final int CAPACIDADE = 100;
    private Entrada<K, V>[] tabela;

    @SuppressWarnings("unchecked")
    public HashMap() {
        tabela = new Entrada[CAPACIDADE];
    }

    private int hash(K chave) {
        return Math.abs(chave.hashCode()) % CAPACIDADE;
    }

    public void put(K chave, V valor) {
        int indice = hash(chave);
        Entrada<K, V> atual = tabela[indice];
        while (atual != null) {
            if (atual.chave.equals(chave)) {
                atual.valor = valor;
                return;
            }
            atual = atual.proximo;
        }
        Entrada<K, V> nova = new Entrada<>(chave, valor);
        nova.proximo = tabela[indice];
        tabela[indice] = nova;
    }

    public V get(K chave) {
        int indice = hash(chave);
        Entrada<K, V> atual = tabela[indice];
        while (atual != null) {
            if (atual.chave.equals(chave)) {
                return atual.valor;
            }
            atual = atual.proximo;
        }
        return null;
    }

    public boolean containsKey(K chave) {
        return get(chave) != null;
    }

    public Iterable<K> keySet() {
        LinkedList<K> chaves = new LinkedList<>();
        for (Entrada<K, V> bucket : tabela) {
            Entrada<K, V> atual = bucket;
            while (atual != null) {
                chaves.add(atual.chave);
                atual = atual.proximo;
            }
        }
        return chaves;
    }
}
