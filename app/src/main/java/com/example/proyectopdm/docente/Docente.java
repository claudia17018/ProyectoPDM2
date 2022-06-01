package com.example.proyectopdm.docente;

import android.content.Entity;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.appcompat.app.AppCompatActivity;

public class Docente implements Parcelable{
    private int idUsuario;
    private String nombreTutor;
    private String apellidosTutor;
    private String duiTutor;
    private String emailTutor;
    private String nitTutor;
    private String telefonoTutor;
    private int idDocente;

    public Docente() {
    }

    public Docente(int idDocente, String duiTutor,int idUsuario, String nombreTutor, String apellidosTutor,  String emailTutor, String telefonoTutor, String nitTutor) {
        this.idUsuario = idUsuario;
        this.nombreTutor = nombreTutor;
        this.apellidosTutor = apellidosTutor;
        this.duiTutor = duiTutor;
        this.emailTutor = emailTutor;
        this.nitTutor = nitTutor;
        this.telefonoTutor = telefonoTutor;
        this.idDocente = idDocente;
    }

    public Docente(String nombreTutor, String emailTutor, String apellidosTutor) {
        this.nombreTutor = nombreTutor;
        this.emailTutor = emailTutor;
        this.apellidosTutor = apellidosTutor;
    }

    protected Docente(Parcel in) {
        idUsuario = in.readInt();
        nombreTutor = in.readString();
        apellidosTutor = in.readString();
        duiTutor = in.readString();
        emailTutor = in.readString();
        nitTutor = in.readString();
        telefonoTutor = in.readString();
        idDocente = in.readInt();
    }

    public static final Creator<Docente> CREATOR = new Creator<Docente>() {
        @Override
        public Docente createFromParcel(Parcel in) {
            return new Docente(in);
        }

        @Override
        public Docente[] newArray(int size) {
            return new Docente[size];
        }
    };

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreTutor() {
        return nombreTutor;
    }

    public void setNombreTutor(String nombreTutor) {
        this.nombreTutor = nombreTutor;
    }

    public String getApellidosTutor() {
        return apellidosTutor;
    }

    public void setApellidosTutor(String apellidosTutor) {
        this.apellidosTutor = apellidosTutor;
    }

    public String getDuiTutor() {
        return duiTutor;
    }

    public void setDuiTutor(String duiTutor) {
        this.duiTutor = duiTutor;
    }

    public String getEmailTutor() {
        return emailTutor;
    }

    public void setEmailTutor(String emailTutor) {
        this.emailTutor = emailTutor;
    }

    public String getNitTutor() {
        return nitTutor;
    }

    public void setNitTutor(String nitTutor) {
        this.nitTutor = nitTutor;
    }

    public String getTelefonoTutor() {
        return telefonoTutor;
    }

    public void setTelefonoTutor(String telefonoTutor) {
        this.telefonoTutor = telefonoTutor;
    }

    public int getIdDocente() {
        return idDocente;
    }

    public void setIdDocente(int idDocente) {
        this.idDocente = idDocente;
    }

    @Override
    public String toString() {
        return "Docente{" +
                "idUsuario=" + idUsuario +
                ", nombreTutor='" + nombreTutor + '\'' +
                ", apellidosTutor='" + apellidosTutor + '\'' +
                ", duiTutor='" + duiTutor + '\'' +
                ", emailTutor='" + emailTutor + '\'' +
                ", nitTutor='" + nitTutor + '\'' +
                ", telefonoTutor='" + telefonoTutor + '\'' +
                ", idDocente='" + idDocente + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {


        parcel.writeInt(idDocente);
        parcel.writeString(duiTutor);
        parcel.writeInt(idUsuario);
        parcel.writeString(nombreTutor);
        parcel.writeString(apellidosTutor);
        parcel.writeString(telefonoTutor);
        parcel.writeString(emailTutor);
        parcel.writeString(nitTutor);


    }
}
