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
    EditText myEmail;
    EditText myPassword;
    Button sendEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_java_email);

        myEmail=(EditText)findViewById(R.id.myEmail);
        myPassword=(EditText)findViewById(R.id.myPassword);
        mRecipient=findViewById(R.id.recepintid);
        mSubject=findViewById(R.id.subject);
        mMessage=findViewById(R.id.messageet);
        sendEmail=findViewById(R.id.sendemail);
        sendEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String sender=myEmail.getText().toString();
                String senderPass=myPassword.getText().toString();
                String recipient = mRecipient.getText().toString().trim();
                String subject = mSubject.getText().toString().trim();
                String message = mMessage.getText().toString().trim();
                sendEmail(sender,senderPass,recipient, subject ,message);
                limpiarInput();
            }
        });
    }


    private void sendEmail(final String Sender,final String Password,final String recipient, final String subject, final String message) {
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    GMailSender sender = new GMailSender(Sender,Password);
                    sender.sendMail(subject, "<b>"+message+"</b>", Sender, recipient);
                    makeAlert();

                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();
    }
    private void makeAlert(){
        this.runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(javaEmailActivity.this, "Mail Sent", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void limpiarInput(){
        mRecipient.setText("");
        mSubject.setText("");
        mRecipient.setText("");
    }
}