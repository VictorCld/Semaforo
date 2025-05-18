package com.example.simulation.traffic.semaforo;

import com.example.CidadeJson.TrafficLightJson;
import com.example.simulation.datastructure.LinkedList;

public class FiltroSemaforos {

    public static LinkedList<TrafficLightJson> filtrarSemaforos(LinkedList<TrafficLightJson> pontos) {
        LinkedList<TrafficLightJson> semaforos = new LinkedList<>();

        for (int i = 0; i < pontos.getSize(); i++) {
            TrafficLightJson ponto = pontos.get(i);
            if (ponto.attributes != null && "traffic_signals".equals(ponto.attributes.highway)) {
                semaforos.add(ponto);
            }
        }

        return semaforos;
    }
}