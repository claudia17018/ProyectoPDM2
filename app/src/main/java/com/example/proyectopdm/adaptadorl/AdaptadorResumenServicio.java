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
import com.example.proyectopdm.ResumenServicio;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Bitacora;
import com.example.proyectopdm.entidades.ResumenServicioSocial;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AdaptadorResumenServicio extends  RecyclerView.Adapter<AdaptadorResumenServicio.ResumenServicioViewHolder> implements  View.OnClickListener{
        BD helper;
        private ArrayList<ResumenServicioSocial> listadoResumenServicio;
        private  Context context;
        private View.OnClickListener listener;
        public int idResumenServicio;


    public AdaptadorResumenServicio(ArrayList<ResumenServicioSocial> listadoResumenServicio, Context context){
        this.listadoResumenServicio = listadoResumenServicio;
        this.context = context;
    }

    @Override
    public AdaptadorResumenServicio.ResumenServicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_resumenservicio,parent,false);
        view.setOnClickListener(this);
        return new AdaptadorResumenServicio.ResumenServicioViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }


    @Override
    public void onBindViewHolder(AdaptadorResumenServicio.ResumenServicioViewHolder holder, @SuppressLint("RecyclerView") int position) {
        helper = new BD(context);
        helper.abrir();
        // holder.id.setText(String.valueOf(listadoCarrera.get(position).getIdCarrera()));
        holder.carnetR.setText(String.valueOf(listadoResumenServicio.get(position).getCarnet()));
        holder.tutorR.setText(String.valueOf(listadoResumenServicio.get(position).getFechaAperturaExpediente()));
        holder.fechaA.setText(String.valueOf(listadoResumenServicio.get(position).getFechaAperturaExpediente()));
        holder.fechaC.setText(String.valueOf(listadoResumenServicio.get(position).getFechaEmisionCertificado()));
        final ResumenServicioSocial resumenServicio=listadoResumenServicio.get(position);

        Bundle resumenServicioEdit = new Bundle();
        resumenServicioEdit.putParcelable("RESUMENSERVICIOTOTAL", resumenServicio);
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
                                //Navigation.findNavController(view).navigate(R.id.editarCarreraFragment,resumenServicioEdit);

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
                                        helper.eliminarResumenServicio(resumenServicio.getIdResumen());
                                        listadoResumenServicio.remove(position);
                                        helper.cerrar();

                                        Navigation.findNavController(view).navigate(R.id.action_resumenServicioSocialFragment_self);
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

        listadoResumenServicio.remove(p);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p, listadoResumenServicio.size());
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
        return listadoResumenServicio.size();
    }

    public class ResumenServicioViewHolder extends RecyclerView.ViewHolder {
        TextView carnetR, tutorR, fechaA,fechaC;
        ImageView menupopUp;

        public ResumenServicioViewHolder(View itemView) {
            super(itemView);

            carnetR = itemView.findViewById(R.id.carnetResServicio);
            tutorR = itemView.findViewById(R.id.docenteResServicio);
            fechaA = itemView.findViewById(R.id.fechaApertura);
            fechaC = itemView.findViewById(R.id.fechaCierre);
            menupopUp = itemView.findViewById(R.id.menuMore);

        }
    }
}
