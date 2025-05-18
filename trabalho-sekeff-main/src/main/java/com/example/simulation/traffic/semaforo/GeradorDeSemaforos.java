package com.example.simulation.traffic.semaforo;
import java.io.Serializable;

import com.example.model.Semaforo;
import com.example.simulation.datastructure.Node;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;



public class GeradorDeSemaforos implements Serializable {
    private static final long serialVersionUID = 1L;

    public GeradorDeSemaforos() {}

    public void configurarSemaforos(Grafo grafo, int tempoVerde, int tempoAmarelo, int tempoVermelho) {
        Node<Intersecao> atual = grafo.vertices.head;
        while (atual != null) {
            Intersecao intersecao = atual.data;

            if (intersecao.getSemaforo() == null) {
                Semaforo s = new Semaforo(intersecao, tempoVerde, tempoAmarelo, tempoVermelho);
                intersecao.setSemaforo(s);

                // Criar grupo e adicionar semáforo
                GrupoSemaforo grupo = new GrupoSemaforo();
                grupo.adicionarSemaforo(s);
                intersecao.configurarGrupoSemaforo(grupo);
            }

            atual = atual.next;
        }
    }
}

