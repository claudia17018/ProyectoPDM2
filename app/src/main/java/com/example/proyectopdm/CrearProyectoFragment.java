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
import com.example.proyectopdm.docente.Docente;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearProyectoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearProyectoFragment extends Fragment {

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

    public CrearProyectoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearProyectoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearProyectoFragment newInstance(String param1, String param2) {
        CrearProyectoFragment fragment = new CrearProyectoFragment();
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
        View v = inflater.inflate(R.layout.fragment_crear_proyecto, container, false);

        Button btnGuardar = v.findViewById(R.id.btnCrearProyectoC);
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


        txtNom = (TextInputEditText) v.findViewById(R.id.nomProCe);
        txtDescripcion = (TextInputEditText) v.findViewById(R.id.descripcionProCe);
        txtlugar = (TextInputEditText) v.findViewById(R.id.lugarProCe);
        txtencargado = (TextInputEditText) v.findViewById(R.id.encargadoProCe);
        txthoras = (TextInputEditText) v.findViewById(R.id.horasProCe);
        txtestudiantes = (TextInputEditText) v.findViewById(R.id.estudiProCe);
        txtEstado = (TextInputEditText) v.findViewById(R.id.estadoProCe);



        listCategorias = (AutoCompleteTextView) v.findViewById(R.id.categoriaProCe);
        listaModalidad = (AutoCompleteTextView) v.findViewById(R.id.modalidadProyectoCe);
        listDocentes = (AutoCompleteTextView) v.findViewById(R.id.docenteProCe);
        ArrayAdapter<CharSequence> adapter5 = ArrayAdapter.createFromResource(getContext(),R.array.op_categoria, android.R.layout.simple_spinner_item);
        listCategorias.setAdapter(adapter5);

        ArrayAdapter<CharSequence> adapter9 = ArrayAdapter.createFromResource(getContext(),R.array.op_modalidad, android.R.layout.simple_spinner_item);
        listaModalidad.setAdapter(adapter9);

         ArrayAdapter<String> adapter11=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,listaA);
        listDocentes.setAdapter(adapter11);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertarCarrera(v);
                //limpiarTexto(v);
            }
        });


        return v;
    }

    public void insertarCarrera(View v){
        String nombre = txtNom.getText().toString();
        //Integer area = Integer.valueOf(txtArea.getText().toString());

        helper.abrir();

        String regInsertado;
        Proyecto pro = new Proyecto();

        pro.setNomPro(txtNom.getText().toString());
        pro.setDesPro(txtDescripcion.getText().toString());
        pro.setNumEst(Integer.valueOf(txtestudiantes.getText().toString()));
        pro.setNumHoras(Integer.valueOf(txthoras.getText().toString()));

        pro.setLugar(txtlugar.getText().toString());
        pro.setRespoIns(txtencargado.getText().toString());
        pro.setEstadoPro(txtEstado.getText().toString());

        String docenteid = helper.consultarDuiDocente(listDocentes.getText().toString());
        int categoriaid = helper.consultarIdCategoria(listCategorias.getText().toString());
        int modalidadid = helper.consultarIdModalidad(listaModalidad.getText().toString());

        pro.setDuiTu(docenteid);
        pro.setIdMod(modalidadid);
        pro.setIdCat(categoriaid);





        regInsertado = helper.insertarProyecto(pro);
        helper.cerrar();
        Toast.makeText(getContext(), regInsertado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        txtNom.setText("");


    }

}