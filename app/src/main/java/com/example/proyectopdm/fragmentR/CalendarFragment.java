package com.example.proyectopdm.fragmentR;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectopdm.CalendarAdapter;
import com.example.proyectopdm.R;
import com.example.proyectopdm.adaptadores.AdaptadorActividad;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Actividad;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CalendarFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CalendarFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    ArrayList<Actividad> listadoActividad;
    String date;
    Date date1;
    BD.DataBaseHelper dataBaseHelper;
    SQLiteDatabase sqLiteDatabase;
    CalendarAdapter adaptadorActividad;
    RecyclerView recyclerViewActividades;
    CalendarView calendarView;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CalendarFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CalendarFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CalendarFragment newInstance(String param1, String param2) {
        CalendarFragment fragment = new CalendarFragment();
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
        View v=inflater.inflate(R.layout.fragment_calendar, container, false);
        calendarView=v.findViewById(R.id.calendarView2);
        dataBaseHelper = new BD.DataBaseHelper(getContext());
        listadoActividad = new ArrayList<>();
        recyclerViewActividades = (RecyclerView) v.findViewById(R.id.list_itemActividades);
        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView calendarView, int i, int i1, int i2) {
                if(i1<10){
                    if(i2<10){
                        date = "0"+i2+"/"+"0"+(i1+1)+"/"+i;
                    }
                    else{
                        date = i2+"/"+"0"+(i1+1)+"/"+i;
                    }
                }

                try {
                    date1=new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    Toast.makeText(getContext(),date,Toast.LENGTH_SHORT).show();
                    listadoActividad = listadoActividades();
                    adaptadorActividad = new CalendarAdapter(listadoActividad,getContext());
                    recyclerViewActividades.setLayoutManager(new LinearLayoutManager(getContext()));
                    recyclerViewActividades.setAdapter(adaptadorActividad);

                    Bundle actEdit = new Bundle();

                    adaptadorActividad.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Toast.makeText(getContext(),""+listadoActividad.get(recyclerViewActividades.getChildAdapterPosition(view)).getNombreActividad(),Toast.LENGTH_SHORT).show();
                            actEdit.putParcelable("ACTIVIDAD",listadoActividad.get(recyclerViewActividades.getChildAdapterPosition(view)));
                            Navigation.findNavController(view).navigate(R.id.detalleActividadFragment,actEdit);
                        }
                    });

                } catch (ParseException e) {
                    Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private ArrayList<Actividad> listadoActividades() {
        sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        String[] fecha={date};
        Cursor cursor = sqLiteDatabase.rawQuery("Select * from ACTIVIDAD as a\n" +
                "WHERE a.FECHAACTIVIDAD = ?", new String[]{date});
        ArrayList<Actividad> listadoActividad = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listadoActividad.add(new Actividad(cursor.getInt(0),cursor.getString(1),cursor.getString(2),cursor.getString(3),cursor.getInt(4)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return listadoActividad;
    }

}