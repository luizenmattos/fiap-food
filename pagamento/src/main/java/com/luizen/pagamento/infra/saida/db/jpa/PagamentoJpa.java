package com.luizen.pagamento.infra.saida.db.jpa;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizen.pagamento.infra.saida.db.entidades.PagamentoEntity;

public interface PagamentoJpa extends JpaRepository<PagamentoEntity, UUID> {
    
}