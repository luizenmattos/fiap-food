package com.luizen.pedido.infra.config;

import com.luizen.pedido.aplicacao.entrada.pedido.PedidoApplicationService;
import com.luizen.pedido.aplicacao.entrada.produto.ProdutoApplicationService;
import com.luizen.pedido.aplicacao.entrada.token.TokenService;
import com.luizen.pedido.dominio.repositories.PedidoRepository;
import com.luizen.pedido.dominio.repositories.ProdutoRepository;
import com.luizen.pedido.infra.entrada.JwtTokenService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguracao {

    @Bean
    public TokenService tokenService() {
        return new JwtTokenService();
    }

    @Bean
    public PedidoApplicationService pedidoApplicationService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, TokenService tokenService) {
        return new PedidoApplicationService(pedidoRepository, produtoRepository, tokenService);
    }
    
    @Bean
    public ProdutoApplicationService produtoApplicationService(ProdutoRepository produtoRepository) {
        return new ProdutoApplicationService(produtoRepository);
    }
}