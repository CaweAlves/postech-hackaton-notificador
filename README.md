# 🛎️ Serviço de Notificação (Notificador)

### 📝 Sobre o Projeto

Este projeto é um microserviço especializado, construído em Java com Spring Boot, cuja única responsabilidade é notificar os usuários sobre o status de suas solicitações de processamento de vídeo.

Ele opera de forma assíncrona, consumindo mensagens de uma fila AWS SQS. Uma vez que uma tarefa de processamento (como a extração de frames de um vídeo) é concluída com sucesso ou falha, este serviço é acionado para enviar um e-mail informativo ao usuário final.

### 🏗️ Arquitetura e Fluxo no Sistema

O **Notificador** é o último elo na cadeia de processamento de vídeo, garantindo que o usuário seja comunicado sobre o resultado. O fluxo, conforme o diagrama de arquitetura, é o seguinte:

1.  **Início do Processo**: Um usuário faz o upload de um vídeo, que é recebido e armazenado no S3. Uma mensagem é enviada para uma primeira fila SQS (`videos-recebidos`).
2.  **Extração de Frames**: O serviço `Frame Generator` consome a mensagem, processa o vídeo, gera um arquivo `.zip` com os frames e o salva no bucket S3 `zips-gerados`.
3.  **Acionamento do Notificador**: Após salvar o `.zip`, o `Frame Generator` publica uma mensagem na fila SQS `zips-gerados`. Esta mensagem contém os detalhes do job, incluindo o status (sucesso ou falha) e o e-mail do usuário.
4.  **Ação do Notificador**:
    * O **Notificador** consome a mensagem da fila `zips-gerados`.
    * Ele processa o conteúdo da mensagem para determinar se o job foi `COMPLETED` ou `FAILED`.
    * Com base no status, ele formata e envia um e-mail para o usuário.

<br>
<img src="image_32d831.png" alt="Diagrama da Arquitetura" width="100%">
<br>

### 🛠️ Tecnologias Utilizadas

-   **Java 21** com **Spring Boot**
-   **Maven** para gerenciamento de dependências
-   **AWS SQS** para consumo de mensagens assíncronas
-   **Spring Mail** para o envio de e-mails
-   **Docker** e **Docker Compose** para containerização e ambiente de desenvolvimento
-   **LocalStack** para simulação do ambiente AWS localmente
-   **Testcontainers** para testes de integração

### 📋 Pré-requisitos

-   Java 21+
-   Maven 3.6+
-   Docker e Docker Compose

### 🚀 Como Executar Localmente

1.  **Inicie o Ambiente AWS Local (LocalStack)**

    O projeto vem com uma configuração do Docker Compose para simular o ambiente AWS. A partir do diretório `run/`, execute:
    ```bash
    docker-compose up -d
    ```
    Este comando sobe um contêiner do LocalStack e executa o script `init-aws.sh` para criar a fila SQS (`zips-gerados-queue`) e o tópico SNS necessários.

2.  **Execute a Aplicação**

    Após o LocalStack estar no ar, execute a aplicação Spring Boot. Você pode usar sua IDE preferida ou rodar o seguinte comando no terminal:
    ```bash
    ./mvnw spring-boot:run
    ```
    A aplicação se conectará automaticamente ao LocalStack, conforme definido no arquivo `application.yml`.

### 🔄 Pipeline de CI/CD

O projeto contém um workflow de GitHub Actions (`.github/workflows/ci-cd-docker.yml`) que automatiza o processo de integração e entrega contínua. A cada `push` na branch `main`, o pipeline realiza as seguintes etapas:
1.  **Build e Teste**: Compila o código e executa todos os testes.
2.  **Construção da Imagem Docker**: Empacota a aplicação em uma imagem Docker.
3.  **Publicação no Docker Hub**: Envia a imagem gerada para o Docker Hub.

Para o pipeline funcionar, é preciso configurar os seguintes **secrets** no repositório do GitHub:
-   `DOCKER_HUB_USERNAME`
-   `DOCKER_HUB_TOKEN`
