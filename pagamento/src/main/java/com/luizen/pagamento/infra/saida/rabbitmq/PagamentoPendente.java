package com.luizen.pagamento.infra.saida.rabbitmq;

import java.util.Map;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

import com.luizen.pagamento.aplicacao.saida.eventoPagamentoPendente.EventoPagamentoPendente;

public class PagamentoPendente implements EventoPagamentoPendente {

    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public PagamentoPendente(RabbitTemplate rabbitTemplate, String exchange, String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @Override
    public void notificarPagamentoPendente(String pagamentoId) {
        var evento = Map.of(
            "pagamentoId", pagamentoId,
            "status", "PENDENTE_PAGAMENTO"
        );

        rabbitTemplate.convertAndSend(exchange, routingKey, evento);
    }
    
}
