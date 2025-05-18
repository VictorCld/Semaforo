package com.example.simulation.traffic.veiculo;

import java.io.Serializable;
import java.util.Random;

import com.example.model.Veiculo;
import com.example.simulation.datastructure.Fila;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.Node;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;
import com.example.simulation.traffic.Dijkstra;

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
        LinkedList<Long> rota = Dijkstra.encontrarMenorCaminho(grafo, origem, destino);

        // Validação: garanta que todos os ids da rota existem no grafo
        boolean rotaValida = true;
        Node<Long> atual = rota.head;
        while (atual != null) {
            if (grafo.obterIntersecaoPorId(atual.data) == null) {
                System.out.println("ERRO: ID " + atual.data + " não existe no grafo! Veículo não será criado.");
                rotaValida = false;
                break;
            }
            atual = atual.next;
        }

        if (!rotaValida || rota.getSize() < 2) {
            // Se a rota não for válida ou tiver menos de 2 pontos, não cria o veículo
            return null;
        }

        Veiculo veiculo = new Veiculo(contador++, rota);
        // Não imprime aqui! O número de simulação ainda não foi definido.
        return veiculo;
    }

    public void gerarMultiplosVeiculos(int quantidade, Fila<Veiculo> filaVeiculos) {
        int criados = 0;
        int tentativas = 0;
        int maxTentativas = quantidade * 10; // Limite para evitar loop infinito

        while (criados < quantidade && tentativas < maxTentativas) {
            Veiculo veiculo = gerarVeiculoComRota();
            if (veiculo != null) {
                veiculo.setNumeroSimulacao(criados + 1); // Numeração sequencial a partir de 1
                System.out.printf("[Veículo %d] Rota: %s -> %s\n", veiculo.getNumeroSimulacao(), veiculo.getOrigem(),
                        veiculo.getDestino());
                filaVeiculos.enfileirar(veiculo);
                criados++;
            }
            tentativas++;
        }
        if (criados < quantidade) {
            System.out.println(
                    "Atenção: Apenas " + criados + " veículos foram criados após " + tentativas + " tentativas.");
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
