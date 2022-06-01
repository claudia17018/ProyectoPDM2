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
import com.example.proyectopdm.docente.Estudiante;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link fragment_estudiante_crear#newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_estudiante_crear extends Fragment {

    EditText txtCarnet, txtNombre, txtApellido, txtTelefono, txtEmail, txtDui, txtDomicilio, txtNit, txtIdUsuario;
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

    public fragment_estudiante_crear() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_estudiante_crear.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_estudiante_crear newInstance(String param1, String param2) {
        fragment_estudiante_crear fragment = new fragment_estudiante_crear();
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
        View v = inflater.inflate(R.layout.fragment_estudiante_crear, container, false);
        Button btnGuardar = v.findViewById(R.id.btnCrearAlumno);
        helper = new BD(getContext());
        txtCarnet = (TextInputEditText) v.findViewById(R.id.textCarnetAlumno);
        txtNombre = (TextInputEditText) v.findViewById(R.id.textNombreEstudiante);
        txtApellido = (TextInputEditText) v.findViewById(R.id.textApellidoEstudiante);
        txtTelefono = (TextInputEditText) v.findViewById(R.id.textTelefonoEstudiante);
        txtEmail = (TextInputEditText) v.findViewById(R.id.textEmailEstudiante);
        txtDui = (TextInputEditText) v.findViewById(R.id.textDuiEstudiante);
        txtDomicilio = (TextInputEditText) v.findViewById(R.id.textDomicilioEstudiante);
        txtNit = (TextInputEditText) v.findViewById(R.id.textNitEstudiante);

        txtNombreUsuario =(TextInputEditText) v.findViewById(R.id.textnombreUsuario);
        txtPassUsuario = (TextInputEditText) v.findViewById(R.id.textPassUsuario);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertarAccesoUsuarioE(v);
                insertarEstudiante(v);
                insertarUsuario(v);
                limpiarTexto(v);
            }
        });

        return v;
    }
    public void insertarEstudiante(View v){
        String carnet = txtCarnet.getText().toString();
        String nombreEstu = txtNombre.getText().toString();
        String apellidoEstu = txtApellido.getText().toString();
        String telefonoEstu = txtTelefono.getText().toString();
        String emailEstu = txtEmail.getText().toString();
        String duiEstu = txtDui.getText().toString();
        String domicilioEstu = txtDomicilio.getText().toString();
        String nitEst = txtNit.getText().toString();

        /*String usuario = txtNombreUsuario.getText().toString();
        String pass = txtPassUsuario.getText().toString();*/

        String regInsertado;
        Estudiante estudiante = new Estudiante();
        estudiante.setCarnet(carnet);
        estudiante.setNombreEstudiante(nombreEstu);
        estudiante.setApellidoEstudiante(apellidoEstu);
        estudiante.setTelefono(telefonoEstu);
        estudiante.setEmailEstudinate(emailEstu);
        estudiante.setDuiEstudiante(duiEstu);
        estudiante.setDomicilioEstudiante(domicilioEstu);
        estudiante.setNitEstudiante(nitEst);

        /*Usuario user1 = new Usuario();
        user1.setUsuario(usuario);
        user1.setContraseña(pass);*/

        helper.abrir();
        regInsertado = helper.insertarEstudiante(estudiante);
        helper.cerrar();
        Toast.makeText(getContext(), regInsertado, Toast.LENGTH_SHORT).show();
    }
    public void insertarAccesoUsuarioE(View v){


        AccesoUsuario au = new AccesoUsuario();
        au.setIdOpcion(3);
        au.setIdUsuario(0);

        helper.abrir();
        helper.insertarAccesoUsuario(au);
        helper.cerrar();

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
    public void limpiarTexto(View v){
        txtCarnet.setText("");
        txtNombre.setText("");
        txtApellido.setText("");
        txtTelefono.setText("");
        txtDui.setText("");
        txtEmail.setText("");
        txtDomicilio.setText("");
        txtNit.setText("");
        txtPassUsuario.setText("");
        txtNombreUsuario.setText("");
    }
}





















