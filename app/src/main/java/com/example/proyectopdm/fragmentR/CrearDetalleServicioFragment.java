package com.example.proyectopdm.fragmentR;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectopdm.R;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.DetalleResumenServicio;
import com.example.proyectopdm.entidades.ResumenServicioSocial;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearDetalleServicioFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearDetalleServicioFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    AutoCompleteTextView estado;
    ResumenServicioSocial rss;
    TextInputEditText numHora,monto,benD,benI,observaciones;
    BD helper;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearDetalleServicioFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearDetalleServicioFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearDetalleServicioFragment newInstance(String param1, String param2) {
        CrearDetalleServicioFragment fragment = new CrearDetalleServicioFragment();
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
        View v=inflater.inflate(R.layout.fragment_crear_resumen_servicio, container, false);
        helper=new BD(getContext());
        monto=v.findViewById(R.id.inputMonto);
        numHora=v.findViewById(R.id.inputHorasAsigadas);
        observaciones = v.findViewById(R.id.inputObservaciones);
        benD=v.findViewById(R.id.inputBenDirec);
        benI=v.findViewById(R.id.inputBenIndirec);
        estado=v.findViewById(R.id.estadoServicio);

        ArrayAdapter<CharSequence> adapterEstado = ArrayAdapter.createFromResource(getContext(),R.array.combo_estadoServicio, android.R.layout.simple_spinner_item);
        estado.setAdapter(adapterEstado);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle =this.getArguments();

        if (bundle != null) {
            rss = bundle.getParcelable("RESUMENSERVICIOTOTAL");

            System.out.println(rss);
        }else{
            Toast.makeText(getContext(),"Fallo al recuperar objeto",Toast.LENGTH_SHORT).show();
        }
        Button btnAgregar;
        btnAgregar=view.findViewById(R.id.btnCrearDetalleResumen);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarDetalle(view);
            }
        });
    }

    public void insertarDetalle(View v){
        String estadoS = estado.getText().toString();
        String regInsertados;
        String observacio = observaciones.getText().toString();
        int numHoras = Integer.valueOf(numHora.getText().toString());
        int beneficiariosDirectos = Integer.valueOf(benD.getText().toString());
        int beneficiariosIndirectos = Integer.valueOf(benI.getText().toString());
        double montoX = Double.valueOf(monto.getText().toString());

        DetalleResumenServicio detalleResumenServicio = new DetalleResumenServicio();

       detalleResumenServicio.setEstadoDeResumen(estadoS);
       detalleResumenServicio.setIdResumen(rss.getIdResumen());
       detalleResumenServicio.setBeneficiariosIndirectos(beneficiariosIndirectos);
       detalleResumenServicio.setMonto(montoX);
       detalleResumenServicio.setBeneficiariosDirectos(beneficiariosDirectos);
       detalleResumenServicio.setHorasAsignadas(numHoras);
       detalleResumenServicio.setObservaciones(observacio);

       helper.abrir();
       regInsertados = helper.insertarDetalleServicio(detalleResumenServicio);
       helper.cerrar();
        Toast.makeText(getContext(),regInsertados, Toast.LENGTH_SHORT).show();
    }

}