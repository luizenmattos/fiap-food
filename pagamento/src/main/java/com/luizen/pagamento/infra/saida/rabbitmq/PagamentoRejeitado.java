package com.luizen.pagamento.infra.saida.rabbitmq;

import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.luizen.pagamento.aplicacao.saida.eventoPagamentoRejeitado.EventoPagamentoRejeitado;

public class PagamentoRejeitado implements EventoPagamentoRejeitado {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public PagamentoRejeitado(RabbitTemplate rabbitTemplate, String exchange, String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @Override
    public void notificarPagamentoRejeitado(String pedidoId, String pagamentoId) {
        var evento = Map.of(
            "pedidoId", pedidoId,
            "pagamentoId", pagamentoId,
            "status", "REJEITADO"
        );

        rabbitTemplate.convertAndSend(exchange, routingKey, evento);
    }
    
}
