package com.luizen.autenticacao.dominio;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SenhaPlana extends Senha {

    public SenhaPlana(String valor) {
        super(valor);
    }
    
    public SenhaCriptografada senhaCriptografada() {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] hash = md.digest(this.valor.getBytes());

            StringBuilder hexString = new StringBuilder();
            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return new SenhaCriptografada(hexString.toString());

        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Erro ao criptografar valor", e);
        }
    }
}
