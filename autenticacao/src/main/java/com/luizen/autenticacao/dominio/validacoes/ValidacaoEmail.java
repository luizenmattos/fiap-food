package com.luizen.autenticacao.dominio.validacoes;

import java.util.regex.Pattern;

import com.luizen.autenticacao.dominio.Usuario;
import com.luizen.autenticacao.dominio.exceptions.DominioException;

public class ValidacaoEmail extends ValidacaoDeDominio<Usuario> {

    private static final Pattern EMAIL_PATTERN =
        Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,}$", Pattern.CASE_INSENSITIVE);

    public ValidacaoEmail(Usuario entidadeParaValidar) {
        super(entidadeParaValidar);
    }

    @Override
    protected void validacaoAbstrata() {
        String email = entidadeParaValidar.getEmail();

        if (email == null || email.isBlank()) {
            throw new DominioException("Email é obrigatório.");
        }

        if (email.length() > 254) {
            throw new DominioException("Email não deve exceder 254 caracteres.");
        }

        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new DominioException("Formato de email inválido.");
        }

    }
}