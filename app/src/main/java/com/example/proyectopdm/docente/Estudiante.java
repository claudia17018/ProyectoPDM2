package com.example.proyectopdm.docente;

import android.os.Parcel;
import android.os.Parcelable;

public class Estudiante implements Parcelable {

    private String carnet;
    private int idUsuario;
    private String nombreEstudiante;
    private String apellidoEstudiante;
    private String telefono;
    private String emailEstudinate;
    private String duiEstudiante;
    private String domicilioEstudiante;
    private String nitEstudiante;

    public Estudiante() {
    }

    public Estudiante( String carnet,int idUsuario, String nombreEstudiante, String apellidoEstudiante, String telefono, String emailEstudinate, String duiEstudiante, String domicilioEstudiante, String nitEstudiante) {
        this.carnet = carnet;
        this.idUsuario = idUsuario;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.telefono = telefono;
        this.emailEstudinate = emailEstudinate;
        this.duiEstudiante = duiEstudiante;
        this.domicilioEstudiante = domicilioEstudiante;
        this.nitEstudiante = nitEstudiante;
    }

    public Estudiante(String carnet, String nombreEstudiante, String apellidoEstudiante, String emailEstudinate) {
        this.carnet = carnet;
        this.nombreEstudiante = nombreEstudiante;
        this.apellidoEstudiante = apellidoEstudiante;
        this.emailEstudinate = emailEstudinate;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreEstudiante() {
        return nombreEstudiante;
    }

    public void setNombreEstudiante(String nombreEstudiante) {
        this.nombreEstudiante = nombreEstudiante;
    }

    public String getApellidoEstudiante() {
        return apellidoEstudiante;
    }

    public void setApellidoEstudiante(String apellidoEstudiante) {
        this.apellidoEstudiante = apellidoEstudiante;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmailEstudinate() {
        return emailEstudinate;
    }

    public void setEmailEstudinate(String emailEstudinate) {
        this.emailEstudinate = emailEstudinate;
    }

    public String getDuiEstudiante() {
        return duiEstudiante;
    }

    public void setDuiEstudiante(String duiEstudiante) {
        this.duiEstudiante = duiEstudiante;
    }

    public String getDomicilioEstudiante() {
        return domicilioEstudiante;
    }

    public void setDomicilioEstudiante(String domicilioEstudiante) {
        this.domicilioEstudiante = domicilioEstudiante;
    }

    public String getNitEstudiante() {
        return nitEstudiante;
    }

    public void setNitEstudiante(String nitEstudiante) {
        this.nitEstudiante = nitEstudiante;
    }

    @Override
    public String toString() {
        return "Estudiante{" +
                "carnet='" + carnet + '\'' +
                ", idUsuario=" + idUsuario +
                ", nombreEstudiante='" + nombreEstudiante + '\'' +
                ", apellidoEstudiante='" + apellidoEstudiante + '\'' +
                ", telefono='" + telefono + '\'' +
                ", emailEstudinate='" + emailEstudinate + '\'' +
                ", duiEstudiante='" + duiEstudiante + '\'' +
                ", domicilioEstudiante='" + domicilioEstudiante + '\'' +
                ", nitEstudiante='" + nitEstudiante + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

        parcel.writeString(carnet);
        parcel.writeInt(idUsuario);
        parcel.writeString(nombreEstudiante);
        parcel.writeString(apellidoEstudiante);
        parcel.writeString(telefono);
        parcel.writeString(emailEstudinate);
        parcel.writeString(duiEstudiante);
        parcel.writeString(domicilioEstudiante);
        parcel.writeString(nitEstudiante);
    }
}
