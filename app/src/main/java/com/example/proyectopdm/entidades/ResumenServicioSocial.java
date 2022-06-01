package com.example.proyectopdm.entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class ResumenServicioSocial implements Parcelable {
    private int idResumen;
    private String carnet;
    private int idDocente;
    private String fechaAperturaExpediente;
    private String fechaEmisionCertificado;

    public ResumenServicioSocial() {
    }

    public ResumenServicioSocial(int idResumen, String carnet, int idDocente, String fechaAperturaExpediente) {
        this.idResumen = idResumen;
        this.carnet = carnet;
        this.idDocente = idDocente;
        this.fechaAperturaExpediente = fechaAperturaExpediente;
    }

    public ResumenServicioSocial(int idResumen, String carnet, int idDocente, String fechaAperturaExpediente, String fechaEmisionCertificado) {
        this.idResumen = idResumen;
        this.carnet = carnet;
        this.idDocente = idDocente;
        this.fechaAperturaExpediente = fechaAperturaExpediente;
        this.fechaEmisionCertificado = fechaEmisionCertificado;
    }

    protected ResumenServicioSocial(Parcel in) {
        idResumen = in.readInt();
        carnet = in.readString();
        idDocente = in.readInt();
        fechaAperturaExpediente = in.readString();
        fechaEmisionCertificado = in.readString();
    }

    public static final Creator<ResumenServicioSocial> CREATOR = new Creator<ResumenServicioSocial>() {
        @Override
        public ResumenServicioSocial createFromParcel(Parcel in) {
            return new ResumenServicioSocial(in);
        }

        @Override
        public ResumenServicioSocial[] newArray(int size) {
            return new ResumenServicioSocial[size];
        }
    };

    public int getIdResumen() {
        return idResumen;
    }

    public void setIdResumen(int idResumen) {
        this.idResumen = idResumen;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public String getFechaAperturaExpediente() {
        return fechaAperturaExpediente;
    }

    public void setFechaAperturaExpediente(String fechaAperturaExpediente) {
        this.fechaAperturaExpediente = fechaAperturaExpediente;
    }

    public String getFechaEmisionCertificado() {
        return fechaEmisionCertificado;
    }

    public void setFechaEmisionCertificado(String fechaEmisionCertificado) {
        this.fechaEmisionCertificado = fechaEmisionCertificado;
    }

    @Override
    public String toString() {
        return "ResumenServicioSocial{" +
                "idResumen=" + idResumen +
                ", carnet='" + carnet + '\'' +
               // ", duiTutor='" + duiTutor + '\'' +
                ", fechaAperturaExpediente='" + fechaAperturaExpediente + '\'' +
                ", fechaEmisionCertificado='" + fechaEmisionCertificado + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idResumen);
        parcel.writeString(carnet);
        parcel.writeInt(idDocente);
        parcel.writeString(fechaAperturaExpediente);
        parcel.writeString(fechaEmisionCertificado);
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }
}
