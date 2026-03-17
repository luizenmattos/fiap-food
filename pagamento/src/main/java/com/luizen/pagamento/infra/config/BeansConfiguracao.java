package com.luizen.pagamento.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.luizen.pagamento.aplicacao.saida.jobVerificarStatusPagamento.VerificarStatusPagamento;
import com.luizen.pagamento.aplicacao.saida.eventoPagamentoAprovado.EventoPagamentoAprovado;
import com.luizen.pagamento.aplicacao.saida.eventoPagamentoRejeitado.EventoPagamentoRejeitado;
import com.luizen.pagamento.aplicacao.saida.servicoPagamentoExterno.PagamentoExternoService;
import com.luizen.pagamento.infra.saida.http.PagamentoExternoFiapService;
import com.luizen.pagamento.infra.saida.rabbitmq.PagamentoAprovado;
import com.luizen.pagamento.infra.saida.rabbitmq.PagamentoRejeitado;


@Configuration
public class BeansConfiguracao {

    @Bean
    EventoPagamentoRejeitado eventoPagamentoRejeitado() {
        return new PagamentoRejeitado();    
    }

    @Bean EventoPagamentoAprovado eventoPagamentoAprovado() {
        return new PagamentoAprovado();
    }


    @Bean
    PagamentoExternoService pagamentoExternoService(){
        return new PagamentoExternoFiapService();
    }
    
    @Bean
    VerificarStatusPagamento verificarStatusPagamento(PagamentoExternoService pagamentoExternoService, EventoPagamentoAprovado eventoPagamentoAprovado, EventoPagamentoRejeitado eventoPagamentoRejeitado) {
        return new VerificarStatusPagamento(pagamentoExternoService, eventoPagamentoAprovado, eventoPagamentoRejeitado);
    }

}
