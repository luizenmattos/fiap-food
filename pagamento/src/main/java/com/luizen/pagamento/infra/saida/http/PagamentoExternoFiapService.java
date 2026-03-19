package com.luizen.pagamento.infra.saida.http;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.luizen.pagamento.aplicacao.saida.servicoPagamentoExterno.PagamentoExternoService;
import com.luizen.pagamento.dominio.Status;

public class PagamentoExternoFiapService implements PagamentoExternoService {

    private final String pagamentoExternoUrl;

    public PagamentoExternoFiapService(String pagamentoExternoUrl) {
        this.pagamentoExternoUrl = pagamentoExternoUrl;
    }

    @Override
    public boolean realizarPagamento(BigDecimal valor, String pagamentoId, String clienteId) {
        try {
            String valorStr = valor.stripTrailingZeros().toPlainString();
            String body = String.format(
                "{\"valor\":%s,\"pagamento_id\":\"%s\",\"cliente_id\":\"%s\"}",
                valorStr, pagamentoId, clienteId
            );

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(pagamentoExternoUrl))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Resposta do serviço externo FIAP: " + response.statusCode() + " - " + response.body());
            
            return response.statusCode() >= 200 && response.statusCode() < 300;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao realizar pagamento via serviço externo da FIAP: " + e.getMessage());
            return false;
        }
    }

    @Override
    public Status verificarStatusPagamento(String pagamentoId) {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(pagamentoExternoUrl+"/"+pagamentoId))
                .header("Content-Type", "application/json")
                .GET()
                .build();

            HttpResponse<String> response = HttpClient.newHttpClient()
                .send(request, HttpResponse.BodyHandlers.ofString());

            System.out.println("Resposta do serviço ver FIAP: " + response.statusCode() + " - " + response.body());
            
            return response.body().contains("pago") ? Status.APROVADO : Status.PENDENTE_PAGAMENTO;

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erro ao verificar status do pagamento via serviço externo da FIAP: " + e.getMessage());
            return Status.PENDENTE_PAGAMENTO;
        }
    }
    
}
