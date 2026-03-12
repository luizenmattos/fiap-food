package com.luizen.pedido.aplicacao.entrada;

import java.util.List;

public record RealizarPedidoInput(String token, String restauranteId, List<PedidoItemInput> itens) {

    public record PedidoItemInput(String produtoId, int quantidade) {}

}
