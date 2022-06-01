package com.example.proyectopdm.entidades;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Actividad implements Parcelable {
    private int idActividad;
    private String nombreActividad;
    private String descripcionTipoActividad;
    private String fechaActividad;
    private int numHorasActividades;

    public Actividad() {
    }

    public Actividad(int idActividad, String nombreActividad, String descripcionTipoActividad, String fechaActividad, int numHorasActividades) {
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.descripcionTipoActividad = descripcionTipoActividad;
        this.fechaActividad = fechaActividad;
        this.numHorasActividades = numHorasActividades;
    }

    public Actividad(String nombreActividad, String fechaActividad) {
        this.nombreActividad = nombreActividad;
        this.fechaActividad = fechaActividad;
    }

    public Actividad(int idActividad, String nombreActividad, String fechaActividad) {
        this.idActividad = idActividad;
        this.nombreActividad = nombreActividad;
        this.fechaActividad = fechaActividad;
    }

    protected Actividad(Parcel in) {
        idActividad = in.readInt();
        nombreActividad = in.readString();
        descripcionTipoActividad = in.readString();
        fechaActividad = in.readString();
        numHorasActividades = in.readInt();
    }

    public static final Creator<Actividad> CREATOR = new Creator<Actividad>() {
        @Override
        public Actividad createFromParcel(Parcel in) {
            return new Actividad(in);
        }

        @Override
        public Actividad[] newArray(int size) {
            return new Actividad[size];
        }
    };

    public int getIdActividad() {
        return idActividad;
    }

    public void setIdActividad(int idActividad) {
        this.idActividad = idActividad;
    }

    public String getNombreActividad() {
        return nombreActividad;
    }

    public void setNombreActividad(String nombreActividad) {
        this.nombreActividad = nombreActividad;
    }

    public String getDescripcionTipoActividad() {
        return descripcionTipoActividad;
    }

    public void setDescripcionTipoActividad(String descripcionTipoActividad) {
        this.descripcionTipoActividad = descripcionTipoActividad;
    }

    public String getFechaActividad() {
        return fechaActividad;
    }

    public void setFechaActividad(String fechaActividad) {
        this.fechaActividad = fechaActividad;
    }

    public int getNumHorasActividades() {
        return numHorasActividades;
    }

    public void setNumHorasActividades(int numHorasActividades) {
        this.numHorasActividades = numHorasActividades;
    }

    @Override
    public String toString() {
        return "Actividad{" +
                "idActividad=" + idActividad +
                ", nombreActividad='" + nombreActividad + '\'' +
                ", descripcionTipoActividad='" + descripcionTipoActividad + '\'' +
                ", fechaActividad='" + fechaActividad + '\'' +
                ", numHorasActividades=" + numHorasActividades +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idActividad);
        parcel.writeString(nombreActividad);
        parcel.writeString(descripcionTipoActividad);
        parcel.writeString(fechaActividad);
        parcel.writeInt(numHorasActividades);
    }
}
