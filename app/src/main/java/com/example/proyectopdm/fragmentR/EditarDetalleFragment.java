package com.example.proyectopdm.fragmentR;

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
import android.widget.Toast;

import com.example.proyectopdm.R;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.DetalleResumenServicio;
import com.example.proyectopdm.entidades.ResumenServicioSocial;
import com.google.android.material.textfield.TextInputEditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarDetalleFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarDetalleFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    AutoCompleteTextView estadoEdit;
    ResumenServicioSocial rss;
    TextInputEditText numHoraEdit,montoEdit,benDEdit,benIEdit,observacionesEdit;
    BD helper;
    Button btnEditResumen;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarDetalleFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarDetalleFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarDetalleFragment newInstance(String param1, String param2) {
        EditarDetalleFragment fragment = new EditarDetalleFragment();
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
        View v = inflater.inflate(R.layout.fragment_editar_detalle, container, false);

        helper=new BD(getContext());
        montoEdit=v.findViewById(R.id.inputMontoEdit);
        numHoraEdit=v.findViewById(R.id.inputHorasAsigadasEdit);
        observacionesEdit = v.findViewById(R.id.inputObservacionesEdit);
        benDEdit=v.findViewById(R.id.inputBenDirecEdit);
        benIEdit=v.findViewById(R.id.inputBenIndirecEdit);
        estadoEdit=v.findViewById(R.id.estadoServicioEdit);
        btnEditResumen=v.findViewById(R.id.btnEditarDetalleResumen);

        ArrayAdapter<CharSequence> adapterEstadoEdit = ArrayAdapter.createFromResource(getContext(),R.array.combo_estadoServicio, android.R.layout.simple_spinner_item);
        estadoEdit.setAdapter(adapterEstadoEdit);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        System.out.println("Hola");
        if(bundle!= null){
            DetalleResumenServicio detalleResumenServicio = bundle.getParcelable("DETALLERESUMENSERVICIO");
            System.out.println("Hola2");
            estadoEdit.setText(detalleResumenServicio.getEstadoDeResumen());
            montoEdit.setText(String.valueOf(detalleResumenServicio.getMonto()));
            numHoraEdit.setText(String.valueOf(detalleResumenServicio.getHorasAsignadas()));
            benDEdit.setText(String.valueOf(detalleResumenServicio.getBeneficiariosDirectos()));
            benIEdit.setText(String.valueOf(detalleResumenServicio.getBeneficiariosIndirectos()));
            observacionesEdit.setText(detalleResumenServicio.getObservaciones());

            btnEditResumen.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String regAfectados;
                    helper.abrir();
                    detalleResumenServicio.getIdDetalleResumen();
                    detalleResumenServicio.setEstadoDeResumen(estadoEdit.getText().toString());
                    detalleResumenServicio.setObservaciones(observacionesEdit.getText().toString());
                    detalleResumenServicio.setHorasAsignadas(Integer.valueOf(numHoraEdit.getText().toString()));
                    detalleResumenServicio.setBeneficiariosDirectos(Integer.valueOf(benDEdit.getText().toString()));
                    detalleResumenServicio.setBeneficiariosIndirectos(Integer.valueOf(benIEdit.getText().toString()));
                    detalleResumenServicio.setMonto(Double.valueOf(montoEdit.getText().toString()));
                    regAfectados = helper.actualizarDetalle(detalleResumenServicio);
                    helper.cerrar();
                    Toast.makeText(getContext(),regAfectados,Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(getContext(),"Problemas al cargar los datos :(", Toast.LENGTH_SHORT).show();
        }
    }
}