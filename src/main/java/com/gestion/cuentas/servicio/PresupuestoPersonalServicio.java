package com.gestion.cuentas.servicio;

import com.gestion.cuentas.constante.EnumClaseCuenta;
import com.gestion.cuentas.constante.EnumFormatoFecha;
import com.gestion.cuentas.constante.EnumMensaje;
import com.gestion.cuentas.conversor.CuentaConversor;
import com.gestion.cuentas.conversor.PresupuestoPersonalConversor;
import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.dto.PresupuestoPersonalDto;
import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.modelo.Cuenta;
import com.gestion.cuentas.modelo.PresupuestoPersonal;
import com.gestion.cuentas.repositorio.CuentaRepositorio;
import com.gestion.cuentas.repositorio.PresupuestoPersonalRepositorio;
import com.gestion.cuentas.utilidad.FechaUtilidad;
import com.gestion.cuentas.validacion.Validacion;
import com.gestion.cuentas.validacion.ValidacionEstadoFinanciero;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Named;
import java.util.List;
import java.util.Optional;

@Named
public class PresupuestoPersonalServicio implements EstadoFinancieroServicio {

    private final PresupuestoPersonalRepositorio presupuestoPersonalRepositorio;
    private final CuentaRepositorio cuentaRepositorio;
    private static final Logger LOGGER = LoggerFactory.getLogger(PresupuestoPersonalServicio.class);

    public PresupuestoPersonalServicio(PresupuestoPersonalRepositorio presupuestoPersonalRepositorio,
                                       CuentaRepositorio cuentaRepositorio){
        this.presupuestoPersonalRepositorio = presupuestoPersonalRepositorio;
        this.cuentaRepositorio = cuentaRepositorio;
    }
    @Override
    public PresupuestoPersonalDto guardar(PresupuestoPersonalDto presupuestoPersonalDto) {
        if(!ValidacionEstadoFinanciero.validarCreacion(presupuestoPersonalDto)){
            throw new CuentaException(EnumMensaje.INFORMACION_INVALIDA_CREAR_PRESUPUESTO_PERSONAL.getMensaje());
        }
        PresupuestoPersonal presupuestoPersonal = PresupuestoPersonalConversor.convertirAEstadoFinanciero(presupuestoPersonalDto);
        presupuestoPersonalRepositorio.insert(presupuestoPersonal);
        LOGGER.info("presupuesto personal creado: "+ presupuestoPersonal);
        return PresupuestoPersonalConversor.convertirAPresupuestoPersonalDto(presupuestoPersonal);
    }

    @Override
    public List<PresupuestoPersonalDto> consultarPresupuestosPorUsuario(String idusuario) {
        if(!Validacion.esCampoCadenaValido(idusuario)){
            throw new CuentaException(EnumMensaje.INFORMACION_INVALIDA_OBTENER_PRESUPUESTOS.getMensaje());
        }
        List<PresupuestoPersonal> presupuestos = presupuestoPersonalRepositorio.findByIdusuarioLikeOrderByFechacreacionDesc(idusuario);
        LOGGER.info("Nro presupuestos encontrados para usuario "+idusuario+": "+presupuestos.size());
        return PresupuestoPersonalConversor.convertirAListaPresupuestoPersonalDto(presupuestos);
    }

    @Override
    public PresupuestoPersonalDto consultar(String id) {
        if(!Validacion.esCampoCadenaValido(id)){
            throw new CuentaException(EnumMensaje.INFORMACION_INVALIDA_CONSULTAR_PRESUPUESTO.getMensaje());
        }
        Optional<PresupuestoPersonal> presupuestoOptional = presupuestoPersonalRepositorio.findById(id);
        if(!presupuestoOptional.isPresent()){
            throw new CuentaException(EnumMensaje.ERROR_CONSULTAR_PRESUPUESTO_ID_NO_EXISTE.getMensaje()+ "idpresupuesto: "+id);
        }
        LOGGER.info("presupuesto encontrado: "+ presupuestoOptional.get());
        return PresupuestoPersonalConversor.convertirAPresupuestoPersonalDto(presupuestoOptional.get());
    }

    @Override
    public List<CuentaDto> consultarCuentas(String idpresupuesto) {
        if(!Validacion.esCampoCadenaValido(idpresupuesto)){
            throw new CuentaException(EnumMensaje.ERROR_CONSULTAR_CUENTAS_IDPRESUPUESTO_INVALIDO.getMensaje());
        }
        List<Cuenta> cuentas = this.cuentaRepositorio.findByIdpresupuesto(idpresupuesto);
        return CuentaConversor.convertirAListaCuentaDto(cuentas);
    }

    @Override
    @Transactional
    public boolean actualizarValoresTotales(String idpresupuesto) {
        PresupuestoPersonalDto presupuestoPersonalDto = this.consultar(idpresupuesto);
        List<CuentaDto> cuentaDtos = this.consultarCuentas(idpresupuesto);
        if(cuentaDtos.isEmpty()){
            LOGGER.info(EnumMensaje.SIN_CUENTAS_ASOCIADAS_ACTUALIZAR_TOTALES.getMensaje());
            return true;
        }
        PresupuestoPersonal presupuestoPersonal = PresupuestoPersonalConversor.convertirAEstadoFinanciero(presupuestoPersonalDto);
        presupuestoPersonal.setTotalgastos(obtenerTotalGastos(cuentaDtos));
        presupuestoPersonal.setTotalingresos(obtenerTotalIngresos(cuentaDtos));
        presupuestoPersonal.setFechaactualizacion(FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S));
        presupuestoPersonalRepositorio.save(presupuestoPersonal);
        LOGGER.info("totales actualizados exitosamente en presupuesto: "+ presupuestoPersonal);
        return true;
    }

    private double obtenerTotalGastos(List<CuentaDto> cuentaDtos) {
        return cuentaDtos.stream().filter(cuentaDto -> EnumClaseCuenta.GASTOS.toString().equals(cuentaDto.getClase())).mapToDouble(CuentaDto::getValor).sum();
    }

    private double obtenerTotalIngresos(List<CuentaDto> cuentaDtos) {
        return cuentaDtos.stream().filter(cuentaDto -> EnumClaseCuenta.INGRESOS.toString().equals(cuentaDto.getClase())).mapToDouble(CuentaDto::getValor).sum();
    }


}
