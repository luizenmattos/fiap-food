package com.luizen.pagamento.dominio;

import java.util.Optional;
import java.util.UUID;

public interface PagamentoRepository {
    Optional<Pagamento> salvar(Pagamento pagamento);
    Optional<Pagamento> buscarPorId(UUID id);
}
