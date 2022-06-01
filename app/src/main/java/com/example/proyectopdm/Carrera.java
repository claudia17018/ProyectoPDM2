package com.example.proyectopdm;

import android.os.Parcel;
import android.os.Parcelable;

public class Carrera implements Parcelable{
    int idCarrera,idArea;
    String nomEscuela,nomCarrera;

    public Carrera(int idCarrera, int idArea,  String nomEscuela,String nomCarrera) {
        this.idCarrera = idCarrera;
        this.idArea = idArea;
        this.nomEscuela = nomEscuela;
        this.nomCarrera = nomCarrera;
    }

    public Carrera() {
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public String getNomCarrera() {
        return nomCarrera;
    }

    public void setNomCarrera(String nomCarrera) {
        this.nomCarrera = nomCarrera;
    }

    public String getNomEscuela() {
        return nomEscuela;
    }

    public void setNomEscuela(String nomEscuela) {
        this.nomEscuela = nomEscuela;
    }

    @Override
    public String toString() {
        return "Carrera{" +
                "idCarrera=" + idCarrera +
                ", idArea=" + idArea +
                ", nomEscuela='" + nomEscuela + '\'' +
                ", nomCarrera='" + nomCarrera + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idCarrera);
        parcel.writeInt(idArea);
        parcel.writeString(nomCarrera);
        parcel.writeString(nomEscuela);

    }


}

