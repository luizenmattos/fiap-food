package com.luizen.autenticacao.infra.entrada;

import java.util.List;

public record ApiResponse(String status, String message, Object data, List<String> errors) {
    
}
