package com.luizen.pagamento.aplicacao.saida.jobVerificarStatusPagamento;

import java.util.List;

import com.luizen.pagamento.aplicacao.saida.eventoPagamentoAprovado.EventoPagamentoAprovado;
import com.luizen.pagamento.aplicacao.saida.eventoPagamentoRejeitado.EventoPagamentoRejeitado;
import com.luizen.pagamento.aplicacao.saida.servicoPagamentoExterno.PagamentoExternoService;
import com.luizen.pagamento.dominio.Pagamento;
import com.luizen.pagamento.dominio.PagamentoRepository;
import com.luizen.pagamento.dominio.Status;

public class VerificarStatusPagamento {

    PagamentoRepository pagamentoRepositorio;
    PagamentoExternoService pagamentoExternoService;
    EventoPagamentoAprovado eventoPagamentoAprovado;
    EventoPagamentoRejeitado eventoPagamentoRejeitado;

    public VerificarStatusPagamento(PagamentoRepository pagamentoRepositorio, PagamentoExternoService pagamentoExternoService, EventoPagamentoAprovado eventoPagamentoAprovado, EventoPagamentoRejeitado eventoPagamentoRejeitado) {
        this.pagamentoRepositorio = pagamentoRepositorio;
        this.pagamentoExternoService = pagamentoExternoService;
        this.eventoPagamentoAprovado = eventoPagamentoAprovado;
        this.eventoPagamentoRejeitado = eventoPagamentoRejeitado;
    }

    public void executar() {
        List<Pagamento> pagamentosPendentes = pagamentoRepositorio.obterPagamentosPorStatus(Status.PENDENTE_PAGAMENTO);
        System.out.println("Verificando status de " + pagamentosPendentes.size() + " pagamentos pendentes.");
        for(Pagamento pagamento : pagamentosPendentes) {
            Status statusPagamento = pagamentoExternoService.verificarStatusPagamento(pagamento.getId().toString());
            if(statusPagamento == Status.APROVADO){
                pagamento.aprovar();
                pagamentoRepositorio.salvar(pagamento);
                eventoPagamentoAprovado.notificarPagamentoAprovado(pagamento.getPedidoId() ,pagamento.getId().toString());
            }
        }

    }
}
