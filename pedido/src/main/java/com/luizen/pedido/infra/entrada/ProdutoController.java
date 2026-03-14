package com.luizen.pedido.infra.entrada;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luizen.pedido.aplicacao.entrada.produto.ProdutoApplicationService;

@RestController
public class ProdutoController {

    private final ProdutoApplicationService produtoApplicationService;
    
    public ProdutoController(ProdutoApplicationService produtoApplicationService) {
        this.produtoApplicationService = produtoApplicationService;
    }

    @GetMapping("/produtos")
    public ResponseEntity<ApiResponse> listarProdutos() {
        var produtos = produtoApplicationService.listarProdutos();
        return ResponseEntity.ok(new ApiResponse(
            "success", 
            "Produtos listados com sucesso", 
            produtos, 
            null));
    }

}
