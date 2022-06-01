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

import com.example.proyectopdm.Record;
import com.example.proyectopdm.R;
import com.example.proyectopdm.bd.BD;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AdaptadorRecord extends  RecyclerView.Adapter<AdaptadorRecord.RecordViewHolder> implements  View.OnClickListener{
    BD helper;
    private ArrayList<Record> listadoRecord;
    private Context context;
    private View.OnClickListener listener;


    public AdaptadorRecord(ArrayList<Record> listadoRecord, Context context){
        this.listadoRecord = listadoRecord;
        this.context = context;
    }

    @Override
    public AdaptadorRecord.RecordViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_record,parent,false);
        view.setOnClickListener(this);
        return new AdaptadorRecord.RecordViewHolder(view);
    }

    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }


    @Override
    public void onBindViewHolder(AdaptadorRecord.RecordViewHolder holder, @SuppressLint("RecyclerView") int position) {
        helper = new BD(context);
        helper.abrir();

        holder.carnetR.setText(String.valueOf(listadoRecord.get(position).getCarnet()));
        holder.carreraRe.setText(String.valueOf(helper.consultarNomCarrera(listadoRecord.get(position).getIdCarrera())));
        holder.uvRecor.setText(String.valueOf(listadoRecord.get(position).getuV()));
        holder.promedioRe.setText(String.valueOf(listadoRecord.get(position).getPromedio()));
        holder.cumRe.setText(String.valueOf(listadoRecord.get(position).getCum()));
        holder.progresoRe.setText(String.valueOf(listadoRecord.get(position).getProceso()));
        helper.cerrar();
        final Record record=listadoRecord.get(position);

        Bundle recordEdit = new Bundle();
        recordEdit.putParcelable("RECORDACADEMICO", record);
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
                                Navigation.findNavController(view).navigate(R.id.editarRecordFragment,recordEdit);

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
                                        helper.eliminarRecord(record.getIdRecord());
                                        listadoRecord.remove(position);
                                        helper.cerrar();

                                        Navigation.findNavController(view).navigate(R.id.action_recordAcademicoFragment_self);
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

        listadoRecord.remove(p);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p, listadoRecord.size());
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
        return listadoRecord.size();
    }

    public class RecordViewHolder extends RecyclerView.ViewHolder {
        TextView carnetR, carreraRe, uvRecor,promedioRe,cumRe,progresoRe;
        ImageView menupopUp;

        public RecordViewHolder(View itemView) {
            super(itemView);

            carnetR = itemView.findViewById(R.id.carnetRecord);
            carreraRe = itemView.findViewById(R.id.carreraRecord);
            uvRecor = itemView.findViewById(R.id.uvRecord);
            promedioRe = itemView.findViewById(R.id.promedioRecord);
            cumRe= itemView.findViewById(R.id.cumRecord);
            progresoRe = itemView.findViewById(R.id.progresoRecord);

            menupopUp = itemView.findViewById(R.id.menuMore);

        }
    }
}
