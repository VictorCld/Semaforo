package com.example.CidadeJson;

import java.io.File;

import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ImportadorDeGrafo {

    public static Grafo importarDeArquivoUnico(String pathJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new LinkedListModule()); // necessário!

        GrafoJson grafoJson = mapper.readValue(new File(pathJson), GrafoJson.class);

        Grafo grafo = new Grafo();

        for (IntersecaoJson inter : grafoJson.nodes) {
            long id = Long.parseLong(inter.id);
            double lat = inter.latitude;
            double lon = inter.longitude;

            grafo.addVertice(new Intersecao(id, lat, lon));
        }

        for (RuaJson rua : grafoJson.edges) {
            long origem = Long.parseLong(rua.source);
            long destino = Long.parseLong(rua.target);
            int tempo = (int) Math.max(1, rua.length / 10);
            grafo.addRua(origem, destino, tempo);
        }

        return grafo;
    }
}
