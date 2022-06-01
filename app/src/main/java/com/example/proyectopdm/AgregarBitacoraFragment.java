package com.example.proyectopdm;

import android.content.ContentValues;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Bitacora;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.w3c.dom.Text;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AgregarBitacoraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AgregarBitacoraFragment extends Fragment {

    BD helper;

    TextInputEditText textInputEditTextAnio;
    AutoCompleteTextView autoCompleteTextViewCiclo, autoCompleteTextViewMes;
    EditText idestudiante;
    DT dt;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AgregarBitacoraFragment() {
        // Required empty public constructor
    }

    public static AgregarBitacoraFragment newInstance(String param1, String param2) {
        AgregarBitacoraFragment fragment = new AgregarBitacoraFragment();
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
      View v = inflater.inflate(R.layout.fragment_agregar_bitacora, container, false);
      helper = new BD(getContext());

        textInputEditTextAnio = v.findViewById(R.id.inputAnioBit);

         autoCompleteTextViewCiclo = v.findViewById(R.id.actCiclo);
         autoCompleteTextViewMes = v.findViewById(R.id.actMeses);


        idestudiante = v.findViewById(R.id.idEstudianteProyecto);

        ArrayAdapter<CharSequence> adapterMeses = ArrayAdapter.createFromResource(getContext(),R.array.combo_meses, android.R.layout.simple_spinner_item);
        autoCompleteTextViewMes.setAdapter(adapterMeses);

        ArrayAdapter<CharSequence> adapterCiclo = ArrayAdapter.createFromResource(getContext(),R.array.combo_ciclo, android.R.layout.simple_spinner_item);
        autoCompleteTextViewCiclo.setAdapter(adapterCiclo);

        Button btnAgregar = v.findViewById(R.id.btnCrearBitacora);

        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View vi) {
                if(autoCompleteTextViewCiclo.getText().toString().isEmpty() || autoCompleteTextViewMes.getText().toString().isEmpty() ||textInputEditTextAnio.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),getString(R.string.camposVacios), Toast.LENGTH_SHORT).show();
                }
                else{
                    insertarBitacora(vi);
                    limpiarInput();
                }
            }
        });
        return v;
    }

    public void insertarBitacora(View v){
        String regInsertados;
        Integer ciclo = Integer.valueOf(autoCompleteTextViewCiclo.getText().toString());
        String mes = autoCompleteTextViewMes.getText().toString();
        Integer anio = Integer.valueOf(textInputEditTextAnio.getText().toString());

        dt=new DT();
        helper.abrir();
        dt=helper.activo();
        Integer idEstudianteProyecto = Integer.valueOf(dt.getIdU());
            Bitacora bitacora = new Bitacora();
            bitacora.setIdEstudianteProyecto(idEstudianteProyecto);
            bitacora.setCiclo(ciclo);
            bitacora.setMes(mes);
            bitacora.setAnio(anio);
            helper.abrir();
            regInsertados =  helper.insertarBitacora(bitacora);
            helper.cerrar();
            Toast.makeText(getContext(),regInsertados, Toast.LENGTH_SHORT).show();

    }

    private void limpiarInput(){
        autoCompleteTextViewCiclo.setText("");
        textInputEditTextAnio.setText("");
        autoCompleteTextViewMes.setText("");
    }
}