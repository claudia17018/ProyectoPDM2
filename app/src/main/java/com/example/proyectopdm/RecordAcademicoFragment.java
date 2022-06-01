package com.example.proyectopdm;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.proyectopdm.adaptadorl.AdaptadorCarrera;
import com.example.proyectopdm.adaptadorl.AdaptadorRecord;
import com.example.proyectopdm.bd.BD;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link RecordAcademicoFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class RecordAcademicoFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Record> listadoRecord;
    BD.DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    AdaptadorRecord adaptadorRecord;
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecordAcademicoFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordAcademicoFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordAcademicoFragment newInstance(String param1, String param2) {
        RecordAcademicoFragment fragment = new RecordAcademicoFragment();
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
        View vista = inflater.inflate(R.layout.fragment_record_academico, container, false);

        dataBaseHelper = new BD.DataBaseHelper(getContext());
        listadoRecord = new ArrayList<>();
        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerViewListRecord);
        listadoRecord = consultarListadoRecord();

        adaptadorRecord= new AdaptadorRecord(listadoRecord,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorRecord);

        return vista;
    }

    private ArrayList<Record> consultarListadoRecord() {
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();

        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM RECORACADEMICO",null);
        ArrayList<Record> listadoRecord = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listadoRecord.add(new Record(cursor.getInt(0),cursor.getInt(1), cursor.getString(2),cursor.getInt(3),cursor.getDouble(4),cursor.getDouble(5),cursor.getDouble(6)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return listadoRecord;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton btn1 = view.findViewById(R.id.btnAgregarRecord);
        adaptadorRecord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"" + listadoRecord.get(recyclerView.getChildAdapterPosition(v)).getIdRecord(),Toast.LENGTH_SHORT).show();
            }
        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Navigation.findNavController(v).navigate(R.id.crearRecordFragment);
            }
        });


    }
}