package com.example.proyectopdm.fragmentR;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectopdm.R;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.ResumenServicioSocial;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ResumenCrearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ResumenCrearFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    TextInputEditText fecha,carnet;
    BD helper;
    AutoCompleteTextView doc;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ResumenCrearFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ResumenCrearFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ResumenCrearFragment newInstance(String param1, String param2) {
        ResumenCrearFragment fragment = new ResumenCrearFragment();
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
        View v= inflater.inflate(R.layout.fragment_resumen_crear, container, false);
        helper = new BD(getContext());
        carnet = v.findViewById(R.id.inputCarnetServicio);
        fecha = v.findViewById(R.id.inputFechaApertura);
        doc = v.findViewById(R.id.inputlistadoDoc);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String date = new SimpleDateFormat("dd/MM/yyyy").format(new Date());
        fecha.setText(date);
        Button button;
        button = view.findViewById(R.id.btnCrearResumen);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                insertarResumen(view);
            }
        });
    }

    public void insertarResumen(View v){
        String regInsertados;

        String carnetS = carnet.getText().toString();
        String fechaApertura =fecha.getText().toString();
        int docente = Integer.valueOf(doc.getText().toString());

        ResumenServicioSocial resumenServicioSocial = new ResumenServicioSocial();
        resumenServicioSocial.setIdDocente(docente);
        resumenServicioSocial.setFechaAperturaExpediente(fechaApertura);
        resumenServicioSocial.setCarnet(carnetS);
        System.out.println(resumenServicioSocial);
        helper.abrir();
        regInsertados=helper.insertarDetalleResumen(resumenServicioSocial);
        helper.cerrar();
        Toast.makeText(getContext(),regInsertados,Toast.LENGTH_SHORT).show();
    }
}