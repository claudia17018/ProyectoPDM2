package com.example.proyectopdm;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectopdm.adaptadorl.AdaptadorEstudiante;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.docente.Estudiante;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EstudiantesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EstudiantesFragment extends Fragment {

    SQLiteDatabase sqLiteDatabase;
    AdaptadorEstudiante adaptadorEstudiante;
    RecyclerView recyclerView;
    ArrayList<Estudiante> listaEstudiante;
    BD.DataBaseHelper dataBaseHelper;
    BD db;
    DT dt;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EstudiantesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EstudiantesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EstudiantesFragment newInstance(String param1, String param2) {
        EstudiantesFragment fragment = new EstudiantesFragment();
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

        View vista = inflater.inflate(R.layout.fragment_estudiantes, container, false);

        dataBaseHelper = new BD.DataBaseHelper(getContext());
        listaEstudiante = new ArrayList<>();
        recyclerView = vista.findViewById(R.id.reciclerEstudianteID);
        listaEstudiante = consultarListaEstudiantes();

        adaptadorEstudiante = new AdaptadorEstudiante(listaEstudiante, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorEstudiante);

        // Inflate the layout for this fragment
        return vista;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        adaptadorEstudiante.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"seleccion" + listaEstudiante.get(recyclerView.getChildAdapterPosition(v)).getCarnet(),
                        Toast.LENGTH_SHORT).show();
            }
        });

       // Button btn1 = view.findViewById(R.id.btnprueba);
        FloatingActionButton btn2 = view.findViewById(R.id.btnCambioEstudiantes2);
       /* btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.fragment_estudiante_modificar2);
            }
        });*/
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db=new BD(getContext());
                dt=new DT();
                db.abrir();
                dt=db.activo();
                //tex.setText(Integer.toString(dt.getIdU()));
                if(db.consultarNivelAcceso(dt.getIdU())==1){
                    Navigation.findNavController(v).navigate(R.id.fragment_estudiante_crear);
                }else{
                    Toast.makeText(getContext(), "No dispone de los permisos para acceder a esta función", Toast.LENGTH_SHORT).show();
                }
                db.cerrar();

            }
        });

    }
    public ArrayList<Estudiante> consultarListaEstudiantes(){
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM ESTUDIANTE", null);
        ArrayList<Estudiante> listadoEstudiante = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                listadoEstudiante.add(new Estudiante(cursor.getString(0), cursor.getInt(1),
                        cursor.getString(2),cursor.getString(3),cursor.getString(4),cursor.getString(5), cursor.getString(6),
                        cursor.getString(7),cursor.getString(8)));
            }while(cursor.moveToNext());
        }
        cursor.close();
        return listadoEstudiante;
    }
}