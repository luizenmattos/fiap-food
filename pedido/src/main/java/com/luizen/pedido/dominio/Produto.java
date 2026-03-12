package com.luizen.pedido.dominio;

import java.math.BigDecimal;
import java.util.UUID;

public class Produto {

    public UUID id;
    public String nome;
    public BigDecimal preco;

    public static Produto carregarProduto(UUID id, String nome, BigDecimal preco) {
        // Simula a carga do produto a partir de um repositório
        Produto produto = new Produto();
        produto.id = id;
        produto.nome = nome;
        produto.preco = preco;
        return produto;
    }

    public BigDecimal precioUnitario() {
        return preco;
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public BigDecimal getPreco() {
        return preco;
    }

    
}
