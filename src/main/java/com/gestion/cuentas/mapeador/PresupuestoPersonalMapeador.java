package com.gestion.cuentas.mapeador;

import com.gestion.cuentas.constante.EnumFormatoFecha;
import com.gestion.cuentas.constante.EnumTipoEstadoFinanciero;
import com.gestion.cuentas.dto.PresupuestoPersonalDto;
import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.modelo.PresupuestoPersonal;
import com.gestion.cuentas.utilidad.FechaUtilidad;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public final class PresupuestoPersonalMapeador {

    public static PresupuestoPersonal convertirAModelo(PresupuestoPersonalDto presupuestoPersonalDto){
        if(presupuestoPersonalDto == null) {
            throw new NullPointerException("[presupuestoPersonalDto] es nulo para hacer mapeo.");
        }
       if(presupuestoPersonalDto.getId() == null){
           return PresupuestoPersonal.nuevoPresupuesto(presupuestoPersonalDto.getNombre(),presupuestoPersonalDto.getIdusuario());
       }
       return PresupuestoPersonal.existentePresupuesto(presupuestoPersonalDto.getId(),
               presupuestoPersonalDto.getNombre(),presupuestoPersonalDto.getIdusuario(),
               presupuestoPersonalDto.getFechacreacion(), presupuestoPersonalDto.getTotalingresos(),
               presupuestoPersonalDto.getTotalgastos(),
               EnumTipoEstadoFinanciero.PRESUPUESTO_PERSONAL.toString());
    }
    public static PresupuestoPersonalDto convertirADto(PresupuestoPersonal presupuestoPersonal){
      PresupuestoPersonalDto presupuestoPersonalDto = new PresupuestoPersonalDto();
      presupuestoPersonalDto.setId(presupuestoPersonal.getId());
      presupuestoPersonalDto.setIdusuario(presupuestoPersonal.getIdusuario());
      presupuestoPersonalDto.setNombre(presupuestoPersonal.getNombre());
      presupuestoPersonalDto.setFechacreacion(presupuestoPersonal.getFechacreacion());
      presupuestoPersonalDto.setTotalgastos(presupuestoPersonal.getTotalgastos());
      presupuestoPersonalDto.setTotalingresos(presupuestoPersonal.getTotalingresos());
      presupuestoPersonalDto.setTotalsaldo(presupuestoPersonal.getTotalingresos()-presupuestoPersonal.getTotalgastos());
      return presupuestoPersonalDto;
    }

    public static List<PresupuestoPersonalDto> convertirAListaDto(List<PresupuestoPersonal> presupuestoPersonals){
       List<PresupuestoPersonalDto> presupuestoPersonalDtos = new ArrayList<>();
       presupuestoPersonals.forEach(e ->{
           presupuestoPersonalDtos.add(convertirADto(e));
       });
       return presupuestoPersonalDtos;
    }

}

