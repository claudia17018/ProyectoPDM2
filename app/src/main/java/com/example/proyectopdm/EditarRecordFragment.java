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
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarRecordFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarRecordFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    EditText txtCarnet, txtPro,txtUv,txtCum, txtProgre;
    Button ton;
    ArrayList<Carrera> listadoCarrera;
    BD helper;
    String nomCar;
    AutoCompleteTextView listCarrera;
    ImageView menuA;
    AutoCompleteTextView nomAr;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarRecordFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarRecordFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarRecordFragment newInstance(String param1, String param2) {
        EditarRecordFragment fragment = new EditarRecordFragment();
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
        View v = inflater.inflate(R.layout.fragment_editar_record, container, false);

         ton = (Button) v.findViewById(R.id.btnEditarReco);
        helper = new BD(getContext());
        listadoCarrera = new ArrayList<>();
        helper.abrir();
        listadoCarrera = helper.consultarCarrera();
        int n = 0;
        System.out.println("Prueba\n\n" + listadoCarrera.size());
        String listab[] = new String[listadoCarrera.size()];
        while (n < listadoCarrera.size()) {


            listab[n] = String.valueOf(listadoCarrera.get(n).getNomCarrera());
            System.out.println("COnsulta \n\n" + listab[n]);
            n++;
        }
        helper.cerrar();
        //nomAr = v.findViewById(R.id.actArea);
        txtCarnet = (TextInputEditText) v.findViewById(R.id.textCarnetReE);
        //txtArea = (TextInputEditText) v.findViewById(R.id.textArea);
        txtUv = (TextInputEditText) v.findViewById(R.id.textUnidadesValoraE);
        txtPro = (TextInputEditText) v.findViewById(R.id.textPromedioE);
        txtCum = (TextInputEditText) v.findViewById(R.id.textcumE);
        txtProgre = (TextInputEditText) v.findViewById(R.id.textProgresoE);


        listCarrera = (AutoCompleteTextView) v.findViewById(R.id.textCarreraReE);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, listab);
        //ArrayAdapter<CharSequence> adapterMeses = ArrayAdapter.createFromResource(getContext(),R.array.combo_meses, android.R.layout.simple_spinner_item);
        //prueba.setAdapter(adapterMeses);
        listCarrera.setAdapter(adapter2);
     return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!= null){
            Record record = bundle.getParcelable("RECORDACADEMICO");

            helper.abrir();
            String nomCar= helper.consultarNomCarrera(record.getIdCarrera());
            helper.cerrar();
            txtCarnet.setText(String.valueOf(record.getCarnet()));
            txtUv.setText(String.valueOf(record.getuV()));
            txtPro.setText(String.valueOf(record.getPromedio()));
            txtCum.setText(String.valueOf(record.getCum()));
            txtProgre.setText(String.valueOf(record.getProceso()));
            listCarrera.setText(nomCar);

            ton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String regAfectados;
                    helper.abrir();

                    record.setCarnet(txtCarnet.getText().toString());
                    record.setuV(Integer.valueOf(txtUv.getText().toString()));
                    record.setPromedio(Double.valueOf(txtPro.getText().toString()));
                    record.setCum(Double.valueOf(txtCum.getText().toString()));
                    record.setProceso(Double.valueOf(txtProgre.getText().toString()));

                    String nomCar = listCarrera.getText().toString();


                    int carre = helper.consultarca(nomCar);
                    record.setIdCarrera(Integer.valueOf(carre));



                    regAfectados = helper.actualizarrecord(record);
                    helper.cerrar();
                    Toast.makeText(getContext(),regAfectados,Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(getContext(),"Problemas al cargar los datos :(", Toast.LENGTH_SHORT).show();
        }
    }
}