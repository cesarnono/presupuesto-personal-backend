package com.gestion.cuentas.mapeador;

import com.gestion.cuentas.constante.EnumFormatoFecha;
import com.gestion.cuentas.dto.MovimientoDto;
import com.gestion.cuentas.modelo.Movimiento;
import com.gestion.cuentas.utilidad.FechaUtilidad;

public final class MovimientoMapeador {
    public static Movimiento convertirAModelo(MovimientoDto movimientoDto){
        return new Movimiento(movimientoDto.getId(), movimientoDto.getDescripcion(), movimientoDto.getIdcuenta(),
                FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S));
    }
}
