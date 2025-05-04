package com.example.model;


import com.example.simulation.graph.Grafo;

import com.example.simulation.traffic.ControladorSemaforos;


public class Simulacao {
    public Grafo grafo;
    public ControladorSemaforos controlador;

    public Simulacao() {
        this.grafo = new Grafo();
        this.controlador = new ControladorSemaforos(grafo);
    }

}
