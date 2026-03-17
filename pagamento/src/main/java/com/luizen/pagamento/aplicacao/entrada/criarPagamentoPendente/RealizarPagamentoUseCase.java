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
    public PagamentoRepository pagamentoRepositorio;
    public PagamentoExternoService pagamentoExternoService;
    public EventoPagamentoPendente eventoPagamentoPendente;
    public EventoPagamentoAprovado eventoPagamentoAprovado;
    
    public RealizarPagamentoUseCase(PagamentoRepository pagamentoRepositorio, PagamentoExternoService pagamentoExternoService, EventoPagamentoPendente eventoPagamentoPendente, EventoPagamentoAprovado eventoPagamentoAprovado, EventoPagamentoRejeitado eventoPagamentoRejeitado) {
        this.pagamentoRepositorio = pagamentoRepositorio;
        this.pagamentoExternoService = pagamentoExternoService; 
        this.eventoPagamentoPendente = eventoPagamentoPendente;
        this.eventoPagamentoAprovado = eventoPagamentoAprovado;
        this.eventoPagamentoRejeitado = eventoPagamentoRejeitado;
    }

    public Pagamento executar(BigDecimal valor, String descricao) {
        Pagamento pagamento = Pagamento.criarPagamentoPendente(valor, descricao);
        
        boolean pagamentoRealizado = pagamentoExternoService.realizarPagamento();
        if(pagamentoRealizado) {
            pagamento.aprovar();
        } 
        
        Pagamento pagamentoSalvo = pagamentoRepositorio.salvar(pagamento).orElse(null);
        
        if (pagamentoSalvo.pendente()) {
            eventoPagamentoPendente.notificarPagamentoPendente(null);

        }else if (pagamentoSalvo.aprovado()) {
            eventoPagamentoAprovado.notificarPagamentoAprovado(null);
        
        } else if (pagamentoSalvo.rejeitado()) {
            eventoPagamentoRejeitado.notificarPagamentoRejeitado(null);
        }
        
        return pagamentoSalvo;
    }
}
