package com.gestion.cuentas.conversor;

import com.gestion.cuentas.dto.PresupuestoPersonalDto;
import com.gestion.cuentas.modelo.PresupuestoPersonal;

import java.util.ArrayList;
import java.util.List;

public final class PresupuestoPersonalConversor {
    public static PresupuestoPersonal convertirAEstadoFinanciero(PresupuestoPersonalDto presupuestoPersonalDto){
        return new PresupuestoPersonal(presupuestoPersonalDto.getId(),
                presupuestoPersonalDto.getNombre(), presupuestoPersonalDto.getIdusuario());
    }

    public static PresupuestoPersonalDto convertirAPresupuestoPersonalDto(PresupuestoPersonal presupuestoPersonal){
      PresupuestoPersonalDto presupuestoPersonalDto = new PresupuestoPersonalDto();
      presupuestoPersonalDto.setId(presupuestoPersonal.getId());
      presupuestoPersonalDto.setIdusuario(presupuestoPersonal.getIdusuario());
      presupuestoPersonalDto.setNombre(presupuestoPersonal.getNombre());
      presupuestoPersonalDto.setTotalgastos(presupuestoPersonal.getTotalgastos());
      presupuestoPersonalDto.setTotalingresos(presupuestoPersonal.getTotalingresos());
      presupuestoPersonalDto.setTotalsaldo(presupuestoPersonal.getTotalingresos()-presupuestoPersonal.getTotalgastos());
      return presupuestoPersonalDto;
    }

    public static List<PresupuestoPersonalDto> convertirAListaPresupuestoPersonalDto(List<PresupuestoPersonal> presupuestoPersonals){
       List<PresupuestoPersonalDto> presupuestoPersonalDtos = new ArrayList<>();
       presupuestoPersonals.forEach(e ->{
           PresupuestoPersonalDto presupuestoPersonalDto = new PresupuestoPersonalDto();
           presupuestoPersonalDto.setNombre(e.getNombre());
           presupuestoPersonalDto.setIdusuario(e.getIdusuario());
           presupuestoPersonalDto.setId(e.getId());
           presupuestoPersonalDto.setTotalingresos(e.getTotalingresos());
           presupuestoPersonalDto.setTotalgastos(e.getTotalgastos());
           presupuestoPersonalDto.setTotalsaldo(e.getTotalingresos()-e.getTotalgastos());
           presupuestoPersonalDtos.add(presupuestoPersonalDto);
       });
       return presupuestoPersonalDtos;
    }

}

