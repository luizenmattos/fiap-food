package com.luizen.pedido.infra.saida.db.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizen.pedido.infra.saida.db.entidades.ProdutoEntity;

public interface ProdutoJpa extends JpaRepository<ProdutoEntity, UUID> {

}
