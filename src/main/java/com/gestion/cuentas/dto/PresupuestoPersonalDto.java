package com.gestion.cuentas.dto;

public class PresupuestoPersonalDto {
    private String id;
    private String nombre;
    private String idusuario;
    private double totalingresos;
    private double totalgastos;
    private double totalsaldo;

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
