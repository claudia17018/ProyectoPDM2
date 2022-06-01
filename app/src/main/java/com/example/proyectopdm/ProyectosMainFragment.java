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
import android.widget.Toast;

import com.example.proyectopdm.adaptadorl.AdaptadorCarrera;
import com.example.proyectopdm.adaptadorl.AdaptadorProyecto;
import com.example.proyectopdm.bd.BD;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProyectosMainFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProyectosMainFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    ArrayList<Proyecto> listadoProyecto;
    BD.DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    AdaptadorProyecto adaptadorProyecto;
    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProyectosMainFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProyectosMainFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProyectosMainFragment newInstance(String param1, String param2) {
        ProyectosMainFragment fragment = new ProyectosMainFragment();
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
        View vista = inflater.inflate(R.layout.fragment_proyectos_main, container, false);

        dataBaseHelper = new BD.DataBaseHelper(getContext());
        listadoProyecto = new ArrayList<>();
        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerViewListProyecto);
        listadoProyecto = consultarListadoProyecto();
        adaptadorProyecto = new AdaptadorProyecto(listadoProyecto,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorProyecto);

        return vista;
    }

    private ArrayList<Proyecto> consultarListadoProyecto() {
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM PROYECTO",null);
        ArrayList<Proyecto> listadoProyecto = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listadoProyecto.add(new Proyecto(cursor.getInt(0),cursor.getInt(1), cursor.getString(2),cursor.getInt(3),cursor.getString(4),cursor.getString(5), cursor.getString(6),cursor.getString(7),cursor.getInt(8),cursor.getInt(9), cursor.getString(10)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return listadoProyecto;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton btn1 = view.findViewById(R.id.btnAgregarProyecto);
        adaptadorProyecto.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"" + listadoProyecto.get(recyclerView.getChildAdapterPosition(v)).getIdPro(),Toast.LENGTH_SHORT).show();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.crearProyectoFragment);
            }
        });


    }
}