package com.example.proyectopdm;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.docente.Estudiante;
import com.example.proyectopdm.entidades.Bitacora;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearCarreraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearCarreraFragment extends Fragment {
    EditText txtNom, txtEsc,txtArea;
    Button btnGuardar;
    ArrayList<Area> listadoArea;
    BD helper;
    AutoCompleteTextView listArea;
    ImageView menuA;
    AutoCompleteTextView nomAr;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearCarreraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearCarreraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearCarreraFragment newInstance(String param1, String param2) {
        CrearCarreraFragment fragment = new CrearCarreraFragment();
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
        View v = inflater.inflate(R.layout.fragment_crear_carrera, container, false);
        Button btnGuardar = v.findViewById(R.id.btnCrearCarrera);
        helper = new BD(getContext());
        listadoArea = new ArrayList<>();
        helper.abrir();
        listadoArea = helper.consultarArea();
        int n=0;
        System.out.println("Prueba\n\n"+listadoArea.size());
        String listaA[]=new String[listadoArea.size()];
        while (n<listadoArea.size()){


            listaA[n]=String.valueOf(listadoArea.get(n).getNomArea());
            System.out.println("COnsulta \n\n"+listaA[n]);
            n++;
        }
        helper.cerrar();
        nomAr = v.findViewById(R.id.actArea);
        txtNom = (TextInputEditText) v.findViewById(R.id.textNomCarrera);
        //txtArea = (TextInputEditText) v.findViewById(R.id.textArea);
        txtEsc = (TextInputEditText) v.findViewById(R.id.textEscuela);
        menuA = (ImageView) v.findViewById(R.id.menuArea);
        listArea = (AutoCompleteTextView) v.findViewById(R.id.actArea);
        ArrayAdapter<String> adapter1=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,listaA);
        //ArrayAdapter<CharSequence> adapterMeses = ArrayAdapter.createFromResource(getContext(),R.array.combo_meses, android.R.layout.simple_spinner_item);
        //prueba.setAdapter(adapterMeses);
        listArea.setAdapter(adapter1);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertarCarrera(v);
                limpiarTexto(v);
            }
        });
        menuA.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Navigation.findNavController(v).navigate(R.id.areaFragment);
            }
        });

        return v;
    }

    public void insertarCarrera(View v){
        String nombre = txtNom.getText().toString();
        //Integer area = Integer.valueOf(txtArea.getText().toString());

        String nomArea = nomAr.getText().toString();
        String escuela = txtEsc.getText().toString();
        helper.abrir();
        int area = helper.consultarIdArea(nomArea);
        String regInsertado;
        Carrera carrera = new Carrera();
        carrera.setNomCarrera(nombre);
        carrera.setIdArea(area);
        carrera.setNomEscuela(escuela);





        regInsertado = helper.insertarCarrera(carrera);
        helper.cerrar();
        Toast.makeText(getContext(), regInsertado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        txtNom.setText("");
        listArea.setText("");
        txtEsc.setText("");

    }
}