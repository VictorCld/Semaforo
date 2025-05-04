package CidadeJson;


import java.io.File;


import com.example.simulation.graph.Grafo;

import com.fasterxml.jackson.databind.ObjectMapper;


public class ImportadorDeGrafo {

    public static Grafo importarDeArquivoUnico(String pathJson) throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        GrafoJson grafoJson = mapper.readValue(new File(pathJson), GrafoJson.class);

        Grafo grafo = new Grafo();

        for (IntersecaoJson inter : grafoJson.nodes) {
            grafo.addVertice(Long.parseLong(inter.id));
        }

        for (RuaJson rua : grafoJson.edges) {
            long origem = Long.parseLong(rua.source);
            long destino = Long.parseLong(rua.target);
            int tempo = (int) Math.max(1, rua.length / 10); // Tempo simulado
            grafo.addRua(origem, destino, tempo);
        }

        return grafo;
    }
}
