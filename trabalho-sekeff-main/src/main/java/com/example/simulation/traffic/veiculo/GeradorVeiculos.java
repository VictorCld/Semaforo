package com.example.simulation.traffic.veiculo;

import java.io.Serializable;
import java.util.List;
import java.util.Random;

import com.example.model.Veiculo;
import com.example.simulation.datastructure.Fila;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.Node;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;
import com.example.simulation.traffic.Dijkstra;
import com.example.simulation.traffic.Roteirizador;

public class GeradorVeiculos implements Serializable {
    private static final long serialVersionUID = 1L;

    private Grafo grafo;
    private Random random;
    private long contador;

    public GeradorVeiculos(Grafo grafo) {
        this.grafo = grafo;
        this.random = new Random();
        this.contador = 0L;
    }

    public Veiculo gerarVeiculoComRota() {
        Intersecao origem = sortearIntersecao();
        Intersecao destino = sortearIntersecaoDiferente(origem);

        // Supondo que Dijkstra retorna LinkedList<Integer> com IDs das interseções
        LinkedList<Integer> rota = Dijkstra.encontrarMenorCaminho(grafo, origem, destino);

        Veiculo veiculo = new Veiculo(contador++, rota);

        System.out.println("Veículo " + veiculo.getId() + " criado com rota de " + origem.getId() + " até " + destino.getId());
        return veiculo;
    }

    public void gerarMultiplosVeiculos(int quantidade, Fila<Veiculo> filaVeiculos) {
        for (int i = 0; i < quantidade; i++) {
            Veiculo veiculo = gerarVeiculoComRota();
            filaVeiculos.enfileirar(veiculo);
        }
    }

    private Intersecao sortearIntersecao() {
        int tamanho = grafo.vertices.tamanho(); // usa size() do LinkedList
        int indice = random.nextInt(tamanho);
        Node<Intersecao> atual = grafo.vertices.head;
        for (int i = 0; i < indice; i++) {
            atual = atual.next;
        }
        return atual.data;
    }

    private Intersecao sortearIntersecaoDiferente(Intersecao diferenteDe) {
        Intersecao candidato;
        do {
            candidato = sortearIntersecao();
        } while (candidato.getId() == diferenteDe.getId());
        return candidato;
    }
}
