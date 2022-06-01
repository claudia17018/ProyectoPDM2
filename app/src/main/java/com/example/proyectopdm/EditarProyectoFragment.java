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
import com.example.proyectopdm.docente.Docente;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarProyectoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarProyectoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    EditText txtNom, txtlugar,txtencargado,txthoras, txtestudiantes,txtDescripcion,txtEstado;
    Button btnGuardar;
    ArrayList<Docente> listadoDocente;

    BD helper;
    AutoCompleteTextView listDocentes,listCategorias,listaModalidad;
    ImageView menuA;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarProyectoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarProyectoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarProyectoFragment newInstance(String param1, String param2) {
        EditarProyectoFragment fragment = new EditarProyectoFragment();
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
        View v = inflater.inflate(R.layout.fragment_editar_proyecto, container, false);

        btnGuardar = v.findViewById(R.id.btnEditarProyecto);
        helper = new BD(getContext());
        listadoDocente = new ArrayList<>();
        helper.abrir();
        listadoDocente = helper.consultarListDocentes();
        int n=0;
        //System.out.println("Prueba\n\n"+listadoDocente.size());
        String listaA[]=new String[listadoDocente.size()];
        while (n<listadoDocente.size()){


            listaA[n]=String.valueOf(listadoDocente.get(n).getNombreTutor());
            System.out.println("COnsulta \n\n"+listaA[n]);
            n++;
        }
        helper.cerrar();


        txtNom = (TextInputEditText) v.findViewById(R.id.nomProC);
        txtDescripcion = (TextInputEditText) v.findViewById(R.id.descripcionProC);
        txtlugar = (TextInputEditText) v.findViewById(R.id.lugarProC);
        txtencargado = (TextInputEditText) v.findViewById(R.id.encargadoProC);
        txthoras = (TextInputEditText) v.findViewById(R.id.horasProC);
        txtestudiantes = (TextInputEditText) v.findViewById(R.id.estudiProC);
        txtEstado = (TextInputEditText) v.findViewById(R.id.estadoProC);



        listCategorias = (AutoCompleteTextView) v.findViewById(R.id.categoriaProC);
        listaModalidad = (AutoCompleteTextView) v.findViewById(R.id.modalidadProyectoC);
        listDocentes = (AutoCompleteTextView) v.findViewById(R.id.docenteProC);

        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(getContext(),R.array.op_categoria, android.R.layout.simple_spinner_item);
        listCategorias.setAdapter(adapter5);

        ArrayAdapter<CharSequence> adapter9 = ArrayAdapter.createFromResource(getContext(),R.array.op_modalidad, android.R.layout.simple_spinner_item);
        listaModalidad.setAdapter(adapter9);

        ArrayAdapter<String> adapter11=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,listaA);
        listDocentes.setAdapter(adapter11);



        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!= null){
            Proyecto carrera = bundle.getParcelable("PROYECTO");

            helper.abrir();
            //areaN= helper.consultarNomArea(carrera.getIdArea());





            txtNom.setText(String.valueOf(carrera.getNomPro()));
            txtDescripcion.setText(String.valueOf(carrera.getDesPro()));
            txtlugar.setText(String.valueOf(carrera.getLugar()));
            txtencargado.setText(String.valueOf(carrera.getRespoIns()));
            txthoras.setText(String.valueOf(carrera.getNumHoras()));
            txtestudiantes.setText(String.valueOf(carrera.getNumEst()));
            txtEstado.setText(String.valueOf(carrera.getEstadoPro()));

            //listCategorias.setText("");
            //listaModalidad.setText("");
            //listDocentes.setText(helper.consultarDocente(carrera.getDuiTu().toString()));



            helper.cerrar();

            btnGuardar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String regAfectados;
                    helper.abrir();

                    carrera.setNomPro(txtNom.getText().toString());
                    carrera.setDesPro(txtDescripcion.getText().toString());
                    carrera.setNumEst(Integer.valueOf(txtestudiantes.getText().toString()));
                    carrera.setNumHoras(Integer.valueOf(txthoras.getText().toString()));

                   carrera.setLugar(txtlugar.getText().toString());
                    carrera.setRespoIns(txtencargado.getText().toString());
                    carrera.setEstadoPro(txtEstado.getText().toString());

                    String docenteid = helper.consultarDuiDocente(listDocentes.getText().toString());
                    int categoriaid = helper.consultarIdCategoria(listCategorias.getText().toString());
                    int modalidadid = helper.consultarIdModalidad(listaModalidad.getText().toString());

                    carrera.setDuiTu(docenteid);
                    carrera.setIdMod(modalidadid);
                    carrera.setIdCat(categoriaid);





                    String regInsertado = helper.actualizarProyecto(carrera);
                    helper.cerrar();
                    Toast.makeText(getContext(), regInsertado, Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(getContext(),"Problemas al cargar los datos :(", Toast.LENGTH_SHORT).show();
        }
    }
}