package com.gestion.cuentas.modelo;

import com.gestion.cuentas.constante.EnumClaseCuenta;
import com.gestion.cuentas.constante.EnumFormatoFecha;
import com.gestion.cuentas.constante.EnumMensaje;
import com.gestion.cuentas.constante.EnumTipoEstadoFinanciero;
import com.gestion.cuentas.dto.CuentaDto;
import com.gestion.cuentas.dto.PresupuestoPersonalDto;
import com.gestion.cuentas.excepcion.CuentaException;
import com.gestion.cuentas.utilidad.FechaUtilidad;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

import static com.gestion.cuentas.validacion.Validacion.esCampoCadenaValido;

@Document(collection = "presupuesto_personal")
public class PresupuestoPersonal {
    @Id
    private final String id;

    private final String nombre;

    @Field("id_usuario")
    private final String idusuario;

    @Field("total_ingresos")
    private final double totalingresos;

    @Field("total_gastos")
    private final double totalgastos;

    @Field("fecha_creacion")
    private final String fechacreacion;

    @Field("fecha_actualizacion")
    private final String fechaactualizacion;

    private final String tipo;

    @PersistenceConstructor
    public PresupuestoPersonal(String id, String nombre, String idusuario,
                               double totalingresos, double totalgastos,
                               String fechacreacion, String fechaactualizacion,
                               String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.idusuario = idusuario;
        this.fechacreacion = fechacreacion;
        this.fechaactualizacion = fechaactualizacion;
        this.totalingresos = totalingresos;
        this.totalgastos = totalgastos;
        this.tipo = tipo;
    }


    public PresupuestoPersonal(PresupuestoPersonalDto presupuestoPersonalDto) {
        if(presupuestoPersonalDto ==null){
            throw new NullPointerException("presupuestoPersonalDto es nulo para crear PresupuestoPersonal");
        }
        this.id = presupuestoPersonalDto.getId();
        this.nombre = presupuestoPersonalDto.getNombre();
        this.idusuario = presupuestoPersonalDto.getIdusuario();
        this.fechacreacion = presupuestoPersonalDto.getFechacreacion();
        this.fechaactualizacion = FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S);
        this.totalingresos = this.calcularIngresosTotales(presupuestoPersonalDto.getCuentaDtos());
        this.totalgastos = this.calcularGastosTotales(presupuestoPersonalDto.getCuentaDtos());
        this.tipo = EnumTipoEstadoFinanciero.PRESUPUESTO_PERSONAL.toString();
    }

    public static PresupuestoPersonal nuevoPresupuesto(String nombre, String idusuario) {
        return new PresupuestoPersonal(null, nombre, idusuario, 0, 0,
                FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S),
                FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S),
                EnumTipoEstadoFinanciero.PRESUPUESTO_PERSONAL.toString());
    }

    public static PresupuestoPersonal existentePresupuesto(String id, String nombre, String idusuario,
                                                           String fechacreacion, double totalingresos,
                                                           double totalgastos, String tipo) {
        return new PresupuestoPersonal(id, nombre, idusuario, totalingresos, totalgastos
                , fechacreacion,
                FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S),
                tipo);
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public double getTotalingresos() {
        return totalingresos;
    }

    public double getTotalgastos() {
        return totalgastos;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public String getFechaactualizacion() {
        return fechaactualizacion;
    }

    public String getTipo() {
        return tipo;
    }

    @Override
    public String toString() {
        return "PresupuestoPersonal{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", idusuario='" + idusuario + '\'' +
                ", totalingresos=" + totalingresos +
                ", totalgastos=" + totalgastos +
                ", tipo='" + tipo + '\'' +
                '}';
    }

    public boolean esValidoParaGuardar() {
        boolean esValido = esCampoCadenaValido(this.nombre) && esCampoCadenaValido(this.idusuario);
        if (!esValido) {
            throw new CuentaException(EnumMensaje.INFORMACION_INVALIDA_GUARDAR_PRESUPUESTO_PERSONAL.getMensaje());
        }
        return true;
    }

    private double calcularGastosTotales(final List<CuentaDto> cuentaDtos) {
        return cuentaDtos.stream().filter(cuentaDto -> EnumClaseCuenta.GASTOS.toString().equals(cuentaDto.getClase())).mapToDouble(CuentaDto::getValor).sum();
    }

    private double calcularIngresosTotales(final List<CuentaDto> cuentaDtos) {
        return cuentaDtos.stream().filter(cuentaDto -> EnumClaseCuenta.INGRESOS.toString().equals(cuentaDto.getClase())).mapToDouble(CuentaDto::getValor).sum();
    }


}
