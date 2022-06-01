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
import android.widget.Toast;

import com.example.proyectopdm.R;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Bitacora;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleBitacoraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleBitacoraFragment extends Fragment {

    BD helper;
    TextInputEditText textAnio;
    AutoCompleteTextView Ciclo, Mes;
    Button edit;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetalleBitacoraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleBitacoraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleBitacoraFragment newInstance(String param1, String param2) {
        DetalleBitacoraFragment fragment = new DetalleBitacoraFragment();
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
        View v = inflater.inflate(R.layout.fragment_detalle_bitacora, container, false);
        helper = new BD(getContext());

        edit = v.findViewById(R.id.btnEditarBitacora);
        textAnio = v.findViewById(R.id.inputAnioBitEdit);
        Ciclo = v.findViewById(R.id.actCicloEdit);
        Mes = v.findViewById(R.id.actMesesEdit);

        ArrayAdapter<CharSequence> adapterMeses = ArrayAdapter.createFromResource(getContext(),R.array.combo_meses, android.R.layout.simple_spinner_item);
        Mes.setAdapter(adapterMeses);

        ArrayAdapter<CharSequence> adapterCiclo = ArrayAdapter.createFromResource(getContext(),R.array.combo_ciclo, android.R.layout.simple_spinner_item);
        Ciclo.setAdapter(adapterCiclo);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!= null){
           Bitacora bitacora = bundle.getParcelable("BITACORA");
           textAnio.setText(String.valueOf(bitacora.getAnio()));
           Ciclo.setText(String.valueOf(bitacora.getCiclo()));
           Mes.setText(bitacora.getMes());

           edit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View view) {
                   String regAfectados;
                  helper.abrir();
                  bitacora.getIdBitacora();
                  bitacora.getIdEstudianteProyecto();
                  bitacora.setCiclo(Integer.valueOf(Ciclo.getText().toString()));
                  bitacora.setMes(Mes.getText().toString());
                  bitacora.setAnio(Integer.valueOf(textAnio.getText().toString()));
                  regAfectados = helper.actualizarBitacora(bitacora);
                  helper.cerrar();
                  Toast.makeText(getContext(),regAfectados,Toast.LENGTH_SHORT).show();
               }
           });

    }else{
            Toast.makeText(getContext(),"Problemas al cargar los datos :(", Toast.LENGTH_SHORT).show();
        }
    }
}