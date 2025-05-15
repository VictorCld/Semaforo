package com.example.simulation.datastructure;

import java.io.Serializable;

public class Fila<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public void enfileirar(T dado) {
        Node<T> novo = new Node<>(dado);
        if (tail == null) {
            head = tail = novo;
        } else {
            tail.next = novo;
            tail = novo;
        }
        size++;
    }

    public T desenfileirar() {
        if (head == null)
            return null;
        T dado = head.data;
        head = head.next;
        if (head == null)
            tail = null;
        size--;
        return dado;
    }

    public T get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index);
        }
        Node<T> atual = head;
        int count = 0;
        while (count < index) {
            atual = atual.next;
            count++;
        }
        return atual.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return head == null;
    }

}
