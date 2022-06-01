package com.example.proyectopdm;

import android.os.Parcel;
import android.os.Parcelable;

public class ResumenServicio implements Parcelable{
    int idResumen;
    String carnetEs,duiTu,fechaApertura,fechaCertificado;

    public ResumenServicio(int idResumen, String carnetEs, String duiTu, String fechaApertura, String fechaCertificado) {
        this.idResumen = idResumen;
        this.carnetEs = carnetEs;
        this.duiTu = duiTu;
        this.fechaApertura = fechaApertura;
        this.fechaCertificado = fechaCertificado;
    }

    public ResumenServicio() {
    }

    public int getIdResumen() {
        return idResumen;
    }

    public void setIdResumen(int idResumen) {
        this.idResumen = idResumen;
    }

    public String getCarnetEs() {
        return carnetEs;
    }

    public void setCarnetEs(String carnetEs) {
        this.carnetEs = carnetEs;
    }

    public String getDuiTu() {
        return duiTu;
    }

    public void setDuiTu(String duiTu) {
        this.duiTu = duiTu;
    }

    public String getFechaApertura() {
        return fechaApertura;
    }

    public void setFechaApertura(String fechaApertura) {
        this.fechaApertura = fechaApertura;
    }

    public String getFechaCertificado() {
        return fechaCertificado;
    }

    public void setFechaCertificado(String fechaCertificado) {
        this.fechaCertificado = fechaCertificado;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public String toString() {
        return "ResumenServicio{" +
                "idResumen=" + idResumen +
                ", carnetEs='" + carnetEs + '\'' +
                ", duiTu='" + duiTu + '\'' +
                ", fechaApertura='" + fechaApertura + '\'' +
                ", fechaCertificado='" + fechaCertificado + '\'' +
                '}';
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idResumen);
        parcel.writeString(carnetEs);
        parcel.writeString(duiTu);
        parcel.writeString(fechaApertura);
        parcel.writeString(fechaCertificado);
    }
}
