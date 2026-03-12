package com.luizen.autenticacao.dominio.services;

import com.luizen.autenticacao.dominio.Papel;
import com.luizen.autenticacao.dominio.Senha;
import com.luizen.autenticacao.dominio.SenhaPlana;
import com.luizen.autenticacao.dominio.Usuario;
import com.luizen.autenticacao.dominio.exceptions.DominioException;
import com.luizen.autenticacao.dominio.validacoes.Validacoes;

public class UsuarioService {

    public Usuario registrarUsuario(String nome, String email, String senha, String papel){
        //Cria usuario
        Papel papelEnum = Papel.fromString(papel);
        Senha senhaObj = new SenhaPlana(senha);
        Usuario usuario = Usuario.registrar(nome, email, senhaObj, papelEnum);
        
        //Valida usuario
        Validacoes.validarCriacaoDeUsuario(usuario);
        
        //Criptografa senha
        usuario.criptografarSenha();

        return usuario;
    }

    public Usuario autenticarUsuario(Usuario usuario, String senha){
        if(usuario.autenticar(new SenhaPlana(senha))){
            return usuario;
        } else {
            throw new DominioException("Senha incorreta.");
        }
    }
}
