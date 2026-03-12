package com.luizen.pedido.dominio.repositories;

import java.util.List;
import java.util.UUID;

import com.luizen.pedido.dominio.Produto;

public interface ProdutoRepository {
    
    public List<Produto> obterProdutos(List<UUID> ids);

    public List<Produto> obterTodosProdutos();
}
