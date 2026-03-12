package com.luizen.pedido.dominio.validacoes;

import java.math.BigDecimal;

import com.luizen.pedido.dominio.exceptions.DominioException;
import com.luizen.pedido.dominio.Pedido;

public class ValidacaoValorDoPedidoMaiorQueDezReais extends ValidacaoDeDominio<Pedido> {

    public ValidacaoValorDoPedidoMaiorQueDezReais(Pedido entidadeParaValidar) {
        super(entidadeParaValidar);
    }

    @Override
    protected void validacaoAbstrata() {
        if (entidadeParaValidar.valorTotal() == null || entidadeParaValidar.valorTotal().compareTo(BigDecimal.TEN) <= 0) {
            throw new DominioException("O valor do pedido deve ser maior que 10 reais.");
        }
    }
    
}
