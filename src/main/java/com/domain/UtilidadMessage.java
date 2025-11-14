package com.domain;

public class UtilidadMessage {

    private String idUsuario;
    private Double valorUtilidad;
    private String fecha;

    public UtilidadMessage() {
    }

    public UtilidadMessage(String idUsuario, Double valorUtilidad, String fecha) {
        this.idUsuario = idUsuario;
        this.valorUtilidad = valorUtilidad;
        this.fecha = fecha;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Double getValorUtilidad() {
        return valorUtilidad;
    }

    public void setValorUtilidad(Double valorUtilidad) {
        this.valorUtilidad = valorUtilidad;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }
}
