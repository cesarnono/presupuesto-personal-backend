package com.gestion.cuentas.modelo;

import com.gestion.cuentas.constante.EnumFormatoFecha;
import com.gestion.cuentas.utilidad.FechaUtilidad;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotNull;

@Document(collection = "usuario")
public class Usuario {
    @Id
    private String id;

    @NotNull
    private String nombreusuario;

    @NotNull
    private String password;

    @NotNull
    @Field("fecha_creacion")
    private String fechacreacion;

    @NotNull
    @Field("fecha_modificacion")
    private String fechamodificacion;


    public Usuario(@NotNull String nombreusuario, @NotNull String password) {
        this.nombreusuario = nombreusuario;
        this.password = password;
        this.fechacreacion = FechaUtilidad.obtenerFechaActual(EnumFormatoFecha.FORMATO_D_M_A_H_M_S);
        this.fechamodificacion = this.fechacreacion;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombreusuario() {
        return nombreusuario;
    }

    public void setNombreusuario(String nombreusuario) {
        this.nombreusuario = nombreusuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
