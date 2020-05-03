package com.gestion.cuentas.servicio;

import com.gestion.cuentas.constante.EnumClaseCuenta;
import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.dto.PresupuestoPersonalDto;
import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.repositorio.MovimientoRepositorio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;

@Named
public class ManejadorServicios {
    private final EstadoFinancieroServicio presupuestoPersonalService;
    private final CuentaServicio cuentaServicio;
    private static  final Logger LOGGER = LoggerFactory.getLogger(ManejadorServicios.class);
    private final MovimientoRepositorio movimientoRepositorio;

    @Value("${app.conf.movimientos.gasto.fijo}")
    private Long configuracionMovimientosGastoFijo;

    public ManejadorServicios(EstadoFinancieroServicio presupuestoPersonalService, CuentaServicio cuentaServicio,
                              MovimientoRepositorio movimientoRepositorio) {
        this.presupuestoPersonalService = presupuestoPersonalService;
        this.cuentaServicio = cuentaServicio;
        this.movimientoRepositorio = movimientoRepositorio;
    }

    @Transactional
    public PresupuestoPersonalDto copiarPresupuesto(PresupuestoPersonalDto presupuestoPersonalDto) {
        PresupuestoPersonalDto presupuestoPersonalDtoCopiar = this.presupuestoPersonalService.consultar(presupuestoPersonalDto.getIdpresupuestopadre());
        PresupuestoPersonalDto presupuestoPersonalDtoGuardar = new PresupuestoPersonalDto();
        List<CuentaDto> cuentaDtos =null;
        if(presupuestoPersonalDtoCopiar != null){
            presupuestoPersonalDtoGuardar.setIdusuario(presupuestoPersonalDtoCopiar.getIdusuario());
            cuentaDtos = this.presupuestoPersonalService.consultarCuentas(presupuestoPersonalDtoCopiar.getId());
        }
        presupuestoPersonalDtoGuardar.setNombre(presupuestoPersonalDto.getNombre());
        PresupuestoPersonalDto presupuestoPersonalDtoGuardado = this.presupuestoPersonalService.guardar(presupuestoPersonalDtoGuardar);
        LOGGER.info("presupuesto creado con id: " + presupuestoPersonalDtoGuardado.getId());
        if(!cuentaDtos.isEmpty()){
            for (CuentaDto cuentaDto: cuentaDtos){
                CuentaDto cuentaDtoGuardar = new CuentaDto();
                cuentaDtoGuardar.setIdpresupuesto(presupuestoPersonalDtoGuardado.getId());
                cuentaDtoGuardar.setValor(esGastoFijo(cuentaDto)? cuentaDto.getValor(): 0d);
                cuentaDtoGuardar.setDescripcion("cuenta copiada desde: "+cuentaDto.getId());
                cuentaDtoGuardar.setClase(cuentaDto.getClase());
                cuentaDtoGuardar.setNombre(cuentaDto.getNombre());
                this.cuentaServicio.guardarCuenta(cuentaDtoGuardar);
                LOGGER.info(cuentaDtoGuardar.getNombre() + " insertada..");
            }
        }
        return presupuestoPersonalDtoGuardado;
    }

    private boolean esGastoFijo(CuentaDto cuentaDto){
        if(cuentaDto != null && EnumClaseCuenta.GASTOS.toString().equals(cuentaDto.getClase())){
           Long numeroMovimientosCuenta = this.movimientoRepositorio.countByIdcuenta(cuentaDto.getId());
           return configuracionMovimientosGastoFijo <= numeroMovimientosCuenta ;
        }
        return false;
    }
}
