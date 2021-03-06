package com.example.proyectopdm;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Declaracion de variables
    EditText user,pass;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 1000;
    GoogleSignInOptions gso;
    TextView register;
    String u;
    SpannableString spannableString;
    String texto="No tienes una cuenta Registrate";
    GoogleSignInClient gsc;
    Button btnEntrar,btnRegistar;
    SignInButton btnGoogle;
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

        register= findViewById(R.id.spanSignin);

        spannableString = new SpannableString(texto);
        ClickableSpan clickableSpan=new ClickableSpan() {
            @Override
            public void onClick(@NonNull View view) {
                Intent i=new Intent(MainActivity.this,RegistrarUsuarioActivity.class);
                startActivity(i);
            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(Color.RED);
                ds.setUnderlineText(false);
            }
        };

        spannableString.setSpan(clickableSpan,20,31, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        register.setText(spannableString);
        register.setMovementMethod(LinkMovementMethod.getInstance());

        //Asignacion de eventos a los botones
        btnEntrar.setOnClickListener(this);
        //btnRegistar.setOnClickListener(this);

        db.abrir();
        db.llenarDB();
        db.cerrar();
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_app_client_id))
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this,gso);

        btnGoogle= findViewById(R.id.btnLoginGoogle);
        btnGoogle.setOnClickListener(this);

    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    // [START handleSignInResult]
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            navigateHome();

        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
            Toast.makeText(this,"Error"+ e.getStatusCode(),Toast.LENGTH_SHORT).show();
        }
    }
    // [END handleSignInResult]

    public void navigateHome(){
        finish();
        Toast.makeText(this, "DATOS CORRECTOS", Toast.LENGTH_SHORT).show();
        Intent i3=new Intent(MainActivity.this,LogoutActivity.class);
        // Pasando el id del usuario
        //i3.putExtra("id",us.getId());
        startActivity(i3);
    }
    // [END onActivityResult]
    // [START signIn]
    private void signIn() {
        Intent signInIntent = gsc.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END signIn]

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            //Definicion de comportamiento de los botones
             case R.id.btnIngresar:
                 db.abrir();
                u=user.getText().toString();
                String p=pass.getText().toString();
                if(u.equals("")||p.equals("")){
                    Toast.makeText(this, "ERROR: Campos vacios", Toast.LENGTH_SHORT).show();
                }else if(db.login(u,p)==1){

                    Usuario us =db.getUsuario(u,p);
                    dt=new DT();
                    dt.setIdU(us.getId());
                    db.insertarDT(dt);
                    guardarPrefencias(u);


                    Toast.makeText(this, "BIENVENIDO", Toast.LENGTH_SHORT).show();
                    Intent i3=new Intent(MainActivity.this,MenuOpcionesActivity.class);
                    // Pasando el id del usuario
                    i3.putExtra("id",us.getId());
                    startActivity(i3);
                   /* if(db.consultarNivelAcceso(dt.getIdU())==1||db.consultarNivelAcceso(dt.getIdU())==2){
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
                    }*/

                    user.setText("");
                    pass.setText("");


                db.cerrar();
                }else{
                    Toast.makeText(this, "Usuario y/o contrase??a incorrectos", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btnLoginGoogle:
                signIn();
                break;
        }
    }

    public void guardarPrefencias(String valor) {
        SharedPreferences sharedPreferences = getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("USUARIO",valor);
        editor.clear().apply();
        editor.apply();
        if (valor != null && !valor.isEmpty() && !valor.equals("null")) {
            Toast.makeText(this, "Datos guardados", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Datos eliminados.", Toast.LENGTH_SHORT).show();
        }
    }
}