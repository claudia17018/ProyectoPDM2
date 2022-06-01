package com.example.proyectopdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Declaracion de variables
    EditText user,pass;
    Button btnEntrar,btnRegistar;
    BD db;
    DT dt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Enlazar las variables con el contenido del layout

        user=(EditText) findViewById(R.id.user);
        pass=(EditText) findViewById(R.id.password);
        btnEntrar=(Button) findViewById(R.id.btnIngresar);
       // btnRegistar=(Button) findViewById(R.id.btnRegistrarse);
        db=new BD(this);

        //Asignacion de eventos a los botones
        btnEntrar.setOnClickListener(this);
        //btnRegistar.setOnClickListener(this);

        db.abrir();
        db.llenarDB();
        db.cerrar();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //Definicion de comportamiento de los botones
             case R.id.btnIngresar:
                 db.abrir();
                String u=user.getText().toString();
                String p=pass.getText().toString();
                if(u.equals("")||p.equals("")){
                    Toast.makeText(this, "ERROR: Campos vacios", Toast.LENGTH_SHORT).show();
                }else if(db.login(u,p)==1){

                    Usuario us =db.getUsuario(u,p);
                    dt=new DT();
                    dt.setIdU(us.getId());
                    db.insertarDT(dt);

                    if(db.consultarNivelAcceso(dt.getIdU())==1||db.consultarNivelAcceso(dt.getIdU())==2){
                        Toast.makeText(this, "DATOS CORRECTOS", Toast.LENGTH_SHORT).show();
                        Intent i3=new Intent(MainActivity.this,MenuOpcionesActivity.class);
                        // Pasando el id del usuario
                        i3.putExtra("id",us.getId());
                        startActivity(i3);
                    }else{
                        Toast.makeText(this, "DATOS CORRECTOS", Toast.LENGTH_SHORT).show();
                        Intent i9=new Intent(MainActivity.this,MenuOpciones2Activity.class);
                        // Pasando el id del usuario
                        i9.putExtra("id",us.getId());
                        startActivity(i9);
                    }

                    user.setText("");
                    pass.setText("");
                db.cerrar();
                }else{
                    Toast.makeText(this, "Usuario y/o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                }
                break;
            /*case R.id.btnRegistrarse:
                Intent i=new Intent(MainActivity.this,RegistrarUsuarioActivity.class);
                startActivity(i);
                break;*/
        }
    }
}