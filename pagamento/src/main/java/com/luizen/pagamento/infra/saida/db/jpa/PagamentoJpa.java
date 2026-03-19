package com.luizen.pagamento.infra.saida.db.jpa;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizen.pagamento.infra.saida.db.entidades.PagamentoEntity;
import com.luizen.pagamento.infra.saida.db.entidades.StatusEntity;

public interface PagamentoJpa extends JpaRepository<PagamentoEntity, UUID> {
    
    List<PagamentoEntity> findByStatus(StatusEntity status);
}