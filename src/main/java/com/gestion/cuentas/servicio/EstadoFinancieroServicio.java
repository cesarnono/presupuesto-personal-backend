package com.gestion.cuentas.servicio;

import com.gestion.cuentas.dto.PresupuestoPersonalDto;
import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.modelo.PresupuestoPersonal;

import java.util.List;

public interface EstadoFinancieroServicio {
     PresupuestoPersonalDto guardar(PresupuestoPersonalDto presupuestoPersonalDto);
     List<PresupuestoPersonalDto> consultarPresupuestosPorUsuario(String idusuario);
     PresupuestoPersonalDto consultar(String id);
     PresupuestoPersonalDto actualizarValoresTotales(String idpresupuesto);
     List<CuentaDto> consultarCuentas(String idpresupuesto);
}