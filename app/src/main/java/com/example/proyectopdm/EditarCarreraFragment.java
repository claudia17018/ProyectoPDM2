package com.example.proyectopdm;

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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Bitacora;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarCarreraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarCarreraFragment extends Fragment {

    TextInputEditText txtNomE, txtEscE,txtAreaE;
    Button btnEdit;
    ArrayList<Area> listadoArea;
    BD helper;
    AutoCompleteTextView listAreaE;
    String areaN;
    AutoCompleteTextView nomArE;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarCarreraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarCarreraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarCarreraFragment newInstance(String param1, String param2) {
        EditarCarreraFragment fragment = new EditarCarreraFragment();
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

        View v = inflater.inflate(R.layout.fragment_editar_carrera, container, false);

        btnEdit = v.findViewById(R.id.btnEditarCarrera);
        helper = new BD(getContext());

        helper.abrir();


        listadoArea = helper.consultarArea();
        int n=0;
        //System.out.println("Prueba\n\n"+listadoArea.size());
        String listaA1[]=new String[listadoArea.size()];
        while (n<listadoArea.size()){


            listaA1[n]=String.valueOf(listadoArea.get(n).getNomArea());
            System.out.println("COnsulta \n\n"+listaA1[n]);
            n++;
        }
        helper.cerrar();
        helper = new BD(getContext());

        txtNomE =(TextInputEditText) v.findViewById(R.id.editarNomCarrera);
        txtEscE = (TextInputEditText) v.findViewById(R.id.editarEscuela);

        listAreaE = (AutoCompleteTextView) v.findViewById(R.id.editarArea);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,listaA1);
        //ArrayAdapter<CharSequence> adapterMeses = ArrayAdapter.createFromResource(getContext(),R.array.combo_meses, android.R.layout.simple_spinner_item);
        //prueba.setAdapter(adapterMeses);
        listAreaE.setAdapter(adapter1);

        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!= null){
            Carrera carrera = bundle.getParcelable("CARRERA");

            helper.abrir();
            areaN= helper.consultarNomArea(carrera.getIdArea());
            helper.cerrar();
            txtNomE.setText(String.valueOf(carrera.getNomCarrera()));
            txtEscE.setText(String.valueOf(carrera.getNomEscuela()));
            listAreaE.setText(areaN);

            btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String regAfectados;
                    helper.abrir();


                    carrera.setNomCarrera(txtNomE.getText().toString());
                    carrera.setNomEscuela(txtEscE.getText().toString());
                    String nomArea = listAreaE.getText().toString();


                    int area = helper.consultarIdArea(nomArea);

                    carrera.setIdArea(Integer.valueOf(area));
                    regAfectados = helper.actualizarCarrera(carrera);
                    helper.cerrar();
                    Toast.makeText(getContext(),regAfectados,Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(getContext(),"Problemas al cargar los datos :(", Toast.LENGTH_SHORT).show();
        }
    }
}