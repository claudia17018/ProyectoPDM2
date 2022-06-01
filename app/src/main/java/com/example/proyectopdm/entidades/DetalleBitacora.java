package com.example.proyectopdm.entidades;

public class DetalleBitacora {
    private int idDetalleBitacora;
    private int idActividad;
    private int idBitacora;
    private String fechaCreacion;

    public DetalleBitacora() {
    }

    public DetalleBitacora(int idDetalleBitacora, int idActividad, int idBitacora, String fechaCreacion) {
        this.idDetalleBitacora = idDetalleBitacora;
        this.idActividad = idActividad;
        this.idBitacora = idBitacora;
        this.fechaCreacion = fechaCreacion;
    }

    public int getIdDetalleBitacora() {
        return idDetalleBitacora;
    }

    public void setIdDetalleBitacora(int idDetalleBitacora) {
        this.idDetalleBitacora = idDetalleBitacora;
    }

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public int getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    @Override
    public String toString() {
        return "DetalleBitacora{" +
                "idDetalleBitacora=" + idDetalleBitacora +
                ", idActividad=" + idActividad +
                ", idBitacora=" + idBitacora +
                ", fechaCreacion='" + fechaCreacion + '\'' +
                '}';
    }
}
