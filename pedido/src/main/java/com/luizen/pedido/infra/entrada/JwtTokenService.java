package com.luizen.pedido.infra.entrada;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.luizen.pedido.aplicacao.entrada.ApplicationException;
import com.luizen.pedido.aplicacao.entrada.token.TokenService;
import com.luizen.pedido.aplicacao.entrada.token.UsuarioToken;

public class JwtTokenService implements TokenService {

    @Value("${api.security.token.secret:chave_padrao_local}")
    private String secret;

    public UsuarioToken obterDadosEValidarToken(String token) {
        try{
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            DecodedJWT decodedJWT = JWT.require(algoritmo)
                    .withIssuer("Luizen Auth API")
                    .build()
                    .verify(token);
    
            UUID id = UUID.fromString(decodedJWT.getSubject());
            String papel = decodedJWT.getClaim("papel").asString();
            return new UsuarioToken(id, papel);

        }catch(Exception e){
            throw new ApplicationException("Token inválido ou expirado");
        }

    }

}
