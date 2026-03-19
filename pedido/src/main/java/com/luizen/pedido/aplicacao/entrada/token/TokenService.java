package com.luizen.pedido.aplicacao.entrada.token;

public interface TokenService {

    public UsuarioToken obterDadosEValidarToken(String token);

}
