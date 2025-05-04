package com.example;


import com.example.simulation.graph.Grafo;
import com.example.simulation.traffic.GeradorDeSemaforos;

import CidadeJson.ImportadorDeGrafo;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrabalhoSekeffApplication {

	public static void main(String[] args) {


		try {
			Grafo grafo = ImportadorDeGrafo.importarDeArquivoUnico("C:\\Users\\victo\\Downloads\\trabalho-sekeff-main\\trabalho-sekeff-main\\src\\main\\java\\CidadeJson\\teresina.json");
			GeradorDeSemaforos geradorDeSemaforos = new GeradorDeSemaforos();
			geradorDeSemaforos.configurarSemaforos(grafo);
		} catch (Exception e) {
			e.printStackTrace();
		}

		
	}

}
