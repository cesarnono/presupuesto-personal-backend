package com.gestion.cuentas.validacion;

import com.gestion.cuentas.dto.PresupuestoPersonalDto;

public class ValidacionEstadoFinanciero extends Validacion {

    public static boolean validarCreacion(PresupuestoPersonalDto presupuestoPersonalDto){
        return (presupuestoPersonalDto != null && esCampoCadenaValido(presupuestoPersonalDto.getNombre()) && esCampoCadenaValido(presupuestoPersonalDto.getIdusuario()));
    }
}
