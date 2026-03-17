package com.luizen.pagamento.dominio;

import java.util.Optional;

public interface PagamentoRepository {
    Optional<Pagamento> salvar(Pagamento pagamento);
}
