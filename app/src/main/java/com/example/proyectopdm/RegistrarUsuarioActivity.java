package com.example.proyectopdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.docente.Estudiante;
import com.example.proyectopdm.entidades.EstudianteProyecto;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class RegistrarUsuarioActivity extends AppCompatActivity implements View.OnClickListener {
    TextInputEditText usuario,contraseña;
    TextInputEditText txtCarnet, txtNombre, txtApellido, txtTelefono, txtEmail, txtDui, txtDomicilio, txtNit;
    Button btnRegistro;
    BD db;
    FirebaseFirestore mFirestore;
    FirebaseAuth mAuth;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registrar_usuario);
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

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
                us.setUsuario(usuario.getText().toString().trim());
                us.setContraseña(contraseña.getText().toString().trim());

                //Para estudiante
                Estudiante estudiante = new Estudiante();
                estudiante.setCarnet(txtCarnet.getText().toString());
                estudiante.setNombreEstudiante(txtNombre.getText().toString().trim());
                estudiante.setApellidoEstudiante(txtApellido.getText().toString());
                estudiante.setTelefono( txtTelefono.getText().toString());
                estudiante.setEmailEstudinate(txtEmail.getText().toString().trim());
                estudiante.setDuiEstudiante(txtDui.getText().toString());
                estudiante.setDomicilioEstudiante(txtDomicilio.getText().toString().trim());
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
                    registerUser(txtNombre, txtEmail,contraseña);
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

    private void registerUser(TextInputEditText txtNombre, TextInputEditText txtEmail, TextInputEditText contraseña) {
        mAuth.createUserWithEmailAndPassword(txtEmail.getText().toString(), contraseña.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(RegistrarUsuarioActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }
    private void updateUI(FirebaseUser user) {

    }
}