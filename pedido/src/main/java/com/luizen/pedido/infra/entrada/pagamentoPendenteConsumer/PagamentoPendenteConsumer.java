package com.luizen.pedido.infra.entrada.pagamentoPendenteConsumer;

import java.util.Map;
import java.util.UUID;

import org.springframework.amqp.rabbit.annotation.RabbitListener;

import com.luizen.pedido.aplicacao.entrada.atualizarStatusPedido.AtualizarStatusPedido;
import com.luizen.pedido.dominio.Status;

public class PagamentoPendenteConsumer {

	private final AtualizarStatusPedido atualizarStatusPedido;

	public PagamentoPendenteConsumer(AtualizarStatusPedido atualizarStatusPedido) {
		this.atualizarStatusPedido = atualizarStatusPedido;
	}

	@RabbitListener(queues = "${app.rabbitmq.pagamento-pendente.queue:pagamento.pendente.queue}")
	public void consumir(Map<String, Object> evento) {
        try{
            Object identificador = evento.get("pedidoId");
    
            if (identificador == null) {
                System.out.println("Evento pagamento.pendente sem pedidoId");
                return;
            }
    
            UUID pedidoId = UUID.fromString(identificador.toString());
            atualizarStatusPedido.executar(pedidoId, Status.PENDENTE_PAGAMENTO);
        }catch(Exception e){
            System.out.println("Erro ao processar evento pagamento.pendente: " + e.getMessage());
        }
	}
    
}
