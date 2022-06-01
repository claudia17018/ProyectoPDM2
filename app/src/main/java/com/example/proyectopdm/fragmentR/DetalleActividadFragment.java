package com.example.proyectopdm.fragmentR;

import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.example.proyectopdm.R;
import com.example.proyectopdm.adaptadores.AdaptadorActividad;
import com.example.proyectopdm.bd.BD;
import com.example.proyectopdm.entidades.Actividad;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;



/**
 * A simple {@link Fragment} subclass.
 * Use the {@link DetalleActividadFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DetalleActividadFragment extends Fragment {
    Actividad act;
    TextView id, nombre, horas, descripcion, fecha;
    ImageView menupopUp;
    BD helper;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public DetalleActividadFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment DetalleActividadFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DetalleActividadFragment newInstance(String param1, String param2) {
        DetalleActividadFragment fragment = new DetalleActividadFragment();
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
        View v = inflater.inflate(R.layout.fragment_detalle_actividad, container, false);

        id = v.findViewById(R.id.textView);
        nombre = v.findViewById(R.id.textView2);
        descripcion = v.findViewById(R.id.textDescription);
        fecha = v.findViewById(R.id.textFecha);
        horas = v.findViewById(R.id.texthoras);
        helper = new BD(getContext());
        menupopUp = v.findViewById(R.id.menuOpcionesActivity);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Bundle bundle = this.getArguments();
        if (bundle != null) {
            act = bundle.getParcelable("ACTIVIDAD");
            id.setText(String.valueOf(act.getIdActividad()));
            nombre.setText(act.getNombreActividad());
            descripcion.setText(act.getDescripcionTipoActividad());
            fecha.setText(act.getFechaActividad());
            horas.setText(String.valueOf(act.getNumHorasActividades()));
        }

        menupopUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PopupMenu popupMenu = new PopupMenu(getContext(),view);
                popupMenu.getMenuInflater().inflate(R.menu.menu_opciones_bitacora,popupMenu.getMenu());
                popupMenu.show();

                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem menuItem) {

                        switch (menuItem.getItemId()){
                            case R.id.editBitacora:
                                Bundle datosAEditar = new Bundle();
                                datosAEditar.putParcelable("ACTIVIDAD",act);
                                //Toast.makeText(context, "Editar Actividad",Toast.LENGTH_SHORT).show();
                                Navigation.findNavController(view).navigate(R.id.editarActividadFragment,datosAEditar);
                                break;

                            case R.id.deleteBitacora:
                                MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(getContext());

                                builder.setTitle(getContext().getString(R.string.titleBitacora  ));
                                builder.setMessage(getContext().getString(R.string.messageBitacora));
                                builder.setNegativeButton(getContext().getString(R.string.buttonNegacion), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        dialogInterface.cancel();
                                    }
                                });
                                // Respond to negative button press
                                builder.setPositiveButton(getContext().getString(R.string.buttonConfirm), new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialogInterface, int i) {
                                        helper.abrir();
                                        int recDelete = helper.eliminarActividad(act.getIdActividad());
                                        helper.cerrar();
                                        if(recDelete==1){
                                            //adaptadorActividad.deleteItem(act.getIdActividad());
                                            Toast.makeText(getContext(),"Registros afectados" + recDelete,Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                                builder.show();
                                break;
                        }
                        return true;
                    }
                });
            }
        });
    }
}

