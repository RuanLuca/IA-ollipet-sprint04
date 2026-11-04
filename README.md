# Challenge Clyvo - Ollie Pet (Sprint 04)

Este repositório contém a implementação do backend em Spring Boot para o sistema de monitorização inteligente Ollie Pet, focado na integração entre sensores IoT e Inteligência Artificial Generativa.

## Tecnologias Utilizadas
* **Java & Spring Boot:** Desenvolvimento do servidor backend e orquestração de APIs.
* **Integração HTTP Nativa:** Utilização da biblioteca `java.net.http.HttpClient` para chamadas externas.
* **Google Gemini API:** IA Generativa utilizada para processar os dados vitais e gerar recomendações de triagem.
* **IoT (ESP32):** Telemetria de temperatura e batimentos cardíacos.
* **Insomnia:** Validação de rotas e simulação do payload do dispositivo IoT.

## Estrutura do Projeto
A arquitetura segue o padrão MVC (Model-View-Controller) adaptado para APIs REST:
* `/controller`: Endpoints REST que recebem as requisições do front-end e dos dispositivos IoT.
* `/service`: Regras de negócio, contendo a classe `AvaliadorDeTriagem.java` responsável por orquestrar a comunicação com a API do Google Gemini.
* `/model` e `/dto`: Entidades de domínio e objetos de transferência de dados (payloads).
* `/repository`: Interfaces de comunicação com o banco de dados.

## Link do video: https://youtu.be/MDKgzCRxbgo

## Instruções de Uso
1. Faça o clone do repositório:
   ```bash
   git clone https://github.com/Olli-Pet/challenge_clyvo_arduino