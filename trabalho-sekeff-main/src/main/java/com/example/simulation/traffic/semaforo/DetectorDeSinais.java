package com.example.simulation.traffic.semaforo;

import com.example.CidadeJson.TrafficLightJson;
import com.example.model.Semaforo;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;

public class DetectorDeSinais {

    public static void configurarSinaisComBaseNoJson(
            Grafo grafo,
            LinkedList<TrafficLightJson> semaforosDoJson,
            int tempoVerde,
            int tempoAmarelo,
            int tempoVermelho) {

        LinkedList<Intersecao> intersecoes = grafo.vertices;

        int totalConfigurados = 0;

        for (int i = 0; i < intersecoes.getSize(); i++) {
            Intersecao inter = intersecoes.get(i);

            for (int j = 0; j < semaforosDoJson.getSize(); j++) {
                TrafficLightJson semaforo = semaforosDoJson.get(j);

                if (coordenadasIguais(inter.latitude, inter.longitude, semaforo.latitude, semaforo.longitude)) {
                    Semaforo s = new Semaforo(inter, tempoVerde, tempoAmarelo, tempoVermelho);
                    GrupoSemaforo gs = new GrupoSemaforo();
                    gs.semaforos.add(s);
                    inter.setSemaforo(s);
                    inter.configurarGrupoSemaforo(gs);

                    totalConfigurados++;
                    break;
                }
            }
        }

        System.out.println("\n===============================================");
        System.out.printf("    Total de sinais configurados: %d\n", totalConfigurados);
        System.out.println("===============================================\n");
    }

    private static boolean coordenadasIguais(double lat1, double lon1, double lat2, double lon2) {
        final double EPSILON = 0.00001;
        return Math.abs(lat1 - lat2) < EPSILON && Math.abs(lon1 - lon2) < EPSILON;
    }
}