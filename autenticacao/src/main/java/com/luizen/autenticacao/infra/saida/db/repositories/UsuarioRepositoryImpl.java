package com.luizen.autenticacao.infra.saida.db.repositories;

import com.luizen.autenticacao.dominio.Papel;
import com.luizen.autenticacao.dominio.SenhaCriptografada;
import com.luizen.autenticacao.dominio.Usuario;
import com.luizen.autenticacao.dominio.repositories.UsuarioRepository;
import com.luizen.autenticacao.infra.saida.db.entidades.TipoUsuarioJpa;
import com.luizen.autenticacao.infra.saida.db.entidades.UsuarioJpa;

import org.springframework.stereotype.Repository;

import jakarta.transaction.Transactional;

import java.util.Optional;
import java.util.UUID;

@Repository
public class UsuarioRepositoryImpl implements UsuarioRepository {

    private final UsuarioJpaRepository usuarioJpaRepository;

    public UsuarioRepositoryImpl(UsuarioJpaRepository usuarioJpaRepository) {
        this.usuarioJpaRepository = usuarioJpaRepository;
    }

    @SuppressWarnings("null")
    @Override
    @Transactional
    public Optional<Usuario> salvar(Usuario usuario) {
        return Optional.ofNullable(usuarioJpaRepository.save(toJpa(usuario)))
                .map(this::toDomain);
    }

    @Override
    public Optional<Usuario> buscarPorEmail(String email) {
        return usuarioJpaRepository.findByEmail(email)
                .map(this::toDomain);
    }

    @SuppressWarnings("null")
    @Override
    @Transactional
    public Optional<Usuario> atualizar(Usuario usuario) {
        if (usuario.getId() == null) {
            throw new IllegalArgumentException("Usuário sem ID não pode ser atualizado");
        }
        return Optional.ofNullable(usuarioJpaRepository.save(toJpa(usuario)))
                .map(this::toDomain);
    }

    @SuppressWarnings("null")
    @Override
    @Transactional
    public void deletar(UUID id) {
        usuarioJpaRepository.deleteById(id);
    }

    @SuppressWarnings("null")
    @Override
    public Optional<Usuario> buscarPorId(UUID id) {
        return usuarioJpaRepository.findById(id)
                .map(this::toDomain);
    }

    private UsuarioJpa toJpa(Usuario usuario) {
        return new UsuarioJpa(
                usuario.getId(),
                usuario.getNome(),
                usuario.getEmail(),
                usuario.getSenha().valor(),
                toTipoUsuarioJpa(usuario.getPapel()));
    }

    private Usuario toDomain(UsuarioJpa usuarioJpa) {
        return Usuario.carregar(
                usuarioJpa.getId(),
                usuarioJpa.getNome(),
                usuarioJpa.getEmail(),
                new SenhaCriptografada(usuarioJpa.getSenha()),
                toPapel(usuarioJpa.getTipoUsuario()));
    }

    private TipoUsuarioJpa toTipoUsuarioJpa(Papel papel) {
        return switch (papel) {
            case CLIENTE -> TipoUsuarioJpa.CLIENTE;
            case DONO_DE_RESTAURANTE -> TipoUsuarioJpa.DONO_DE_RESTAURANTE;
        };
    }

    private Papel toPapel(TipoUsuarioJpa tipoUsuario) {
        return switch (tipoUsuario) {
            case CLIENTE -> Papel.CLIENTE;
            case DONO_DE_RESTAURANTE -> Papel.DONO_DE_RESTAURANTE;
        };
    }
}