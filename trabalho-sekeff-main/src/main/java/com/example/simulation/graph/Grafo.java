package com.example.simulation.graph;

import java.io.Serializable;

import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.Node;
import com.example.model.Semaforo;

public class Grafo implements Serializable {
    private static final long serialVersionUID = 1L;
    public LinkedList<Intersecao> vertices;

    public Grafo() {
        this.vertices = new LinkedList<Intersecao>();
    }

   
    public void addVertice(long id, double latitude, double longitude) {
        if (!containsVertice(id)) {
            Intersecao intersecao = new Intersecao(id, latitude, longitude);
            vertices.add(intersecao);
        }
    }

    
    public void addVertice(Intersecao intersecao) {
        vertices.add(intersecao);
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

    public int size() {
        return vertices.tamanho();
    }

    public void removeVertice(long id) {
        Intersecao vRemover = obterVertice(id);

        if (vRemover == null)
            return;

        Node<Intersecao> atual = vertices.head;
        while (atual != null) {
            atual.data.adjacentes.remove(id);
            atual = atual.next;
        }

        vertices.removerVertice(id);
    }

    public Intersecao obterVertice(long id) {
        Node<Intersecao> atual = vertices.head;
        while (atual != null) {
            if (atual.data.getId() == id) {
                return atual.data;
            }
            atual = atual.next;
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
        Node<Intersecao> atual = vertices.head;
        while (atual != null) {
            if (atual.data.getId() == id) {
                return true;
            }
            atual = atual.next;
        }
        return false;
    }

    public Intersecao obterIntersecaoPorId(long id) {
        return obterVertice(id);
    }

    public void printGraph() {
        Node<Intersecao> atual = vertices.head;
        while (atual != null) {
            Intersecao intersecao = atual.data;
            System.out.println("Interseção " + intersecao.getId() + " (lat: " + intersecao.latitude + ", lon: "
                    + intersecao.longitude + "):");

            Node<Rua> ruaAtual = intersecao.adjacentes.head;
            if (ruaAtual == null) {
                System.out.println("   Nenhuma rua conectada.");
            }

            while (ruaAtual != null) {
                Rua rua = ruaAtual.data;
                System.out.println("   -> Para: " + rua.destino + " | Tempo: " + rua.getTempoTravessia() + "s");
                ruaAtual = ruaAtual.next;
            }
            System.out.println();
            atual = atual.next;
        }
    }

    public LinkedList<Long> getIdsIntersecoes() {
        LinkedList<Long> ids = new LinkedList<>();
        Node<Intersecao> atual = vertices.head;
        while (atual != null) {
            ids.add(atual.data.getId());
            atual = atual.next;
        }
        return ids;
    }

    public LinkedList<Rua> obterArestasDe(Intersecao intersecao) {
        return intersecao.adjacentes;
    }

    public LinkedList<Semaforo> getSemaforos() {
        LinkedList<Semaforo> semaforos = new LinkedList<>();
        Node<Intersecao> atual = vertices.head;
        while (atual != null) {
            Intersecao intersecao = atual.data;
            if (intersecao.getSemaforo() != null) {
                semaforos.add(intersecao.getSemaforo());
            }
            atual = atual.next;
        }
        return semaforos;
    }
}
