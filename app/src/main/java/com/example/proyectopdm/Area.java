package com.example.proyectopdm;

import android.os.Parcel;
import android.os.Parcelable;

public class Area implements Parcelable {
    int idArea;
    String nomArea,desArea;

    public Area(int idArea, String desArea,String nomArea) {
        this.idArea = idArea;

        this.desArea = desArea;
        this.nomArea = nomArea;
    }

    public Area() {
    }

    public int getIdArea() {
        return idArea;
    }

    public void setIdArea(int idArea) {
        this.idArea = idArea;
    }

    public String getNomArea() {
        return nomArea;
    }

    public void setNomArea(String nomArea) {
        this.nomArea = nomArea;
    }

    public String getDesArea() {
        return desArea;
    }

    public void setDesArea(String desArea) {
        this.desArea = desArea;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idArea);
        parcel.writeString(nomArea);
        parcel.writeString(desArea);
    }

    @Override
    public String toString() {
        return "Area{" +
                "idArea=" + idArea +
                ", nomArea='" + nomArea + '\'' +
                ", desArea='" + desArea + '\'' +
                '}';
    }
}
