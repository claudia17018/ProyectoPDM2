package com.example.proyectopdm;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
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
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.proyectopdm.adaptadores.AdaptadorBitacora;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Bitacora;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MiServicioSocialFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MiServicioSocialFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    //CREAR BITACORA
    BD helper;
    private Context context;
    TextInputEditText textInputEditTextAnio;
    AutoCompleteTextView autoCompleteTextViewCiclo, autoCompleteTextViewMes;
    DT dt;
    int idEP;
    ArrayList<Bitacora> listadoBitacora;
    BD.DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    AdaptadorBitacora adaptadorBitacora;
    RecyclerView recyclerView;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MiServicioSocialFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MiServicioSocialFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MiServicioSocialFragment newInstance(String param1, String param2) {
        MiServicioSocialFragment fragment = new MiServicioSocialFragment();
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
        View vista = inflater.inflate(R.layout.fragment_mi_servicio_social, container, false);
        helper = new BD(getContext());
        dataBaseHelper = new BD.DataBaseHelper(getContext());
        listadoBitacora = new ArrayList<>();
        recyclerView = (RecyclerView) vista.findViewById(R.id.recyclerViewListBitacora);
        listadoBitacora = consultarListadoBitacora();

        adaptadorBitacora = new AdaptadorBitacora(listadoBitacora,getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adaptadorBitacora);
        
        return vista;
    }

    private ArrayList<Bitacora> consultarListadoBitacora() {
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        idEP=loadPreferencias();
        String[] idEPr= {String.valueOf(idEP)};
        Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM BITACORA WHERE IDESTUDIANTEPROYECTO =?",idEPr);
        ArrayList<Bitacora> listadoBitacora = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listadoBitacora.add(new Bitacora(cursor.getInt(0), cursor.getInt(2),cursor.getString(3),cursor.getInt(4)));
            } while (cursor.moveToNext());

            }
        cursor.close();
        return listadoBitacora;
        }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FloatingActionButton btn1 = view.findViewById(R.id.fabAgegarBitacora);

        adaptadorBitacora.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(),"seleccion" + listadoBitacora.get(recyclerView.getChildAdapterPosition(view)).getIdBitacora(),Toast.LENGTH_SHORT).show();
                //Navigation.findNavController(view).navigate(R.id.actividadCrearFragment);
                Bundle datosBitacora = new Bundle();
                datosBitacora.putParcelable("BITACORA",listadoBitacora.get(recyclerView.getChildAdapterPosition(view)));
                adaptadorBitacora.idBitacora =(listadoBitacora.get(recyclerView.getChildAdapterPosition(view)).getIdBitacora());
                Navigation.findNavController(view).navigate(R.id.listaActividadesFragment,datosBitacora);
            }
        });

        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // Navigation.findNavController(view).navigate(R.id.agregarBitacoraFragment);

                MaterialAlertDialogBuilder builder1 = new MaterialAlertDialogBuilder(getContext());
                //MaterialAlertDialogBuilder(context)
                builder1.setTitle("Editar Bitacora");
                builder1.setMessage(getContext().getString(R.string.long_message));
                LayoutInflater inflater = LayoutInflater.from(getContext());
                View v = inflater.inflate(R.layout.fragment_agregar_bitacora, null);

                textInputEditTextAnio = v.findViewById(R.id.inputAnioBit);
                autoCompleteTextViewCiclo = v.findViewById(R.id.actCiclo);
                autoCompleteTextViewMes = v.findViewById(R.id.actMeses);
                EditText idestudiante = v.findViewById(R.id.inputEstP);

                ArrayAdapter<CharSequence> adapterMeses = ArrayAdapter.createFromResource(getContext(),R.array.combo_meses, android.R.layout.simple_spinner_item);
                autoCompleteTextViewMes.setAdapter(adapterMeses);

                ArrayAdapter<CharSequence> adapterCiclo = ArrayAdapter.createFromResource(getContext(),R.array.combo_ciclo, android.R.layout.simple_spinner_item);
                autoCompleteTextViewCiclo.setAdapter(adapterCiclo);
                builder1.setView(v);
                builder1.setNegativeButton(getContext().getString(R.string.decline), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                });
                // Respond to negative button press
                builder1.setPositiveButton(getContext().getString(R.string.accept), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(autoCompleteTextViewCiclo.getText().toString().isEmpty() || autoCompleteTextViewMes.getText().toString().isEmpty() ||textInputEditTextAnio.getText().toString().isEmpty()) {
                            Toast.makeText(getContext(),getString(R.string.camposVacios), Toast.LENGTH_SHORT).show();
                        }
                        else{
                            insertarBitacora(v);
                            limpiarInput();
                        }
                    }
                });
                // Respond to positive button press
                builder1.show();
            }
        });
    }

    public void insertarBitacora(View v){
        loadPreferencias();
        String regInsertados;
        Integer ciclo = Integer.valueOf(autoCompleteTextViewCiclo.getText().toString());
        String mes = autoCompleteTextViewMes.getText().toString();
        Integer anio = Integer.valueOf(textInputEditTextAnio.getText().toString());

        dt=new DT();
        helper.abrir();
        dt=helper.activo();
        Integer idEstudianteProyecto = loadPreferencias();
        Bitacora bitacora = new Bitacora();
        bitacora.setIdEstudianteProyecto(idEstudianteProyecto);
        bitacora.setCiclo(ciclo);
        bitacora.setMes(mes);
        bitacora.setAnio(anio);
        helper.abrir();
        regInsertados =  helper.insertarBitacora(bitacora);
        helper.cerrar();
        Toast.makeText(getContext(),regInsertados, Toast.LENGTH_SHORT).show();

    }

    private void limpiarInput(){
        autoCompleteTextViewCiclo.setText("");
        textInputEditTextAnio.setText("");
        autoCompleteTextViewMes.setText("");
    }

    public Integer loadPreferencias(){
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Usuario", Context.MODE_PRIVATE);
        String carnet = sharedPreferences.getString("USUARIO","0");
        helper.abrir();
        int id= helper.recuperarIdEstudiante(carnet);
        helper.cerrar();
        return id;
    }
}
