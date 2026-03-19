package com.luizen.pedido.aplicacao.entrada.consultarPedido;

import java.util.UUID;

import com.luizen.pedido.aplicacao.entrada.ApplicationException;
import com.luizen.pedido.aplicacao.entrada.token.TokenService;
import com.luizen.pedido.dominio.Pedido;
import com.luizen.pedido.dominio.repositories.PedidoRepository;

public class ConsultarPedidoUseCase {

    private final PedidoRepository pedidoRepository;
    private final TokenService tokenService;

    public ConsultarPedidoUseCase(PedidoRepository pedidoRepository, TokenService tokenService) {
        this.pedidoRepository = pedidoRepository;
        this.tokenService = tokenService;
    }

    public Pedido executar(String token, UUID pedidoId){
        tokenService.obterDadosEValidarToken(token);
        return pedidoRepository.obterPorId(pedidoId).orElseThrow(() -> new ApplicationException("Pedido não encontrado"));
    }
}
