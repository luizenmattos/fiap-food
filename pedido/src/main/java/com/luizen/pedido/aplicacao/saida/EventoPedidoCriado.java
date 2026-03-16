package com.luizen.pedido.aplicacao.saida;

import java.math.BigDecimal;

public interface EventoPedidoCriado {
    
    void dispararEvento (String pedidoId, BigDecimal valorTotal);
}
