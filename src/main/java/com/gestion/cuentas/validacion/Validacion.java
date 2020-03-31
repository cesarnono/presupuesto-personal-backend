package com.gestion.cuentas.validacion;

public class Validacion {

    public static boolean esCampoCadenaValido(String campo){
        return campo != null && !campo.isEmpty();
    }

    public static boolean esCampoNumericoMayorIgualACero (double campo){
        return  campo >=  0;
    }
}
