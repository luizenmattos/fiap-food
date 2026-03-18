package com.luizen.pagamento.infra.saida.db.entidades;

import java.math.BigDecimal;
import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "pagamentos")
public class PagamentoEntity {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.UUID)
    public UUID id;

    public String clienteId;
    
    public BigDecimal valor;

    @Enumerated(jakarta.persistence.EnumType.STRING)
    public StatusEntity status;
}
