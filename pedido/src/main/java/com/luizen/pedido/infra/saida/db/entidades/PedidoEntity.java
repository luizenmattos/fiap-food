package com.luizen.pedido.infra.saida.db.entidades;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "pedidos")
public class PedidoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    @Column(nullable = false)
    public UUID clienteId;

    @Column(nullable = false)
    public UUID restauranteId;

    @OneToMany(mappedBy = "pedido", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<PedidoItemEntity> itens;

    @Enumerated(jakarta.persistence.EnumType.STRING)
    public StatusEntity status;

    public PedidoEntity() {
    }

    public PedidoEntity(UUID id, UUID clienteId, UUID restauranteId, List<PedidoItemEntity> itens, StatusEntity status) {
        this.id = id;
        this.clienteId = clienteId;
        this.restauranteId = restauranteId;
        this.itens = itens;
        this.status = status;
    }

}
