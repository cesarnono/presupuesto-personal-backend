package com.gestion.cuentas.controlador;

import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.dto.PresupuestoPersonalDto;
import com.gestion.cuentas.servicio.CuentaServicio;
import com.gestion.cuentas.servicio.EstadoFinancieroServicio;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/presupuesto-personal")
public class PresupuestoPersonalControlador {
    private EstadoFinancieroServicio estadoFinancieroServicio;
    private CuentaServicio cuentaServicio;

    public PresupuestoPersonalControlador(EstadoFinancieroServicio estadoFinancieroServicio,
                                          CuentaServicio cuentaServicio){
        this.estadoFinancieroServicio = estadoFinancieroServicio;
        this.cuentaServicio = cuentaServicio;
    }

    @PostMapping
    public PresupuestoPersonalDto guardar(@RequestBody PresupuestoPersonalDto presupuestoPersonalDto) {
        return estadoFinancieroServicio.guardar(presupuestoPersonalDto);
    }

    @GetMapping("/usuarios/{idusuario}")
    public List<PresupuestoPersonalDto> consultarEstadosFinancierosPorUsuario(@PathVariable String idusuario){
       return estadoFinancieroServicio.consultarPresupuestosPorUsuario(idusuario);
    }

    @GetMapping("/{idpresupuesto}")
    public PresupuestoPersonalDto consultar(@PathVariable String idpresupuesto){
      return estadoFinancieroServicio.consultar(idpresupuesto);
    }

    @GetMapping("/{idpresupuesto}/cuentas")
    public List<CuentaDto> consultarCuentas(@PathVariable String idpresupuesto){
        return this.estadoFinancieroServicio.consultarCuentas(idpresupuesto);
    }

}
