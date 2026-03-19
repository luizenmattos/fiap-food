package com.luizen.pagamento.infra.saida.rabbitmq;

import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.luizen.pagamento.aplicacao.saida.eventoPagamentoAprovado.EventoPagamentoAprovado;

public class PagamentoAprovado implements EventoPagamentoAprovado {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public PagamentoAprovado(RabbitTemplate rabbitTemplate, String exchange, String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @Override
    public void notificarPagamentoAprovado(String pedidoId, String pagamentoId) {
        var evento = Map.of(
            "pedidoId", pedidoId,
            "pagamentoId", pagamentoId,
            "status", "APROVADO"
        );

        rabbitTemplate.convertAndSend(exchange, routingKey, evento);
    }
    
}
