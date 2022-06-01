package com.example.proyectopdm;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectopdm.adaptadorl.AdaptadorDocente;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.docente.Docente;

import com.example.proyectopdm.bd.BD;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DocentesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DocentesFragment extends Fragment {

    BD db;
    DT dt;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    SQLiteDatabase sqLiteDatabase;
    AdaptadorDocente adaptadorDocente;
    RecyclerView recyclerView;
    ArrayList<Docente> listaDocentes;
    BD.DataBaseHelper dataBaseHelper;
    //////
    //RecyclerView.LayoutManager layoutManager;


    public DocentesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DocentesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DocentesFragment newInstance(String param1, String param2) {
        DocentesFragment fragment = new DocentesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View vista = inflater.inflate(R.layout.fragment_docentes, container, false);

        dataBaseHelper = new BD.DataBaseHelper(getContext());
        listaDocentes = new ArrayList<>();
        recyclerView = vista.findViewById(R.id.reciclerDocenteID);
        listaDocentes = consultarListaDocentes();

        adaptadorDocente = new AdaptadorDocente(listaDocentes, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorDocente);

        // Inflate the layout for this fragment
        return vista;
    }

    private ArrayList<Docente> consultarListaDocentes(){
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM DOCENTE", null);
        ArrayList<Docente> listadoDocente = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                listadoDocente.add(new Docente(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4), cursor.getString(5),cursor.getString(6),cursor.getString(7)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listadoDocente;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adaptadorDocente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Toast.makeText(getContext(),"seleccion" + listaDocentes.get(recyclerView.getChildAdapterPosition(v)).getIdDocente(),
                        Toast.LENGTH_SHORT).show();
               // Navigation.findNavController(v).navigate(R.id.fragment_editar_docente);
            }
        });

        FloatingActionButton btn1 = view.findViewById(R.id.cambiobtn);
        //Button btn2 = view.findViewById(R.id.button2);

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                db=new BD(getContext());
                dt=new DT();
                db.abrir();
                dt=db.activo();
                //tex.setText(Integer.toString(dt.getIdU()));
                if(db.consultarNivelAcceso(dt.getIdU())==1){
                    Navigation.findNavController(view).navigate(R.id.docenteCrear);
                }else{
                    Toast.makeText(getContext(), "No dispone de los permisos para acceder a esta función", Toast.LENGTH_SHORT).show();
                }
                db.cerrar();



                //Navigation.findNavController(view).navigate(R.id.docenteCrear);
                //Intent i = new Intent(getContext(), RegistrarUsuarioActivity.class);
                //startActivity(i);
            }
        });
       /* btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.fragment_editar_docente);
            }
        });*/
    }
}