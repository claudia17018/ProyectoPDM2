package com.example.proyectopdm.docente;

public class OpcionCrud {

    private String idOption;
    private String desOpcion;
    private int numCrud;

    public OpcionCrud() {
    }

    public OpcionCrud(String desOpcion, int numCrud) {
        this.desOpcion = desOpcion;
        this.numCrud = numCrud;
    }

    public String getDesOpcion() {
        return desOpcion;
    }

    public void setDesOpcion(String desOpcion) {
        this.desOpcion = desOpcion;
    }

    public int getNumCrud() {
        return numCrud;
    }

    public void setNumCrud(int numCrud) {
        this.numCrud = numCrud;
    }
}
