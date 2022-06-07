package com.example.proyectopdm;

public class PutPDF {

    public String name;
    public String url;
    public PutPDF(){
    }

    public PutPDF(String name,String url){
        this.name=name;
        this.url=url;
    }

    public String getName(){
        return name;
    }
    public void setName(String name){
        this.name=name;
    }
}
