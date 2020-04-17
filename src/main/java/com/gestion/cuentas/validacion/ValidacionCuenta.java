package com.gestion.cuentas.validacion;

import com.gestion.cuentas.dto.CuentaDto;

public final class ValidacionCuenta extends  Validacion {

    public static boolean esCuentaValidaParaGuardar(CuentaDto cuentaDto){
        return cuentaDto != null && esCampoCadenaValido(cuentaDto.getNombre()) &&
                esCampoCadenaValido(cuentaDto.getIdpresupuesto()) &&
                esCampoCadenaValido(cuentaDto.getClase()) &&
                esCampoNumericoMayorIgualACero(cuentaDto.getValor());
    }

    public static boolean esCuentaValidaParaActualizar(CuentaDto cuentaDto){
        return cuentaDto != null && esCampoCadenaValido(cuentaDto.getId())
                && esCampoCadenaValido(cuentaDto.getNombre());
    }
}
