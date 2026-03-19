package com.luizen.pedido.infra.entrada.pedido;

import java.util.List;
import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.luizen.pedido.aplicacao.entrada.consultarPedido.ConsultarPedidoUseCase;
import com.luizen.pedido.aplicacao.entrada.listarPedido.ListarPedidosUseCase;
import com.luizen.pedido.aplicacao.entrada.realizarPedido.RealizarPedidoInput;
import com.luizen.pedido.aplicacao.entrada.realizarPedido.RealizarPedidoUseCase;
import com.luizen.pedido.dominio.Pedido;
import com.luizen.pedido.infra.entrada.ApiResponse;

@RestController
public class PedidoController {

    private final RealizarPedidoUseCase realizarPedido;
    private final ListarPedidosUseCase listarPedidos;
    private final ConsultarPedidoUseCase consultarPedido;

    public PedidoController(RealizarPedidoUseCase realizarPedido, ListarPedidosUseCase listarPedidos, ConsultarPedidoUseCase consultarPedido) {
        this.realizarPedido = realizarPedido;
        this.listarPedidos = listarPedidos;
        this.consultarPedido = consultarPedido;
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

        Pedido pedido = realizarPedido.executar(input);

        return ResponseEntity.ok(new ApiResponse(
            "success", 
            "Pedido realizado com sucesso", 
            pedido, 
            null));
    }

    @GetMapping("/pedidos")
    public ResponseEntity<ApiResponse> listarPedidos(@RequestHeader("Authorization") String token) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        List<Pedido> pedidos = listarPedidos.executar(token);

        return ResponseEntity.ok(new ApiResponse(
            "success", 
            "Pedidos listados com sucesso", 
            pedidos, 
            null));
    }

    @GetMapping("/pedidos/{pedidoId}")
    public ResponseEntity<ApiResponse> consultarPedido(@RequestHeader("Authorization") String token, @PathVariable UUID pedidoId) {
        if (token != null && token.startsWith("Bearer ")) {
            token = token.substring(7);
        }

        Pedido pedido = consultarPedido.executar(token, pedidoId);

        return ResponseEntity.ok(new ApiResponse(
            "success", 
            "Pedido consultado com sucesso", 
            pedido, 
            null));
    }

}

record RealizarPedidoRequest(String restauranteId, List<ItemRealizarPedidoRequest> itens) {}
record ItemRealizarPedidoRequest(String produtoId, int quantidade) {}