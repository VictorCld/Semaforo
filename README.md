 🚦 Trabalho Sekeff - Simulador de Tráfego Urbano

Bem-vindo ao **Trabalho Sekeff**, um simulador de tráfego urbano desenvolvido em Java, com interface gráfica e integração de dados reais de mapas. O projeto permite simular o fluxo de veículos em uma malha urbana, controlando semáforos com diferentes modelos de operação e visualizando o resultado em tempo real.

---

 ✨ Funcionalidades

- **Importação de mapas reais** (formato JSON), com interseções, ruas e semáforos.
- **Simulação de veículos**, com rotas automáticas e movimentação dinâmica.
- **Controle de semáforos** com múltiplos modelos:
  - Ciclo fixo
  - Otimização do tempo de espera
  - Otimização do consumo de energia
- **Interface gráfica interativa** para visualização do tráfego e controle da simulação.
- **Geração de estatísticas** ao final da simulação:
  - Tempo médio de viagem
  - Tempo de espera
  - Níveis de congestionamento
- **Salvar e carregar simulações** para continuar de onde parou.

---

 🗂️ Estrutura do Projeto
 ```
trabalho-sekeff-main/
├── src/
│ ├── main/
│ │ ├── java/
│ │ │ └── com/example/
│ │ │ ├── CidadeJson/ # Importação e parsing de mapas e semáforos
│ │ │ ├── gui/ # Interface gráfica (Swing)
│ │ │ ├── model/ # Modelos de domínio (Veículo, Semáforo, Simulação)
│ │ │ └── simulation/ # Lógica de simulação, grafos e tráfego
│ │ └── resources/
│ │ └── application.properties
│ └── test/
│ └── java/
├── pom.xml
├── mvnw / mvnw.cmd
└── .mvn/
```
---

 🚀 Como Executar

 1. Pré-requisitos

- Java 17 ou superior  
- Maven

 2. Clone o repositório

```bash
git clone https://github.com/seu-usuario/seu-repo.git
cd trabalho-sekeff-main
```

 3. Build do projeto

-  ./mvnw clean package

 4. Execute a aplicação
-  ./mvnw clean package

### 5. Use a interface gráfica
Configure e inicie as simulações de forma interativa.

---

 🧭 Como Usar

- Ao iniciar, selecione o arquivo de mapa (formato JSON).
- Configure o número de veículos e o modelo de semáforo desejado.
- Use os botões da interface para **iniciar**, **pausar** ou **parar** a simulação.
- Salve ou carregue simulações conforme necessário.

---

 🛠️ Tecnologias Utilizadas

- Java 17+
- Maven
- Swing (interface gráfica)
- Jackson (leitura de JSON)
- Spring Boot

---

## 👨‍💻 Créditos

Desenvolvido por **[Victor Gabriel]** para a disciplina de **Engenharia de Software**.
