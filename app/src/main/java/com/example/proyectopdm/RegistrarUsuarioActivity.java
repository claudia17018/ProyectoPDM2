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
import com.example.proyectopdm.docente.Estudiante;
import com.example.proyectopdm.entidades.EstudianteProyecto;
import com.google.android.material.textfield.TextInputEditText;

public class RegistrarUsuarioActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText usuario,contraseña;
    TextInputEditText txtCarnet, txtNombre, txtApellido, txtTelefono, txtEmail, txtDui, txtDomicilio, txtNit;
    Button btnRegistro;
    BD db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);

        //Estudiante
        txtCarnet = (TextInputEditText) findViewById(R.id.textCarnetAlumnoF);
        txtNombre = (TextInputEditText) findViewById(R.id.textNombreEstudianteF);
        txtApellido = (TextInputEditText) findViewById(R.id.textApellidoEstudianteF);
        txtTelefono = (TextInputEditText) findViewById(R.id.textTelefonoEstudianteF);
        txtEmail = (TextInputEditText) findViewById(R.id.textEmailEstudianteF);
        txtDui = (TextInputEditText) findViewById(R.id.textDuiEstudianteF);
        txtDomicilio = (TextInputEditText) findViewById(R.id.textDomicilioEstudianteF);
        txtNit = (TextInputEditText) findViewById(R.id.textNitEstudianteF);
        usuario=(TextInputEditText) findViewById(R.id.textCarnetAlumnoF);
        contraseña=(TextInputEditText) findViewById(R.id.textPassUsuarioF);
        btnRegistro=(Button) findViewById(R.id.btnRegister);
        //Base de datos
        db = new BD(this);
        //Asignacion de eventos a los botones
        btnRegistro.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnRegister:
                db.abrir();
                //Llenado de usuario
                Usuario us=new Usuario();
                us.setUsuario(usuario.getText().toString());
                us.setContraseña(contraseña.getText().toString());

                //Para estudiante
                Estudiante estudiante = new Estudiante();
                estudiante.setCarnet(txtCarnet.getText().toString());
                estudiante.setNombreEstudiante(txtNombre.getText().toString());
                estudiante.setApellidoEstudiante(txtApellido.getText().toString());
                estudiante.setTelefono( txtTelefono.getText().toString());
                estudiante.setEmailEstudinate(txtEmail.getText().toString());
                estudiante.setDuiEstudiante(txtDui.getText().toString());
                estudiante.setDomicilioEstudiante(txtDomicilio.getText().toString());
                estudiante.setNitEstudiante(txtNit.getText().toString());

                EstudianteProyecto estudianteProyecto = new EstudianteProyecto();
                estudianteProyecto.setCarnet(txtCarnet.getText().toString());

                if(!us.isNull()||!estudiante.isNull()){
                    //Mensaje
                    Toast.makeText(this, "ERROR: Campos vacios", Toast.LENGTH_SHORT).show();
                }else if(db.insertUsuario(us)){
                    System.out.println(estudianteProyecto);
                    db.insertarEstudiante(estudiante);
                    String r =  db.insertarEP(estudianteProyecto);
                    //Toast.makeText(this, "Registro exitoso" +r, Toast.LENGTH_SHORT).show();

                    Toast.makeText(this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    Intent i2=new Intent(RegistrarUsuarioActivity.this,MainActivity.class);
                    startActivity(i2);
                    finish();
                }
                else{
                    Toast.makeText(this, "Usuario ya registrado", Toast.LENGTH_SHORT).show();
                }
                db.cerrar();
                break;

        }
    }
}