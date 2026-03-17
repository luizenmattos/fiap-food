package com.luizen.pagamento.aplicacao.saida.servicoPagamentoExterno;

import com.luizen.pagamento.dominio.Status;

public interface PagamentoExternoService {
    
    boolean realizarPagamento();

    Status verificarStatusPagamento(String pagamentoId);

}
