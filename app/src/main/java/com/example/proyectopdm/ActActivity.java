package com.example.proyectopdm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.proyectopdm.entidades.Actividad;
import com.example.proyectopdm.fragmentR.DetalleActividadFragment;
import com.example.proyectopdm.fragmentR.ListaActividadesFragment;
import com.example.proyectopdm.interfaces.IComunicaFragment;

public class ActActivity extends AppCompatActivity {

    ListaActividadesFragment listaActividadesFragment;
    DetalleActividadFragment detalleActividadFragment;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act);
        //listaActividadesFragment = new ListaActividadesFragment();
        detalleActividadFragment = new DetalleActividadFragment();

    }
}