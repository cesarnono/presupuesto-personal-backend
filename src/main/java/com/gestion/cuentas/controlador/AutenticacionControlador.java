package com.gestion.cuentas.controlador;

import com.gestion.cuentas.constante.EnumMensaje;
import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.dto.PeticionAutenticacionDto;
import com.gestion.cuentas.dto.RespuestaAutenticacionDto;
import com.gestion.cuentas.servicio.ServicioDetalleUsuario;
import com.gestion.cuentas.utilidad.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*", methods = {RequestMethod.POST})
public class AutenticacionControlador {

    public static final String AUTENTICACION = "/autenticacion";
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ServicioDetalleUsuario servicioDetalleUsuario;

    @Autowired
    private JwtUtil jwtUtil;

    @RequestMapping(value = AUTENTICACION, method = RequestMethod.POST)
    public ResponseEntity<?> crearTokenAutenticacion(@RequestBody PeticionAutenticacionDto peticionAutenticacionDto) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(peticionAutenticacionDto.getNombreusuario(),
                    peticionAutenticacionDto.getPassword()));
        } catch (BadCredentialsException e) {
            throw new CuentaException(EnumMensaje.NOMBRE_DE_USUARIO_O_PASSWORD_INCORRECTO.getMensaje(),e);
        }
        final UserDetails userDetails = servicioDetalleUsuario.loadUserByUsername(peticionAutenticacionDto.getNombreusuario());
        final String jwt = jwtUtil.generateToken(userDetails);
        return ResponseEntity.ok(new RespuestaAutenticacionDto(jwt));
    }
}
