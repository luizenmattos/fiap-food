package com.luizen.pagamento.infra.saida.jobs;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.luizen.pagamento.aplicacao.saida.jobVerificarStatusPagamento.VerificarStatusPagamento;

@Component
public class VerificarStatusPagamentoJob {

    VerificarStatusPagamento verificarStatusPagamento;

    public VerificarStatusPagamentoJob(VerificarStatusPagamento verificarStatusPagamento) {
        this.verificarStatusPagamento = verificarStatusPagamento;
    }

    @Scheduled(fixedDelay = 10000) // Executa a cada 10 segundos
    public void verificarStatusPagamento() {
        verificarStatusPagamento.executar(); 
    }
}
