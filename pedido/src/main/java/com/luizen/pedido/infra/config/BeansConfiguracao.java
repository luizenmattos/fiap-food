package com.luizen.pedido.infra.config;

import com.luizen.pedido.aplicacao.entrada.consultarPedido.ConsultarPedidoUseCase;
import com.luizen.pedido.aplicacao.entrada.listarPedido.ListarPedidosUseCase;
import com.luizen.pedido.aplicacao.entrada.produto.ProdutoApplicationService;
import com.luizen.pedido.aplicacao.entrada.realizarPedido.RealizarPedidoUseCase;
import com.luizen.pedido.aplicacao.entrada.atualizarStatusPedido.AtualizarStatusPedido;
import com.luizen.pedido.aplicacao.entrada.token.TokenService;
import com.luizen.pedido.aplicacao.saida.EventoPedidoCriado;
import com.luizen.pedido.dominio.repositories.PedidoRepository;
import com.luizen.pedido.dominio.repositories.ProdutoRepository;
import com.luizen.pedido.infra.entrada.JwtTokenService;
import com.luizen.pedido.infra.entrada.pagamentoAprovadoConsumer.PagamentoAprovadorConsumer;
import com.luizen.pedido.infra.entrada.pagamentoPendenteConsumer.PagamentoPendenteConsumer;
import com.luizen.pedido.infra.saida.rabbitmq.PedidoCriadoProduces;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguracao {

    @Value("${app.rabbitmq.pedido-criado.exchange:pedido.exchange}")
    private String pedidoCriadoExchange;

    @Value("${app.rabbitmq.pedido-criado.queue:pedido.criado.queue}")
    private String pedidoCriadoQueue;

    @Value("${app.rabbitmq.pedido-criado.routing-key:pedido.criado}")
    private String pedidoCriadoRoutingKey;

    @Value("${app.rabbitmq.pagamento.exchange:pagamento.exchange}")
    private String pagamentoExchange;

    @Value("${app.rabbitmq.pagamento-aprovado.queue:pagamento.aprovado.queue}")
    private String pagamentoAprovadoQueue;

    @Value("${app.rabbitmq.pagamento-pendente.queue:pagamento.pendente.queue}")
    private String pagamentoPendenteQueue;

    @Value("${app.rabbitmq.pagamento-aprovado.routing-key:pagamento.aprovado}")
    private String pagamentoAprovadoRoutingKey;

    @Value("${app.rabbitmq.pagamento-pendente.routing-key:pagamento.pendente}")
    private String pagamentoPendenteRoutingKey;

    @Bean
    public TokenService tokenService() {
        return new JwtTokenService();
    }

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
    public DirectExchange pedidoCriadoExchange() {
        return new DirectExchange(pedidoCriadoExchange);
    }

    @Bean
    public Queue pedidoCriadoQueue() {
        return new Queue(pedidoCriadoQueue, true);
    }

    @Bean
    public Binding pedidoCriadoBinding(Queue pedidoCriadoQueue, DirectExchange pedidoCriadoExchange) {
        return BindingBuilder.bind(pedidoCriadoQueue).to(pedidoCriadoExchange).with(pedidoCriadoRoutingKey);
    }

    @Bean
    public DirectExchange pagamentoExchange() {
        return new DirectExchange(pagamentoExchange);
    }

    @Bean
    public Queue pagamentoAprovadoQueue() {
        return new Queue(pagamentoAprovadoQueue, true);
    }

    @Bean
    public Queue pagamentoPendenteQueue() {
        return new Queue(pagamentoPendenteQueue, true);
    }

    @Bean
    public Binding pagamentoAprovadoBinding(Queue pagamentoAprovadoQueue, DirectExchange pagamentoExchange) {
        return BindingBuilder.bind(pagamentoAprovadoQueue).to(pagamentoExchange).with(pagamentoAprovadoRoutingKey);
    }

    @Bean
    public Binding pagamentoPendenteBinding(Queue pagamentoPendenteQueue, DirectExchange pagamentoExchange) {
        return BindingBuilder.bind(pagamentoPendenteQueue).to(pagamentoExchange).with(pagamentoPendenteRoutingKey);
    }

    @Bean
    public EventoPedidoCriado eventoPedidoCriado(RabbitTemplate rabbitTemplate) {
        return new PedidoCriadoProduces(rabbitTemplate, pedidoCriadoExchange, pedidoCriadoRoutingKey);
    }

    @Bean
    public ListarPedidosUseCase listarPedidos(PedidoRepository pedidoRepository, TokenService tokenService) {
        return new ListarPedidosUseCase(pedidoRepository, tokenService);
    }

    @Bean
    public ConsultarPedidoUseCase consultarPedido(PedidoRepository pedidoRepository, TokenService tokenService) {
        return new ConsultarPedidoUseCase(pedidoRepository, tokenService);
    }

    @Bean
    public RealizarPedidoUseCase realizarPedido(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository, TokenService tokenService, EventoPedidoCriado eventoPedidoCriado) {
        return new RealizarPedidoUseCase(pedidoRepository, produtoRepository, tokenService, eventoPedidoCriado);
    }

    @Bean
    public AtualizarStatusPedido atualizarStatusPedido(PedidoRepository pedidoRepository) {
        return new AtualizarStatusPedido(pedidoRepository);
    }

    @Bean
    public PagamentoAprovadorConsumer pagamentoAprovadorConsumer(AtualizarStatusPedido atualizarStatusPedido) {
        return new PagamentoAprovadorConsumer(atualizarStatusPedido);
    }

    @Bean
    public PagamentoPendenteConsumer pagamentoPendenteConsumer(AtualizarStatusPedido atualizarStatusPedido) {
        return new PagamentoPendenteConsumer(atualizarStatusPedido);
    }
    
    @Bean
    public ProdutoApplicationService produtoApplicationService(ProdutoRepository produtoRepository) {
        return new ProdutoApplicationService(produtoRepository);
    }
}