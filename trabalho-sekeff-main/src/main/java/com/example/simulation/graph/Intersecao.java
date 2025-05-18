package com.example.simulation.graph;

import java.io.Serializable;

import com.example.model.Semaforo;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.traffic.semaforo.GrupoSemaforo;

public class Intersecao implements Serializable {
    private static final long serialVersionUID = 1L;

    private long id;
    public LinkedList<Rua> adjacentes;
    public GrupoSemaforo gs;
    private Semaforo semaforo;
    private Grafo grafo;

    public double latitude;
    public double longitude;

    public Intersecao(long id) {
        this.id = id;
        this.adjacentes = new LinkedList<>();
        this.gs = null;
        this.semaforo = null;
    }

    public Intersecao(long id, double latitude, double longitude) {
        this(id);
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Integer getProximo() {
        if (adjacentes != null && adjacentes.head != null) {
            Rua proximaRua = adjacentes.head.data;
            if (proximaRua != null) {
                return (int) proximaRua.destino;
            }
        }
        return null;
    }

    public Integer getProximaIntersecaoId() {
        return getProximo();
    }

    public long getId() {
        return id;
    }

    @Override
    public String toString() {
        return "Intersecao " + id;
    }

    public Semaforo getSemaforo() {
        return semaforo;
    }

    public void setSemaforo(Semaforo semaforo) {
        this.semaforo = semaforo;
    }

    public boolean possuiSemaforo() {
        return semaforo != null;
    }

    public GrupoSemaforo getGrupoSemaforo() {
        return gs;
    }

    public void configurarGrupoSemaforo(GrupoSemaforo gs) {
        this.gs = gs;
    }

    public void setGrafo(Grafo grafo) {
        this.grafo = grafo;
    }

    public Grafo getGrafo() {
        return this.grafo;
    }
}
