package com.luizen.pedido.dominio.validacoes;

import com.luizen.pedido.dominio.exceptions.DominioException;
import com.luizen.pedido.dominio.Pedido;

public class ValidacaoQuantidadeDeItensMaiorQueZero extends ValidacaoDeDominio<Pedido> {

    public ValidacaoQuantidadeDeItensMaiorQueZero(Pedido entidadeParaValidar) {
        super(entidadeParaValidar);
    }

    @Override
    protected void validacaoAbstrata() {
        if (entidadeParaValidar.itens == null || entidadeParaValidar.itens.isEmpty()) {
            throw new DominioException("O pedido deve conter pelo menos um item.");
        }

    }
}