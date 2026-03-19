package com.luizen.pedido.aplicacao.entrada.listarPedido;

import java.util.List;

import com.luizen.pedido.aplicacao.entrada.token.TokenService;
import com.luizen.pedido.aplicacao.entrada.token.UsuarioToken;
import com.luizen.pedido.dominio.Pedido;
import com.luizen.pedido.dominio.repositories.PedidoRepository;

public class ListarPedidosUseCase {

    private final PedidoRepository pedidoRepository;
    private final TokenService tokenService;

    public ListarPedidosUseCase(PedidoRepository pedidoRepository, TokenService tokenService) {
        this.pedidoRepository = pedidoRepository;
        this.tokenService = tokenService;
    }

    public List<Pedido> executar(String token){
        UsuarioToken usuarioToken = tokenService.obterDadosEValidarToken(token);
        return pedidoRepository.listarPorClienteId(usuarioToken.id());
    }
}
