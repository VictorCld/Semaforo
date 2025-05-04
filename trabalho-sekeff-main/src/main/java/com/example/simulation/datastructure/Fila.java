package com.example.simulation.datastructure;

public class Fila<T> {
    private Node<T> head;
    private Node<T> tail;

    public void enfileirar(T dado) {
        Node<T> novo = new Node<>(dado);
        if (tail == null) {
            head = tail = novo;
        } else {
            tail.next = novo;
            tail = novo;
        }
    }

    public T desenfileirar() {
        if (head == null) return null;
        T dado = head.data;
        head = head.next;
        if (head == null) tail = null;
        return dado;
    }

    public boolean isEmpty() {
        return head == null;
    }
}
