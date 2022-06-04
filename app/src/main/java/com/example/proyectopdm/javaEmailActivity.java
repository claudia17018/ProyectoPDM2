package com.example.proyectopdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectopdm.entidades.GMailSender;

public class javaEmailActivity extends AppCompatActivity {

    EditText mRecipient,mSubject,mMessage;
    Button sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_email);


        mRecipient=findViewById(R.id.recepintid);
        mSubject=findViewById(R.id.subject);
        mMessage=findViewById(R.id.messageet);
        sendEmail=findViewById(R.id.sendemail);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipient = mRecipient.getText().toString().trim();
                String subject = mSubject.getText().toString().trim();
                String message = mMessage.getText().toString().trim();
                sendEmail(recipient, subject ,message);
                limpiarInput();
            }
        });
    }


    private void sendEmail(String recipient, String subject, String message) {
        Intent mEmailIntent = new Intent(Intent.ACTION_SEND);
        mEmailIntent.setData(Uri.parse("mail to:"));
        mEmailIntent.setType("text/plain");
        mEmailIntent.putExtra(Intent.EXTRA_EMAIL,new String[]{recipient});
        mEmailIntent.putExtra(Intent.EXTRA_SUBJECT, subject);
        mEmailIntent.putExtra(Intent.EXTRA_TEXT, message);;
        try {
            startActivity(Intent.createChooser(mEmailIntent,"Elegir un cliente de correo electrónico"));
        }
        catch (Exception e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarInput(){
        mRecipient.setText("");
        mSubject.setText("");
        mRecipient.setText("");
    }
}