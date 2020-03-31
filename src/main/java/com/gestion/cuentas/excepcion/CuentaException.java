package com.gestion.cuentas.excepcion;

public class CuentaException extends RuntimeException {

    public CuentaException(String mensaje){
        super(mensaje);
    }
}
