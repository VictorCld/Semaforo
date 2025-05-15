package com.example.simulation.graph;

import java.io.Serializable;

import com.example.model.Veiculo;
import com.example.simulation.datastructure.LinkedList;

public class Rua implements Serializable {
    private static final long serialVersionUID = 1L;
    public long origem;
    public long destino;
    public int tempoTravessia;

    public LinkedList<Veiculo> filaVeiculos;

    public Rua(long origem, long destino, int tempo) {
        this.origem = origem;
        this.destino = destino;
        this.tempoTravessia = tempo;
        this.filaVeiculos = new LinkedList<>();
    }

    public String toString(){
        return "(Origem: " + origem + ", Destino: " + destino + ", Tempo de Travessia: " + tempoTravessia + ")";
    }

    public int getTempoTravessia() {
        return tempoTravessia;
    }
}
