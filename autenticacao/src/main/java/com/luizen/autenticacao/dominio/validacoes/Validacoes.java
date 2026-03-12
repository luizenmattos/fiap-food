package com.luizen.autenticacao.dominio.validacoes;

import com.luizen.autenticacao.dominio.Usuario;

public class Validacoes {

    public static void validarCriacaoDeUsuario(Usuario usuario){
        ValidacaoDeDominio<?> chainValidations = new ValidacaoEmail(usuario);
        chainValidations.proximaValidacao(new ValidacaoSenha(usuario))
                        .proximaValidacao(new ValidacaoPapel(usuario));
        
        chainValidations.validar();
    }
    
}
