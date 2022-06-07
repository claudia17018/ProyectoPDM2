package com.example.proyectopdm;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Actividad;
import com.example.proyectopdm.entidades.Bitacora;

import java.util.ArrayList;

public class PdfActivity extends AppCompatActivity {

    BD db;
    DT dt;

    public int k=0;
    private String[]header={"Fecha","Nombre","Descripción","Horas"};
    private String shortTaxt,shortText;
    private String longText="Probando a ver si cambia el texto";
    private  TemplatePDF templatePDF;
    int idEP;
    ArrayList<Bitacora> listadoBitacora;
    ArrayList<Actividad> listadoActividad;
    BD.DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_pdf);

        SharedPreferences sharedPreferences = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        String carnet = sharedPreferences.getString("USUARIO","0");

        db =new BD(this);
        dt=new DT();
        db.abrir();
        dt=db.activo();

        String nom=db.obtenerNombreEstudiante(dt.getIdU());

        listadoBitacora=db.consultarBi(carnet);

        db.cerrar();
        /******************* Inicializacion de list ****************************/
        //listadoBitacora=db.consultarListadoBitacora2(carnet);





        /*************  Permisos ******************/
        if(checkPermission()){
            Toast.makeText(this, "PDF creado", Toast.LENGTH_SHORT).show();
        }else{
            requestPermissions();
        }

        /****************** Creacion del PDF **********************************/
        templatePDF= new TemplatePDF(getApplicationContext());
        templatePDF.openDocument();
        templatePDF.addMetaData("Servicio Social","Control",carnet);
        templatePDF.addTitles("Informe Servicio Social",nom,carnet.toString());

        for (int i=0;i<listadoBitacora.size();i++){

            shortText="Bitacora  "+i;
            templatePDF.addParagraphC(shortText);
            shortTaxt="Ciclo  "+listadoBitacora.get(i).getCiclo()+",    "+listadoBitacora.get(i).getMes()+"    "+listadoBitacora.get(i).getAnio();
            db.abrir();
            listadoActividad=db.consultarActividad(listadoBitacora.get(i).getIdBitacora());
            System.out.println("\n\n\n Pruebaaaa PDF"+listadoActividad);
            db.cerrar();
            templatePDF.addParagraph(shortTaxt);
            templatePDF.createTable(header,getClients(listadoActividad));


        }
        templatePDF.addParagraphE("Total de horas realizadas:  "+String.valueOf(k));

        //templatePDF.addParagraph(longText);

        templatePDF.closeDocument();
    }






    public void pdfView(View view){
        templatePDF.viewPDF();

    }
    private ArrayList<String[]> getClients( ArrayList<Actividad> listAct){
        ArrayList<String[]>rows=new ArrayList<>();

    for(int j =0;j<listAct.size();j++) {
         rows.add(new String[]{listAct.get(j).getFechaActividad(), listAct.get(j).getNombreActividad(), listAct.get(j).getDescripcionTipoActividad(), String.valueOf(listAct.get(j).getNumHorasActividades())});
    k=k+listAct.get(j).getNumHorasActividades();
    }
        return rows;
    }


    public boolean checkPermission(){
        int permission1= ContextCompat.checkSelfPermission(getApplicationContext(),WRITE_EXTERNAL_STORAGE);
        int permission2= ContextCompat.checkSelfPermission(getApplicationContext(),READ_EXTERNAL_STORAGE);
        return permission1 == PackageManager.PERMISSION_GRANTED && permission2==PackageManager.PERMISSION_GRANTED;

    }
    private void requestPermissions(){
        ActivityCompat.requestPermissions(this,new String[]{WRITE_EXTERNAL_STORAGE,READ_EXTERNAL_STORAGE},200);
    }
    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults){
        if(requestCode==200){
            if(grantResults.length>0){
                boolean writeStorage=grantResults[0]==PackageManager.PERMISSION_GRANTED;
                boolean readStorage=grantResults[1]==PackageManager.PERMISSION_GRANTED;

                if(writeStorage && readStorage){
                    Toast.makeText(this, "Permiso concedido", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }
    }


}