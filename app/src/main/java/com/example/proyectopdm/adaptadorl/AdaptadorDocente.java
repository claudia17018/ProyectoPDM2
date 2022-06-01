package com.example.proyectopdm.adaptadorl;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.example.proyectopdm.Carrera;
import com.example.proyectopdm.DT;
import com.example.proyectopdm.R;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.databinding.ListDocentesBinding;
import com.example.proyectopdm.docente.Docente;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

public class AdaptadorDocente extends RecyclerView.Adapter<AdaptadorDocente.DocenteViewHolder> implements View.OnClickListener{
    private ArrayList<Docente> listaDocente;
    private Context context;
    BD helper;
    DT dt;
    private View.OnClickListener listener;
    //RecyclerView rvPrograms;

    //final View.OnClickListener onClickListener = new MyOnClickListener();


    /*
    public AdaptadorDocente(Context context, ArrayList<Docente> listaDocente, RecyclerView rvPrograms){
        this.context = context;
        this.listaDocente = listaDocente;
        this.rvPrograms = rvPrograms;
    }*/

    public  AdaptadorDocente(ArrayList<Docente> listaDocente, Context context){
        this.listaDocente = listaDocente;
        this.context = context;

    }

    @Override
    public DocenteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_docentes, null, false);

        view.setOnClickListener(this);
        return new DocenteViewHolder(view);
        //return viewHolder;
    }
    public void setOnClickListener(View.OnClickListener listener){
        this.listener =listener;
    }

    @Override
    public void onBindViewHolder(DocenteViewHolder holder,  @SuppressLint("RecyclerView") int position) {
        helper = new BD(context);
        helper.abrir();
        holder.docenteNombre.setText(String.valueOf(listaDocente.get(position).getNombreTutor()));
        holder.docenteEmail.setText(String.valueOf(listaDocente.get(position).getEmailTutor()));
        holder.docenteApellido.setText(String.valueOf(listaDocente.get(position).getApellidosTutor()));

        //Docente docente = listaDocente.get(position);
        //holder.docenteNombre.setText(""+docente.getNombreTutor());
        //holder.docenteEmail.setText(""+docente.getEmailTutor());

        final Docente docen=listaDocente.get(position);
        helper.cerrar();

        /* helper=new BD(context);
        dt=new DT();
        helper.abrir();
        dt=helper.activo();
        //tex.setText(Integer.toString(dt.getIdU()));
        if(helper.consultarNivelAcceso(dt.getIdU())==1){

        }else{
            Toast.makeText(context, "No dispone de los permisos para acceder a esta función", Toast.LENGTH_SHORT).show();
        }
        helper.cerrar();*/

        helper=new BD(context);
        dt=new DT();
        helper.abrir();
        dt=helper.activo();
        //tex.setText(Integer.toString(dt.getIdU()));
        if(helper.consultarNivelAcceso(dt.getIdU())==1){

            Bundle docenteEdit = new Bundle();
            docenteEdit.putParcelable("DOCENTE", docen);
        helper.cerrar();
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
                                Navigation.findNavController(view).navigate(R.id.modificarDocenteFragment,docenteEdit);

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
                                        String lol=helper.eliminarDocente(docen.getIdDocente());
                                        listaDocente.remove(position);
                                        helper.cerrar();
                                        Toast.makeText(context, lol,Toast.LENGTH_SHORT).show();
                                        Navigation.findNavController(view).navigate(R.id.action_docentesFragment_self);
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
        }else{
            Toast.makeText(context, "Solo visualizacion", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public int getItemCount() {
        return listaDocente.size();
    }
    /*
        public class DocenteViewHolder extends RecyclerView.ViewHolder {
            TextView dato;
            TextView dato2;
            public DocenteViewHolder(@NonNull View itemView) {
                super(itemView);
                dato = itemView.findViewById(R.id.docenteNombreList);
                dato2 = itemView.findViewById(R.id.docenteCorreoList);
            }
        }
    */


    @Override
    public void onClick(View v) {
        if(listener!=null){
            listener.onClick(v);
        }
    }

    public class DocenteViewHolder extends RecyclerView.ViewHolder{
        TextView docenteNombre;
        TextView docenteEmail;
        TextView docenteApellido;
        ImageView menupopUp;
        public DocenteViewHolder(@NonNull View itemView) {
            super(itemView);
            docenteNombre = itemView.findViewById(R.id.docenteNombreList);
            docenteEmail = itemView.findViewById(R.id.docenteCorreoList);
            docenteApellido = itemView.findViewById(R.id.docenteApellidoList);
            menupopUp = itemView.findViewById(R.id.menuMore);
        }
    }
}