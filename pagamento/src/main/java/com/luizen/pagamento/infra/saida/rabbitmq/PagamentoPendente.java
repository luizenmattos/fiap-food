package com.luizen.pagamento.infra.saida.rabbitmq;

import com.luizen.pagamento.aplicacao.saida.eventoPagamentoPendente.EventoPagamentoPendente;

public class PagamentoPendente implements EventoPagamentoPendente {

    @Override
    public void notificarPagamentoPendente(String pagamentoId) {
        System.out.println("Pagamento pendente: " + pagamentoId);
    }
    
}
