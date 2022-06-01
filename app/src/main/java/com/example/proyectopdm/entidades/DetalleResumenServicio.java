package com.example.proyectopdm.entidades;

import android.os.Parcel;
import android.os.Parcelable;

public class DetalleResumenServicio implements Parcelable {
    private int idDetalleResumen;
    private int idResumen;
    private  int idDocente;
    // private String fechaInicio;
    //private String fechaFinal;
    private int horasAsignadas;
    private double monto;
    private int beneficiariosDirectos;
    private int beneficiariosIndirectos;
    private String observaciones;
    private String estadoDeResumen;

    public DetalleResumenServicio() {
    }

    public DetalleResumenServicio(int idDetalleResumen, int idResumen, int horasAsignadas, double monto, int beneficiariosDirectos, int beneficiariosIndirectos, String observaciones, String estadoDeResumen) {
        this.idDetalleResumen = idDetalleResumen;
        this.idResumen = idResumen;
        this.horasAsignadas = horasAsignadas;
        this.monto = monto;
        this.beneficiariosDirectos = beneficiariosDirectos;
        this.beneficiariosIndirectos = beneficiariosIndirectos;
        this.observaciones = observaciones;
        this.estadoDeResumen = estadoDeResumen;
    }

    protected DetalleResumenServicio(Parcel in) {
        idDetalleResumen = in.readInt();
        idResumen = in.readInt();
        horasAsignadas = in.readInt();
        monto = in.readDouble();
        beneficiariosDirectos = in.readInt();
        beneficiariosIndirectos = in.readInt();
        observaciones = in.readString();
        estadoDeResumen = in.readString();
    }

    public static final Creator<DetalleResumenServicio> CREATOR = new Creator<DetalleResumenServicio>() {
        @Override
        public DetalleResumenServicio createFromParcel(Parcel in) {
            return new DetalleResumenServicio(in);
        }

        @Override
        public DetalleResumenServicio[] newArray(int size) {
            return new DetalleResumenServicio[size];
        }
    };

    public int getIdDetalleResumen() {
        return idDetalleResumen;
    }

    public void setIdDetalleResumen(int idDetalleResumen) {
        this.idDetalleResumen = idDetalleResumen;
    }

    public int getIdResumen() {
        return idResumen;
    }

    public void setIdResumen(int idResumen) {
        this.idResumen = idResumen;
    }


    public int getHorasAsignadas() {
        return horasAsignadas;
    }

    public void setHorasAsignadas(int horasAsignadas) {
        this.horasAsignadas = horasAsignadas;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public int getBeneficiariosDirectos() {
        return beneficiariosDirectos;
    }

    public void setBeneficiariosDirectos(int beneficiariosDirectos) {
        this.beneficiariosDirectos = beneficiariosDirectos;
    }

    public int getBeneficiariosIndirectos() {
        return beneficiariosIndirectos;
    }

    public void setBeneficiariosIndirectos(int beneficiariosIndirectos) {
        this.beneficiariosIndirectos = beneficiariosIndirectos;
    }

    public String getEstadoDeResumen() {
        return estadoDeResumen;
    }

    public void setEstadoDeResumen(String estadoDeResumen) {
        this.estadoDeResumen = estadoDeResumen;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }

    @Override
    public String toString() {
        return "DetalleResumenServicio{" +
                "idDetalleResumen=" + idDetalleResumen +
                ", idResumen=" + idResumen +
                ", horasAsignadas=" + horasAsignadas +
                ", monto=" + monto +
                ", beneficiariosDirectos=" + beneficiariosDirectos +
                ", beneficiariosIndirectos=" + beneficiariosIndirectos +
                ", observaciones='" + observaciones + '\'' +
                ", estadoDeResumen='" + estadoDeResumen + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idDetalleResumen);
        parcel.writeInt(idResumen);
        parcel.writeInt(horasAsignadas);
        parcel.writeInt(beneficiariosDirectos);
        parcel.writeInt(beneficiariosIndirectos);
        parcel.writeString(observaciones);
        parcel.writeString(estadoDeResumen);
    }

}
