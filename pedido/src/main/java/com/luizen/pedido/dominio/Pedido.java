package com.luizen.pedido.dominio;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class Pedido {

    public UUID id;
    public String clienteId;
    public String restauranteId;
    public List<PedidoItem> itens;
    public Status status;

    public static Pedido novoPedido(String clienteId, String restauranteId) {
        Pedido pedido = new Pedido();
        pedido.clienteId = clienteId;
        pedido.restauranteId = restauranteId;
        pedido.status = Status.EM_PROCESSAMENTO;
        return pedido;
    }

    public static Pedido carregarDados(UUID id, String clienteId, String restauranteId, List<PedidoItem> itens, Status status) {
        Pedido pedido = new Pedido();
        pedido.id = id;
        pedido.clienteId = clienteId;
        pedido.restauranteId = restauranteId;
        pedido.itens = itens;
        pedido.status = status;
        return pedido;
    }

    public void adicionarItem(Produto produto, BigDecimal quantidade) {
        PedidoItem item = PedidoItem.novoItem(produto, quantidade);
        if(itens != null){
            itens.add(item);
        } else {
            itens = List.of(item);
        }
    }

    public BigDecimal valorTotal(){
        return itens.stream()
                    .map(PedidoItem::valorDoItem)
                    .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void aprovar() {
        this.status = Status.APROVADO;
    }

    public boolean pendente() {
        return this.status == Status.PENDENTE_PAGAMENTO;
    }

    public boolean aprovado() {
        return this.status == Status.APROVADO;
    }

    public boolean rejeitado() {
        return this.status == Status.REJEITADO;
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

    public Status getStatus() {
        return status;
    }
}
