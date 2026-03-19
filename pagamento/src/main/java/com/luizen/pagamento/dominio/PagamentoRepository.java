package com.luizen.pagamento.dominio;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository {
    Optional<Pagamento> salvar(Pagamento pagamento);
    Optional<Pagamento> buscarPorId(UUID id);
    List<Pagamento> obterPagamentosPorStatus(Status status);
}
