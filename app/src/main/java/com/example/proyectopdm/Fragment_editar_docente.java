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
 * Use the {@link Fragment_editar_docente#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Fragment_editar_docente extends Fragment {

    EditText editNombre, editApellido, editDui, editNit, editIdUsuario,editTelefono, editIdDocente, editEmail;
    Button btnGuardar;
    BD helper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Fragment_editar_docente() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Fragment_editar_docente.
     */
    // TODO: Rename and change types and number of parameters
    public static Fragment_editar_docente newInstance(String param1, String param2) {
        Fragment_editar_docente fragment = new Fragment_editar_docente();
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
        View v =inflater.inflate(R.layout.fragment_editar_docente, container, false);
        btnGuardar = v.findViewById(R.id.btnActualizarDocente);
        helper = new BD(getContext());

        editNombre = (TextInputEditText) v.findViewById(R.id.textNombreDocente);
        editApellido = (TextInputEditText) v.findViewById(R.id.textApellidosDocente);
        editDui = (TextInputEditText) v.findViewById(R.id.textDuiDocente);
        editNit = (TextInputEditText) v.findViewById(R.id.textNitDocente);
        editTelefono = (TextInputEditText) v.findViewById(R.id.txtDocenteTelefono);
        editEmail = (TextInputEditText) v.findViewById(R.id.textCorreoDocente);
        editIdUsuario = (EditText) v.findViewById(R.id.idUsuario);

        return null;
    }
    /*
    public void actualizarAlumno(View v) {
        Docente docente = new Docente();
        docente.setNombreTutor(editNombre.getText().toString());
        docente.setApellidosTutor(editApellido.getText().toString());
        docente.setDuiTutor(editDui.getText().toString());
        docente.setNitTutor(editNit.getText().toString());
        docente.setTelefonoTutor(editTelefono.getText().toString());
        docente.setEmailTutor(editEmail.getText().toString());

        helper.abrir();
        String estado = helper.actualizarDocente(docente);
        helper.cerrar();
        Toast.makeText(getContext(), estado, Toast.LENGTH_SHORT).show();
    }*/


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}