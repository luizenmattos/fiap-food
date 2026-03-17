package com.luizen.pagamento.infra.saida.http;

import com.luizen.pagamento.aplicacao.saida.servicoPagamentoExterno.PagamentoExternoService;
import com.luizen.pagamento.dominio.Status;

public class PagamentoExternoFiapService implements PagamentoExternoService {

    @Override
    public boolean realizarPagamento() {
        System.out.println("Realizando pagamento via serviço externo da FIAP...");
        return true;
    }

    @Override
    public Status verificarStatusPagamento(String pagamentoId) {
        System.out.println("Verificando status do pagamento via serviço externo da FIAP...");
        return Status.APROVADO;
    }
    
}
