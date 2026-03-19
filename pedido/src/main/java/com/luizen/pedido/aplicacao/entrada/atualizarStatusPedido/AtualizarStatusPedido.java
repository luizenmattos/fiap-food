package com.luizen.pedido.aplicacao.entrada.atualizarStatusPedido;

import java.util.UUID;

import com.luizen.pedido.dominio.Pedido;
import com.luizen.pedido.dominio.Status;
import com.luizen.pedido.dominio.repositories.PedidoRepository;

public class AtualizarStatusPedido {
    
    private PedidoRepository pedidoRepository;

    public AtualizarStatusPedido(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public void executar(UUID pedidoId, Status status) {
        Pedido pedido = pedidoRepository.obterPorId(pedidoId).orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
        
        if(status == Status.APROVADO) {
            pedido.aprovar();
        } else if(status == Status.PENDENTE_PAGAMENTO) {
            pedido.pender();
        }

        pedidoRepository.salvar(pedido);
    }
}
