package com.luizen.autenticacao.dominio;

public class SenhaCriptografada extends Senha {

    public SenhaCriptografada(String valor) {
        super(valor);
    }

    @Override
    public SenhaCriptografada senhaCriptografada() {
        return this;
    }
}
