package com.gestion.cuentas.mapeador;

import com.gestion.cuentas.constante.EnumFormatoFecha;
import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.modelo.Cuenta;
import com.gestion.cuentas.utilidad.FechaUtilidad;

import java.util.ArrayList;
import java.util.List;

public final class CuentaMapeador {

    public static Cuenta convertirAModelo(CuentaDto cuentaDto){
        if(cuentaDto == null){
            throw new NullPointerException("CuentaDTO null para crear Cuenta.");
        }
        if(cuentaDto.getId() == null){
            return  Cuenta.nuevaCuenta(
                    cuentaDto.getNombre(),
                    cuentaDto.getValor(),
                    cuentaDto.getClase(),
                    cuentaDto.getIdpresupuesto()
            );
        }
        return new Cuenta(cuentaDto.getId(),cuentaDto.getNombre(),
                cuentaDto.getValor(),cuentaDto.getClase(),cuentaDto.getIdpresupuesto(),
                cuentaDto.getFechacreacion(),
                FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S));
    }

    public static  CuentaDto convertirADto(Cuenta cuenta){
      CuentaDto cuentaDto = new CuentaDto();
      cuentaDto.setId(cuenta.getId());
      cuentaDto.setNombre(cuenta.getNombre());
      cuentaDto.setValor(cuenta.getValor());
      cuentaDto.setIdpresupuesto(cuenta.getIdpresupuesto());
      cuentaDto.setClase(cuenta.getClase());
      cuentaDto.setFechacreacion(cuenta.getFechacreacion());
      return cuentaDto;
    }

    public static List<CuentaDto> convertirAListaDto(List<Cuenta> cuentas){
        List<CuentaDto> cuentasDtos = new ArrayList<>();
        cuentas.forEach(cuenta ->{
            cuentasDtos.add(convertirADto(cuenta));
        });
        return cuentasDtos;
    }
}
