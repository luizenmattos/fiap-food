package com.luizen.pagamento.aplicacao.saida.servicoPagamentoExterno;

import java.math.BigDecimal;

import com.luizen.pagamento.dominio.Status;

public interface PagamentoExternoService {
    
    boolean realizarPagamento(BigDecimal valor, String pagamentoId, String clienteId);

    Status verificarStatusPagamento(String pagamentoId);

}
