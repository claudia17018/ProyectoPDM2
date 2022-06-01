package com.example.proyectopdm.adaptadorl;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.text.style.UpdateLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectopdm.Carrera;
import com.example.proyectopdm.MiServicioSocialFragment;
import com.example.proyectopdm.R;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Bitacora;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;


public class AdaptadorCarrera extends  RecyclerView.Adapter<AdaptadorCarrera.CarreraViewHolder> implements  View.OnClickListener{
    BD helper;
    private ArrayList<Carrera> listadoCarrera;
    private  Context context;
    private View.OnClickListener listener;


    public AdaptadorCarrera(ArrayList<Carrera> listadoCarrera, Context context){
        this.listadoCarrera = listadoCarrera;
        this.context = context;
    }

    @Override
    public CarreraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_carrera,parent,false);
        view.setOnClickListener(this);
        return new CarreraViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }


    @Override
    public void onBindViewHolder(CarreraViewHolder holder, @SuppressLint("RecyclerView") int position) {
        helper = new BD(context);
        helper.abrir();
        // holder.id.setText(String.valueOf(listadoCarrera.get(position).getIdCarrera()));
        holder.nom.setText(String.valueOf(listadoCarrera.get(position).getNomCarrera()));
        holder.escuela.setText(String.valueOf(listadoCarrera.get(position).getNomEscuela()));

        holder.area.setText(String.valueOf(helper.consultarNomArea(listadoCarrera.get(position).getIdArea())));
        helper.cerrar();
        final Carrera carrera=listadoCarrera.get(position);

        Bundle carreraEdit = new Bundle();
        carreraEdit.putParcelable("CARRERA", carrera);
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
                                Navigation.findNavController(view).navigate(R.id.editarCarreraFragment,carreraEdit);

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
                                        helper.eliminarCarrera(carrera.getIdCarrera());
                                        listadoCarrera.remove(position);
                                        helper.cerrar();

                                        Navigation.findNavController(view).navigate(R.id.action_carrerasFragment_self);
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
                        return true;
                    }
                });
            }
        });
    }

    public void deleteItem(int p) {

        listadoCarrera.remove(p);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p, listadoCarrera.size());
        // Respond to positive button press

    }


    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    @Override
    public int getItemCount() {
        return listadoCarrera.size();
    }

    public class CarreraViewHolder extends RecyclerView.ViewHolder {
        TextView nom, escuela, area;
        ImageView menupopUp;

        public CarreraViewHolder(View itemView) {
            super(itemView);

            nom = itemView.findViewById(R.id.nombreCarrera);
            escuela = itemView.findViewById(R.id.escuelaCarrera);
            area = itemView.findViewById(R.id.areaCarrera);
            menupopUp = itemView.findViewById(R.id.menuMore);

        }
    }
}
