package com.example.gui;

import javax.swing.*;
import java.awt.*;
import java.io.File;

import com.example.VisualizacaoSimulador;
import com.example.model.Simulacao;
import com.example.simulation.graph.Grafo;
import com.example.simulation.traffic.veiculo.GeradorVeiculos;

public class SimulacaoGUI extends JFrame {
    private JTextField txtNumCarros;
    private JComboBox<String> comboModelo;
    private JButton btnIniciar, btnPausar, btnParar, btnSalvar, btnCarregar;
    private VisualizacaoSimulador painelVisual;
    private Simulacao simulacao;
    private Thread threadSimulacao;

    public SimulacaoGUI(Grafo grafo, GeradorVeiculos geradorVeiculos) {

        setTitle("Controle da Simulação");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Painel de controle
        JPanel painelControle = new JPanel();
        painelControle.setLayout(new FlowLayout());

        painelControle.add(new JLabel("Nº de carros:"));
        txtNumCarros = new JTextField("10", 5);
        painelControle.add(txtNumCarros);

        painelControle.add(new JLabel("Modelo:"));
        comboModelo = new JComboBox<>(
                new String[] { "1 - Ciclo fixo", "2 - Otimização espera", "3 - Otimização energia" });
        painelControle.add(comboModelo);

        btnIniciar = new JButton("Iniciar");
        btnPausar = new JButton("Pausar");
        btnParar = new JButton("Parar");
        btnSalvar = new JButton("Salvar");
        btnCarregar = new JButton("Carregar");

        btnPausar.setEnabled(false);
        btnParar.setEnabled(false);

        painelControle.add(btnIniciar);
        painelControle.add(btnPausar);
        painelControle.add(btnParar);
        painelControle.add(btnSalvar);
        painelControle.add(btnCarregar);

        add(painelControle, BorderLayout.NORTH);

        // Painel de visualização
        painelVisual = new VisualizacaoSimulador(grafo);
        add(painelVisual, BorderLayout.CENTER);

        // Botão iniciar/continuar
        btnIniciar.addActionListener(e -> {
            int numCarros = Integer.parseInt(txtNumCarros.getText());
            int modelo = comboModelo.getSelectedIndex() + 1;

            btnIniciar.setEnabled(false);
            btnPausar.setEnabled(true);
            btnParar.setEnabled(true);

            if (simulacao != null && simulacao.isPausado()) {
                simulacao.continuar();
                return;
            }

            simulacao = new Simulacao(grafo, geradorVeiculos);

            threadSimulacao = new Thread(() -> {
                simulacao.testarVeiculosESemaforosComModelo(numCarros, modelo, painelVisual);
                SwingUtilities.invokeLater(() -> {
                    btnIniciar.setEnabled(true);
                    btnPausar.setEnabled(false);
                    btnParar.setEnabled(false);
                });
            });
            threadSimulacao.start();
        });

        // Botão pausar
        btnPausar.addActionListener(e -> {
            if (simulacao != null) {
                simulacao.pausar();
            }
            btnIniciar.setEnabled(true);
            btnPausar.setEnabled(false);
            btnParar.setEnabled(true);
        });

        // Botão parar
        btnParar.addActionListener(e -> {
            if (simulacao != null) {
                simulacao.parar();
            }
            btnIniciar.setEnabled(true);
            btnPausar.setEnabled(false);
            btnParar.setEnabled(false);
        });

        // Botão salvar
        btnSalvar.addActionListener(e -> {
            if (simulacao != null) {
                simulacao.salvarEstatisticas();
                JOptionPane.showMessageDialog(this, "Arquivo salvo");
            }
        });

        // Botão carregar
        btnCarregar.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File("saves"));
            int result = chooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String nomeArquivo = chooser.getSelectedFile().getName();
                Simulacao sim = Simulacao.carregar(nomeArquivo);
                if (sim != null) {
                    this.simulacao = sim;
                    JOptionPane.showMessageDialog(this, "Simulação carregada!");
                }
            }
        });

        setSize(1000, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}