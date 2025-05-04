package com.example.simulation.traffic;

import com.example.model.Semaforo;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.Node;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;
import com.example.simulation.graph.Rua;

public class GrupoSemaforo {
    public LinkedList<Semaforo> semaforos = new LinkedList<Semaforo>();
        long id;

        public GrupoSemaforo() {
        }

        public GrupoSemaforo(long qtd, int tempoVerde, int tempoAmarelo, int tempoVermelho) {
            for (int i = 0; i < qtd; i++) {
                Semaforo s = new Semaforo(tempoVerde, tempoAmarelo, tempoVermelho);
                semaforos.add(s);
            }
        }

        public void click(){
            Node<Semaforo> actual = semaforos.head;
            while (actual != null) {
                actual.data.attSemaforo();
                actual = actual.next;
            }
        }

        public void adicionarSemaforos(Grafo grafo, int tempoVerde, int tempoAmarelo, int tempoVermelho) {
        // Percorre todos os vértices
        Node<Intersecao> atual = grafo.vertices.head;

        while (atual != null) {
            Intersecao intersecao = atual.data;

            // Conta quantas ruas têm como destino essa interseção
            long entradas = contarEntradas(intersecao.id, grafo);

            // Se houver mais de uma entrada, adiciona semáforos
            if (entradas > 3) {
                intersecao.gs = new GrupoSemaforo(entradas, tempoVerde, tempoAmarelo, tempoVermelho);
                System.out.println("Grupo de semáforos adicionado na interseção " + intersecao.id + " com " + entradas + " entradas.");
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
