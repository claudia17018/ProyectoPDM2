package com.example.proyectopdm;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.proyectopdm.bd.BD;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ProyectosAsignadosFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProyectosAsignadosFragment extends Fragment {
    BD db;
    TextView tex;
    DT dt;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ProyectosAsignadosFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ProyectosAsignadosFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ProyectosAsignadosFragment newInstance(String param1, String param2) {
        ProyectosAsignadosFragment fragment = new ProyectosAsignadosFragment();
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
        View v= inflater.inflate(R.layout.fragment_proyectos_asignados, container, false);
        tex =(TextView) v.findViewById(R.id.pro);
        db=new BD(getContext());
        dt=new DT();
        db.abrir();
        dt=db.activo();
        tex.setText(Integer.toString(dt.getIdU()));
        db.cerrar();

        // Inflate the layout for this fragment
        return v;
    }

}