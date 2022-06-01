package com.example.proyectopdm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.docente.Docente;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModificarDocenteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModificarDocenteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText txtNombre, txtApellido, txtDui, txtNit, txtIdUsuario,txtTelefono, txtIdDocente, txtEmail;
    EditText txtNombreUsuario, txtPassUsuario;
    Button btnGuardar;
    BD helper;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ModificarDocenteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ModificarDocenteFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ModificarDocenteFragment newInstance(String param1, String param2) {
        ModificarDocenteFragment fragment = new ModificarDocenteFragment();
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


        View v = inflater.inflate(R.layout.fragment_modificar_docente, container, false);

        btnGuardar = v.findViewById(R.id.btnagregardocenteE);

        helper = new BD(getContext());//getContext sustituye a this
        txtNombre = (TextInputEditText) v.findViewById(R.id.textNombreDocenteE);
        txtApellido = (TextInputEditText) v.findViewById(R.id.textApellidosDocenteE);
        txtDui = (TextInputEditText) v.findViewById(R.id.textDuiDocenteE);
        txtNit = (TextInputEditText) v.findViewById(R.id.textNitDocenteE);
        txtTelefono = (TextInputEditText) v.findViewById(R.id.txtDocenteTelefonoE);
        txtEmail = (TextInputEditText) v.findViewById(R.id.textCorreoDocenteE);


        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!= null){
            Docente docente = bundle.getParcelable("DOCENTE");





            txtNombre.setText(String.valueOf(docente.getNombreTutor()));
            txtApellido.setText(String.valueOf(docente.getApellidosTutor()));
            txtDui.setText(String.valueOf(docente.getDuiTutor()));
            txtNit.setText(String.valueOf(docente.getNitTutor()));
            txtTelefono.setText(String.valueOf(docente.getTelefonoTutor()));
            txtEmail.setText(String.valueOf(docente.getEmailTutor()));



            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String regAfectados;
                    helper.abrir();



                    docente.setNombreTutor(txtNombre.getText().toString());
                    docente.setApellidosTutor(txtDui.getText().toString());
                    docente.setDuiTutor(txtDui.getText().toString());
                    docente.setNitTutor(txtNit.getText().toString());
                    docente.setTelefonoTutor(txtTelefono.getText().toString());
                    docente.setEmailTutor(txtEmail.getText().toString());

                    regAfectados = helper.actualizarDocente(docente);
                    helper.cerrar();
                    Toast.makeText(getContext(),regAfectados,Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(getContext(),"Problemas al cargar los datos :(", Toast.LENGTH_SHORT).show();
        }
    }
}