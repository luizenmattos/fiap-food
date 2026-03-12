package com.luizen.pedido.infra.config;

import com.luizen.pedido.aplicacao.entrada.PedidoApplicationService;
import com.luizen.pedido.aplicacao.entrada.ProdutoApplicationService;
import com.luizen.pedido.dominio.repositories.PedidoRepository;
import com.luizen.pedido.dominio.repositories.ProdutoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguracao {

    @Bean
    public PedidoApplicationService pedidoApplicationService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        return new PedidoApplicationService(pedidoRepository, produtoRepository);
    }
    
    @Bean
    public ProdutoApplicationService produtoApplicationService(ProdutoRepository produtoRepository) {
        return new ProdutoApplicationService(produtoRepository);
    }
}