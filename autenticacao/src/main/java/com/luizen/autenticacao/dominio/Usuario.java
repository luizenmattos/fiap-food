package com.luizen.autenticacao.dominio;

import java.util.UUID;

public class Usuario {
    private UUID id;
    private String nome;
    private String email;
    private Senha senha;
    private Papel papel;

    public static Usuario registrar(String nome, String email, Senha senha, Papel papel) {
        Usuario usuario = new Usuario();
        usuario.nome = nome;
        usuario.email = email;
        usuario.senha = senha;
        usuario.papel = papel;
        return usuario;
    }

    public static Usuario carregar(UUID id, String nome, String email, Senha senha, Papel papel) {
        Usuario usuario = new Usuario();
        usuario.id = id;
        usuario.nome = nome;
        usuario.email = email;
        usuario.senha = senha;
        usuario.papel = papel;
        return usuario;
    }
    
    public void criptografarSenha() {
        this.senha = this.senha.senhaCriptografada();
    }

    public boolean autenticar(SenhaPlana senhaFornecida) {
        var senha1 = this.senha.senhaCriptografada();
        var senha2 = senhaFornecida.senhaCriptografada();
        return senha1.valor().equals(senha2.valor());
    }

    public UUID getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getEmail() {
        return email;
    }

    public Senha getSenha() {
        return senha;
    }

    public Papel getPapel() {
        return papel;
    }
}
