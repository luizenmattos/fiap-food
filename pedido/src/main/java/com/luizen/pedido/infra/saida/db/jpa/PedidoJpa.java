package com.luizen.pedido.infra.saida.db.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizen.pedido.infra.saida.db.entidades.PedidoEntity;

public interface PedidoJpa extends JpaRepository<PedidoEntity, UUID> {

}
