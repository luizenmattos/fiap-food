package com.luizen.pagamento.aplicacao.saida.eventoPagamentoPendente;

public interface EventoPagamentoPendente {
    void notificarPagamentoPendente(String pagamentoId);
}
