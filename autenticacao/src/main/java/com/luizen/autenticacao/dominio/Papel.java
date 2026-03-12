package com.luizen.autenticacao.dominio;

public enum Papel {
    CLIENTE,
    DONO_DE_RESTAURANTE;

    public static Papel fromString(String papel) {
        try {
            return Papel.valueOf(papel.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null; 
        }
    }
}
