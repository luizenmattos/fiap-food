package com.luizen.pedido.aplicacao.entrada.aprovarPedido;

import java.util.UUID;

import com.luizen.pedido.dominio.repositories.PedidoRepository;

public class AprovarPedido {
    
    private PedidoRepository pedidoRepository;

    public AprovarPedido(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public void executar(UUID pedidoId) {
        pedidoRepository.atualizarStatus(pedidoId, "APROVADO");
    }
}
