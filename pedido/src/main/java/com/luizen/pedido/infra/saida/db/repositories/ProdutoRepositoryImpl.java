package com.luizen.pedido.infra.saida.db.repositories;

import com.luizen.pedido.dominio.Produto;
import com.luizen.pedido.dominio.repositories.ProdutoRepository;
import com.luizen.pedido.infra.saida.db.entidades.ProdutoEntity;
import com.luizen.pedido.infra.saida.db.jpa.ProdutoJpa;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public class ProdutoRepositoryImpl implements ProdutoRepository {

    private final ProdutoJpa produtoJpaRepository;

    public ProdutoRepositoryImpl(ProdutoJpa produtoJpaRepository) {
        this.produtoJpaRepository = produtoJpaRepository;
    }

    @Override
    public List<Produto> obterProdutos(List<UUID> ids) {
        List<ProdutoEntity> entities = produtoJpaRepository.findAllById(ids);
        return entities.stream().map(this::toDomain).toList();
    }

    @Override
    public List<Produto> obterTodosProdutos() {
        List<ProdutoEntity> entities = produtoJpaRepository.findAll();
        System.out.println("Produtos obtidos do banco: " + entities);
        return entities.stream().map(this::toDomain).toList();
    }

    public Produto toDomain(ProdutoEntity entity) {
        return Produto.carregarProduto(entity.id, entity.nome, entity.preco);
    }
}