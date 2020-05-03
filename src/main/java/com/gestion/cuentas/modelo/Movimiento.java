package com.gestion.cuentas.modelo;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "movimiento")
public class Movimiento {
    private String id;

    private final String descripcion;

    private final String idcuenta;

    @Field("fecha_creacion")
    private final String fechacreacion;

    public Movimiento(String id, String descripcion, String idcuenta, String fechacreacion) {
        this.id = id;
        this.descripcion = descripcion;
        this.idcuenta = idcuenta;
        this.fechacreacion = fechacreacion;
    }

    public String getId() {
        return id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public String getIdcuenta() {
        return idcuenta;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }
}
