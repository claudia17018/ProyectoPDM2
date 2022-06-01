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
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CrearRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CrearRecordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText txtCarnet, txtPro,txtUv,txtCum, txtProgre;
    Button btnGuardarRe;
    ArrayList<Carrera> listadoCarrera;
    BD helper;
    AutoCompleteTextView listCarrera;
    ImageView menuA;
    AutoCompleteTextView nomAr;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CrearRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CrearRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CrearRecordFragment newInstance(String param1, String param2) {
        CrearRecordFragment fragment = new CrearRecordFragment();
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
        View v = inflater.inflate(R.layout.fragment_crear_record, container, false);
        Button btnGuardar = v.findViewById(R.id.btnCrearRecordA);
        helper = new BD(getContext());
        listadoCarrera = new ArrayList<>();
        helper.abrir();
        listadoCarrera = helper.consultarCarrera();
        int n=0;
        System.out.println("Prueba\n\n"+listadoCarrera.size());
        String listab[]=new String[listadoCarrera.size()];
        while (n<listadoCarrera.size()){


            listab[n]=String.valueOf(listadoCarrera.get(n).getNomCarrera());
            System.out.println("COnsulta \n\n"+listab[n]);
            n++;
        }
        helper.cerrar();
        //nomAr = v.findViewById(R.id.actArea);
        txtCarnet = (TextInputEditText) v.findViewById(R.id.textCarnetRe);
        //txtArea = (TextInputEditText) v.findViewById(R.id.textArea);
        txtUv = (TextInputEditText) v.findViewById(R.id.textUnidadesValora);
        txtPro = (TextInputEditText) v.findViewById(R.id.textPromedio);
        txtCum = (TextInputEditText) v.findViewById(R.id.textcum);
        txtProgre = (TextInputEditText) v.findViewById(R.id.textProgreso);


        listCarrera = (AutoCompleteTextView) v.findViewById(R.id.textCarreraRe);
        ArrayAdapter<String> adapter2=new ArrayAdapter<String>(getContext(),android.R.layout.simple_spinner_item,listab);
        //ArrayAdapter<CharSequence> adapterMeses = ArrayAdapter.createFromResource(getContext(),R.array.combo_meses, android.R.layout.simple_spinner_item);
        //prueba.setAdapter(adapterMeses);
        listCarrera.setAdapter(adapter2);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                insertarRecord(v);
                limpiarTexto(v);
            }
        });


        return v;
    }

    public void insertarRecord(View v){

        helper.abrir();
        String carne =txtCarnet.getText().toString();
        String carrer = listCarrera.getText().toString();
        int carreraId = Integer.valueOf(helper.consultarca(carrer));
        //int carreraId = 0;
        int uv =Integer.valueOf(txtUv.getText().toString());
        double proo = Double.valueOf(txtPro.getText().toString());
        double cumm =Double.valueOf(txtCum.getText().toString());
        double progres = Double.valueOf(txtProgre.getText().toString());

       // int area = helper.consultarIdArea(nomArea);
        String regInsertado;
        Record record = new Record();
        record.setCarnet(carne);
        record.setIdCarrera(carreraId);
        record.setuV(uv);
        record.setPromedio(proo);
        record.setCum(cumm);
        record.setProceso(progres);





        regInsertado = helper.insertarRecord(record);
        helper.cerrar();
        Toast.makeText(getContext(), regInsertado, Toast.LENGTH_SHORT).show();
    }

    public void limpiarTexto(View v){
        txtCarnet.setText("");
        listCarrera.setText("");
        txtUv.setText("");
        txtProgre.setText("");
        txtPro.setText("");
        txtCum.setText("");

    }
}