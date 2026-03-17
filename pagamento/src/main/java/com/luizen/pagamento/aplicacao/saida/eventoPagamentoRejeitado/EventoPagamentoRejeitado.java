package com.luizen.pagamento.aplicacao.saida.eventoPagamentoRejeitado;

public interface EventoPagamentoRejeitado {
    void notificarPagamentoRejeitado(String pagamentoId);
}
