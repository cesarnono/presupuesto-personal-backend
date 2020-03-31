package com.gestion.cuentas.constante;

public enum EnumFormatoFecha {
    FORMATO_D_M_A_H_M_S();

    private final String formato;

    EnumFormatoFecha() {
        this.formato = "dd-MM-yyyy HH:mm:ss";
    }

    public String getFormato(){
        return this.formato;
    }
}
