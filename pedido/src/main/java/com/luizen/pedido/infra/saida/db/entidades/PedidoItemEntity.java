package com.luizen.pedido.infra.saida.db.entidades;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedido_itens")
public class PedidoItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    public ProdutoEntity produto;

    @Column(nullable = false)
    public BigDecimal quantidade;

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    public PedidoEntity pedido;

    public PedidoItemEntity() {
    }
    
    public PedidoItemEntity(UUID id, ProdutoEntity produto, BigDecimal quantidade, PedidoEntity pedido) {
        this.id = id;
        this.produto = produto;
        this.quantidade = quantidade;
        this.pedido = pedido;
    }

}
