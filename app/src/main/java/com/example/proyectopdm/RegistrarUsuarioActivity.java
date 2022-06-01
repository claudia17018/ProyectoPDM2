package com.example.proyectopdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;

public class RegistrarUsuarioActivity extends AppCompatActivity implements View.OnClickListener {
    EditText usuario,contraseña;
    Button btnRegistro;
    BD db;
    fragment_docente_crear f = new fragment_docente_crear();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        usuario=(EditText) findViewById(R.id.registrarUser);
        contraseña=(EditText) findViewById(R.id.registrarPassword);
        btnRegistro=(Button) findViewById(R.id.btnRegistro);
        //Base de datos
        db = new BD(this);

        //Asignacion de eventos a los botones
        btnRegistro.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegistro:
                db.abrir();
                //Llenado de usuario
                Usuario us=new Usuario();
                us.setUsuario(usuario.getText().toString());
                us.setContraseña(contraseña.getText().toString());
                if(!us.isNull()){
                    //Mensaje
                    Toast.makeText(this, "ERROR: Campos vacios", Toast.LENGTH_SHORT).show();
                }else if(db.insertUsuario(us)){
                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent i2=new Intent(RegistrarUsuarioActivity.this,MainActivity.class);
                    startActivity(i2);
                    finish();
                    //NavController navController = Navigation.findNavController(this, R.id.docenteCrear);
                    //Navigation.findNavController(v).navigate(R.id.docenteCrear);
                    //getSupportFragmentManager().beginTransaction().replace(f).addToBackStack(null).commit();
                    //View vista = getActivity().findViewById(R.id.lista);

                }
                else{
                    Toast.makeText(this, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
                }
                db.cerrar();
                break;

        }
    }
}