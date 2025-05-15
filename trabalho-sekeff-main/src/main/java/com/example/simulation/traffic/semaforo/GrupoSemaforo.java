package com.example.simulation.traffic.semaforo;

import java.io.Serializable;

import com.example.model.Semaforo;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.Node;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;
import com.example.simulation.graph.Rua;

public class GrupoSemaforo implements Serializable {
    private static final long serialVersionUID = 1L;
    public LinkedList<Semaforo> semaforos = new LinkedList<Semaforo>();
    long id;
    private int tempoVerde;
    private int tempoAmarelo;
    private int tempoVermelho;
    private int quantidade;

    public GrupoSemaforo() {
    }

    public GrupoSemaforo(int tempoVerde, int tempoAmarelo, int tempoVermelho, int quantidade) {
        this.tempoVerde = tempoVerde;
        this.tempoAmarelo = tempoAmarelo;
        this.tempoVermelho = tempoVermelho;
        this.quantidade = quantidade;
        this.semaforos = new LinkedList<>();

        // Cria os semáforos e adiciona na lista
        for (int i = 0; i < quantidade; i++) {
            Semaforo s = new Semaforo(tempoVerde, tempoAmarelo, tempoVermelho);
            semaforos.add(s);
        }
    }

    public void click() {
        Node<Semaforo> actual = semaforos.head;
        while (actual != null) {
            actual.data.attSemaforo();
            actual = actual.next;
        }
    }

    public void adicionarSemaforos(Grafo grafo, int tempoVerde, int tempoAmarelo, int tempoVermelho) {
        Node<Intersecao> atual = grafo.vertices.head;

        while (atual != null) {
            Intersecao intersecao = atual.data;

            // Só adiciona se ainda não tiver grupo de semáforo
            if (intersecao.gs == null || intersecao.gs.semaforos.isEmpty()) {
                long entradas = contarEntradas(intersecao.id, grafo);

                if (entradas > 2) {
                    intersecao.gs = new GrupoSemaforo(tempoVerde, tempoAmarelo, tempoVermelho, (int) entradas);
                }
            }

            atual = atual.next;
        }
    }

    private static long contarEntradas(long idIntersecao, Grafo grafo) {
        int entradas = 0;
        Node<Intersecao> atual = grafo.vertices.head;

        while (atual != null) {
            LinkedList<Rua> ruas = atual.data.adjacentes;
            Node<Rua> ruaAtual = ruas.head;

            while (ruaAtual != null) {
                if (ruaAtual.data.destino == idIntersecao) {
                    entradas++;
                }
                ruaAtual = ruaAtual.next;
            }

            atual = atual.next;
        }

        return entradas;
    }
}
