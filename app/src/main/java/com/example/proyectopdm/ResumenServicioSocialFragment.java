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
import com.example.proyectopdm.adaptadorl.AdaptadorResumenServicio;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.ResumenServicioSocial;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResumenServicioSocialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResumenServicioSocialFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<ResumenServicioSocial> listadoResumenServicio;
    BD.DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    AdaptadorResumenServicio adaptadorResumenServicio;
    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResumenServicioSocialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResumenServicioSocialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResumenServicioSocialFragment newInstance(String param1, String param2) {
        ResumenServicioSocialFragment fragment = new ResumenServicioSocialFragment();
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
        View vista = inflater.inflate(R.layout.fragment_resumen_servicio_social, container, false);

        dataBaseHelper = new BD.DataBaseHelper(getContext());
        listadoResumenServicio = new ArrayList<>();
        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerViewListResumenSer);
        listadoResumenServicio = consultarListadoResumenServicio();

        adaptadorResumenServicio = new AdaptadorResumenServicio(listadoResumenServicio,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorResumenServicio);
        return vista;
    }

    private ArrayList<ResumenServicioSocial> consultarListadoResumenServicio() {
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM RESUMENSERVICIOTOTAL",null);
        ArrayList<ResumenServicioSocial> listadoResumenServicio = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listadoResumenServicio.add(new ResumenServicioSocial(cursor.getInt(0),cursor.getString(1), cursor.getInt(2),cursor.getString(3),cursor.getString(4)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return listadoResumenServicio;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton btn1 = view.findViewById(R.id.btnAgregarResumenServicio);
        Button button= view.findViewById(R.id.button);
        adaptadorResumenServicio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {

                //Toast.makeText(getContext(),"seleccion" + listadoResumenServicio.get(recyclerView.getChildAdapterPosition(view)).getIdBitacora(),Toast.LENGTH_SHORT).show();
                //Navigation.findNavController(view).navigate(R.id.actividadCrearFragment);
                Bundle datosResumenServicio = new Bundle();
                datosResumenServicio.putParcelable("RESUMENSERVICIOTOTAL",listadoResumenServicio.get(recyclerView.getChildAdapterPosition(v)));
                adaptadorResumenServicio.idResumenServicio =(listadoResumenServicio.get(recyclerView.getChildAdapterPosition(v)).getIdResumen());
                Navigation.findNavController(v).navigate(R.id.detalleServicioFragment,datosResumenServicio);

                //Toast.makeText(getContext(),"" + listadoResumenServicio.get(recyclerView.getChildAdapterPosition(v)).getIdResumen(),Toast.LENGTH_SHORT).show();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.resumenCrearFragment);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.javaEmailActivity);
            }
        });
    }
}