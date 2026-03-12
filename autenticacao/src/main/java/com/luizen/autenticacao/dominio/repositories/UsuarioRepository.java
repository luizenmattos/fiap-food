package com.luizen.autenticacao.dominio.repositories;

import java.util.Optional;
import java.util.UUID;

import com.luizen.autenticacao.dominio.Usuario;

public interface UsuarioRepository {
    public Optional<Usuario> salvar(Usuario usuario);
    public Optional<Usuario> atualizar(Usuario usuario);
    public void deletar(UUID id);
    public Optional<Usuario> buscarPorId(UUID id);
    public Optional<Usuario> buscarPorEmail(String email);
}
