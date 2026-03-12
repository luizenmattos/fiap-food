package com.luizen.pedido.aplicacao.entrada;

import java.util.List;
import java.util.Map;

import com.luizen.pedido.dominio.Produto;
import com.luizen.pedido.dominio.repositories.ProdutoRepository;
import com.luizen.pedido.kernelcompartilhado.MyLogger;
import com.luizen.pedido.kernelcompartilhado.MyLoggerMessage;

public class ProdutoApplicationService {

    private static final MyLogger logger = MyLogger.getInstance(ProdutoApplicationService.class);

    private final ProdutoRepository produtoRepository;

    public ProdutoApplicationService(ProdutoRepository produtoRepository) {
        this.produtoRepository = produtoRepository; 
    }

    public List<Produto> listarProdutos(){
        try{
            // obter produtos
            var produtos = produtoRepository.obterTodosProdutos();

            //Log do evento
            logger.info(MyLoggerMessage.of("SUCESSO", "LISTAR_PRODUTOS", Map.of(
                "produtosCount", String.valueOf(produtos.size()),
                "produtos.ids", produtos.stream().map(p -> p.getId().toString()).toList().toString()
            )));

            return produtos;
            
        }catch(Exception e){
            //Log do evento
            logger.info(MyLoggerMessage.of("ERRO", "LISTAR_PRODUTOS", null));
            
           throw e;
        }
    }

}
