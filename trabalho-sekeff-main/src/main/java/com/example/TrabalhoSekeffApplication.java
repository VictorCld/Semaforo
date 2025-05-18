package com.example;


import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.example.CidadeJson.ImportadorDeGrafo;
import com.example.CidadeJson.SemaforoJsonUtils;
import com.example.CidadeJson.TrafficLightJson;
import com.example.model.Simulacao;
import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.graph.Grafo;
import com.example.simulation.traffic.semaforo.DetectorDeSinais;
import com.example.simulation.traffic.veiculo.GeradorVeiculos;

@SpringBootApplication
public class TrabalhoSekeffApplication {

	public static void main(String[] args) {
		// com.example.model.Simulacao.executarSimulacaoTerminal();
		String caminhoJson = "C:\\Users\\victo\\Downloads\\trabalho-sekeff-main\\trabalho-sekeff-main\\src\\main\\java\\com\\example\\CidadeJson\\jsons\\CentroTeresinaPiauíBrazil.json";

		try {
			Grafo grafo = ImportadorDeGrafo.importarDeArquivoUnico(caminhoJson);

			LinkedList<TrafficLightJson> semaforosJson = SemaforoJsonUtils.carregarSemaforosDoJson(caminhoJson);

			DetectorDeSinais.configurarSinaisComBaseNoJson(grafo, semaforosJson, 1, 1, 1);

			Simulacao sim = new Simulacao(grafo, new GeradorVeiculos(grafo));
			sim.testarVeiculosESemaforos(4);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}