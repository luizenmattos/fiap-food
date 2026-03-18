package com.luizen.pagamento.dominio;

import java.math.BigDecimal;
import java.util.UUID;

public class Pagamento {
    UUID id;
    String clienteId;
    BigDecimal valor;
    Status status;

    public static Pagamento criarPagamentoPendente(BigDecimal valor, String descricao, String clienteId) {
        Pagamento pagamento = new Pagamento();
        pagamento.clienteId = clienteId;
        pagamento.valor = valor;
        pagamento.status = Status.PENDENTE;
        return pagamento;
    }

    public static Pagamento carregarDaDataBase(UUID id, BigDecimal valor, Status status, String clienteId) {
        Pagamento pagamento = new Pagamento();
        pagamento.id = id;
        pagamento.valor = valor;
        pagamento.status = status;
        pagamento.clienteId = clienteId;
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

    public UUID getId() {
        return id;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public Status getStatus() {
        return status;
    }

    public String getClienteId() {
        return clienteId;
    }
}
