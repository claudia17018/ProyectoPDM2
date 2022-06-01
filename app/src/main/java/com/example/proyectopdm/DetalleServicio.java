package com.example.proyectopdm;

import android.os.Parcel;
import android.os.Parcelable;

public class DetalleServicio implements Parcelable {
    int idDetalle,idRes,horasAsignadas, directos,indirectos;
    String observaciones,estado;

    public DetalleServicio(int idDetalle, int idRes, int horasAsignadas, int directos, int indirectos, String observaciones, String estado) {
        this.idDetalle = idDetalle;
        this.idRes = idRes;
        this.horasAsignadas = horasAsignadas;
        this.directos = directos;
        this.indirectos = indirectos;
        this.observaciones = observaciones;
        this.estado = estado;
    }

    public DetalleServicio() {
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdRes() {
        return idRes;
    }

    public void setIdRes(int idRes) {
        this.idRes = idRes;
    }

    public int getHorasAsignadas() {
        return horasAsignadas;
    }

    public void setHorasAsignadas(int horasAsignadas) {
        this.horasAsignadas = horasAsignadas;
    }

    public int getDirectos() {
        return directos;
    }

    public void setDirectos(int directos) {
        this.directos = directos;
    }

    public int getIndirectos() {
        return indirectos;
    }

    public void setIndirectos(int indirectos) {
        this.indirectos = indirectos;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return "DetalleServicio{" +
                "idDetalle=" + idDetalle +
                ", idRes=" + idRes +
                ", horasAsignadas=" + horasAsignadas +
                ", directos=" + directos +
                ", indirectos=" + indirectos +
                ", observaciones='" + observaciones + '\'' +
                ", estado='" + estado + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(idDetalle);
        parcel.writeInt(idRes);
        parcel.writeInt(horasAsignadas);
        parcel.writeInt(directos);
        parcel.writeInt(indirectos);
        parcel.writeString(observaciones);
        parcel.writeString(estado);


    }
}
