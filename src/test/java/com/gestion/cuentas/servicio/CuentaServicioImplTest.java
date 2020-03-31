package com.gestion.cuentas.servicio;

import com.gestion.cuentas.constante.EnumMensaje;
import com.gestion.cuentas.conversor.CuentaConversor;
import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.modelo.Cuenta;
import com.gestion.cuentas.repositorio.CuentaRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CuentaServicioImplTest {

    private CuentaServicioImpl cuentaServicio;

    @Mock
    private CuentaRepositorio cuentaRepositorio;

    @Mock
    private EstadoFinancieroServicio presupuestoPersonalServicio;

    @BeforeEach
    public void inicializador() {
        // presupuestoPersonalServicio = new PresupuestoPersonalServicio(null, null);
        MockitoAnnotations.initMocks(this);
        //cuentaRepositorio = Mockito.mock(CuentaRepositorio.class);
        //presupuestoPersonalServicio = Mockito.mock(PresupuestoPersonalServicio.class);
        cuentaServicio = new CuentaServicioImpl(cuentaRepositorio, presupuestoPersonalServicio);
    }

    @Test
    @DisplayName("guardar cuenta nula")
    public void guardarCuentaNulaTest() {
        Exception excepcion = assertThrows(CuentaException.class, () -> {
            cuentaServicio.guardarCuenta(null);
        });
        String mensajeEsperado = EnumMensaje.INFORMACION_INVALIDA_GUARDAR_CUENTA.getMensaje();
        String mensajeActual = excepcion.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }

    @Test
    @DisplayName("guardar cuenta con campos vacios")
    public void guardarCuentaCamposVaciosTest() {
        Exception excepcion = assertThrows(CuentaException.class, () -> {
            CuentaDto cuentaDto = new CuentaDto();
            cuentaServicio.guardarCuenta(cuentaDto);
        });
        String mensajeEsperado = EnumMensaje.INFORMACION_INVALIDA_GUARDAR_CUENTA.getMensaje();
        String mensajeActual = excepcion.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }

    @Test
    @DisplayName("guardar cuenta idpresupuesto nulo")
    public void guardarIdPresupuestoNulaTest() {
        Exception excepcion = assertThrows(CuentaException.class, () -> {
            CuentaDto cuentaDto = new CuentaDto();
            cuentaDto.setNombre("cuenta test");
            cuentaServicio.guardarCuenta(cuentaDto);
        });
        String mensajeEsperado = EnumMensaje.INFORMACION_INVALIDA_GUARDAR_CUENTA.getMensaje();
        String mensajeActual = excepcion.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }

    @Test
    @DisplayName("guardar cuenta clase cuenta nulo")
    public void guardarClaseCuentaNulaNulaTest() {
        Exception excepcion = assertThrows(CuentaException.class, () -> {
            CuentaDto cuentaDto = new CuentaDto();
            cuentaDto.setNombre("cuenta test");
            cuentaDto.setIdpresupuesto("45rrt55");
            cuentaServicio.guardarCuenta(cuentaDto);
        });
        String mensajeEsperado = EnumMensaje.INFORMACION_INVALIDA_GUARDAR_CUENTA.getMensaje();
        String mensajeActual = excepcion.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }

    @Test
    @DisplayName("guardar cuenta valor menor a cero")
    public void guardarValorMenorIgualCeroTest() {
        Exception excepcion = assertThrows(CuentaException.class, () -> {
            CuentaDto cuentaDto = new CuentaDto();
            cuentaDto.setNombre("cuenta test");
            cuentaDto.setIdpresupuesto("45rrt55");
            cuentaDto.setClase("GASTOS");
            cuentaDto.setValor(-10);
            cuentaServicio.guardarCuenta(cuentaDto);
        });
        String mensajeEsperado = EnumMensaje.INFORMACION_INVALIDA_GUARDAR_CUENTA.getMensaje();
        String mensajeActual = excepcion.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }

    @Test
    public void guardarCuentaValida() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setNombre("cuenta test");
        cuentaDto.setIdpresupuesto("45rrt55");
        cuentaDto.setClase("GASTOS");
        cuentaDto.setValor(100);
        Cuenta cuenta = CuentaConversor.convertirACuenta(cuentaDto);
        CuentaDto cuentaDtoGuardado = cuentaServicio.guardarCuenta(cuentaDto);
        assertTrue(cuentaDto.getNombre().equals(cuentaDtoGuardado.getNombre()));
        assertTrue(cuentaDto.getIdpresupuesto().equals(cuentaDtoGuardado.getIdpresupuesto()));
        assertTrue(cuentaDto.getClase().equals(cuentaDtoGuardado.getClase()));
        assertTrue(cuentaDto.getValor() == cuentaDtoGuardado.getValor());
    }

    @Test
    public void actualizarCuentaInformacionInvalida() {
        Exception excepcion = assertThrows(CuentaException.class, () -> {
            CuentaDto cuentaDto = new CuentaDto();
            cuentaDto.setNombre("cuenta test");
            cuentaServicio.actualizarCuenta(cuentaDto);
        });
        String mensajeEsperado = EnumMensaje.INFORMACION_INVALIDA_ACTUALIZAR_CUENTA.getMensaje();
        String mensajeActual = excepcion.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }

    @Test
    public void actualizarCuentaNoExistente() {
        Exception excepcion = assertThrows(CuentaException.class, () -> {
            CuentaDto cuentaDto = new CuentaDto();
            cuentaDto.setId("56UTR5");
            cuentaDto.setNombre("cuenta test");
            cuentaDto.setIdpresupuesto("45rrt55");
            cuentaDto.setClase("GASTOS");
            cuentaDto.setValor(100);
            cuentaServicio.actualizarCuenta(cuentaDto);
        });
        String mensajeEsperado = EnumMensaje.ERROR_ACTUALIZAR_CUENTA_NO_EXISTE.getMensaje();
        String mensajeActual = excepcion.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }

    @Test
    public void actualizarCuentaValida() {
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setId("56UTR5");
        cuentaDto.setNombre("cuenta modificada");
        cuentaDto.setIdpresupuesto("45rrt55");
        cuentaDto.setClase("GASTOS");
        cuentaDto.setValor(100);
        Cuenta cuentaConsultada = new Cuenta("cuenta original", 100, "GASTOS","45rrt55");
        cuentaConsultada.setId(cuentaDto.getId());
        when(cuentaRepositorio.findById(anyString())).thenReturn(Optional.of(cuentaConsultada));
        CuentaDto cuentaDtoModificada =cuentaServicio.actualizarCuenta(cuentaDto);
        assertTrue(cuentaDto.getNombre().equals(cuentaDtoModificada.getNombre()));
        assertTrue(cuentaDtoModificada.getValor() == 200);
    }

    @Test
    public void eliminarCuentaIdInvalido() {
        Exception excepcion = assertThrows(CuentaException.class, () -> {
            CuentaDto cuentaDto = new CuentaDto();
            cuentaServicio.eliminarCuenta(cuentaDto);
        });
        String mensajeEsperado = EnumMensaje.ERROR_ELIMINAR_IDCUENTA_INVALIDO.getMensaje();
        String mensajeActual = excepcion.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }

    @Test
    public void eliminarCuentaIdNoexistente() {
        Exception excepcion = assertThrows(CuentaException.class, () -> {
            CuentaDto cuentaDto = new CuentaDto();
            cuentaDto.setId("3445");
            cuentaServicio.eliminarCuenta(cuentaDto);
        });
        String mensajeEsperado = EnumMensaje.ERROR_ELIMINAR_CUENTA_NO_EXISTE.getMensaje();
        String mensajeActual = excepcion.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }

    @Test
    public void eliminarCuentaExistente(){
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setId("56UTR5");
        cuentaDto.setNombre("cuenta modificada");
        cuentaDto.setIdpresupuesto("45rrt55");
        cuentaDto.setClase("GASTOS");
        cuentaDto.setValor(100);
        Cuenta cuentaConsultada = CuentaConversor.convertirACuenta(cuentaDto);
        cuentaConsultada.setId(cuentaDto.getId());
        when(cuentaRepositorio.findById(anyString())).thenReturn(Optional.of(cuentaConsultada));
        assertTrue(cuentaServicio.eliminarCuenta(cuentaDto));
    }

}