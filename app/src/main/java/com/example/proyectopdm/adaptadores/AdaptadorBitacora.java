package com.example.proyectopdm.adaptadores;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Layout;
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

import com.example.proyectopdm.MiServicioSocialFragment;
import com.example.proyectopdm.R;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Bitacora;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AdaptadorBitacora extends  RecyclerView.Adapter<AdaptadorBitacora.BitacoraViewHolder> implements  View.OnClickListener{
    private ArrayList<Bitacora> listadoBitacora;
    private  Context context;
    public  int idBitacora;
    BD helper;
    BD.DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    private View.OnClickListener listener;

    public AdaptadorBitacora(ArrayList<Bitacora> listadoBitacora, Context context){
        this.listadoBitacora = listadoBitacora;
        this.context = context;
    }

    public AdaptadorBitacora(ArrayList<Bitacora> listadoBitacora, Context context, SQLiteDatabase sqLiteDatabase) {
        this.listadoBitacora = listadoBitacora;
        this.context = context;
        this.sqLiteDatabase = sqLiteDatabase;
    }

    @Override
    public BitacoraViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_bitacoras,parent,false);
        view.setOnClickListener(this);
        helper = new BD(context);
        return new BitacoraViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }


    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(BitacoraViewHolder holder, int position) {
        holder.idBit.setText(String.valueOf(listadoBitacora.get(position).getIdBitacora()));
        holder.cicloBit.setText(String.valueOf(listadoBitacora.get(position).getCiclo()));
        holder.mesBit.setText(listadoBitacora.get(position).getMes());
        holder.anio.setText(String.valueOf(listadoBitacora.get(position).getAnio()));
        final Bitacora bitacora =listadoBitacora.get(position);
        Bundle bitacoraEdit = new Bundle();
        bitacoraEdit.putParcelable("BITACORA", bitacora);
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
                                Navigation.findNavController(view).navigate(R.id.detalleBitacoraFragment,bitacoraEdit);
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
                                     int recDelete = helper.eliminar(bitacora.getIdBitacora());
                                     helper.cerrar();
                                     if(recDelete==1){
                                         listadoBitacora.remove(position);
                                         notifyItemRemoved(position);
                                         notifyDataSetChanged();
                                     }else {
                                         Toast.makeText(context,"No se pudo eliminar el registro tiene actividades relacionadas",Toast.LENGTH_SHORT).show();
                                     }
                                     ;}
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

    @Override
    public int getItemCount() {
        return listadoBitacora.size();
    }

    @Override
    public void onClick(View view) {
        if(listener!=null){
            listener.onClick(view);
        }
    }

    public class BitacoraViewHolder extends RecyclerView.ViewHolder {
        TextView idBit,cicloBit, mesBit,anio;
        ImageView menupopUp;
        public BitacoraViewHolder(View itemView) {
            super(itemView);

            idBit = itemView.findViewById(R.id.bitNum);
            cicloBit = itemView.findViewById(R.id.bitacoraCiclo);
            mesBit=itemView.findViewById(R.id.bitacoraMes);
            anio=itemView.findViewById(R.id.anioBitacora);
            menupopUp= itemView.findViewById(R.id.menuMore);

        }
    }
}
