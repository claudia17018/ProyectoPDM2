package com.example.proyectopdm;

import android.content.Context;
import android.content.DialogInterface;
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

import com.example.proyectopdm.adaptadores.AdaptadorBitacora;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Bitacora;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MiServicioSocialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiServicioSocialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    ArrayList<Bitacora> listadoBitacora;
    BD.DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    AdaptadorBitacora adaptadorBitacora;
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MiServicioSocialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MiServicioSocialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MiServicioSocialFragment newInstance(String param1, String param2) {
        MiServicioSocialFragment fragment = new MiServicioSocialFragment();
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
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_mi_servicio_social, container, false);

        dataBaseHelper = new BD.DataBaseHelper(getContext());
        listadoBitacora = new ArrayList<>();
        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerViewListBitacora);
        listadoBitacora = consultarListadoBitacora();

        adaptadorBitacora = new AdaptadorBitacora(listadoBitacora,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorBitacora);
        
        return vista;
    }

    private ArrayList<Bitacora> consultarListadoBitacora() {
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BITACORA",null);
        ArrayList<Bitacora> listadoBitacora = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listadoBitacora.add(new Bitacora(cursor.getInt(0), cursor.getInt(2),cursor.getString(3),cursor.getInt(4)));
            } while (cursor.moveToNext());

            }
        cursor.close();
        return listadoBitacora;
        }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton btn1 = view.findViewById(R.id.fabAgegarBitacora);

        adaptadorBitacora.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"seleccion" + listadoBitacora.get(recyclerView.getChildAdapterPosition(view)).getIdBitacora(),Toast.LENGTH_SHORT).show();
                //Navigation.findNavController(view).navigate(R.id.actividadCrearFragment);
                Bundle datosBitacora = new Bundle();
                datosBitacora.putParcelable("BITACORA",listadoBitacora.get(recyclerView.getChildAdapterPosition(view)));
                adaptadorBitacora.idBitacora =(listadoBitacora.get(recyclerView.getChildAdapterPosition(view)).getIdBitacora());
                Navigation.findNavController(view).navigate(R.id.listaActividadesFragment,datosBitacora);
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.agregarBitacoraFragment);
            }
        });
    }

}