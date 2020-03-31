package com.gestion.cuentas.utilidad;

import com.gestion.cuentas.constante.EnumFormatoFecha;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class FechaUtilidad {

    public static String obtenerFechaActual(EnumFormatoFecha enumFormatoFecha) {
        String fechaActual = null;
        switch (enumFormatoFecha) {
            case FORMATO_D_M_A_H_M_S:
                fechaActual = LocalDateTime.now().format(DateTimeFormatter.ofPattern(enumFormatoFecha.getFormato()));
                break;
            default:
                fechaActual = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
                break;
        }
        return fechaActual;
    }
}
