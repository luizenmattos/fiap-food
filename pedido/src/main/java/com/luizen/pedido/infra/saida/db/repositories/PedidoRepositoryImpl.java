package com.luizen.pedido.infra.saida.db.repositories;

import com.luizen.pedido.dominio.Pedido;
import com.luizen.pedido.dominio.PedidoItem;
import com.luizen.pedido.dominio.Produto;
import com.luizen.pedido.dominio.Status;
import com.luizen.pedido.dominio.repositories.PedidoRepository;
import com.luizen.pedido.infra.saida.db.entidades.PedidoEntity;
import com.luizen.pedido.infra.saida.db.entidades.PedidoItemEntity;
import com.luizen.pedido.infra.saida.db.entidades.ProdutoEntity;
import com.luizen.pedido.infra.saida.db.entidades.StatusEntity;
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
        PedidoEntity entity = null;
        if(pedido.getId()!=null){
            entity = pedidoJpaRepository.findById(pedido.getId()).get();
        }else{
            entity = new PedidoEntity();
        }    

        // dados básicos
        entity.id = pedido.id;
        entity.clienteId = UUID.fromString(pedido.clienteId);
        entity.restauranteId = UUID.fromString(pedido.restauranteId);
        entity.status = StatusEntity.valueOf(pedido.getStatus().name());

        atualizarItens(entity, pedido);

        PedidoEntity savedEntity = pedidoJpaRepository.save(entity);
        return Optional.of(toDomain(savedEntity));
    }
    @Override
    public Optional<Pedido> obterPorId(UUID id) {
        Optional<PedidoEntity> entityOpt = pedidoJpaRepository.findById(id);
        return entityOpt.map(this::toDomain);
    }

    public List<Pedido> listarPorClienteId(UUID clienteId){
        List<PedidoEntity> entities = pedidoJpaRepository.findByClienteId(clienteId);
        return entities.stream().map(this::toDomain).toList();
    }

    @Override
    public void atualizarStatus(UUID pedidoId, String novoStatus) {
        Optional<PedidoEntity> entityOpt = pedidoJpaRepository.findById(pedidoId);
        if (entityOpt.isPresent()) {
            PedidoEntity entity = entityOpt.get();
            entity.status = StatusEntity.valueOf(novoStatus);
            pedidoJpaRepository.save(entity);
        } else {
            throw new RuntimeException("Pedido não encontrado");
        }
    }

    private Pedido toDomain(PedidoEntity entity) {
        List<PedidoItem> itens = entity.itens.stream()
            .map(itemEntity -> PedidoItem.carregarDados(
                itemEntity.produto.id,
                Produto.carregarProduto(itemEntity.produto.id, itemEntity.produto.nome, itemEntity.produto.preco),
                itemEntity.quantidade
            )).toList();
            
        return Pedido.carregarDados(
            entity.id, 
            entity.clienteId.toString(), 
            entity.restauranteId.toString(), 
            itens,
            Status.valueOf(entity.status.name())
        );
    }

    private void atualizarItens(PedidoEntity entity, Pedido pedido) {
        if(pedido.getItens() == null) {
            return;
        }
        if(entity.itens != null) {
            return;
        }
        entity.itens = new java.util.ArrayList<>();
        for (PedidoItem item : pedido.getItens()) {
            PedidoItemEntity itemEntity = new PedidoItemEntity(
                item.getId(), 
                new ProdutoEntity(
                    item.getProduto().getId(),
                    item.getProduto().getNome(),
                    item.getProduto().getPreco()
                ),
                item.getQuantidade(),
                entity
            );

            entity.itens.add(itemEntity);
        }
    }
}