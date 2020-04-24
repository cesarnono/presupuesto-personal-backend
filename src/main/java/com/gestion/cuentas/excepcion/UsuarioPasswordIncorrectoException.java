package com.gestion.cuentas.excepcion;

public class UsuarioPasswordIncorrectoException extends RuntimeException {

    public UsuarioPasswordIncorrectoException (String mensaje){
        super(mensaje);
    }
}
