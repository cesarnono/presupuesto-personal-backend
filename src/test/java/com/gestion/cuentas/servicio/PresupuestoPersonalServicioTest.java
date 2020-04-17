package com.gestion.cuentas.servicio;

import com.gestion.cuentas.constante.EnumMensaje;
import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.dto.PresupuestoPersonalDto;
import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.modelo.Cuenta;
import com.gestion.cuentas.modelo.PresupuestoPersonal;
import com.gestion.cuentas.repositorio.CuentaRepositorio;
import com.gestion.cuentas.repositorio.PresupuestoPersonalRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PresupuestoPersonalServicioTest {

    private EstadoFinancieroServicio presupuestoPersonalServicio;

    @Mock
    private PresupuestoPersonalRepositorio presupuestoPersonalRepositorio;

    @Mock
    private CuentaRepositorio cuentaRepositorio;

    @BeforeEach
    public void inicializador(){
        MockitoAnnotations.initMocks(this);
        presupuestoPersonalServicio = new PresupuestoPersonalServicio(presupuestoPersonalRepositorio,cuentaRepositorio);
    }

    @Test
    public void guardarConInformacionInvalida(){
        Exception excepcion = assertThrows(CuentaException.class, () ->{
            PresupuestoPersonalDto presupuestoPersonalDto = new PresupuestoPersonalDto();
            presupuestoPersonalServicio.guardar(presupuestoPersonalDto);
        });
        String mensajeEsperado = EnumMensaje.INFORMACION_INVALIDA_GUARDAR_PRESUPUESTO_PERSONAL.getMensaje();
        String mensajeActual = excepcion.getMessage();
        assertTrue(mensajeEsperado.contains(mensajeActual));
    }

    @Test
    public void guardarPresupuestoValido(){
        PresupuestoPersonalDto presupuestoPersonalDto = new PresupuestoPersonalDto();
        presupuestoPersonalDto.setNombre("presupuesto test");
        presupuestoPersonalDto.setIdusuario("CC34554");
        PresupuestoPersonalDto presupuestoGuardado = presupuestoPersonalServicio.guardar(presupuestoPersonalDto);
        assertTrue(presupuestoGuardado.getNombre().equals(presupuestoPersonalDto.getNombre()));
        assertTrue(presupuestoGuardado.getIdusuario().equals(presupuestoPersonalDto.getIdusuario()));
        assertTrue(presupuestoGuardado.getTotalgastos() == 0);
        assertTrue(presupuestoGuardado.getTotalingresos() == 0);
    }

    @Test
    public void consultarPresupuestosInformacionInvalida(){
        Exception excepcion = assertThrows(CuentaException.class, () ->{
            presupuestoPersonalServicio.consultarPresupuestosPorUsuario(null);
        });
        String mensajeEsperado = EnumMensaje.INFORMACION_INVALIDA_OBTENER_PRESUPUESTOS.getMensaje();
        String mensajeActual = excepcion.getMessage();
        assertTrue(mensajeEsperado.contains(mensajeActual));
    }

    @Test
    public void consultarPresupuestosUsuario(){
        PresupuestoPersonal presupuestoPersonal = new PresupuestoPersonal("45533yu","presupuesto abril","CC12334",100,0,null,null,null);
        PresupuestoPersonal presupuestoPersonal1 = new PresupuestoPersonal("45533rt","presupuesto mayo","CC12334",200,0,null, null,null);
        List<PresupuestoPersonal> presupuestosUsuario = Arrays.asList(new PresupuestoPersonal[]{presupuestoPersonal, presupuestoPersonal1});
        when(presupuestoPersonalRepositorio.findByIdusuarioOrderByFechacreacionDesc("CC12334")).thenReturn(presupuestosUsuario);
        List<PresupuestoPersonalDto> presupuestoPersonalDtos =presupuestoPersonalServicio.consultarPresupuestosPorUsuario("CC12334");
        assertTrue(presupuestoPersonalDtos.size() == presupuestosUsuario.size());
    }

    @Test
    public void consultarPresupuestoIdinvalido(){
        Exception exception = assertThrows(CuentaException.class, () ->{
            presupuestoPersonalServicio.consultar(null);
        });

        String mensajeEsperado = EnumMensaje.INFORMACION_INVALIDA_CONSULTAR_PRESUPUESTO.getMensaje();
        String mensajeExcepcion = exception.getMessage();
        assertTrue(mensajeEsperado.contains(mensajeExcepcion));
    }

    @Test
    public void consultarIdPresupuestoNoexistente(){
        final String  idPresupuesto ="54534TYU";
        Exception exception = assertThrows(CuentaException.class, () ->{
            presupuestoPersonalServicio.consultar(idPresupuesto);
        });
        String mensajeEsperado = EnumMensaje.ERROR_CONSULTAR_PRESUPUESTO_ID_NO_EXISTE.getMensaje()+ "idpresupuesto: "+idPresupuesto;
        String mensajeExcepcion = exception.getMessage();
        assertTrue(mensajeEsperado.contains(mensajeExcepcion));
    }

    @Test
    public void consultarPresupuestoValido(){
        String idPresupuesto ="345656TY";
        PresupuestoPersonal presupuestoPersonalConsultado = new PresupuestoPersonal(idPresupuesto,"presupuesto test","CC234445",100,0,null, null,null);
        when(presupuestoPersonalRepositorio.findById(idPresupuesto)).thenReturn(Optional.of(presupuestoPersonalConsultado));
        assertNotNull(presupuestoPersonalServicio.consultar(idPresupuesto));
    }

    @Test
    public void consultarCuentasIdPresupuestoInvalido(){
        Exception excepcion = assertThrows(CuentaException.class, () ->{
            presupuestoPersonalServicio.consultarCuentas("");
        });
        String mensajeEsperado = EnumMensaje.ERROR_CONSULTAR_CUENTAS_IDPRESUPUESTO_INVALIDO.getMensaje();
        String mensajeExcepcion = excepcion.getMessage();
        assertTrue(mensajeEsperado.contains(mensajeExcepcion));
    }

    @Test
    public void consultarCuentas(){
        List<Cuenta> cuentas = Arrays.asList(new Cuenta []{new Cuenta("1", "test",100, "GASTOS", "",null,null)});
        when(cuentaRepositorio.findByIdpresupuesto(anyString())).thenReturn(cuentas);
        List<CuentaDto> cuentaDtos = presupuestoPersonalServicio.consultarCuentas("345545ty");
        assertTrue(cuentas.size() == cuentaDtos.size());
    }


    @Test
    public void actualizarValoresTotales(){
        String idpresupuesto = "455665po";
        PresupuestoPersonal presupuestoPersonal = new PresupuestoPersonal(idpresupuesto,"presupuesto enero","CC3455566",300,0,null, null, null);
        //cuentas
        List<Cuenta> cuentas = Arrays.asList(new Cuenta []{
                new Cuenta("1","Agua", 100, "GASTOS", idpresupuesto,null,null),
                new Cuenta("2","Luz", 50, "GASTOS", idpresupuesto,null, null),
                new Cuenta("3","Internet", 100, "GASTOS", idpresupuesto,null,null)
        });
        when(presupuestoPersonalRepositorio.findById(idpresupuesto)).thenReturn(Optional.of(presupuestoPersonal));
        when(cuentaRepositorio.findByIdpresupuesto(idpresupuesto)).thenReturn(cuentas);
        //assertTrue(presupuestoPersonalServicio.actualizarValoresTotales(idpresupuesto));
        PresupuestoPersonalDto presupuestoPersonalDto = presupuestoPersonalServicio.actualizarValoresTotales(idpresupuesto);
        assertTrue(presupuestoPersonalDto.getTotalgastos() == 250);
    }
}