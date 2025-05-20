package com.example.simulation.datastructure;

import com.example.simulation.graph.Rua;

import java.util.Iterator;
import java.io.Serializable;

import com.example.simulation.graph.Intersecao;

public class LinkedList<T> implements Iterable<T>, Serializable {
  private static final long serialVersionUID = 1L;
  public Node<T> head;

  public void add(T data) {
    Node<T> newNode = new Node<>(data);
    if (head == null) {
      head = newNode;
    } else {
      Node<T> actual = head;
      while (actual.next != null) {
        actual = actual.next;
      }
      actual.next = newNode;
    }
  }

  public boolean isEmpty() {
    return head == null;
  }

  public void remove(long destino) {
    if (head == null)
      return;

    if (((Rua) head.data).destino == destino) {
      head = head.next;
      return;
    }

    Node<T> actual = head;
    while (actual.next != null) {
      if (((Rua) actual.next.data).destino == destino) {
        actual.next = actual.next.next;
        return;
      }
      actual = actual.next;
    }
  }

  public void removerVertice(long id) {
    if (head == null)
      return;

    if (((Intersecao) head.data).getId() == id) {
      head = head.next;
      return;
    }

    Node<T> actual = head;
    while (actual.next != null) {
      if (((Intersecao) actual.next.data).getId() == id) {
        actual.next = actual.next.next;
        return;
      }
      actual = actual.next;
    }
  }

  public void removeFirst() {
    if (head != null) {
      head = head.next;
    }
  }

  public boolean contains(T data) {
    Node<T> actual = head;
    int position = 0;
    while (actual != null) {
      if (actual.data.equals(data)) {
        System.out.println("O valor " + data + " foi encontrado na posição " + position);
        return true;
      }
      actual = actual.next;
      position++;
    }
    System.out.println("O valor " + data + " não foi encontrado");
    return false;
  }

  
  public T getPrimeiro() {
    return head != null ? head.data : null;
  }

  
  public T getProximo(T valorAtual) {
    Node<T> atual = head;
    while (atual != null && atual.next != null) {
      if (atual.data.equals(valorAtual)) {
        return atual.next.data;
      }
      atual = atual.next;
    }
    return null;
  }

  public T getUltimo() {
    if (head == null)
      return null;
    Node<T> atual = head;
    while (atual.next != null) {
      atual = atual.next;
    }
    return atual.data;
  }

  public void print() {
    Node<T> actual = head;
    while (actual != null) {
      System.out.print(actual.data + " -> ");
      actual = actual.next;
    }
    System.out.println("null");
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    Node<T> current = head;

    while (current != null) {
      sb.append(current.data); 
      if (current.next != null) {
        sb.append(" -> ");
      }
      current = current.next;
    }

    return sb.toString();
  }

  public T get(int index) {
    if (index < 0 || index >= getSize()) {
      throw new IndexOutOfBoundsException("Index: " + index);
    }
    Node<T> current = head;
    int count = 0;
    while (count < index) {
      current = current.next;
      count++;
    }
    return current.data;
  }

  public int getSize() {
    int tamanho = 0;
    Node<T> atual = head;
    while (atual != null) {
      tamanho++;
      atual = atual.next;
    }
    return tamanho;
  }

  @Override
  public Iterator<T> iterator() {
    return new Iterator<T>() {
      private Node<T> atual = head;

      @Override
      public boolean hasNext() {
        return atual != null;
      }

      @Override
      public T next() {
        if (atual == null) {
          throw new java.util.NoSuchElementException();
        }
        T data = atual.data;
        atual = atual.next;
        return data;
      }
    };
  }

  public int tamanho() {
    int tamanho = 0;
    Node<T> atual = head;
    while (atual != null) {
      tamanho++;
      atual = atual.next;
    }
    return tamanho;
  }

  public T obter(int index) {
    Node<T> atual = head;
    int contador = 0;
    while (atual != null) {
      if (contador == index) {
        return atual.data;
      }
      atual = atual.next;
      contador++;
    }
    throw new IndexOutOfBoundsException("Índice fora do limite: " + index);
  }
}