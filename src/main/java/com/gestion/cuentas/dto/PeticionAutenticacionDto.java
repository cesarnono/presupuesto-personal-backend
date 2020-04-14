package com.gestion.cuentas.dto;

public class PeticionAutenticacionDto {

    public PeticionAutenticacionDto() {
    }

    public PeticionAutenticacionDto(String nombreusuario, String password) {
        this.nombreusuario = nombreusuario;
        this.password = password;
    }

    private String nombreusuario;
    private String password;


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }
}
