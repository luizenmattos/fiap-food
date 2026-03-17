package com.luizen.pagamento.aplicacao.saida.jobVerificarStatusPagamento;

import com.luizen.pagamento.aplicacao.saida.eventoPagamentoAprovado.EventoPagamentoAprovado;
import com.luizen.pagamento.aplicacao.saida.eventoPagamentoRejeitado.EventoPagamentoRejeitado;
import com.luizen.pagamento.aplicacao.saida.servicoPagamentoExterno.PagamentoExternoService;
import com.luizen.pagamento.dominio.Status;

public class VerificarStatusPagamento {

    PagamentoExternoService pagamentoExternoService;
    EventoPagamentoAprovado eventoPagamentoAprovado;
    EventoPagamentoRejeitado eventoPagamentoRejeitado;

    public VerificarStatusPagamento(PagamentoExternoService pagamentoExternoService, EventoPagamentoAprovado eventoPagamentoAprovado, EventoPagamentoRejeitado eventoPagamentoRejeitado) {
        this.pagamentoExternoService = pagamentoExternoService;
        this.eventoPagamentoAprovado = eventoPagamentoAprovado;
        this.eventoPagamentoRejeitado = eventoPagamentoRejeitado;
    }

    public void executar() {
        Status statusPagamento = pagamentoExternoService.verificarStatusPagamento(null);

        if(statusPagamento == Status.APROVADO) {
            eventoPagamentoAprovado.notificarPagamentoAprovado(null);
        } else if(statusPagamento == Status.REJEITADO) {
            eventoPagamentoRejeitado.notificarPagamentoRejeitado(null);
        }
    }
}
