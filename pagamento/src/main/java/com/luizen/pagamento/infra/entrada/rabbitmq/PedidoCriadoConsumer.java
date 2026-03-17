package com.luizen.pagamento.infra.entrada.rabbitmq;

import com.luizen.pagamento.aplicacao.entrada.criarPagamentoPendente.RealizarPagamentoUseCase;

public class PedidoCriadoConsumer {

    RealizarPagamentoUseCase realizarPagamentoUseCase;

    public PedidoCriadoConsumer(RealizarPagamentoUseCase realizarPagamentoUseCase) {
        this.realizarPagamentoUseCase = realizarPagamentoUseCase;
    }
    
}
