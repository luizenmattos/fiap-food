package com.luizen.pedido.aplicacao.entrada;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.luizen.pedido.dominio.Pedido;
import com.luizen.pedido.dominio.Produto;
import com.luizen.pedido.dominio.repositories.PedidoRepository;
import com.luizen.pedido.dominio.repositories.ProdutoRepository;
import com.luizen.pedido.kernelcompartilhado.MyLogger;
import com.luizen.pedido.kernelcompartilhado.MyLoggerMessage;

public class PedidoApplicationService {

    private static final MyLogger logger = MyLogger.getInstance(PedidoApplicationService.class);
    private final PedidoRepository pedidoRepository;
    private final ProdutoRepository produtoRepository;

    public PedidoApplicationService(PedidoRepository pedidoRepository, ProdutoRepository produtoRepository) {
        this.pedidoRepository = pedidoRepository;
        this.produtoRepository = produtoRepository; 
    }

    public Pedido realizarPedido(RealizarPedidoInput input){
        try{    
            //obter o usuário a partir do token
            // var jwt = input.token();
            
            // converter itens do input para Map<UUID, Integer> 
            List<UUID> produtoIds = input.itens().stream().map(item -> UUID.fromString(item.produtoId())).toList();
            Map<UUID, Produto> produtos = produtoRepository.obterProdutos(produtoIds)
                .stream().collect(Collectors.toMap(Produto::getId, Function.identity()));

            //cria pedido
            var clienteId = "123";
            Pedido pedido = Pedido.novoPedido(clienteId, input.restauranteId());
            
            //adicionar itens ao pedido
            for(int i = 0; i < input.itens().size(); i++){
                var itemInput = input.itens().get(i);
                var produto = produtos.get(UUID.fromString(itemInput.produtoId()));
                pedido.adicionarItem(produto, BigDecimal.valueOf(itemInput.quantidade()));
            }

            //salvar pedido
            var pedidoSalvo = pedidoRepository.salvar(pedido)
                .orElseThrow(() -> new RuntimeException("Erro ao salvar pedido"));

            //Log do evento
            logger.info(MyLoggerMessage.of("SUCESSO", "REALIZAR_PEDIDO", Map.of(
                "input.restauranteId", input.restauranteId(), 
                "input.itens", input.itens().stream()
                    .map(item -> Map.of(
                        "produtoId", item.produtoId(),
                        "quantidade", item.quantidade()
                    )).toList().toString()
            )));

            return pedidoSalvo;
            
        }catch(Exception e){
            //Log do evento
            logger.error(MyLoggerMessage.of("ERRO", "REALIZAR_PEDIDO", Map.of(
                "input.restauranteId", input.restauranteId(), 
                "input.itens", input.itens().stream()
                            .map(item -> Map.of(
                                "produtoId", item.produtoId(),
                                "quantidade", item.quantidade()
                            )).toList().toString(),
                "error", e.getMessage()
            )));
            
           throw e;
        }
    }

}
