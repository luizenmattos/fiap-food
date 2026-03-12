package com.luizen.autenticacao.infra.config;

import com.luizen.autenticacao.aplicacao.entrada.UsuarioApplicationService;
import com.luizen.autenticacao.dominio.repositories.UsuarioRepository;
import com.luizen.autenticacao.dominio.services.UsuarioService;

// Importe as dependências que o serviço precisa (ex: repositório)
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguracao {

    @Bean
    public UsuarioService usuarioService() {
        return new UsuarioService();
    }

    @Bean
    public UsuarioApplicationService usuarioApplicationService(UsuarioService usuarioService, UsuarioRepository usuarioRepository) {
        return new UsuarioApplicationService(usuarioService, usuarioRepository);
    }
    
}