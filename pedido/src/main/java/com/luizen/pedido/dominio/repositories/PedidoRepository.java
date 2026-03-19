package com.luizen.pedido.dominio.repositories;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import com.luizen.pedido.dominio.Pedido;

public interface PedidoRepository {
    public Optional<Pedido> salvar(Pedido pedido);
    public Optional<Pedido> obterPorId(UUID id);
    public List<Pedido> listarPorClienteId(UUID id);
    public void atualizarStatus(UUID pedidoId, String novoStatus);
}
