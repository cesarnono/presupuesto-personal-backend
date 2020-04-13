package com.gestion.cuentas.excepcion;

public class CuentaException extends RuntimeException {

    public CuentaException(String mensaje, Throwable cause) {
        super(mensaje, cause);
    }

    public CuentaException(String mensaje){
        super(mensaje);
    }
}
