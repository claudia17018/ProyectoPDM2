package com.example.proyectopdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.Navigation;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class LogoutActivity extends AppCompatActivity implements View.OnClickListener{
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;
    GoogleSignInAccount acct;

    Button signOutBtn,btnCancelar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logout);
        signOutBtn = findViewById(R.id.signout);
        btnCancelar= findViewById(R.id.btncancelar);

        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        gsc = GoogleSignIn.getClient(this,gso);

        signOutBtn.setOnClickListener(this);
        btnCancelar.setOnClickListener(this);

        acct = GoogleSignIn.getLastSignedInAccount(this);
    }

    void signOut(){
        gsc.signOut().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(Task<Void> task) {
                finish();
                startActivity(new Intent(LogoutActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.signout:
                if(acct!=null){
                    signOut();
                }else{
                    finish();
                    startActivity(new Intent(LogoutActivity.this,MainActivity.class));
                    //System.exit(0)
                }
                break;
            case R.id.btncancelar:
                Navigation.findNavController(view).navigate(R.id.nav_inicio);
                break;
        }
    }

}