package com.example.proyectopdm.entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class Bitacora implements Parcelable {
    private int idBitacora;
    private int idEstudianteProyecto;
    private int ciclo;
    private String mes;
    private int anio;

    public Bitacora(){
    }

    public Bitacora(int idBitacora, int idEstudianteProyecto, int ciclo, String mes, int anio) {
        this.idBitacora = idBitacora;
        this.idEstudianteProyecto = idEstudianteProyecto;
        this.ciclo = ciclo;
        this.mes = mes;
        this.anio = anio;
    }

    public Bitacora(int idBitacora, int ciclo, String mes, int anio) {
        this.idBitacora = idBitacora;
        this.ciclo = ciclo;
        this.mes = mes;
        this.anio = anio;
    }

    public int getIdBitacora() {
        return idBitacora;
    }

    public void setIdBitacora(int idBitacora) {
        this.idBitacora = idBitacora;
    }

    public int getIdEstudianteProyecto() {
        return idEstudianteProyecto;
    }

    public void setIdEstudianteProyecto(int idEstudianteProyecto) {
        this.idEstudianteProyecto = idEstudianteProyecto;
    }

    public int getCiclo() {
        return ciclo;
    }

    public void setCiclo(int ciclo) {
        this.ciclo = ciclo;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public int getAnio() {
        return anio;
    }

    public void setAnio(int anio) {
        this.anio = anio;
    }

    @Override
    public String toString() {
        return "Bitacora{" +
                "idBitacora=" + idBitacora +
                ", idEstudianteProyecto=" + idEstudianteProyecto +
                ", ciclo=" + ciclo +
                ", mes='" + mes + '\'' +
                ", anio=" + anio +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idBitacora);
        parcel.writeInt(ciclo);
        parcel.writeString(mes);
        parcel.writeInt(anio);
        parcel.writeInt(idEstudianteProyecto);
    }
}
