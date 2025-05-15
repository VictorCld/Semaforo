package com.example.simulation.traffic.veiculo;

import java.io.Serializable;

import com.example.model.Veiculo;

public class VeiculoProgramado implements Serializable {
    private static final long serialVersionUID = 1L;
    public final int tempoEntrada;
    public final Veiculo veiculo;

    public VeiculoProgramado(int tempoEntrada, Veiculo veiculo) {
        this.tempoEntrada = tempoEntrada;
        this.veiculo = veiculo;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }
}
