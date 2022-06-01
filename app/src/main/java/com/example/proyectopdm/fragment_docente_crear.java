package com.example.proyectopdm;

import android.os.Bundle;

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
 * Use the {@link fragment_docente_crear#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_docente_crear extends Fragment {

    EditText txtNombre, txtApellido, txtDui, txtNit, txtIdUsuario,txtTelefono, txtIdDocente, txtEmail;
    EditText txtNombreUsuario, txtPassUsuario;
    Button btnGuardar;
    BD helper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public fragment_docente_crear() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_docente_crear.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_docente_crear newInstance(String param1, String param2) {
        fragment_docente_crear fragment = new fragment_docente_crear();
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
        View v = inflater.inflate(R.layout.fragment_docente_crear, container, false);
        Button btnGuardar = v.findViewById(R.id.btnagregardocente);

        helper = new BD(getContext());//getContext sustituye a this
        txtNombre = (TextInputEditText) v.findViewById(R.id.textNombreDocente);
        txtApellido = (TextInputEditText) v.findViewById(R.id.textApellidosDocente);
        txtDui = (TextInputEditText) v.findViewById(R.id.textDuiDocente);
        txtNit = (TextInputEditText) v.findViewById(R.id.textNitDocente);
        txtTelefono = (TextInputEditText) v.findViewById(R.id.txtDocenteTelefono);
        txtEmail = (TextInputEditText) v.findViewById(R.id.textCorreoDocente);
        //txtIdUsuario = (EditText) v.findViewById(R.id.idUsuarioCrear);
        //txtIdDocente = (EditText) v.findViewById(R.id.idDocenteCrear);

        txtNombreUsuario =(TextInputEditText) v.findViewById(R.id.txtUsuarioDocente);
        txtPassUsuario = (TextInputEditText) v.findViewById(R.id.txtPassDocente);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                insertarAccesoUsuario(v);
                insertarDocente(v);
                insertarUsuario(v);
                limpiarTexto(v);
            }
        });

        return v;
    }
    public void insertarDocente(View v){
        String nombre = txtNombre.getText().toString();
        String apellidos = txtApellido.getText().toString();
        String dui = txtDui.getText().toString();
        String nit = txtNit.getText().toString();
        String telefono = txtTelefono.getText().toString();
        String email = txtEmail.getText().toString();
        //Integer idUsuario = Integer.valueOf(txtIdUsuario.getText().toString());
        //String idDocente = txtIdDocente.getText().toString();


        String regInsertados;
        Docente docente = new Docente();
        docente.setNombreTutor(nombre);
        docente.setApellidosTutor(apellidos);
        docente.setDuiTutor(dui);
        docente.setNitTutor(nit);
        docente.setTelefonoTutor(telefono);
        docente.setEmailTutor(email);

        //docente.setIdDocente(idDocente);
        //docente.setIdUsuario(idUsuario);

        helper.abrir();
        regInsertados = helper.insertar(docente);
        helper.cerrar();
        Toast.makeText(getContext(), regInsertados, Toast.LENGTH_SHORT).show();

    }
    public void insertarUsuario(View v){
        String usuario = txtNombreUsuario.getText().toString();
        String pass = txtPassUsuario.getText().toString();

        String regInsertado;

        Usuario user1 = new Usuario();
        user1.setUsuario(usuario);
        user1.setContraseña(pass);
        helper.abrir();
        regInsertado = helper.insertarUsuario(user1);
        helper.cerrar();
        Toast.makeText(getContext(), regInsertado, Toast.LENGTH_SHORT).show();
    }


    public void insertarAccesoUsuario(View v){


        AccesoUsuario au = new AccesoUsuario();
        au.setIdOpcion(2);
        au.setIdUsuario(0);

        helper.abrir();
        helper.insertarAccesoUsuario(au);
        helper.cerrar();

    }
    public void limpiarTexto(View v){
        txtNombre.setText("");
        txtApellido.setText("");
        txtDui.setText("");
        txtNit.setText("");
        txtTelefono.setText("");
        txtEmail.setText("");
        txtPassUsuario.setText("");
        txtNombreUsuario.setText("");
    }
}