package com.example.simulation.graph;

import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.Node;

public class Grafo {
    public LinkedList<Intersecao> vertices;

    public Grafo() {
        this.vertices = new LinkedList<Intersecao>();
    }

    public void addRua(long origem, long destino, int tempoTravessia) {
        Intersecao origemIntersecao = obterVertice(origem);
        Intersecao destinoIntersecao = obterVertice(destino);
    
        if (origemIntersecao == null || destinoIntersecao == null) {
            throw new IllegalArgumentException("Interseção de origem ou destino não encontrada.");
        }
    
        Rua rua = new Rua(origem, destino, tempoTravessia);
        origemIntersecao.adjacentes.add(rua);
    }

    public void addVertice(long id) {
        if (!containsVertice(id)) {
            vertices.add(new Intersecao(id));
        }
    }

    public void removeVertice(long id){
        Intersecao vRemover = obterVertice(id);

        if (vRemover == null) return;

        Node<Intersecao> actual = vertices.head;
        while (actual != null) {
            actual.data.adjacentes.remove(id);
            actual = actual.next;
        }

        vertices.removerVertice(id);
    }

    public Intersecao obterVertice(long id) {
        Node<Intersecao> actual = vertices.head;
        while (actual != null) {
            if (actual.data.id == id) {
                return actual.data;
            }
            actual = actual.next;
        }
        return null;
    }

    public Rua getRua(long origem, long destino) {
        Intersecao intersecaoOrigem = obterVertice(origem);
        if (intersecaoOrigem == null) return null;
    
        Node<Rua> atual = intersecaoOrigem.adjacentes.head;
        while (atual != null) {
            if (atual.data.destino == destino) {
                return atual.data;
            }
            atual = atual.next;
        }
    
        return null;
    }

    public boolean containsVertice(long id) {
        Node<Intersecao> actual = vertices.head;
        while (actual != null) {
            if (actual.data.id == id) {
                return true;
            }
            actual = actual.next;
        }
        return false;
    }


    public void printGraph() {
        Node<Intersecao> atual = vertices.head;
        while (atual != null) {
            System.out.print("Vértice " + atual.data.id + " -> ");
            atual.data.adjacentes.print();
            atual = atual.next;
        }
    }
}
