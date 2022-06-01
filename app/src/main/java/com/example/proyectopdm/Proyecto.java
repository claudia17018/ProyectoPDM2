package com.example.proyectopdm;

import android.os.Parcel;
import android.os.Parcelable;

public class Proyecto implements Parcelable {
    int idPro, idMod;
    String duiTu;
    int idCat;
    String nomPro,desPro,lugar,respoIns;
    int numHoras,numEst;
    String estadoPro;

    public Proyecto(int idPro, int idMod, String duiTu, int idCat, String nomPro, String desPro, String lugar, String respoIns, int numHoras, int numEst, String estadoPro) {
        this.idPro = idPro;
        this.idMod = idMod;
        this.duiTu = duiTu;
        this.idCat = idCat;
        this.nomPro = nomPro;
        this.desPro = desPro;
        this.lugar = lugar;
        this.respoIns = respoIns;
        this.numHoras = numHoras;
        this.numEst = numEst;
        this.estadoPro = estadoPro;
    }

    public Proyecto() {
    }

    protected Proyecto(Parcel in) {
        idPro = in.readInt();
        idMod = in.readInt();
        duiTu = in.readString();
        idCat = in.readInt();
        nomPro = in.readString();
        desPro = in.readString();
        lugar = in.readString();
        respoIns = in.readString();
        numHoras = in.readInt();
        numEst = in.readInt();
        estadoPro = in.readString();
    }

    public static final Creator<Proyecto> CREATOR = new Creator<Proyecto>() {
        @Override
        public Proyecto createFromParcel(Parcel in) {
            return new Proyecto(in);
        }

        @Override
        public Proyecto[] newArray(int size) {
            return new Proyecto[size];
        }
    };

    public int getIdPro() {
        return idPro;
    }

    public void setIdPro(int idPro) {
        this.idPro = idPro;
    }

    public int getIdMod() {
        return idMod;
    }

    public void setIdMod(int idMod) {
        this.idMod = idMod;
    }

    public String getDuiTu() {
        return duiTu;
    }

    public void setDuiTu(String duiTu) {
        this.duiTu = duiTu;
    }

    public int getIdCat() {
        return idCat;
    }

    public void setIdCat(int idCat) {
        this.idCat = idCat;
    }

    public String getNomPro() {
        return nomPro;
    }

    public void setNomPro(String nomPro) {
        this.nomPro = nomPro;
    }

    public String getDesPro() {
        return desPro;
    }

    public void setDesPro(String desPro) {
        this.desPro = desPro;
    }

    public String getLugar() {
        return lugar;
    }

    public void setLugar(String lugar) {
        this.lugar = lugar;
    }

    public String getRespoIns() {
        return respoIns;
    }

    public void setRespoIns(String respoIns) {
        this.respoIns = respoIns;
    }

    public int getNumHoras() {
        return numHoras;
    }

    public void setNumHoras(int numHoras) {
        this.numHoras = numHoras;
    }

    public int getNumEst() {
        return numEst;
    }

    public void setNumEst(int numEst) {
        this.numEst = numEst;
    }

    public String getEstadoPro() {
        return estadoPro;
    }

    public void setEstadoPro(String estadoPro) {
        this.estadoPro = estadoPro;
    }

    @Override
    public String toString() {
        return "Proyecto{" +
                "idPro=" + idPro +
                ", idMod=" + idMod +
                ", duiTu='" + duiTu + '\'' +
                ", idCat=" + idCat +
                ", nomPro='" + nomPro + '\'' +
                ", desPro='" + desPro + '\'' +
                ", lugar='" + lugar + '\'' +
                ", respoIns='" + respoIns + '\'' +
                ", numHoras='" + numHoras + '\'' +
                ", numEst='" + numEst + '\'' +
                ", estadoPro='" + estadoPro + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeInt(idPro);
        parcel.writeInt(idMod);
        parcel.writeString(duiTu);
        parcel.writeInt(idCat);
        parcel.writeString(nomPro);
        parcel.writeString(desPro);
        parcel.writeString(lugar);
        parcel.writeString(respoIns);
        parcel.writeInt(numHoras);
        parcel.writeInt(numEst);
        parcel.writeString(estadoPro);
    }
}
