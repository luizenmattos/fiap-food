package com.luizen.pedido.infra.saida.rabbitmq;

import java.math.BigDecimal;
import java.util.Map;

import com.luizen.pedido.aplicacao.saida.EventoPedidoCriado;
import com.luizen.pedido.kernelcompartilhado.MyLogger;
import com.luizen.pedido.kernelcompartilhado.MyLoggerMessage;

import org.springframework.amqp.rabbit.core.RabbitTemplate;

public class PedidoCriadoProduces implements EventoPedidoCriado{

    private static final MyLogger logger = MyLogger.getInstance(PedidoCriadoProduces.class);
    private final RabbitTemplate rabbitTemplate;
    private final String exchange;
    private final String routingKey;

    public PedidoCriadoProduces(RabbitTemplate rabbitTemplate, String exchange, String routingKey) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchange = exchange;
        this.routingKey = routingKey;
    }

    @Override
    public void dispararEvento(String pedidoId, BigDecimal valorTotal) {
        var evento = Map.of(
            "pedidoId", pedidoId,
            "valorTotal", valorTotal
        );

        rabbitTemplate.convertAndSend(exchange, routingKey, evento);

        logger.info(MyLoggerMessage.of("SUCESSO", "EVENTO_PEDIDO_CRIADO_PUBLICADO", Map.of(
            "pedidoId", pedidoId,
            "valorTotal", valorTotal.toPlainString(),
            "exchange", exchange,
            "routingKey", routingKey
        )));
    }
    
}
