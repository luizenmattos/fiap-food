package com.luizen.pagamento.dominio;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public class Pagamento {
    UUID id;
    BigDecimal valor;
    String descricao;
    LocalDateTime dataPagamento;
    Status status;

    public static Pagamento criarPagamentoPendente(BigDecimal valor, String descricao) {
        Pagamento pagamento = new Pagamento();
        pagamento.valor = valor;
        pagamento.descricao = descricao;
        pagamento.dataPagamento = LocalDateTime.now();
        pagamento.status = Status.PENDENTE;
        return pagamento;
    }

    public boolean aprovado() {
        return this.status == Status.APROVADO;
    }

    public boolean rejeitado() {
        return this.status == Status.REJEITADO;
    }

    public boolean pendente() {
        return this.status == Status.PENDENTE;
    }

    public void aprovar() {
        if (this.status == Status.PENDENTE) {
            this.status = Status.APROVADO;
        }
    }

    public void rejeitar() {
        if (this.status == Status.PENDENTE) {
            this.status = Status.REJEITADO;
        }
    }
}
