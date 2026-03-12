package com.luizen.pedido.dominio;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Pedido {

    public UUID id;
    public String clienteId;
    public String restauranteId;
    public List<PedidoItem> itens;

    public static Pedido novoPedido(String clienteId, String restauranteId) {
        Pedido pedido = new Pedido();
        pedido.clienteId = clienteId;
        pedido.restauranteId = restauranteId;
        return pedido;
    }

    public static Pedido carregarDados(UUID id, String clienteId, String restauranteId, List<PedidoItem> itens) {
        Pedido pedido = new Pedido();
        pedido.id = id;
        pedido.clienteId = clienteId;
        pedido.restauranteId = restauranteId;
        pedido.itens = itens;
        return pedido;
    }

    public void adicionarItem(Produto produto, BigDecimal quantidade) {
        PedidoItem item = PedidoItem.novoItem(produto, quantidade);
        itens.add(item);
    }

    public BigDecimal valorTotal(){
        return itens.stream()
                    .map(PedidoItem::valorDoItem)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public UUID getId() {
        return id;
    }

    public String getClienteId() {
        return clienteId;
    }

    public String getRestauranteId() {
        return restauranteId;
    }

    public List<PedidoItem> getItens() {
        return itens;
    }

    
}
