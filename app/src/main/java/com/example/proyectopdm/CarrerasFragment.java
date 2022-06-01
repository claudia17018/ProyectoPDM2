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

import com.example.proyectopdm.adaptadorl.AdaptadorCarrera;
import com.example.proyectopdm.bd.BD;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CarrerasFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CarrerasFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<Carrera> listadoCarrera;
    BD.DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    AdaptadorCarrera adaptadorCarrera;
    RecyclerView recyclerView;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public CarrerasFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CarrerasFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CarrerasFragment newInstance(String param1, String param2) {
        CarrerasFragment fragment = new CarrerasFragment();
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
        View vista = inflater.inflate(R.layout.fragment_carreras, container, false);

        dataBaseHelper = new BD.DataBaseHelper(getContext());
        listadoCarrera = new ArrayList<>();
        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerViewListCarrera);
        listadoCarrera = consultarListadoCarrera();

        adaptadorCarrera = new AdaptadorCarrera(listadoCarrera,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorCarrera);

        return vista;
    }
    private ArrayList<Carrera> consultarListadoCarrera() {
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM CARRERA",null);
        ArrayList<Carrera> listadoCarrera = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listadoCarrera.add(new Carrera(cursor.getInt(0),cursor.getInt(1), cursor.getString(2),cursor.getString(3)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return listadoCarrera;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton btn1 = view.findViewById(R.id.fabAgegarCarrera);
        adaptadorCarrera.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"" + listadoCarrera.get(recyclerView.getChildAdapterPosition(v)).getNomCarrera(),Toast.LENGTH_SHORT).show();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.crearCarreraFragment);
            }
        });


    }
}