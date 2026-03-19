package com.luizen.pagamento.infra.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.luizen.pagamento.aplicacao.entrada.criarPagamentoPendente.RealizarPagamentoUseCase;
import com.luizen.pagamento.aplicacao.saida.eventoPagamentoPendente.EventoPagamentoPendente;
import com.luizen.pagamento.aplicacao.saida.jobVerificarStatusPagamento.VerificarStatusPagamento;
import com.luizen.pagamento.aplicacao.saida.eventoPagamentoAprovado.EventoPagamentoAprovado;
import com.luizen.pagamento.aplicacao.saida.eventoPagamentoRejeitado.EventoPagamentoRejeitado;
import com.luizen.pagamento.infra.entrada.rabbitmq.PedidoCriadoConsumer;
import com.luizen.pagamento.aplicacao.saida.servicoPagamentoExterno.PagamentoExternoService;
import com.luizen.pagamento.dominio.PagamentoRepository;
import com.luizen.pagamento.infra.saida.http.PagamentoExternoFiapService;
import com.luizen.pagamento.infra.saida.rabbitmq.PagamentoAprovado;
import com.luizen.pagamento.infra.saida.rabbitmq.PagamentoPendente;
import com.luizen.pagamento.infra.saida.rabbitmq.PagamentoRejeitado;


@Configuration
public class BeansConfiguracao {

    @Value("${app.rabbitmq.pagamento.exchange:pagamento.exchange}")
    private String pagamentoExchange;

    @Value("${app.rabbitmq.pagamento-pendente.queue:pagamento.pendente.queue}")
    private String pagamentoPendenteQueue;

    @Value("${app.rabbitmq.pagamento-aprovado.queue:pagamento.aprovado.queue}")
    private String pagamentoAprovadoQueue;

    @Value("${app.rabbitmq.pagamento-rejeitado.queue:pagamento.rejeitado.queue}")
    private String pagamentoRejeitadoQueue;

    @Value("${app.rabbitmq.pagamento-pendente.routing-key:pagamento.pendente}")
    private String pagamentoPendenteRoutingKey;

    @Value("${app.rabbitmq.pagamento-aprovado.routing-key:pagamento.aprovado}")
    private String pagamentoAprovadoRoutingKey;

    @Value("${app.rabbitmq.pagamento-rejeitado.routing-key:pagamento.rejeitado}")
    private String pagamentoRejeitadoRoutingKey;

    @Value("${app.rabbitmq.pedido-criado.exchange:pedido.exchange}")
    private String pedidoCriadoExchange;

    @Value("${app.rabbitmq.pedido-criado.queue:pedido.criado.queue}")
    private String pedidoCriadoQueue;

    @Value("${app.rabbitmq.pedido-criado.routing-key:pedido.criado}")
    private String pedidoCriadoRoutingKey;

    @Bean
    public MessageConverter rabbitMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory, MessageConverter rabbitMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(rabbitMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public DirectExchange pagamentoExchange() {
        return new DirectExchange(pagamentoExchange);
    }

    @Bean
    public DirectExchange pedidoCriadoExchange() {
        return new DirectExchange(pedidoCriadoExchange);
    }

    @Bean
    public Queue pagamentoPendenteQueue() {
        return new Queue(pagamentoPendenteQueue, true);
    }

    @Bean
    public Queue pagamentoAprovadoQueue() {
        return new Queue(pagamentoAprovadoQueue, true);
    }

    @Bean
    public Queue pagamentoRejeitadoQueue() {
        return new Queue(pagamentoRejeitadoQueue, true);
    }

    @Bean
    public Queue pedidoCriadoQueue() {
        return new Queue(pedidoCriadoQueue, true);
    }

    @Bean
    public Binding pagamentoPendenteBinding(
        @Qualifier("pagamentoPendenteQueue") Queue pagamentoPendenteQueue,
        @Qualifier("pagamentoExchange") DirectExchange pagamentoExchange
    ) {
        return BindingBuilder.bind(pagamentoPendenteQueue).to(pagamentoExchange).with(pagamentoPendenteRoutingKey);
    }

    @Bean
    public Binding pagamentoAprovadoBinding(
        @Qualifier("pagamentoAprovadoQueue") Queue pagamentoAprovadoQueue,
        @Qualifier("pagamentoExchange") DirectExchange pagamentoExchange
    ) {
        return BindingBuilder.bind(pagamentoAprovadoQueue).to(pagamentoExchange).with(pagamentoAprovadoRoutingKey);
    }

    @Bean
    public Binding pagamentoRejeitadoBinding(
        @Qualifier("pagamentoRejeitadoQueue") Queue pagamentoRejeitadoQueue,
        @Qualifier("pagamentoExchange") DirectExchange pagamentoExchange
    ) {
        return BindingBuilder.bind(pagamentoRejeitadoQueue).to(pagamentoExchange).with(pagamentoRejeitadoRoutingKey);
    }

    @Bean
    public Binding pedidoCriadoBinding(
        @Qualifier("pedidoCriadoQueue") Queue pedidoCriadoQueue,
        @Qualifier("pedidoCriadoExchange") DirectExchange pedidoCriadoExchange
    ) {
        return BindingBuilder.bind(pedidoCriadoQueue).to(pedidoCriadoExchange).with(pedidoCriadoRoutingKey);
    }

    @Bean
    EventoPagamentoPendente eventoPagamentoPendente(RabbitTemplate rabbitTemplate) {
        return new PagamentoPendente(rabbitTemplate, pagamentoExchange, pagamentoPendenteRoutingKey);
    }

    @Bean
    EventoPagamentoRejeitado eventoPagamentoRejeitado(RabbitTemplate rabbitTemplate) {
        return new PagamentoRejeitado(rabbitTemplate, pagamentoExchange, pagamentoRejeitadoRoutingKey);
    }

    @Bean
    EventoPagamentoAprovado eventoPagamentoAprovado(RabbitTemplate rabbitTemplate) {
        return new PagamentoAprovado(rabbitTemplate, pagamentoExchange, pagamentoAprovadoRoutingKey);
    }

    @Bean
    RealizarPagamentoUseCase realizarPagamentoUseCase(
        PagamentoExternoService pagamentoExternoService, 
        EventoPagamentoPendente eventoPagamentoPendente, 
        EventoPagamentoAprovado eventoPagamentoAprovado, 
        EventoPagamentoRejeitado eventoPagamentoRejeitado,
        PagamentoRepository pagamentoRepositorio
    ) {
        return new RealizarPagamentoUseCase(pagamentoExternoService, eventoPagamentoPendente, eventoPagamentoAprovado, eventoPagamentoRejeitado, pagamentoRepositorio);
    }

    @Bean
    PedidoCriadoConsumer pedidoCriadoConsumer(RealizarPagamentoUseCase realizarPagamentoUseCase) {
        return new PedidoCriadoConsumer(realizarPagamentoUseCase);
    }


    @Bean
    PagamentoExternoService pagamentoExternoService(
        @Value("${app.pagamento-externo.url:http://localhost:8089/requisicao}") String pagamentoExternoUrl
    ){
        return new PagamentoExternoFiapService(pagamentoExternoUrl);
    }
    
    @Bean
    VerificarStatusPagamento verificarStatusPagamento(PagamentoRepository pagamentoRepositorio, PagamentoExternoService pagamentoExternoService, EventoPagamentoAprovado eventoPagamentoAprovado, EventoPagamentoRejeitado eventoPagamentoRejeitado) {
        return new VerificarStatusPagamento(pagamentoRepositorio, pagamentoExternoService, eventoPagamentoAprovado, eventoPagamentoRejeitado);
    }

}
