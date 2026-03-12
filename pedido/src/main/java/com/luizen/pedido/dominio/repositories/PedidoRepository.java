package com.luizen.pedido.dominio.repositories;

import java.util.Optional;

import com.luizen.pedido.dominio.Pedido;

public interface PedidoRepository {
    public Optional<Pedido> salvar(Pedido pedido);
}
