package com.example.proyectopdm.adaptadores;

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
import com.example.proyectopdm.entidades.Actividad;
import com.example.proyectopdm.entidades.Bitacora;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


import org.w3c.dom.Text;

import java.util.ArrayList;

public class AdaptadorActividad extends RecyclerView.Adapter<AdaptadorActividad.ActividadViewHolder> implements  View.OnClickListener{
    private ArrayList<Actividad> listadoActividad;
    private Context context;
    private View.OnClickListener listener;

    public AdaptadorActividad(ArrayList<Actividad> listadoActividad, Context context) {
        this.listadoActividad = listadoActividad;
        this.context = context;
    }

    @NonNull
    @Override
    public ActividadViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_actividades,parent,false);
        view.setOnClickListener(this);
        return new AdaptadorActividad.ActividadViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ActividadViewHolder holder, @SuppressLint("RecyclerView") int position) {
            holder.nomAct.setText(listadoActividad.get(position).getNombreActividad());
            holder.fecha.setText(listadoActividad.get(position).getFechaActividad());
            holder.id.setText(String.valueOf(listadoActividad.get(position).getIdActividad()));
            final Actividad bitacora =listadoActividad.get(position);

    }

    public void deleteItem(int p){

        listadoActividad.remove(p);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p,listadoActividad.size());

    }
    @Override
    public int getItemCount() {
        return listadoActividad.size();
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null) {
            listener.onClick(view);
        }
    }

    public class ActividadViewHolder extends RecyclerView.ViewHolder{
        TextView nomAct, fecha,id;
        public ActividadViewHolder(@NonNull View itemView) {
            super(itemView);
            nomAct = itemView.findViewById(R.id.actNombre);
            fecha = itemView.findViewById(R.id.fechaActivity);
            id=itemView.findViewById(R.id.actID);
        }
    }
}
