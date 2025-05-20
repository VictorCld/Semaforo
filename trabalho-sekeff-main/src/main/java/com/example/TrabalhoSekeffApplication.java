package com.example;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.CidadeJson.ImportadorDeGrafo;
import com.example.CidadeJson.SemaforoJsonUtils;
import com.example.CidadeJson.TrafficLightJson;
import com.example.gui.SimulacaoGUI;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.graph.Grafo;
import com.example.simulation.traffic.semaforo.DetectorDeSinais;
import com.example.simulation.traffic.veiculo.GeradorVeiculos;

@SpringBootApplication
public class TrabalhoSekeffApplication {

	public static void main(String[] args) {
		String caminhoJson = "C:\\Users\\victo\\Documents\\GitHub\\Semaforo\\trabalho-sekeff-main\\src\\main\\java\\com\\example\\CidadeJson\\jsons\\CentroTeresinaPiauíBrazil.json";

        try {
            Grafo grafo = ImportadorDeGrafo.importarDeArquivoUnico(caminhoJson);

            for (com.example.simulation.graph.Intersecao inter : grafo.vertices) {
                inter.setGrafo(grafo);
            }

            LinkedList<TrafficLightJson> semaforosJson = SemaforoJsonUtils.carregarSemaforosDoJson(caminhoJson);

            DetectorDeSinais.configurarSinaisComBaseNoJson(grafo, semaforosJson, 1, 1, 1);

            GeradorVeiculos geradorVeiculos = new GeradorVeiculos(grafo);

        
            new SimulacaoGUI(grafo, geradorVeiculos);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}