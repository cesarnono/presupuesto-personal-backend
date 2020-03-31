package com.gestion.cuentas.modelo;

import com.gestion.cuentas.constante.EnumFormatoFecha;
import com.gestion.cuentas.constante.EnumTipoEstadoFinanciero;
import com.gestion.cuentas.utilidad.FechaUtilidad;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import javax.validation.constraints.NotNull;

@Document(collection = "presupuesto_personal")
public class PresupuestoPersonal {
    @Id
    private String id;

    @NotNull
    private String nombre;

    @NotNull
    @Field("id_usuario")
    private String idusuario;

    @Field("total_ingresos")
    private double totalingresos;

    @Field("total_gastos")
    private double totalgastos;

    @NotNull
    @Field("fecha_creacion")
    private String fechacreacion;

    @NotNull
    @Field("fecha_actualizacion")
    private String fechaactualizacion;

    @NotNull
    private String tipo;

    public PresupuestoPersonal(String id, String nombre, String idusuario){
        this.id = id;
        this.nombre = nombre;
        this.idusuario = idusuario;
        this.fechacreacion = FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S);
        this.fechaactualizacion =this.fechacreacion;
        this.tipo = EnumTipoEstadoFinanciero.PRESUPUESTO_PERSONAL.name();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getIdusuario() {
        return idusuario;
    }

    public void setIdusuario(String idusuario) {
        this.idusuario = idusuario;
    }

    public double getTotalingresos() {
        return totalingresos;
    }

    public void setTotalingresos(double totalingresos) {
        this.totalingresos = totalingresos;
    }

    public double getTotalgastos() {
        return totalgastos;
    }

    public void setTotalgastos(double totalgastos) {
        this.totalgastos = totalgastos;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getFechaactualizacion() {
        return fechaactualizacion;
    }

    public void setFechaactualizacion(String fechaactualizacion) {
        this.fechaactualizacion = fechaactualizacion;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
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
}
