package com.gestion.cuentas.servicio;

import com.gestion.cuentas.constante.EnumMensaje;
import com.gestion.cuentas.mapeador.CuentaMapeador;
import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.modelo.Cuenta;
import com.gestion.cuentas.modelo.PresupuestoPersonal;
import com.gestion.cuentas.repositorio.CuentaRepositorio;
import com.gestion.cuentas.repositorio.PresupuestoPersonalRepositorio;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class CuentaServicioImplTest {

    private CuentaServicioImpl cuentaServicio;

    private CuentaRepositorio cuentaRepositorioMock;

    private PresupuestoPersonalRepositorio presupuestoPersonalRepositorioMock;

    private EstadoFinancieroServicio presupuestoPersonalServicio;

    @BeforeEach
    public void inicializador() {
        cuentaRepositorioMock = Mockito.mock(CuentaRepositorio.class);
        presupuestoPersonalRepositorioMock = Mockito.mock(PresupuestoPersonalRepositorio.class);
        presupuestoPersonalServicio = new PresupuestoPersonalServicio(presupuestoPersonalRepositorioMock, cuentaRepositorioMock);
        cuentaServicio = new CuentaServicioImpl(cuentaRepositorioMock, presupuestoPersonalServicio);

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
        Cuenta cuenta = CuentaMapeador.convertirAModelo(cuentaDto);
        PresupuestoPersonal presupuestoPersonal =PresupuestoPersonal.existentePresupuesto("34TREW","presupuesto test",
                "CC34554","",500,0,null);

        when(cuentaRepositorioMock.findById(anyString())).thenReturn(Optional.of(cuenta));
        when(presupuestoPersonalRepositorioMock.findById(anyString())).thenReturn(Optional.of(presupuestoPersonal));
        when(cuentaRepositorioMock.findByIdpresupuesto(anyString())).thenReturn(Arrays.asList(cuenta));

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
        String mensajeEsperado = EnumMensaje.ERROR_CONSULTAR_IDCUENTA_NO_EXISTE.getMensaje();
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
        Cuenta cuentaConsultada = new Cuenta(cuentaDto.getId(),"cuenta original", 100, "GASTOS","45rrt55"
        ,null, null);
        when(cuentaRepositorioMock.findById(anyString())).thenReturn(Optional.of(cuentaConsultada));

        PresupuestoPersonal presupuestoPersonal =PresupuestoPersonal.existentePresupuesto("34TREW","presupuesto test",
                "CC34554","",500,0,null);

        when(presupuestoPersonalRepositorioMock.findById(anyString())).thenReturn(Optional.of(presupuestoPersonal));
        when(cuentaRepositorioMock.findByIdpresupuesto(anyString())).thenReturn(Arrays.asList(cuentaConsultada));

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
        String mensajeEsperado = EnumMensaje.ERROR_CONSULTAR_IDCUENTA_NO_EXISTE.getMensaje();
        String mensajeActual = excepcion.getMessage();
        assertTrue(mensajeActual.contains(mensajeEsperado));
    }

    @Test
    public void eliminarCuentaExistente(){
        Cuenta cuenta = new Cuenta("56UTR5", "cuenta1",100,"GASTOS","34TREW",null,null);
        PresupuestoPersonal presupuestoPersonal =PresupuestoPersonal.existentePresupuesto("34TREW","presupuesto test",
                "CC34554","",500,100,null);
        when(cuentaRepositorioMock.findById(anyString())).thenReturn(Optional.of(cuenta));
        when(presupuestoPersonalRepositorioMock.findById(anyString())).thenReturn(Optional.of(presupuestoPersonal));
        when(cuentaRepositorioMock.findByIdpresupuesto(anyString())).thenReturn(Arrays.asList(cuenta));
        CuentaDto cuentaDto = new CuentaDto();
        cuentaDto.setId("56UTR5");
        assertTrue(cuentaServicio.eliminarCuenta(cuentaDto));
    }

}