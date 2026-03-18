package com.luizen.pagamento.infra.saida.db.repositorios;

import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.luizen.pagamento.dominio.Pagamento;
import com.luizen.pagamento.dominio.PagamentoRepository;
import com.luizen.pagamento.infra.saida.db.entidades.PagamentoEntity;
import com.luizen.pagamento.infra.saida.db.entidades.StatusEntity;
import com.luizen.pagamento.infra.saida.db.jpa.PagamentoJpa;

@Repository
public class PagamentoRepositoryImpl implements PagamentoRepository {

    private final PagamentoJpa pagamentoJpa;

    public PagamentoRepositoryImpl(PagamentoJpa pagamentoJpa) {
        this.pagamentoJpa = pagamentoJpa;
    }

    @Override
    public Optional<Pagamento> salvar(Pagamento pagamento) {
        var pagamentoEntity = toJpa(pagamento);
        var pagamentoEntitySalva = pagamentoJpa.save(pagamentoEntity);

        return Optional.of(toDomain(pagamentoEntitySalva));
    }

    @Override
    public Optional<Pagamento> buscarPorId(UUID id) {
        var pagamentoEntityOptional = pagamentoJpa.findById(id);
        return pagamentoEntityOptional.map(this::toDomain);
    }
    
    private PagamentoEntity toJpa(Pagamento pagamento) {
        var pagamentoEntity = new PagamentoEntity();
        pagamentoEntity.id = pagamento.getId();
        pagamentoEntity.clienteId = pagamento.getClienteId();
        pagamentoEntity.valor = pagamento.getValor();
        pagamentoEntity.status = StatusEntity.valueOf(pagamento.getStatus().name());
        return pagamentoEntity;
    }

    private Pagamento toDomain(PagamentoEntity pagamentoEntity) {
        var pagamento = Pagamento.carregarDaDataBase(
            pagamentoEntity.id, 
            pagamentoEntity.valor, 
            com.luizen.pagamento.dominio.Status.valueOf(pagamentoEntity.status.name()),
            pagamentoEntity.clienteId
        );
        return pagamento;
    }
}
