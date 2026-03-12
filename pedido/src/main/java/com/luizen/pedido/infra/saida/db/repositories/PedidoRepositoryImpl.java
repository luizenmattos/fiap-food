package com.luizen.pedido.infra.saida.db.repositories;

import com.luizen.pedido.dominio.Pedido;
import com.luizen.pedido.dominio.PedidoItem;
import com.luizen.pedido.dominio.Produto;
import com.luizen.pedido.dominio.repositories.PedidoRepository;
import com.luizen.pedido.infra.saida.db.entidades.PedidoEntity;
import com.luizen.pedido.infra.saida.db.entidades.PedidoItemEntity;
import com.luizen.pedido.infra.saida.db.entidades.ProdutoEntity;
import com.luizen.pedido.infra.saida.db.jpa.PedidoJpa;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class PedidoRepositoryImpl implements PedidoRepository {

    private final PedidoJpa pedidoJpaRepository;

    public PedidoRepositoryImpl(PedidoJpa pedidoJpaRepository) {
        this.pedidoJpaRepository = pedidoJpaRepository;
    }

    @Override
    public Optional<Pedido> salvar(Pedido pedido) {
        PedidoEntity entity = toJpa(pedido);
        PedidoEntity savedEntity = pedidoJpaRepository.save(entity);
        return Optional.of(toDomain(savedEntity));
    }

    private Pedido toDomain(PedidoEntity entity) {
        List<PedidoItem> itens = entity.itens.stream()
            .map(itemEntity -> PedidoItem.carregarDados(
                itemEntity.produto.id,
                Produto.carregarProduto(itemEntity.produto.id, itemEntity.produto.nome, itemEntity.produto.preco),
                itemEntity.quantidade
            )).toList();
            
        return Pedido.carregarDados(entity.id, entity.clienteId.toString(), entity.restauranteId.toString(), itens);
    }

    private PedidoEntity toJpa(Pedido pedido) {
        PedidoEntity entity = new PedidoEntity();
        entity.id = pedido.id;
        entity.clienteId = UUID.fromString(pedido.clienteId);
        entity.restauranteId = UUID.fromString(pedido.restauranteId);
        entity.itens = pedido.getItens().stream()
            .map(item -> new PedidoItemEntity(
                item.getId(),
                new ProdutoEntity(item.getProduto().getId(), item.getProduto().getNome(), item.getProduto().getPreco()),
                item.getQuantidade(),
                entity
            )).toList();
        return entity;


    }
}