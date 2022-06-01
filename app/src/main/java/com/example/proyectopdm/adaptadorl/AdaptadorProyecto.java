package com.example.proyectopdm.adaptadorl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectopdm.Carrera;
import com.example.proyectopdm.Proyecto;
import com.example.proyectopdm.R;
import com.example.proyectopdm.bd.BD;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AdaptadorProyecto extends  RecyclerView.Adapter<AdaptadorProyecto.ProyectoViewHolder> implements  View.OnClickListener{
    BD helper;
    private ArrayList<Proyecto> listadoProyecto;
    private Context context;
    private View.OnClickListener listener;


    public AdaptadorProyecto(ArrayList<Proyecto> listadoProyecto, Context context){
        this.listadoProyecto = listadoProyecto;
        this.context = context;
    }

    @Override
    public AdaptadorProyecto.ProyectoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_proyecto,parent,false);
        view.setOnClickListener(this);
        return new AdaptadorProyecto.ProyectoViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }


    @Override
    public void onBindViewHolder(AdaptadorProyecto.ProyectoViewHolder holder, @SuppressLint("RecyclerView") int position) {
        helper = new BD(context);
        helper.abrir();
        TextView nom, moda, tutor,numE,numH,lugar,des,cat,estado;
        holder.nom.setText(String.valueOf(listadoProyecto.get(position).getNomPro()));
        holder.moda.setText(String.valueOf(helper.consultarModalidad(listadoProyecto.get(position).getIdMod())));
        holder.tutor.setText(String.valueOf(helper.consultarDocente(listadoProyecto.get(position).getDuiTu())));
        holder.numE.setText(String.valueOf(listadoProyecto.get(position).getNumEst()));
        holder.numH.setText(String.valueOf(listadoProyecto.get(position).getNumHoras()));
        holder.lugar.setText(String.valueOf(listadoProyecto.get(position).getLugar()));
        holder.des.setText(String.valueOf(listadoProyecto.get(position).getDesPro()));
        holder.cat.setText(String.valueOf(helper.consultarCategoria(listadoProyecto.get(position).getIdCat())));
        holder.estado.setText(String.valueOf(listadoProyecto.get(position).getEstadoPro()));

        helper.cerrar();
        final Proyecto proyecto=listadoProyecto.get(position);

        Bundle proyectoEdit = new Bundle();
        proyectoEdit.putParcelable("PROYECTO", proyecto);
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
                                Navigation.findNavController(view).navigate(R.id.editarProyectoFragment,proyectoEdit);

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
                                        helper.eliminarProyecto(proyecto.getIdPro());
                                        listadoProyecto.remove(position);
                                        helper.cerrar();

                                        Navigation.findNavController(view).navigate(R.id.action_proyectosMainFragment_self);
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

        listadoProyecto.remove(p);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p, listadoProyecto.size());
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
        return listadoProyecto.size();
    }

    public class ProyectoViewHolder extends RecyclerView.ViewHolder {
        TextView nom, moda, tutor,numE,numH,lugar,des,cat,estado;
        ImageView menupopUp;

        public ProyectoViewHolder(View itemView) {
            super(itemView);

            nom = itemView.findViewById(R.id.nomPro);
            moda = itemView.findViewById(R.id.modalidadPro);
            lugar = itemView.findViewById(R.id.lugarPro);
            estado = itemView.findViewById(R.id.estadoPro);
            cat = itemView.findViewById(R.id.categoriaPro);
            numE = itemView.findViewById(R.id.estuPro);
            des = itemView.findViewById(R.id.descripcionPro);
            numH = itemView.findViewById(R.id.horasPro);
            tutor = itemView.findViewById(R.id.tutorPro);

            menupopUp = itemView.findViewById(R.id.menuMore);

        }
    }
}