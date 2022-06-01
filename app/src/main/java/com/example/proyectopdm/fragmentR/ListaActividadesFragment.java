package com.example.proyectopdm.fragmentR;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyectopdm.R;
import com.example.proyectopdm.adaptadores.AdaptadorActividad;
import com.example.proyectopdm.adaptadores.AdaptadorBitacora;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Actividad;
import com.example.proyectopdm.entidades.Bitacora;
import com.example.proyectopdm.interfaces.IComunicaFragment;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ListaActividadesFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListaActividadesFragment extends Fragment {

    ArrayList<Actividad> listadoActividad;
    BD.DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    Activity actividadSeleccionada;
    AdaptadorActividad adaptadorActividad;
    RecyclerView recyclerViewActividad;
    Bitacora bitacora;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ListaActividadesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ListaActividadesFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ListaActividadesFragment newInstance(String param1, String param2) {
        ListaActividadesFragment fragment = new ListaActividadesFragment();
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
        View v = inflater.inflate(R.layout.fragment_lista_actividades, container, false);

        dataBaseHelper = new BD.DataBaseHelper(getContext());
        listadoActividad = new ArrayList<>();
        recyclerViewActividad = (RecyclerView) v.findViewById(R.id.recyclerViewListAct);

        return v;
    }

    private ArrayList<Actividad> consultarListadoActividades() {
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        String[] id= {String.valueOf(bitacora.getIdBitacora())};
        Cursor cursor = sqLiteDatabase.rawQuery("Select a.IDACTIVIDAD, a.NOMBREACTIVIDAD,a.DESCRIPCIONTIPOACTIVIDAD, a.FECHAACTIVIDAD, a.NUMHORASACTIVIDAD from DETALLEBITACORA as d\n" +
                "INNER JOIN ACTIVIDAD as a\n" +
                "ON d.IDACTIVIDAD = a.IDACTIVIDAD\n" +
                "INNER JOIN BITACORA as b\n" +
                "ON d.IDBITACORA = b.IDBITACORA\n" +
                "WHERE d.IDBITACORA = ?", id);
        ArrayList<Actividad> listadoActividad = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listadoActividad.add(new Actividad(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return listadoActividad;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            bitacora = bundle.getParcelable("BITACORA");
        }else{
            Toast.makeText(getContext(),"Fallo al recuperar objeto",Toast.LENGTH_SHORT).show();
        }

        listadoActividad = consultarListadoActividades();

        adaptadorActividad = new AdaptadorActividad(listadoActividad,getContext());
        recyclerViewActividad.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerViewActividad.setAdapter(adaptadorActividad);

        FloatingActionButton fab = view.findViewById(R.id.fabAgregarActividad);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Navigation.findNavController(view).navigate(R.id.actividadCrearFragment,bundle);
            }
        });

        adaptadorActividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"seleccion" + listadoActividad.get(recyclerViewActividad.getChildAdapterPosition(view)).getIdActividad(),Toast.LENGTH_SHORT).show();
                //actividadSeleccionada =  consultarListadoActividades().get(recyclerViewActividad.getChildAdapterPosition(view));
                Bundle datosAenviar = new Bundle();
                datosAenviar.putParcelable("ACTIVIDAD",listadoActividad.get(recyclerViewActividad.getChildAdapterPosition(view)));

                Navigation.findNavController(view).navigate(R.id.detalleActividadFragment,datosAenviar);

            }
        });
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof Activity){
            this.actividadSeleccionada =(Activity) context;

        }
    }
}