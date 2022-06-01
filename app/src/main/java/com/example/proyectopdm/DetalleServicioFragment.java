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
import com.example.proyectopdm.adaptadorl.AdaptadorDetalleServicio;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.DetalleResumenServicio;
import com.example.proyectopdm.entidades.ResumenServicioSocial;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleServicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleServicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<DetalleResumenServicio> listadoDetalleServicio;
    BD.DataBaseHelper dataBaseHelper;
    ResumenServicioSocial resumenServicioSocial;
    SQLiteDatabase sqLiteDatabase;
    AdaptadorDetalleServicio adaptadorDetalleServicio;
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetalleServicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleServicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleServicioFragment newInstance(String param1, String param2) {
        DetalleServicioFragment fragment = new DetalleServicioFragment();
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
        View vista = inflater.inflate(R.layout.fragment_detalle_servicio, container, false);

        dataBaseHelper = new BD.DataBaseHelper(getContext());
        listadoDetalleServicio = new ArrayList<>();
        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerViewListDetalleServicio);


        return vista;
    }
    private ArrayList<DetalleResumenServicio> consultarListadoDetalleServicio() {
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        String[] id= {String.valueOf(resumenServicioSocial.getIdResumen())};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM DETALLERESUMENSERVICIO WHERE IDRESUMEN=?",id);
        ArrayList<DetalleResumenServicio> listadoDetalleServicio = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listadoDetalleServicio.add(new DetalleResumenServicio(cursor.getInt(0),cursor.getInt(1),cursor.getInt(2),cursor.getDouble(3),cursor.getInt(4),cursor.getInt(5), cursor.getString(6),cursor.getString(7)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return listadoDetalleServicio;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle =this.getArguments();

        if (bundle != null) {
            resumenServicioSocial = bundle.getParcelable("RESUMENSERVICIOTOTAL");
        }else{
            Toast.makeText(getContext(),"Fallo al recuperar objeto",Toast.LENGTH_SHORT).show();
        }

        listadoDetalleServicio = consultarListadoDetalleServicio();

        adaptadorDetalleServicio = new AdaptadorDetalleServicio(listadoDetalleServicio,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorDetalleServicio);

        FloatingActionButton btn1 = view.findViewById(R.id.btnAgregarDetalleServicio);
        adaptadorDetalleServicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
             //   Toast.makeText(getContext(),"" + listadoDetalleServicio.get(recyclerView.getChildAdapterPosition(v)).get(),Toast.LENGTH_SHORT).show();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.crearResumenServicioFragment,bundle);
            }
        });
    }
}