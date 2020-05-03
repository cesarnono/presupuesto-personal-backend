package com.gestion.cuentas.servicio;

import com.gestion.cuentas.constante.EnumMensaje;
import com.gestion.cuentas.mapeador.CuentaMapeador;
import com.gestion.cuentas.mapeador.PresupuestoPersonalMapeador;
import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.dto.PresupuestoPersonalDto;
import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.modelo.Cuenta;
import com.gestion.cuentas.modelo.PresupuestoPersonal;
import com.gestion.cuentas.repositorio.CuentaRepositorio;
import com.gestion.cuentas.repositorio.PresupuestoPersonalRepositorio;
import com.gestion.cuentas.validacion.Validacion;
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
    @Transactional
    public PresupuestoPersonalDto guardar(PresupuestoPersonalDto presupuestoPersonalDto) {
        PresupuestoPersonal presupuestoPersonal = PresupuestoPersonalMapeador.convertirAModelo(presupuestoPersonalDto);
        presupuestoPersonal.esValidoParaGuardar();
        presupuestoPersonalRepositorio.insert(presupuestoPersonal);
        LOGGER.info("presupuesto personal creado: "+ presupuestoPersonal);
        return PresupuestoPersonalMapeador.convertirADto(presupuestoPersonal);
    }

    @Override
    public List<PresupuestoPersonalDto> consultarPresupuestosPorUsuario(String idusuario) {
        if(!Validacion.esCampoCadenaValido(idusuario)){
            throw new CuentaException(EnumMensaje.INFORMACION_INVALIDA_OBTENER_PRESUPUESTOS.getMensaje());
        }
        List<PresupuestoPersonal> presupuestos = presupuestoPersonalRepositorio.findByIdusuarioOrderByFechacreacionDesc(idusuario);
        LOGGER.info("Nro presupuestos encontrados para usuario "+idusuario+": "+presupuestos.size());
        return PresupuestoPersonalMapeador.convertirAListaDto(presupuestos);
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
        return PresupuestoPersonalMapeador.convertirADto(presupuestoOptional.get());
    }

    @Override
    public List<CuentaDto> consultarCuentas(String idpresupuesto) {
        if(!Validacion.esCampoCadenaValido(idpresupuesto)){
            throw new CuentaException(EnumMensaje.ERROR_CONSULTAR_CUENTAS_IDPRESUPUESTO_INVALIDO.getMensaje());
        }
        List<Cuenta> cuentas = this.cuentaRepositorio.findByIdpresupuesto(idpresupuesto);
        return CuentaMapeador.convertirAListaDto(cuentas);
    }

    @Override
    @Transactional
    public PresupuestoPersonalDto actualizarValoresTotales(String idpresupuesto) {
        PresupuestoPersonalDto presupuestoPersonalDto = this.consultar(idpresupuesto);
        List<CuentaDto> cuentaDtos = this.consultarCuentas(idpresupuesto);
        if(cuentaDtos.isEmpty()){
            LOGGER.info(EnumMensaje.SIN_CUENTAS_ASOCIADAS_PARA_ACTUALIZAR_TOTALES.getMensaje());
            return presupuestoPersonalDto;
        }
        presupuestoPersonalDto.setCuentaDtos(cuentaDtos);
        PresupuestoPersonal presupuestoPersonal = new PresupuestoPersonal(presupuestoPersonalDto);
        presupuestoPersonalRepositorio.save(presupuestoPersonal);
        LOGGER.info("totales actualizados exitosamente en presupuesto: "+ presupuestoPersonal.getId());
        return PresupuestoPersonalMapeador.convertirADto(presupuestoPersonal);
    }


}