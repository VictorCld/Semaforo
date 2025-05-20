package com.example.simulation.traffic.semaforo;

import java.io.Serializable;

import com.example.model.Semaforo;
import com.example.simulation.datastructure.LinkedList;

public class GrupoSemaforo implements Serializable {
    private static final long serialVersionUID = 1L;

    public LinkedList<Semaforo> semaforos = new LinkedList<>();

    public GrupoSemaforo() {}

    
    public void adicionarSemaforo(Semaforo s) {
        semaforos.add(s);
    }

    
    public int quantidade() {
        return semaforos.tamanho();
    }
}
