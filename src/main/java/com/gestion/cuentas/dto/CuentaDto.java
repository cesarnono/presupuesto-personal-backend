package com.gestion.cuentas.dto;

import java.util.List;

public class CuentaDto {

    private String id;

    private String nombre;

    private double valor;

    private String clase;

    private String idpresupuesto;

    private String fechacreacion;

    private String descripcion;

    private List<MovimientoDto> movimientosDto;

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

    public List<MovimientoDto> getMovimientosDto() {
        return movimientosDto;
    }

    public void setMovimientosDto(List<MovimientoDto> movimientosDto) {
        this.movimientosDto = movimientosDto;
    }

    public String getDescripcion() {
        return descripcion!= null? descripcion: "--";
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
