package com.example.proyectopdm;

public class AccesoUsuario {
    int idOpcion,idUsuario;

    public AccesoUsuario(int idOpcion, int idUsuario) {
        this.idOpcion = idOpcion;
        this.idUsuario = idUsuario;
    }

    public AccesoUsuario() {
    }

    public int getIdOpcion() {
        return idOpcion;
    }

    public void setIdOpcion(int idOpcion) {
        this.idOpcion = idOpcion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }
}
