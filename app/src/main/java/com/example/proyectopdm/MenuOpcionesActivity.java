package com.example.proyectopdm;

import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.TextView;

import com.example.proyectopdm.bd.BD;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.proyectopdm.databinding.ActivityMenuOpcionesBinding;

public class MenuOpcionesActivity extends AppCompatActivity {
    BD db;
    DT dt;
    int id=0;
    Usuario u;
    TextView usuario;
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMenuOpcionesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        db =new BD(this);
        binding = ActivityMenuOpcionesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMenuOpciones.toolbar);
        binding.appBarMenuOpciones.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_inicio, R.id.nav_miperfil, R.id.nav_proyectos,R.id.estudiantesFragment,
                R.id.miServicioSocialFragment, R.id.recordAcademicoFragment,R.id.cerrarSesionFragment,
                R.id.docentesFragment,R.id.carrerasFragment,R.id.modalidadesFragment,
                R.id.proyectosAsignadosFragment,R.id.resumenServicioSocialFragment,R.id.crearCarreraFragment)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_opciones);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_opciones, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        recuperarNombre();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_opciones);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    public void recuperarNombre(){
        usuario=(TextView)findViewById(R.id.usuarioIngresado);
        String nom;
        dt=new DT();
        db.abrir();
        dt=db.activo();

        usuario.setText(db.obtenerNombreDocente(dt.getIdU()));
        db.cerrar();


    }
}