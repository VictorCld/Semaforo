package com.example.simulation.datastructure;

import com.example.simulation.graph.Rua;
import com.example.simulation.graph.Intersecao;

public class LinkedList<T> {
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

  public void remove(long destino){
    if(head == null) return;

    if(((Rua) head.data).destino == destino){
      head = head.next;
      return;
    }

    Node<T> actual = head;
    while(actual.next != null){
      if(((Rua) actual.next.data).destino == destino){
        actual.next = actual.next.next;
        return;
      }
      actual = actual.next;
    }
  }

  public void removerVertice(long id){
    if(head == null) return;

    if(((Intersecao) head.data).id == id){
      head = head.next;
      return;
    }

    Node<T> actual = head;
    while(actual.next != null){
      if(((Intersecao) actual.next.data).id == id){
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

  //ORIGEM DO CAMINHO
  public T getPrimeiro() {
    return head != null ? head.data : null;
  }

  //CAMINHO QUE O VEICULO VAI
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

  public void print() {
    Node<T> actual = head;
    while (actual != null) {
      System.out.print(actual.data + " -> ");
      actual = actual.next;
    }
    System.out.println("null");
  }
}