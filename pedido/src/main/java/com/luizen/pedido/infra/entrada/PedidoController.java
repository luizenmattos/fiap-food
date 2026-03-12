package com.luizen.pedido.infra.entrada;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.luizen.pedido.aplicacao.entrada.PedidoApplicationService;

@RestController
public class PedidoController {

    private final PedidoApplicationService pedidoApplicationService;
    
    public PedidoController(PedidoApplicationService pedidoApplicationService) {
        this.pedidoApplicationService = pedidoApplicationService;
    }

    @PostMapping("/pedidos")
    public ResponseEntity<ApiResponse> fazerPedido() {
        pedidoApplicationService.realizarPedido(null);

        return ResponseEntity.ok(new ApiResponse(
            "success", 
            "Pedido realizado com sucesso", 
            null, 
            null));
    }

    @PostMapping("/autenticar")
    public ResponseEntity<ApiResponse> autenticarUsuario() {
        return ResponseEntity.ok(new ApiResponse(
            "success", 
            "Autenticação realizada com sucesso", 
            null,
            null));
    }
}
