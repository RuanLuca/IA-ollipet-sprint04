package org.fiap.challenge_clyvo.service;

import org.fiap.challenge_clyvo.model.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

@Service
public class AvaliadorDeTriagem {

    @Value("${spring.ai.openai.api-key:}")
    private String apiKey;

    // Método original exigido pelo TriagemService para o questionário clínico
    public ResultadoDaTriagem avaliar(Pet pet, Queixa queixa, List<OpcaoResposta> escolhas, ContextoClinico contexto) {
        List<String> observacoes = new ArrayList<>();
        int pontuacao = 0;

        for (OpcaoResposta opcao : escolhas) {
            if (opcao.isSinalAlerta()) {
                observacoes.add("Sinal de alerta identificado.");
                return new ResultadoDaTriagem(pontuacao, ClassificacaoTriagem.EMERGENCIA, AcaoSugerida.IR_AGORA, observacoes);
            }
        }

        ClassificacaoTriagem classificacao = queixa.classificar(pontuacao);
        return new ResultadoDaTriagem(pontuacao, classificacao, classificacao.getAcaoSugerida(), observacoes);
    }

    // Novo método de IA Generativa para a telemetria do IoT / ESP32 (Sprint 04)
    public String analisarTelemetriaIoT(Double temperatura, Integer batimentos) {
        try {
            String prompt = String.format(
                    "És um veterinário especialista em triagem do sistema CLYVO VET. " +
                            "Dados de telemetria recebidos de um cão: Temperatura: %.1f°C, Batimentos: %d bpm. " +
                            "Responde apenas com o Nível de Urgência (Normal, Alerta ou Crítico) seguido de uma recomendação curta.",
                    temperatura, batimentos
            );

            String requestBody = """
                {
                    "contents": [{
                        "parts":[{"text": "%s"}]
                    }]
                }
                """.formatted(prompt);

            HttpClient client = HttpClient.newHttpClient();

            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://generativelanguage.googleapis.com/v1/models/gemini-3.8-flash:generateContent?key=" + apiKey))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception e) {
            return "Erro ao processar IA: " + e.getMessage();
        }
    }
}