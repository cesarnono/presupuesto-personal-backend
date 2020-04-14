package com.gestion.cuentas.dto;

public class RespuestaAutenticacionDto {
    private final String jwt;

    public RespuestaAutenticacionDto(String jwt) {
        this.jwt = jwt;
    }

    public String getJwt() {
        return jwt;
    }
}
