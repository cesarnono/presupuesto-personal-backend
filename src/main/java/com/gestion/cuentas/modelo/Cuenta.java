package com.gestion.cuentas.modelo;

import com.gestion.cuentas.constante.EnumFormatoFecha;
import com.gestion.cuentas.utilidad.FechaUtilidad;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document(collection = "cuenta")
public class Cuenta {

    @Id
    private String id;

    @NotNull
    private String nombre;

    @NotNull
    private double valor;
    @NotNull
    @Field("clase")
    private String clase;

    @NotNull
    @Field("id_presupuesto")
    private String idpresupuesto;

    @NotNull
    @Field("fecha_creacion")
    private String fechacreacion;

    @NotNull
    @Field("fecha_modificacion")
    private String fechamodificacion;

    public Cuenta(@NotNull String nombre, @NotNull double valor, @NotNull String clase, @NotNull String idpresupuesto) {
        this.nombre = nombre;
        this.valor = valor;
        this.clase = clase;
        this.idpresupuesto = idpresupuesto;
        this.fechacreacion = FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S);
        this.fechamodificacion = this.fechacreacion;
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

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public String getClase() {
        return clase;
    }

    public void setClase(String clase) {
        this.clase = clase;
    }

    public String getIdpresupuesto() {
        return idpresupuesto;
    }

    public void setIdpresupuesto(String idpresupuesto) {
        this.idpresupuesto = idpresupuesto;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public String getFechamodificacion() {
        return fechamodificacion;
    }

    public void setFechamodificacion(String fechamodificacion) {
        this.fechamodificacion = fechamodificacion;
    }

    @Override
    public String toString() {
        return "Cuenta{" +
                "id='" + id + '\'' +
                ", nombre='" + nombre + '\'' +
                ", valor=" + valor +
                ", clase='" + clase + '\'' +
                ", idpresupuesto='" + idpresupuesto + '\'' +
                '}';
    }
}
