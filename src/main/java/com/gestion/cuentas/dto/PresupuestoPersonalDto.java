package com.gestion.cuentas.dto;

import java.util.List;

public class PresupuestoPersonalDto {
    private String id;
    private String nombre;
    private String idusuario;
    private String fechacreacion;
    private double totalingresos;
    private double totalgastos;
    private double totalsaldo;
    private String idpresupuestopadre;

    public String getIdpresupuestopadre() {
        return idpresupuestopadre;
    }

    public void setIdpresupuestopadre(String idpresupuestopadre) {
        this.idpresupuestopadre = idpresupuestopadre;
    }

    private List<CuentaDto> cuentaDtos;

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

    public double getTotalsaldo() {
        return totalsaldo;
    }

    public void setTotalsaldo(double totalsaldo) {
        this.totalsaldo = totalsaldo;
    }

    public String getFechacreacion() {
        return fechacreacion;
    }

    public void setFechacreacion(String fechacreacion) {
        this.fechacreacion = fechacreacion;
    }

    public List<CuentaDto> getCuentaDtos() {
        return cuentaDtos;
    }

    public void setCuentaDtos(List<CuentaDto> cuentaDtos) {
        this.cuentaDtos = cuentaDtos;
    }

    @Override
    public String toString() {
        return "PresupuestoPersonalDto{" +
                "nombre='" + nombre + '\'' +
                ", idusuario='" + idusuario + '\'' +
                ", totalingresos=" + totalingresos +
                ", totalgastos=" + totalgastos +
                ", totalsaldo=" + totalsaldo +
                '}';
    }
}
