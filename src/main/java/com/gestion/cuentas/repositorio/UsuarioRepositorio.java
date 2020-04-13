package com.gestion.cuentas.repositorio;

import com.gestion.cuentas.modelo.Usuario;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UsuarioRepositorio extends MongoRepository<Usuario,String> {
    Optional<Usuario> findByNombreusuario(String nombreusuario);
}
