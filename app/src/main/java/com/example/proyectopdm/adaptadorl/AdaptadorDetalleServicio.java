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
import com.example.proyectopdm.DetalleServicio;
import com.example.proyectopdm.R;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.DetalleResumenServicio;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class
AdaptadorDetalleServicio extends  RecyclerView.Adapter<AdaptadorDetalleServicio.DetalleServicioViewHolder> implements  View.OnClickListener{
    BD helper;
    private ArrayList<DetalleResumenServicio> listadoDetalleServicio;
    private Context context;
    private View.OnClickListener listener;


    public AdaptadorDetalleServicio(ArrayList<DetalleResumenServicio> listadoDetalleServicio, Context context){
        this.listadoDetalleServicio = listadoDetalleServicio;
        this.context = context;
    }

    @Override
    public AdaptadorDetalleServicio.DetalleServicioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_detalle,parent,false);
        view.setOnClickListener(this);
        return new AdaptadorDetalleServicio.DetalleServicioViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }


    @Override
    public void onBindViewHolder(AdaptadorDetalleServicio.DetalleServicioViewHolder holder, @SuppressLint("RecyclerView") int position) {
//TextView estadoDe, horasAs, beneI,beneD,observacionesD;
        // holder.id.setText(String.valueOf(listadoCarrera.get(position).getIdCarrera()));
        holder.estadoDe.setText(String.valueOf(listadoDetalleServicio.get(position).getEstadoDeResumen()));
        holder.horasAs.setText(String.valueOf(listadoDetalleServicio.get(position).getHorasAsignadas()));
        holder.beneD.setText(String.valueOf(listadoDetalleServicio.get(position).getBeneficiariosDirectos()));
        holder.beneI.setText(String.valueOf(listadoDetalleServicio.get(position).getBeneficiariosIndirectos()));
        holder.observacionesD.setText(String.valueOf(listadoDetalleServicio.get(position).getObservaciones()));
        final DetalleResumenServicio detalleServicio=listadoDetalleServicio.get(position);

        Bundle DetalleServicioEdit = new Bundle();
        DetalleServicioEdit.putParcelable("DETALLERESUMENSERVICIO",detalleServicio);
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
                               Navigation.findNavController(view).navigate(R.id.editarDetalleFragment,DetalleServicioEdit);

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
                                        helper.eliminarDetalleServicio(detalleServicio.getIdDetalleResumen());
                                        listadoDetalleServicio.remove(position);
                                        helper.cerrar();

                                        Navigation.findNavController(view).navigate(R.id.action_detalleServicioFragment_self);
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

        listadoDetalleServicio.remove(p);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p, listadoDetalleServicio.size());
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
        return listadoDetalleServicio.size();
    }

    public class DetalleServicioViewHolder extends RecyclerView.ViewHolder {
        TextView estadoDe, horasAs, beneI,beneD,observacionesD;
        ImageView menupopUp;

        public DetalleServicioViewHolder(View itemView) {
            super(itemView);

            estadoDe = itemView.findViewById(R.id.estadoDe);
            horasAs = itemView.findViewById(R.id.horasAsig);
            beneD = itemView.findViewById(R.id.beneficiariosD);
            beneI = itemView.findViewById(R.id.beneficiariosI);
            observacionesD = itemView.findViewById(R.id.observacionesDe);
            menupopUp = itemView.findViewById(R.id.menuMore);

        }
    }
}
