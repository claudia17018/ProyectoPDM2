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
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditarAreaFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditarAreaFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";


    TextInputEditText txtNomEArea, txtDesEditArea;
    Button btnEditArea;
    BD helper;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditarAreaFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditarAreaFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static EditarAreaFragment newInstance(String param1, String param2) {
        EditarAreaFragment fragment = new EditarAreaFragment();
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
        View v = inflater.inflate(R.layout.fragment_editar_area, container, false);

        btnEditArea = v.findViewById(R.id.btnEdit2Area);
        helper = new BD(getContext());

        txtNomEArea =(TextInputEditText) v.findViewById(R.id.textEditNomArea);
        txtDesEditArea= (TextInputEditText) v.findViewById(R.id.textEditAreaDes);


        return v;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if(bundle!= null){
            Area area = bundle.getParcelable("AREA");


            txtNomEArea.setText(String.valueOf(area.getNomArea()));
            txtDesEditArea.setText(String.valueOf(area.getDesArea()));


            btnEditArea.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String regAfectados;
                    helper.abrir();


                    area.setNomArea(txtNomEArea.getText().toString());
                    area.setDesArea(txtDesEditArea.getText().toString());

                    regAfectados = helper.actualizarArea(area);
                    helper.cerrar();
                    Toast.makeText(getContext(),regAfectados,Toast.LENGTH_SHORT).show();
                }
            });

        }else{
            Toast.makeText(getContext(),"Problemas al cargar los datos :(", Toast.LENGTH_SHORT).show();
        }
    }
}