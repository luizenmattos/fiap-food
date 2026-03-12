package com.luizen.autenticacao.dominio.validacoes;

import com.luizen.autenticacao.dominio.Usuario;
import com.luizen.autenticacao.dominio.exceptions.DominioException;

public class ValidacaoPapel extends ValidacaoDeDominio<Usuario> {

    public ValidacaoPapel(Usuario entidadeParaValidar) {
        super(entidadeParaValidar);
    }

    @Override
    protected void validacaoAbstrata() {
        if (entidadeParaValidar.getPapel() == null) {
            throw new DominioException("O papel do usuário é obrigatório.");
        }
    }
    
}
