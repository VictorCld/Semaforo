package com.example.simulation.traffic;
import com.example.simulation.graph.Grafo;



public class GeradorDeSemaforos {

    public GeradorDeSemaforos() {
       
    }

    public void configurarSemaforos(Grafo grafo) {
        GrupoSemaforo gs = new GrupoSemaforo();
        gs.adicionarSemaforos(grafo, 3, 1, 4);
    }
}
