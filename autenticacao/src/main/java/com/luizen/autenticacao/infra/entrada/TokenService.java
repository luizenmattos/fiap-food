package com.luizen.autenticacao.infra.entrada;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.luizen.autenticacao.dominio.Usuario;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    // A chave secreta deve ser configurada no application.properties
    // Exemplo: api.security.token.secret=sua_senha_super_secreta
    @Value("${api.security.token.secret:chave_padrao_local}")
    private String secret;

    public String gerarToken(Usuario usuario) {
        try {
            // Define o algoritmo HMAC256 usando a sua chave secreta
            Algorithm algoritmo = Algorithm.HMAC256(secret);
            
            return JWT.create()
                    .withIssuer("Luizen Auth API") // Identifica quem emitiu o token
                    .withSubject(usuario.getId().toString()) // Identifica o dono do token (geralmente e-mail ou ID)
                    .withClaim("papel", usuario.getPapel().name()) // <-- Identificar o papel do usuário no token
                    .withExpiresAt(gerarDataExpiracao()) // Define quando o token perde a validade
                    .sign(algoritmo); // Assina e gera a string final
                    
        } catch (JWTCreationException exception) {
            // É importante lançar uma exceção clara caso a assinatura falhe
            throw new RuntimeException("Erro ao gerar o token JWT", exception);
        }
    }

    private Instant gerarDataExpiracao() {
        // Define o token para expirar em 30 minutos no fuso horário do Brasil (-03:00)
        return LocalDateTime.now().plusMinutes(30).toInstant(ZoneOffset.of("-03:00"));
    }
}