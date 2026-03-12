package com.luizen.autenticacao.dominio.validacoes;

import com.luizen.autenticacao.dominio.Usuario;
import com.luizen.autenticacao.dominio.exceptions.DominioException;

public class ValidacaoSenha extends ValidacaoDeDominio<Usuario> {

    public ValidacaoSenha(Usuario entidadeParaValidar) {
        super(entidadeParaValidar);
    }

    @Override
    protected void validacaoAbstrata() {
        String senha = entidadeParaValidar.getSenha().valor();

        if (senha == null || senha.isBlank()) {
            throw new DominioException("Senha é obrigatória.");
        }

        if (senha.length() < 8) {
            throw new DominioException("Senha deve ter pelo menos 8 caracteres.");
        }

        if (!senha.matches(".*[A-Z].*")) {
            throw new DominioException("Senha deve conter pelo menos uma letra maiúscula.");
        }

        if (!senha.matches(".*[a-z].*")) {
            throw new DominioException("Senha deve conter pelo menos uma letra minúscula.");
        }

        if (!senha.matches(".*\\d.*")) {
            throw new DominioException("Senha deve conter pelo menos um dígito.");
        }

        if (!senha.matches(".*[!@#$%^&*(),.?\":{}|<>].*")) {
            throw new DominioException("Senha deve conter pelo menos um caractere especial.");
        }
    }
}