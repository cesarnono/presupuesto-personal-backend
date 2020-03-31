package com.gestion.cuentas.conversor;

import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.modelo.Cuenta;

import java.util.ArrayList;
import java.util.List;

public final class CuentaConversor {

    public static Cuenta convertirACuenta(CuentaDto cuentaDto){
      return new Cuenta(
              cuentaDto.getNombre(),
              cuentaDto.getValor(),
              cuentaDto.getClase(),
              cuentaDto.getIdpresupuesto()
      );
    }

    public static  CuentaDto convertirACuentaDto(Cuenta cuenta){
      CuentaDto cuentaDto = new CuentaDto();
      cuentaDto.setId(cuenta.getId());
      cuentaDto.setNombre(cuenta.getNombre());
      cuentaDto.setValor(cuenta.getValor());
      cuentaDto.setIdpresupuesto(cuenta.getIdpresupuesto());
      cuentaDto.setClase(cuenta.getClase());
      return cuentaDto;
    }

    public static List<CuentaDto> convertirAListaCuentaDto(List<Cuenta> cuentas){
        List<CuentaDto> cuentasDtos = new ArrayList<>();
        cuentas.forEach(e ->{
            CuentaDto cuentaDto = new CuentaDto();
            cuentaDto.setId(e.getId());
            cuentaDto.setClase(e.getClase());
            cuentaDto.setIdpresupuesto(e.getIdpresupuesto());
            cuentaDto.setValor(e.getValor());
            cuentaDto.setNombre(e.getNombre());
            cuentasDtos.add(cuentaDto);
        });
        return cuentasDtos;
    }
}
