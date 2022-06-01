package com.example.proyectopdm;

import android.os.Parcel;
import android.os.Parcelable;

public class Record implements Parcelable{
    int idRecord,idCarrera;
    String carnet;
    int uV;
    double promedio, cum,proceso;

    public Record(int idRecord, int idCarrera, String carnet, int uV, double promedio, double cum, double proceso) {
        this.idRecord = idRecord;
        this.idCarrera = idCarrera;
        this.carnet = carnet;
        this.uV = uV;
        this.promedio = promedio;
        this.cum = cum;
        this.proceso = proceso;
    }

    public Record() {
    }

    public int getIdRecord() {
        return idRecord;
    }

    public void setIdRecord(int idRecord) {
        this.idRecord = idRecord;
    }

    public int getIdCarrera() {
        return idCarrera;
    }

    public void setIdCarrera(int idCarrera) {
        this.idCarrera = idCarrera;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public int getuV() {
        return uV;
    }

    public void setuV(int uV) {
        this.uV = uV;
    }

    public double getPromedio() {
        return promedio;
    }

    public void setPromedio(double promedio) {
        this.promedio = promedio;
    }

    public double getCum() {
        return cum;
    }

    public void setCum(double cum) {
        this.cum = cum;
    }

    public double getProceso() {
        return proceso;
    }

    public void setProceso(double proceso) {
        this.proceso = proceso;
    }

    @Override
    public String toString() {
        return "Record{" +
                "idRecord=" + idRecord +
                ", idCarrera=" + idCarrera +
                ", carnet='" + carnet + '\'' +
                ", uV=" + uV +
                ", promedio=" + promedio +
                ", cum=" + cum +
                ", proceso=" + proceso +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(idRecord);
        parcel.writeInt(idCarrera);
        parcel.writeString(carnet);
        parcel.writeInt(uV);
        parcel.writeDouble(promedio);
        parcel.writeDouble(cum);
        parcel.writeDouble(proceso);


    }
}
