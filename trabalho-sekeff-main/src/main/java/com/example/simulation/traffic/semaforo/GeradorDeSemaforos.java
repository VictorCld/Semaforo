package com.example.simulation.traffic.semaforo;
import java.io.Serializable;

import com.example.simulation.graph.Grafo;



public class GeradorDeSemaforos implements Serializable {
    private static final long serialVersionUID = 1L;

    public GeradorDeSemaforos() {
       
    }

    public void configurarSemaforos(Grafo grafo) {
        GrupoSemaforo gs = new GrupoSemaforo();
        gs.adicionarSemaforos(grafo, 3, 1, 4);
    }
}
