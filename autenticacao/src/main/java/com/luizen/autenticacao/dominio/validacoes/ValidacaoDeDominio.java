package com.luizen.autenticacao.dominio.validacoes;

import java.util.ArrayList;
import java.util.List;

import com.luizen.autenticacao.dominio.exceptions.DominioException;

public abstract class ValidacaoDeDominio<T> {

    protected T entidadeParaValidar;
    protected List<String> erros = new ArrayList<>();
    protected ValidacaoDeDominio<?> proximaValidacao;

    public ValidacaoDeDominio(T entidadeParaValidar) {
        this.entidadeParaValidar = entidadeParaValidar;
    }

    public ValidacaoDeDominio<?> proximaValidacao(ValidacaoDeDominio<?> proximaValidacao) {
        this.proximaValidacao = proximaValidacao;
        proximaValidacao.erros = this.erros;
        return proximaValidacao;
    }

    public void validar() {
        try {
            validacaoAbstrata();
        } catch (DominioException e) {
            erros.add(e.getMessage());
        }

        if (proximaValidacao != null) {
            proximaValidacao.validar();
        }

        if (!erros.isEmpty()) {
            throw new DominioException(erros);
        }
    }

    protected abstract void validacaoAbstrata();
}