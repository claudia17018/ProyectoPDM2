package com.example.proyectopdm;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectopdm.entidades.Actividad;
import com.example.proyectopdm.entidades.Bitacora;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class CalendarAdapter extends RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder> implements View.OnClickListener {

    private ArrayList<Actividad> listadoActividad;
    private Context context;
    private View.OnClickListener listener;

    public CalendarAdapter(ArrayList<Actividad> listadoActividad, Context context)
    {
        this.listadoActividad= listadoActividad;
        this.context= context;
    }

    @NonNull
    @Override
    public CalendarViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_actividades_calendar, parent, false);
        view.setOnClickListener(this);
        return new CalendarAdapter.CalendarViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CalendarViewHolder holder, int position)
    {
    holder.actividad.setText(listadoActividad.get(position).getNombreActividad());
    }

    @Override
    public int getItemCount()
    {
        return listadoActividad.size();
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class CalendarViewHolder extends RecyclerView.ViewHolder{
        TextView actividad;
        ImageView ico;
        public CalendarViewHolder(@NonNull View itemView) {
            super(itemView);
            actividad= itemView.findViewById(R.id.actNombreActividad);
            ico=itemView.findViewById(R.id.iconActividad);
        }
    }

}
