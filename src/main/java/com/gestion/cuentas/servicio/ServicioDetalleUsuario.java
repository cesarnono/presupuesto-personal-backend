package com.gestion.cuentas.servicio;

import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.excepcion.UsuarioPasswordIncorrectoException;
import com.gestion.cuentas.modelo.Usuario;
import com.gestion.cuentas.repositorio.UsuarioRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Optional;

@Service
public class ServicioDetalleUsuario implements UserDetailsService {
    public static final String USUARIO_O_PASSWORD_INCORRECTO = "Usuario y/o password incorrecto.";
    @Autowired
    UsuarioRepositorio usuarioRepositorio;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Usuario> usuarioOptional = usuarioRepositorio.findByNombreusuario(username);
        if(!usuarioOptional.isPresent()){
            throw new UsuarioPasswordIncorrectoException(USUARIO_O_PASSWORD_INCORRECTO);
        }
        Usuario usuario = usuarioOptional.get();
        return new User(usuario.getNombreusuario(),usuario.getPassword(), new ArrayList<>());
    }
}
