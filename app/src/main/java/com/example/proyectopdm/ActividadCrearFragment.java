package com.example.proyectopdm;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Actividad;
import com.example.proyectopdm.entidades.Bitacora;
import com.example.proyectopdm.entidades.DetalleBitacora;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ActividadCrearFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ActividadCrearFragment extends Fragment {

    TextInputEditText nombre, descripcion, horas;
    TextInputEditText fecha;
    TextInputLayout date;
    Bitacora bitacora;
    BD helper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public ActividadCrearFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ActividadCrearFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ActividadCrearFragment newInstance(String param1, String param2) {
        ActividadCrearFragment fragment = new ActividadCrearFragment();
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
        View v = inflater.inflate(R.layout.fragment_actividad_crear, container, false);
        helper = new BD(getContext());
        nombre = v.findViewById(R.id.inputNombreActividad);
        fecha = v.findViewById(R.id.inputFechaActividad);
        horas = v.findViewById(R.id.inputNumHorasActividad);
        descripcion = v.findViewById(R.id.inputDescripcionActividad);
        date  = v.findViewById(R.id.fechaActividad);

        Bundle bundle = getArguments();

        if (bundle != null) {
            bitacora = bundle.getParcelable("BITACORA");
            System.out.println(bitacora);

        }else{
            Toast.makeText(getContext(),"Fallo al recuperar objeto",Toast.LENGTH_SHORT).show();
        }

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnAgregar = view.findViewById(R.id.btnAgregarActividad);

        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT-6"));
        calendar.clear();

        long hoy = MaterialDatePicker.todayInUtcMilliseconds();

        calendar.setTimeInMillis(hoy);

        calendar.set(Calendar.MONTH,Calendar.JANUARY);
        long january = calendar.getTimeInMillis();

        calendar.set(Calendar.MONTH,Calendar.DECEMBER);
        long december = calendar.getTimeInMillis();

        CalendarConstraints.Builder constraintBuilder = new CalendarConstraints.Builder();
        constraintBuilder.setValidator(DateValidatorPointForward.now());

        MaterialDatePicker.Builder builder = MaterialDatePicker.Builder.datePicker();
        builder.setTitleText(getString(R.string.seleccionFecha));
        builder.setSelection(hoy);
        builder.setCalendarConstraints(constraintBuilder.build());
        final  MaterialDatePicker<Long> materialDatePicker = builder.build();

        date.setStartIconOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                materialDatePicker.show(getActivity().getSupportFragmentManager(), "Fecha");
            }
        });

        //AGREGAR NUEVA ACTIVIDADD
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(nombre.getText().toString().isEmpty() || fecha.getText().toString().isEmpty() ||horas.getText().toString().isEmpty()|| descripcion.getText().toString().isEmpty()) {
                    Toast.makeText(getContext(),getString(R.string.camposVacios), Toast.LENGTH_SHORT).show();
                }else{
                insertarActividad(view);
                limpiarInput();
                insertarDetalleBitacora(view);
                }
            }
        });

        materialDatePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener() {
            @Override
            public void onPositiveButtonClick(Object selection) {

                Date date = new Date((Long) selection);
                System.out.println(date);
                try {
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    fecha.setText(format.format(date));
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        });
    }

    private void limpiarInput() {
        nombre.setText("");
        fecha.setText("");
        horas.setText("");
        descripcion.setText("");
    }

    public void insertarActividad(View view){
        String regInsertados;
        String nombreAct = nombre.getText().toString();
        String description = descripcion.getText().toString();
        String fechaAct = fecha.getText().toString();
        Integer numHoras = Integer.valueOf(horas.getText().toString());

        Actividad actividad = new Actividad();
        actividad.setNombreActividad(nombreAct);
        actividad.setDescripcionTipoActividad(description);
        actividad.setFechaActividad(fechaAct);
        actividad.setNumHorasActividades(numHoras);
        helper.abrir();
        regInsertados =  helper.insertarActividad(actividad);
        helper.cerrar();
        Toast.makeText(getContext(),regInsertados, Toast.LENGTH_SHORT).show();
    }

    public  void insertarDetalleBitacora(View v){
        String registroInsertado;
        helper.abrir();
        DetalleBitacora detalleBitacora = new DetalleBitacora();
        int idBit = bitacora.getIdBitacora();
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        detalleBitacora.setIdBitacora(idBit);
        detalleBitacora.setFechaCreacion(date);
        registroInsertado = helper.insertarDetalleBitacora(detalleBitacora);
        helper.cerrar();
        Toast.makeText(getContext(),"Se agrego detalle bitacora" + registroInsertado,Toast.LENGTH_SHORT).show();

    }
}