package com.gestion.cuentas.constante;

public enum EnumEstado {
    ACTIVO,
    INACTIVO;

   public String estado(){
       return this.name();
   }
}
