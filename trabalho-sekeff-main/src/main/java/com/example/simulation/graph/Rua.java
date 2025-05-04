package com.example.simulation.graph;

import com.example.model.Veiculo;
import com.example.simulation.datastructure.LinkedList;

public class Rua {
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
}
