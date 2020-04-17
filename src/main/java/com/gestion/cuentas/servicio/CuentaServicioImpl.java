package com.gestion.cuentas.servicio;

import com.gestion.cuentas.constante.EnumMensaje;
import com.gestion.cuentas.mapeador.CuentaMapeador;
import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.modelo.Cuenta;
import com.gestion.cuentas.repositorio.CuentaRepositorio;
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
        Cuenta cuenta = CuentaMapeador.convertirAModelo(cuentaDto);
        cuentaRepositorio.insert(cuenta);
        LOGGER.info("Cuenta creada exitosamente idcuenta: "+cuenta.getId());
        this.estadoFinancieroServicio.actualizarValoresTotales(cuenta.getIdpresupuesto());
        return CuentaMapeador.convertirADto(cuenta);
    }

    @Override
    @Transactional
    public CuentaDto actualizarCuenta(CuentaDto cuentaDto) {
        if (!ValidacionCuenta.esCuentaValidaParaActualizar(cuentaDto)) {
            throw new CuentaException(EnumMensaje.INFORMACION_INVALIDA_ACTUALIZAR_CUENTA.getMensaje());
        }
        CuentaDto cuentaDtoDesdeBaseDatos = consultarCuenta(cuentaDto.getId());
        cuentaDtoDesdeBaseDatos.setNombre(cuentaDto.getNombre());
        if(cuentaDto.getValor() > 0){
            cuentaDtoDesdeBaseDatos.setValor(cuentaDtoDesdeBaseDatos.getValor() + cuentaDto.getValor());
        }
        Cuenta cuenta =CuentaMapeador.convertirAModelo(cuentaDtoDesdeBaseDatos);
        cuentaRepositorio.save(cuenta);
        LOGGER.info("Cuenta actualizada exitosamente idcuenta: "+cuenta.getId());
        this.estadoFinancieroServicio.actualizarValoresTotales(cuenta.getIdpresupuesto());
        return CuentaMapeador.convertirADto(cuenta);
    }

    @Transactional
    public CuentaDto consultarCuenta(String idcuenta) {
        Optional<Cuenta> cuentaOptional = cuentaRepositorio.findById(idcuenta);
        if(!cuentaOptional.isPresent()){
            throw new CuentaException(EnumMensaje.ERROR_CONSULTAR_IDCUENTA_NO_EXISTE.getMensaje() +" idcuenta: "+ idcuenta);
        }
        return CuentaMapeador.convertirADto(cuentaOptional.get());
    }

    @Override
    @Transactional
    public boolean eliminarCuenta(CuentaDto cuentaDto) {
        if(!Validacion.esCampoCadenaValido(cuentaDto.getId())){
            throw new CuentaException(EnumMensaje.ERROR_ELIMINAR_IDCUENTA_INVALIDO.getMensaje());
        }
        Cuenta cuenta = CuentaMapeador.convertirAModelo(consultarCuenta(cuentaDto.getId()));
        cuentaRepositorio.delete(cuenta);
        LOGGER.info("Cuenta eliminada exitosamente idcuenta: "+cuenta.getId());
        this.estadoFinancieroServicio.actualizarValoresTotales(cuenta.getIdpresupuesto());
        return true;
    }
}
