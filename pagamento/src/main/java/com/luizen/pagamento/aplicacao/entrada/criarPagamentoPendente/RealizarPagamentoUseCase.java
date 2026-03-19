package com.luizen.pagamento.aplicacao.entrada.criarPagamentoPendente;

import java.math.BigDecimal;

import com.luizen.pagamento.aplicacao.saida.eventoPagamentoAprovado.EventoPagamentoAprovado;
import com.luizen.pagamento.aplicacao.saida.eventoPagamentoPendente.EventoPagamentoPendente;
import com.luizen.pagamento.aplicacao.saida.eventoPagamentoRejeitado.EventoPagamentoRejeitado;
import com.luizen.pagamento.aplicacao.saida.servicoPagamentoExterno.PagamentoExternoService;
import com.luizen.pagamento.dominio.Pagamento;
import com.luizen.pagamento.dominio.PagamentoRepository;

public class RealizarPagamentoUseCase {
    
    public EventoPagamentoRejeitado eventoPagamentoRejeitado;
    public PagamentoExternoService pagamentoExternoService;
    public EventoPagamentoPendente eventoPagamentoPendente;
    public EventoPagamentoAprovado eventoPagamentoAprovado;
    public PagamentoRepository pagamentoRepositorio;
    
    public RealizarPagamentoUseCase(PagamentoExternoService pagamentoExternoService, EventoPagamentoPendente eventoPagamentoPendente, EventoPagamentoAprovado eventoPagamentoAprovado, EventoPagamentoRejeitado eventoPagamentoRejeitado, PagamentoRepository pagamentoRepositorio) {
        this.pagamentoExternoService = pagamentoExternoService; 
        this.eventoPagamentoPendente = eventoPagamentoPendente;
        this.eventoPagamentoAprovado = eventoPagamentoAprovado;
        this.eventoPagamentoRejeitado = eventoPagamentoRejeitado;
        this.pagamentoRepositorio = pagamentoRepositorio;
    }

    public Pagamento executar(BigDecimal valor, String pedidoId, String clienteId) {
        Pagamento pagamento = Pagamento.criarPagamentoPendente(valor, clienteId, pedidoId);

        Pagamento pagamentoSalvo = pagamentoRepositorio.salvar(pagamento).orElse(null);

        boolean pagamentoRealizado = pagamentoExternoService.realizarPagamento(
            pagamentoSalvo.getValor(), 
            pagamentoSalvo.getId().toString(), 
            pagamentoSalvo.getClienteId()
        );

        if(pagamentoRealizado) {
            pagamentoSalvo.aprovar();
        }

        Pagamento pagamentoAtualizado = pagamentoRepositorio.salvar(pagamentoSalvo).orElse(null);

        if (pagamentoAtualizado.pendente()) {
            eventoPagamentoPendente.notificarPagamentoPendente(pagamentoAtualizado.getPedidoId(), pagamentoAtualizado.getId().toString());

        }else if (pagamentoAtualizado.aprovado()) {
            eventoPagamentoAprovado.notificarPagamentoAprovado(pagamentoAtualizado.getPedidoId(), pagamentoAtualizado.getId().toString());
        
        }
        
        return pagamentoAtualizado;
    }
}
