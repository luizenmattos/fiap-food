package com.luizen.autenticacao.dominio;

public abstract class Senha {
    
    protected String valor;

    public Senha(String valor){
        this.valor = valor;
    }

    public String valor() {
        return valor;
    }

    public abstract SenhaCriptografada senhaCriptografada();

}