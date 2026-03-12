package com.luizen.pedido.infra.entrada.pedido;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.luizen.pedido.aplicacao.entrada.PedidoApplicationService;
import com.luizen.pedido.aplicacao.entrada.RealizarPedidoInput;
import com.luizen.pedido.dominio.Pedido;
import com.luizen.pedido.infra.entrada.ApiResponse;

@RestController
public class PedidoController {

    private final PedidoApplicationService pedidoApplicationService;
    
    public PedidoController(PedidoApplicationService pedidoApplicationService) {
        this.pedidoApplicationService = pedidoApplicationService;
    }

    @PostMapping("/pedidos")
    public ResponseEntity<ApiResponse> realizarPedido(@RequestHeader("Authorization") String token, @RequestBody RealizarPedidoRequest request) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        RealizarPedidoInput input = new RealizarPedidoInput(
            token,
            request.restauranteId(), 
            request.itens().stream().map(item -> new RealizarPedidoInput.PedidoItemInput(item.produtoId(), item.quantidade())).toList()
        );

        Pedido pedido = pedidoApplicationService.realizarPedido(input);

        return ResponseEntity.ok(new ApiResponse(
            "success", 
            "Pedido realizado com sucesso", 
            pedido, 
            null));
    }

}

record RealizarPedidoRequest(String restauranteId, List<ItemRealizarPedidoRequest> itens) {}
record ItemRealizarPedidoRequest(String produtoId, int quantidade) {}