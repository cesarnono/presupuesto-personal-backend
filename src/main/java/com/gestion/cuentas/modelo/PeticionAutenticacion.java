package com.gestion.cuentas.modelo;

public class PeticionAutenticacion {

    public PeticionAutenticacion() {
    }

    public PeticionAutenticacion(String nombreusuario, String password) {
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
