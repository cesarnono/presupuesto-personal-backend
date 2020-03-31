package com.gestion.cuentas.servicio;

import com.gestion.cuentas.constante.EnumFormatoFecha;
import com.gestion.cuentas.constante.EnumMensaje;
import com.gestion.cuentas.conversor.CuentaConversor;
import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.modelo.Cuenta;
import com.gestion.cuentas.repositorio.CuentaRepositorio;
import com.gestion.cuentas.utilidad.FechaUtilidad;
import com.gestion.cuentas.validacion.Validacion;
import com.gestion.cuentas.validacion.ValidacionCuenta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.Optional;

@Named
public class CuentaServicioImpl implements CuentaServicio {

    private final CuentaRepositorio cuentaRepositorio;
    private final EstadoFinancieroServicio estadoFinancieroServicio;

    private static final Logger LOGGER = LoggerFactory.getLogger(CuentaServicioImpl.class);

    public CuentaServicioImpl(CuentaRepositorio cuentaRepositorio,
                              EstadoFinancieroServicio estadoFinancieroServicio) {
        this.cuentaRepositorio = cuentaRepositorio;
        this.estadoFinancieroServicio = estadoFinancieroServicio;
    }

    @Override
    @Transactional
    public CuentaDto guardarCuenta(CuentaDto cuentaDto) {
        if (!ValidacionCuenta.esCuentaValidaParaGuardar(cuentaDto)) {
            throw new CuentaException(EnumMensaje.INFORMACION_INVALIDA_GUARDAR_CUENTA.getMensaje());
        }
        Cuenta cuenta = CuentaConversor.convertirACuenta(cuentaDto);
        cuentaRepositorio.insert(cuenta);
        LOGGER.info("Cuenta creada exitosamente: "+cuenta);
        this.estadoFinancieroServicio.actualizarValoresTotales(cuentaDto.getIdpresupuesto());
        return CuentaConversor.convertirACuentaDto(cuenta);
    }

    @Override
    @Transactional
    public CuentaDto actualizarCuenta(CuentaDto cuentaDto) {
        if (!ValidacionCuenta.esCuentaValidaParaActualizar(cuentaDto)) {
            throw new CuentaException(EnumMensaje.INFORMACION_INVALIDA_ACTUALIZAR_CUENTA.getMensaje());
        }
        Optional<Cuenta> cuentaOptional = cuentaRepositorio.findById(cuentaDto.getId());
        if(!cuentaOptional.isPresent()){
            throw new CuentaException(EnumMensaje.ERROR_ACTUALIZAR_CUENTA_NO_EXISTE.getMensaje() +" idcuenta: "+cuentaDto.getId());
        }
        Cuenta cuenta = cuentaOptional.get();
        cuenta.setNombre(cuentaDto.getNombre());
        if(cuentaDto.getValor() != 0){
            cuenta.setValor(cuenta.getValor() + cuentaDto.getValor());
        }
        cuenta.setFechamodificacion(FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S));
        cuentaRepositorio.save(cuenta);
        LOGGER.info("Cuenta actualizada exitosamente: "+cuenta);
        this.estadoFinancieroServicio.actualizarValoresTotales(cuentaDto.getIdpresupuesto());
        return CuentaConversor.convertirACuentaDto(cuenta);
    }

    @Override
    @Transactional
    public boolean eliminarCuenta(CuentaDto cuentaDto) {
        if(!Validacion.esCampoCadenaValido(cuentaDto.getId())){
            throw new CuentaException(EnumMensaje.ERROR_ELIMINAR_IDCUENTA_INVALIDO.getMensaje());
        }
        Optional<Cuenta> cuentaOptional = cuentaRepositorio.findById(cuentaDto.getId());
        if(!cuentaOptional.isPresent()){
            throw new CuentaException(EnumMensaje.ERROR_ELIMINAR_CUENTA_NO_EXISTE.getMensaje() +" idcuenta: "+cuentaDto.getId());
        }
        Cuenta cuenta = cuentaOptional.get();
        cuentaRepositorio.delete(cuenta);
        LOGGER.info("Cuenta eliminada exitosamente: "+cuenta);
        this.estadoFinancieroServicio.actualizarValoresTotales(cuentaDto.getIdpresupuesto());
        return true;
    }

}
