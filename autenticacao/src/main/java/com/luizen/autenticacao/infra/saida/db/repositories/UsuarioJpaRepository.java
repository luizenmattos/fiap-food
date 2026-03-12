package com.luizen.autenticacao.infra.saida.db.repositories;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.luizen.autenticacao.infra.saida.db.entidades.UsuarioJpa;

public interface UsuarioJpaRepository extends JpaRepository<UsuarioJpa, UUID> {

    Optional<UsuarioJpa> findByEmail(String email);
}