package com.gestion.cuentas.modelo;

import com.gestion.cuentas.constante.EnumFormatoFecha;
import com.gestion.cuentas.utilidad.FechaUtilidad;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document(collection = "cuenta")
public class Cuenta {

    private String id;

    private final String nombre;

    private final double valor;

    private final String clase;

    @Field("id_presupuesto")
    private final String idpresupuesto;

    @Field("fecha_creacion")
    private final String fechacreacion;

    @Field("fecha_modificacion")
    private final String fechamodificacion;

    @PersistenceConstructor
    public Cuenta(String id ,String nombre, double valor, String clase, String idpresupuesto,
                  String fechacreacion, String fechamodificacion) {
        this.id= id;
        this.nombre = nombre;
        this.valor = valor;
        this.clase = clase;
        this.idpresupuesto = idpresupuesto;
        this.fechacreacion = fechacreacion;
        this.fechamodificacion = fechamodificacion;
    }

    public static Cuenta nuevaCuenta(String nombre, double valor, String clase, String idpresupuesto){
        return new Cuenta(null,nombre,valor,clase,idpresupuesto,
                FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S),
                FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S));
    }

    public String getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public double getValor() {
        return valor;
    }

    public String getClase() {
        return clase;
    }

    public String getIdpresupuesto() {
        return idpresupuesto;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public String getFechamodificacion() {
        return fechamodificacion;
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
