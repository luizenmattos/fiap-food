package com.luizen.pedido.dominio.validacoes;

import com.luizen.pedido.dominio.Pedido;

public class Validacoes {

public static void validarCriacaoDePedido(Pedido pedido){
        ValidacaoDeDominio<?> chainValidations = new ValidacaoQuantidadeDeItensMaiorQueZero(pedido);
        chainValidations.proximaValidacao(new ValidacaoValorDoPedidoMaiorQueDezReais(pedido));
        
        chainValidations.validar();
    }
    
}
