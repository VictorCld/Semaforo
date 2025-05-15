package com.example.simulation.graph;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.Node;

public class Grafo implements Serializable {
    private static final long serialVersionUID = 1L;
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

    public int size() {
        return vertices.tamanho();
    }

    public void removeVertice(long id) {
        Intersecao vRemover = obterVertice(id);

        if (vRemover == null)
            return;

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
        if (intersecaoOrigem == null)
            return null;

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

    public Intersecao obterIntersecaoPorId(long id) {
        for (Intersecao intersecao : vertices) {
            if (intersecao.getId() == id) {
                return intersecao;
            }
        }
        return null; // Return null if no intersection with the given ID is found
    }

    public void printGraph() {
        Node<Intersecao> atual = vertices.head;
        while (atual != null) {
            Intersecao intersecao = atual.data;
            System.out.println("Interseção " + intersecao.getId() + ":");

            Node<Rua> ruaAtual = intersecao.adjacentes.head;
            if (ruaAtual == null) {
                System.out.println("   Nenhuma rua conectada.");
            }

            while (ruaAtual != null) {
                Rua rua = ruaAtual.data;
                System.out.println("   -> Para: " + rua.destino + " | Tempo: " + rua.getTempoTravessia() + "s");
                ruaAtual = ruaAtual.next;
            }
            System.out.println(); // Linha em branco para separar interseções
            atual = atual.next;
        }
    }

    public List<Integer> getIdsIntersecoes() {
        List<Integer> ids = new ArrayList<>();
        Node<Intersecao> atual = vertices.head;
        while (atual != null) {
            ids.add((int) atual.data.getId()); // Supondo que o id seja int
            atual = atual.next;
        }
        return ids;
    }

    public LinkedList<Rua> obterArestasDe(Intersecao intersecao) {
        return intersecao.adjacentes;
    }
}
