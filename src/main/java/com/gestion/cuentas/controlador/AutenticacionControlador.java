package com.gestion.cuentas.controlador;

import com.gestion.cuentas.constante.EnumMensaje;
import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.modelo.PeticionAutenticacion;
import com.gestion.cuentas.modelo.RespuestaAutenticacion;
import com.gestion.cuentas.servicio.ServicioDetalleUsuario;
import com.gestion.cuentas.utilidad.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AutenticacionControlador {

    public static final String AUTENTICACION = "/autenticacion";
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ServicioDetalleUsuario servicioDetalleUsuario;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = AUTENTICACION, method = RequestMethod.POST)
    public ResponseEntity<?> crearTokenAutenticacion(@RequestBody PeticionAutenticacion peticionAutenticacion) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(peticionAutenticacion.getNombreusuario(),
                    peticionAutenticacion.getPassword()));
        } catch (BadCredentialsException e) {
            throw new CuentaException(EnumMensaje.NOMBRE_DE_USUARIO_O_PASSWORD_INCORRECTO.getMensaje(),e);
        }
        final UserDetails userDetails = servicioDetalleUsuario.loadUserByUsername(peticionAutenticacion.getNombreusuario());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new RespuestaAutenticacion(jwt));
    }
}
