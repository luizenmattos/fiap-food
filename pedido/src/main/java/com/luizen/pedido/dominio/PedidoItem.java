package com.luizen.pedido.dominio;

import java.math.BigDecimal;
import java.util.UUID;

public class PedidoItem {
    
    public UUID id;
    public Produto produto;
    public BigDecimal quantidade;

    public static PedidoItem novoItem(Produto produto, BigDecimal quantidade) {
        PedidoItem item = new PedidoItem();
        item.produto = produto;
        item.quantidade = quantidade;
        return item;
    }

    public static PedidoItem carregarDados(UUID id, Produto produto, BigDecimal quantidade) {
        PedidoItem item = new PedidoItem();
        item.id = id;
        item.produto = produto;
        item.quantidade = quantidade;
        return item;
    }

    public BigDecimal valorDoItem() {
        return produto.precioUnitario().multiply(quantidade);
    }

    public UUID getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public BigDecimal getQuantidade() {
        return quantidade;
    }

}
