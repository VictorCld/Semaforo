package com.example;

import javax.swing.*;
import java.awt.*;

import com.example.simulation.datastructure.LinkedList;
import com.example.simulation.datastructure.HashSet;
import com.example.simulation.graph.Grafo;
import com.example.simulation.graph.Intersecao;
import com.example.simulation.graph.Rua;
import com.example.model.Semaforo;
import com.example.simulation.traffic.semaforo.TrafficLightState;
import com.example.model.Veiculo;

public class VisualizacaoSimulador extends JPanel {
    private Grafo grafo;
    private LinkedList<Veiculo> veiculos; // Lista dos veículos ativos

    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;

    public VisualizacaoSimulador(Grafo grafo) {
        this.grafo = grafo;
        this.veiculos = new LinkedList<>();
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
    }

    // Permite atualizar a lista de veículos a cada ciclo
    public void setVeiculos(LinkedList<Veiculo> veiculos) {
        this.veiculos = veiculos;
    }

    private Point converterCoordenadas(double latitude, double longitude, double minLat, double maxLat, double minLon,
            double maxLon) {
        int x = (int) ((longitude - minLon) / (maxLon - minLon) * (WIDTH - 100)) + 50;
        int y = (int) ((maxLat - latitude) / (maxLat - minLat) * (HEIGHT - 100)) + 50;
        return new Point(x, y);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Descubra os limites das coordenadas
        double minLat = Double.MAX_VALUE, maxLat = -Double.MAX_VALUE;
        double minLon = Double.MAX_VALUE, maxLon = -Double.MAX_VALUE;
        for (int i = 0; i < grafo.vertices.tamanho(); i++) {
            Intersecao inter = grafo.vertices.get(i);
            if (inter.latitude < minLat)
                minLat = inter.latitude;
            if (inter.latitude > maxLat)
                maxLat = inter.latitude;
            if (inter.longitude < minLon)
                minLon = inter.longitude;
            if (inter.longitude > maxLon)
                maxLon = inter.longitude;
        }

        // Desenha ruas (arestas)
        for (int i = 0; i < grafo.vertices.tamanho(); i++) {
            Intersecao origem = grafo.vertices.get(i);
            Point p1 = converterCoordenadas(origem.latitude, origem.longitude, minLat, maxLat, minLon, maxLon);
            for (int j = 0; j < origem.adjacentes.tamanho(); j++) {
                Rua rua = origem.adjacentes.get(j);
                Intersecao destino = grafo.obterIntersecaoPorId(rua.destino);
                if (destino != null) {
                    Point p2 = converterCoordenadas(destino.latitude, destino.longitude, minLat, maxLat, minLon,
                            maxLon);
                    g.setColor(Color.GRAY);
                    g.drawLine(p1.x, p1.y, p2.x, p2.y);
                }
            }
        }

        // Descobre quais interseções têm veículos (para mostrar apenas os semáforos
        // relevantes)
        HashSet<Long> intersecoesComVeiculos = new HashSet<>();
        for (int i = 0; i < veiculos.tamanho(); i++) {
            Veiculo v = veiculos.get(i);
            Long idInter = v.getIntersecaoAtualId(grafo);
            if (idInter != null)
                intersecoesComVeiculos.adicionar(idInter);
            Long proxId = v.getProximaIntersecaoId(grafo);
            if (proxId != null)
                intersecoesComVeiculos.adicionar(proxId);
        }

        // Desenha interseções (nós) e semáforos apenas onde há veículos
        for (int i = 0; i < grafo.vertices.tamanho(); i++) {
            Intersecao inter = grafo.vertices.get(i);
            Point p = converterCoordenadas(inter.latitude, inter.longitude, minLat, maxLat, minLon, maxLon);
            g.setColor(Color.BLACK);
            g.fillOval(p.x - 5, p.y - 5, 12, 12);

            // Desenha o semáforo só se algum veículo vai passar por aqui
            if (intersecoesComVeiculos.contem(inter.getId())) {
                Semaforo semaforo = inter.getSemaforo();
                if (semaforo != null) {
                    TrafficLightState estado = semaforo.getEstadoAtual();
                    Color cor = Color.RED;
                    if (estado == TrafficLightState.VERDE)
                        cor = Color.GREEN;
                    else if (estado == TrafficLightState.AMARELO)
                        cor = Color.YELLOW;
                    g.setColor(cor);
                    g.fillOval(p.x - 3, p.y - 18, 10, 10);
                }
            }
        }

        // Desenha os veículos (círculos azuis)
        for (int i = 0; i < veiculos.tamanho(); i++) {
            Veiculo v = veiculos.get(i);
            Long idInter = v.getIntersecaoAtualId(grafo);
            if (idInter != null) {
                Intersecao inter = grafo.obterIntersecaoPorId(idInter);
                if (inter != null) {
                    Point p = converterCoordenadas(inter.latitude, inter.longitude, minLat, maxLat, minLon, maxLon);
                    if (v.chegouAoDestino()) {
                        g.setColor(Color.GREEN);
                    } else {
                        g.setColor(Color.BLUE);
                    }
                    g.fillOval(p.x - 6, p.y - 6, 14, 14);
                }
            }
        }
    }

    public static VisualizacaoSimulador mostrar(Grafo grafo) {
        JFrame frame = new JFrame("Visualização do Tráfego Urbano");
        VisualizacaoSimulador painel = new VisualizacaoSimulador(grafo);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.add(painel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        return painel;
    }
}