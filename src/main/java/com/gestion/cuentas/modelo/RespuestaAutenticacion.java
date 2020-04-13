package com.gestion.cuentas.modelo;

public class RespuestaAutenticacion {
    private final String jwt;

    public RespuestaAutenticacion(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
