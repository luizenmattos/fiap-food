package com.luizen.pagamento.infra.entrada.rabbitmq;

import java.math.BigDecimal;
import java.util.Map;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import com.luizen.pagamento.aplicacao.entrada.criarPagamentoPendente.RealizarPagamentoUseCase;

//TODO: PADRAP DE RESILIENCIA CASO DE ERRO NO CONSUMO DO EVENTO, COMO REPROCESSAR O EVENTO, LOG DE ERROS, ETC
public class PedidoCriadoConsumer {

    private final RealizarPagamentoUseCase realizarPagamentoUseCase;

    public PedidoCriadoConsumer(RealizarPagamentoUseCase realizarPagamentoUseCase) {
        this.realizarPagamentoUseCase = realizarPagamentoUseCase;
    }

    @RabbitListener(queues = "${app.rabbitmq.pedido-criado.queue:pedido.criado.queue}")
    public void consumir(Map<String, Object> evento) {

        String pedidoId = evento.get("pedidoId") != null ? evento.get("pedidoId").toString() : "desconhecido";
        String clienteId = evento.get("clienteId") != null ? evento.get("clienteId").toString() : "";
        BigDecimal valorTotal = converterParaBigDecimal(evento.get("valorTotal"));

        realizarPagamentoUseCase.executar(valorTotal, pedidoId, clienteId);
    }

    private BigDecimal converterParaBigDecimal(Object valor) {
        if (valor == null) {
            return BigDecimal.ZERO;
        }
        if (valor instanceof BigDecimal) {
            return (BigDecimal) valor;
        }
        if (valor instanceof Number) {
            return BigDecimal.valueOf(((Number) valor).doubleValue());
        }
        return new BigDecimal(valor.toString());
    }
    
}
