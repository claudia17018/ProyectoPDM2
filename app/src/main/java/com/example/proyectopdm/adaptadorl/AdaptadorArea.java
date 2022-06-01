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

import com.example.proyectopdm.Area;
import com.example.proyectopdm.Carrera;
import com.example.proyectopdm.R;
import com.example.proyectopdm.bd.BD;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AdaptadorArea extends  RecyclerView.Adapter<AdaptadorArea.AreaViewHolder> implements  View.OnClickListener{
    BD helper;
    private ArrayList<Area> listadoArea;
    private Context context;
    private View.OnClickListener listener;

    public AdaptadorArea(ArrayList<Area> listadoArea, Context context){
        this.listadoArea = listadoArea;
        this.context = context;
    }

    @Override
    public AdaptadorArea.AreaViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_area,parent,false);
        view.setOnClickListener(this);
        return new AdaptadorArea.AreaViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }


    @Override
    public void onBindViewHolder(AdaptadorArea.AreaViewHolder holder, @SuppressLint("RecyclerView") int position) {

        // holder.id.setText(String.valueOf(listadoCarrera.get(position).getIdCarrera()));
        holder.nomAre.setText(String.valueOf(listadoArea.get(position).getNomArea()));
        holder.desAre.setText(String.valueOf(listadoArea.get(position).getDesArea()));

       //holder.area.setText(String.valueOf(listadoArea.get(position).getIdArea()));
        final Area area=listadoArea.get(position);

        Bundle areaEdit = new Bundle();
        areaEdit.putParcelable("AREA", area);
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
                                Navigation.findNavController(view).navigate(R.id.editarAreaFragment,areaEdit);

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
                                        String textoDelete=helper.eliminarAreaCarrera(area.getIdArea());
                                        listadoArea.remove(position);
                                        helper.cerrar();
                                        Toast.makeText(context,textoDelete,Toast.LENGTH_SHORT).show();

                                        Navigation.findNavController(view).navigate(R.id.action_areaFragment_self);
                                        /*if(recDelete==1){
                                            deleteItem(position);
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

        listadoArea.remove(p);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p, listadoArea.size());
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
        return listadoArea.size();
    }

    public class AreaViewHolder extends RecyclerView.ViewHolder {
        TextView nomAre, desAre;
        ImageView menupopUp;

        public AreaViewHolder(View itemView) {
            super(itemView);

            nomAre = itemView.findViewById(R.id.nombreAreaT);
            desAre = itemView.findViewById(R.id.descripAreaT);

            menupopUp = itemView.findViewById(R.id.menuMore);

        }
    }

}
