package com.luizen.pagamento.infra.saida.rabbitmq;

import com.luizen.pagamento.aplicacao.saida.eventoPagamentoRejeitado.EventoPagamentoRejeitado;

public class PagamentoRejeitado implements EventoPagamentoRejeitado {

    @Override
    public void notificarPagamentoRejeitado(String pagamentoId) {
        System.out.println("Pagamento rejeitado: " + pagamentoId);
    }
    
}
