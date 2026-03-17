package com.luizen.pagamento.infra.saida.rabbitmq;

import com.luizen.pagamento.aplicacao.saida.eventoPagamentoAprovado.EventoPagamentoAprovado;

public class PagamentoAprovado implements EventoPagamentoAprovado {

    @Override
    public void notificarPagamentoAprovado(String pagamentoId) {
        System.out.println("Pagamento aprovado: " + pagamentoId);
        return;
    }
    
}
