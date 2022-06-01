package com.example.proyectopdm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearAreaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearAreaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText txtNomArea, txtDesArea,txtArea;
    Button btnGuardar;
    ArrayList<Area> listadoArea;
    BD helper;
    AutoCompleteTextView listArea;
    ImageView menuA;
    AutoCompleteTextView nomAr;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearAreaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearAreaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearAreaFragment newInstance(String param1, String param2) {
        CrearAreaFragment fragment = new CrearAreaFragment();
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
        View v = inflater.inflate(R.layout.fragment_crear_area, container, false);
        Button btncrear = v.findViewById(R.id.btnCrear2Area);
        helper = new BD(getContext());

        txtNomArea = (TextInputEditText) v.findViewById(R.id.textCrearNomArea);

        txtDesArea = (TextInputEditText) v.findViewById(R.id.textCreaAreaDes);


        btncrear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertarArea(v);
                limpiarTexto(v);
            }
        });


        return v;
    }
    public void insertarArea(View v){
        String nombre = txtNomArea.getText().toString();
        //Integer area = Integer.valueOf(txtArea.getText().toString());


        String des = txtDesArea.getText().toString();
        helper.abrir();

        String regInsertado;
        Area area = new Area();
        area.setNomArea(nombre);

        area.setDesArea(des);





        regInsertado = helper.insertarAreaCarrera(area);
        helper.cerrar();
        Toast.makeText(getContext(), regInsertado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        txtNomArea.setText("");

        txtDesArea.setText("");

    }

}