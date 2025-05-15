package com.example;

import com.example.CidadeJson.ImportadorDeGrafo;
import com.example.model.Semaforo;
import com.example.model.Simulacao;
import com.example.model.Veiculo;
import com.example.simulation.datastructure.Fila;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;
import com.example.simulation.traffic.Roteirizador;
import com.example.simulation.traffic.semaforo.ControladorSemaforos;
import com.example.simulation.traffic.semaforo.GeradorDeSemaforos;
import com.example.simulation.traffic.semaforo.TrafficLightState;
import com.example.simulation.traffic.veiculo.GeradorVeiculos;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TrabalhoSekeffApplication {

	public static void main(String[] args) {

		// ===================teste grafo========================
		/*
		 * try {
		 * Grafo grafo = ImportadorDeGrafo.importarDeArquivoUnico(
		 * "C:\\Users\\victo\\Downloads\\trabalho-sekeff-main\\trabalho-sekeff-main\\src\\main\\java\\com\\example\\CidadeJson\\CentroTeresinaPiauíBrazil.json"
		 * );
		 * grafo.printGraph();
		 * //GeradorDeSemaforos geradorDeSemaforos = new GeradorDeSemaforos();
		 * //geradorDeSemaforos.configurarSemaforos(grafo);
		 * } catch (Exception e) {
		 * e.printStackTrace();
		 * }
		 */
		// ====================================================

		// ====================teste carros==========================
		/*
		 * Grafo grafo = null;
		 * try {
		 * grafo = ImportadorDeGrafo.importarDeArquivoUnico(
		 * "C:\\Users\\victo\\Downloads\\trabalho-sekeff-main\\trabalho-sekeff-main\\src\\main\\java\\com\\example\\CidadeJson\\CentroTeresinaPiauíBrazil.json"
		 * );
		 * } catch (Exception e) {
		 * e.printStackTrace();
		 * return;
		 * }
		 * 
		 * GeradorVeiculos gerador = new GeradorVeiculos(grafo);
		 * 
		 * // 3. Cria uma fila de teste
		 * Fila<Veiculo> filaTeste = new Fila<>();
		 * 
		 * // 4. Gera N veículos de teste
		 * int veiculosParaTestar = 5;
		 * gerador.gerarMultiplosVeiculos(veiculosParaTestar, filaTeste); // modo padrão
		 * 
		 * // 5. Imprime os veículos e seus caminhos
		 * System.out.println("=== Veículos Gerados ===");
		 * for (int i = 0; i < veiculosParaTestar; i++) {
		 * Veiculo v = filaTeste.desenfileirar();
		 * System.out.println("Veículo ID: " + v.getId());
		 * System.out.println("Origem: " + v.getOrigem());
		 * System.out.println("Destino: " + v.getDestino());
		 * System.out.print("Caminho: ");
		 * for (Integer interId : v.getCaminho()) {
		 * System.out.print(interId + " -> ");
		 * }
		 * System.out.println("FIM\n");
		 * }
		 * }
		 */

		// ===========================================================

		Grafo grafo = null;
		try {
			grafo = ImportadorDeGrafo.importarDeArquivoUnico(
					"C:\\Users\\victo\\Downloads\\trabalho-sekeff-main\\trabalho-sekeff-main\\src\\main\\java\\com\\example\\CidadeJson\\CentroTeresinaPiauíBrazil.json");
		} catch (Exception e) {
			e.printStackTrace();
			return;
		}

		Simulacao sim = new Simulacao(grafo, new GeradorVeiculos(grafo));
		sim.testarVeiculosESemaforos(3);

	}

}
