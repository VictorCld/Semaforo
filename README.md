<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <title>Trabalho Sekeff - Simulador de Tráfego Urbano</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 40px;
      max-width: 900px;
      text-align: left;
      background-color: #1e1e1e;
      color: #ffffff;
    }

    h1, h2, h3 {
      color: #ffcc00;
    }

    code {
      background-color: #2e2e2e;
      padding: 2px 4px;
      border-radius: 4px;
      font-family: monospace;
    }

    pre {
      background-color: #2e2e2e;
      padding: 10px;
      border-radius: 8px;
      overflow-x: auto;
    }

    hr {
      border: none;
      border-top: 1px solid #444;
      margin: 2em 0;
    }

    a {
      color: #00afff;
      text-decoration: none;
    }
  </style>
</head>
<body>

<h1>🚦 Trabalho Sekeff - Simulador de Tráfego Urbano</h1>

<p>
Bem-vindo ao <strong>Trabalho Sekeff</strong>, um simulador de tráfego urbano desenvolvido em Java, com interface gráfica e integração de dados reais de mapas. O projeto permite simular o fluxo de veículos em uma malha urbana, controlando semáforos com diferentes modelos de operação e visualizando o resultado em tempo real.
</p>

<hr>

<h2>✨ Funcionalidades</h2>
<ul>
  <li><strong>Importação de mapas reais</strong> (formato JSON), com interseções, ruas e semáforos.</li>
  <li><strong>Simulação de veículos</strong>, com rotas automáticas e movimentação dinâmica.</li>
  <li><strong>Controle de semáforos</strong> com múltiplos modelos:
    <ul>
      <li>Ciclo fixo</li>
      <li>Otimização do tempo de espera</li>
      <li>Otimização do consumo de energia</li>
    </ul>
  </li>
  <li><strong>Interface gráfica interativa</strong> para visualização do tráfego e controle da simulação.</li>
  <li><strong>Geração de estatísticas</strong> ao final da simulação:
    <ul>
      <li>Tempo médio de viagem</li>
      <li>Tempo de espera</li>
      <li>Níveis de congestionamento</li>
    </ul>
  </li>
  <li><strong>Salvar e carregar simulações</strong> para continuar de onde parou.</li>
</ul>

<hr>

<h2>🗂️ Estrutura do Projeto</h2>
<pre><code>
trabalho-sekeff-main/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/example/
│   │   │       ├── CidadeJson/        # Importação e parsing de mapas e semáforos
│   │   │       ├── gui/               # Interface gráfica (Swing)
│   │   │       ├── model/             # Modelos de domínio (Veículo, Semáforo, Simulação)
│   │   │       └── simulation/        # Lógica de simulação, grafos e tráfego
│   ├── resources/
│   │   └── application.properties
│   └── test/
│       └── java/
├── pom.xml
├── mvnw / mvnw.cmd
└── .mvn/
</code></pre>

<hr>

<h2>🚀 Como Executar</h2>

<h3>1. Pré-requisitos</h3>
<ul>
  <li>Java 17 ou superior</li>
  <li>Maven</li>
</ul>

<h3>2. Clone o repositório</h3>
<pre><code>git clone https://github.com/seu-usuario/seu-repo.git
cd trabalho-sekeff-main</code></pre>

<h3>3. Build do projeto</h3>
<pre><code>./mvnw clean package</code></pre>

<h3>4. Execute a aplicação</h3>
<pre><code>./mvnw spring-boot:run</code></pre>

<h3>5. Use a interface gráfica</h3>
<p>Configure e inicie as simulações de forma interativa.</p>

<hr>

<h2>🧭 Como Usar</h2>
<ul>
  <li>Ao iniciar, selecione o arquivo de mapa (formato JSON).</li>
  <li>Configure o número de veículos e o modelo de semáforo desejado.</li>
  <li>Use os botões da interface para <strong>iniciar</strong>, <strong>pausar</strong> ou <strong>parar</strong> a simulação.</li>
  <li>Salve ou carregue simulações conforme necessário.</li>
</ul>

<hr>

<h2>🛠️ Tecnologias Utilizadas</h2>
<ul>
  <li>Java 17+</li>
  <li>Maven</li>
  <li>Swing (interface gráfica)</li>
  <li>Jackson (leitura de JSON)</li>
  <li>Spring Boot</li>
</ul>

<hr>

<h2>👨‍💻 Créditos</h2>
<p>Desenvolvido por <strong>Victor Gabriel</strong> para a disciplina de <strong>Engenharia de Software</strong>.</p>

</body>
</html>
