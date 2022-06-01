package com.example.proyectopdm.adaptadorl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectopdm.R;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.docente.Docente;
import com.example.proyectopdm.docente.Estudiante;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AdaptadorEstudiante extends RecyclerView.Adapter<AdaptadorEstudiante.EstudianteViewHolder> implements View.OnClickListener{

    private ArrayList<Estudiante> listaEstudiante;
    private Context context;
    BD helper;
    private View.OnClickListener listener;
    RecyclerView rvPrograms;
    //final View.OnClickListener onClickListener = new MyOnClickListener();

    /*
    private View.OnClickListener MyOnClickListener() {
        return null;
    }*/


    @Override
    public EstudianteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_estudiantes, null, false);

        view.setOnClickListener(this);
        return new EstudianteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(EstudianteViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.nomreEstudiante.setText(String.valueOf(listaEstudiante.get(position).getNombreEstudiante()));
        holder.apellidoEstudiante.setText(String.valueOf(listaEstudiante.get(position).getApellidoEstudiante()));
        holder.carnetEstudiante.setText(String.valueOf(listaEstudiante.get(position).getCarnet()));
        holder.correoEstudiante.setText(String.valueOf(listaEstudiante.get(position).getEmailEstudinate()));
        final Estudiante estudiante =listaEstudiante.get(position);
        holder.menupopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                PopupMenu popupMenu = new PopupMenu(context,view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_opciones_bitacora,popupMenu.getMenu());
                popupMenu.show();
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {
                        switch (menuItem.getItemId()){
                            case R.id.editBitacora:
                                Toast.makeText(context, "Editar",Toast.LENGTH_SHORT).show();
                                break;
                            case R.id.deleteBitacora:
                                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(context);

                                builder.setTitle(context.getString(R.string.titleBitacora  ));
                                builder.setMessage(context.getString(R.string.messageBitacora));
                                builder.setNegativeButton(context.getString(R.string.buttonNegacion), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                // Respond to negative button press
                                builder.setPositiveButton(context.getString(R.string.buttonConfirm), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        helper = new BD(context);
                                        helper.abrir();
                                        helper.eliminarEstudiante(estudiante.getCarnet());

                                        listaEstudiante.remove(position);
                                        helper.cerrar();

                                        Navigation.findNavController(view).navigate(R.id.action_estudiantesFragment_self);
                                        /*if(recDelete==1){
                                            deleteItem(position);
                                            Toast.makeText(context,"Registros afectados" + recDelete,Toast.LENGTH_SHORT).show();
                                        }*/
                                    }
                                });
                                // Respond to positive button press
                                builder.show();
                                break;
                        }
                        return false;
                    }
                });
            }
        });

    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }

    @Override
    public int getItemCount() {
        return listaEstudiante.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public  AdaptadorEstudiante(ArrayList<Estudiante> listaEstudiante, Context context){
        this.listaEstudiante = listaEstudiante;
        this.context = context;
    }

    public class EstudianteViewHolder extends RecyclerView.ViewHolder {
        TextView nomreEstudiante, apellidoEstudiante, carnetEstudiante, correoEstudiante;
        ImageView menupopUp;

        public EstudianteViewHolder(@NonNull View itemView) {
            super(itemView);
            nomreEstudiante = itemView.findViewById(R.id.listLabelEstudianteNombre);
            apellidoEstudiante = itemView.findViewById(R.id.listApellidosEstudiante);
            carnetEstudiante = itemView.findViewById(R.id.listCarnetEstudiante);
            correoEstudiante = itemView.findViewById(R.id.listCorreoEstudiante);

            menupopUp = itemView.findViewById(R.id.menuMore);

        }
    }
/*
    private class MyOnClickListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            int itemPosition = rvPrograms.getChildLayoutPosition(v);
            String item = listaEstudiante.get(itemPosition).getNombreEstudiante();

            Toast.makeText(context, item, Toast.LENGTH_SHORT).show();


        }
    }*/
}
