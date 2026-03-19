package com.luizen.pagamento.aplicacao.saida.eventoPagamentoAprovado;

public interface EventoPagamentoAprovado {
    void notificarPagamentoAprovado(String pedidoId, String pagamentoId);
}
