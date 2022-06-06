package com.example.proyectopdm.entidades;

public class EstudianteProyecto
{
    private int id;
    private String carnet;
    private int idProyecto,idResumen,num;

    public EstudianteProyecto(int id, String carnet, int idProyecto, int idResumen, int num) {
        this.id = id;
        this.carnet = carnet;
        this.idProyecto = idProyecto;
        this.idResumen = idResumen;
        this.num = num;
    }

    public EstudianteProyecto(int id, String carnet, int num) {
        this.id = id;
        this.carnet = carnet;
        this.num = num;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public EstudianteProyecto() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCarnet() {
        return carnet;
    }

    public void setCarnet(String carnet) {
        this.carnet = carnet;
    }

    public int getIdProyecto() {
        return idProyecto;
    }

    public void setIdProyecto(int idProyecto) {
        this.idProyecto = idProyecto;
    }

    public int getIdResumen() {
        return idResumen;
    }

    public void setIdResumen(int idResumen) {
        this.idResumen = idResumen;
    }
}
