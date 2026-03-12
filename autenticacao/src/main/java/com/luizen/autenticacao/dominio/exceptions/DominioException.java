package com.luizen.autenticacao.dominio.exceptions;

import java.util.List;

public class DominioException extends RuntimeException{

    private List<String> errors;
    
    public DominioException(String message){
        this.errors = List.of(message);
    }
    
    public DominioException(List<String> errors){
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }

    @Override
    public String getMessage() {
        return this.errors != null ? "[" + String.join(";", errors) + "]" : super.getMessage();
    }
}