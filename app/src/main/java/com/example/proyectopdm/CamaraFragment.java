package com.example.proyectopdm;

import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;
import static android.app.Activity.RESULT_OK;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import static android.Manifest.permission.CAMERA;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CamaraFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CamaraFragment extends Fragment {

    public final String CARPETA_RAIZ = "misImagenesPrueba/";
    public final String RUTA_IMAGEN = CARPETA_RAIZ + "misFotos";

    final int COD_SELECCIONA = 10;
    final int COD_FOTO = 20;

    ImageView imagen;
    Button btnImagen;
    Button botncargar;
    Bitmap bitmap = null;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    String path;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CamaraFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CamaraFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CamaraFragment newInstance(String param1, String param2) {
        CamaraFragment fragment = new CamaraFragment();
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
        View v = inflater.inflate(R.layout.fragment_camara, container, false);
        imagen = (ImageView) v.findViewById(R.id.imagenId);
        btnImagen = v.findViewById(R.id.btnCargarImagen);

        if(validaPermisos()){
            btnImagen.setEnabled(true);
        }else{
            btnImagen.setEnabled(false);
        }

        btnImagen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cargarImagen();
            }
        });
        // Inflate the layout for this fragment
        return v;
    }

    public boolean validaPermisos() {
        if(Build.VERSION.SDK_INT <Build.VERSION_CODES.M){
            return true;
        }
        if((ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.CAMERA)== PackageManager.PERMISSION_GRANTED )&&
                (ActivityCompat.checkSelfPermission(getContext(),Manifest.permission.WRITE_EXTERNAL_STORAGE)==PackageManager.PERMISSION_GRANTED)){
            return true;
        }
        if((shouldShowRequestPermissionRationale(CAMERA))||
                (shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE))){
            cargarDialogoRecomendacion();
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},100);
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == 100){
            if(grantResults.length==2 && grantResults[0]==PackageManager.PERMISSION_GRANTED &&
            grantResults[1]==PackageManager.PERMISSION_GRANTED){
                btnImagen.setEnabled(true);
            }else{
                solicitarPermisosmanuales();
            }
        }
    }

    public void solicitarPermisosmanuales() {
        final CharSequence[] opciones = {"SI","NO"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("¿Desea configurar los permisos de forma manual?");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(opciones[which].equals("SI")){
                    Intent intent = new Intent();
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    Uri uri = Uri.fromParts("package", getActivity().getPackageName(), null);
                    intent.setData(uri);
                    startActivity(intent);
                }else{
                    Toast.makeText(getContext(), "Los permisos no fueron aceptados", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }
            }
        });
        alertOpciones.show();
    }

    public void cargarDialogoRecomendacion() {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(getContext());
        dialogo.setTitle("Permiso Desactivados");
        dialogo.setMessage("Acepta las permisos >:v");

        dialogo.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA},100);
            }
        });
        dialogo.show();
    }

    public void cargarImagen(){

        final CharSequence[] opciones = {"Tomar Foto","Cargar Imagen","Cancelar"};
        final AlertDialog.Builder alertOpciones = new AlertDialog.Builder(getContext());
        alertOpciones.setTitle("Seleccione una opción");
        alertOpciones.setItems(opciones, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(opciones[which].equals("Tomar Foto")){
                    tomarFotografia();
                    //takePicture();
                    guardarImagen();
                }else{
                    if (opciones[which].equals("Cargar Imagen")){
                        Intent intent = new Intent(Intent.ACTION_GET_CONTENT, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        intent.setType("image/");
                        startActivityForResult(intent.createChooser(intent, "Seleccione la aplicacion"),COD_SELECCIONA);
                    }else{
                        dialog.dismiss();
                    }
                }
            }
        });
        alertOpciones.show();

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK){

            switch (requestCode){
                case COD_SELECCIONA:
                    Uri miPath = data.getData();
                    imagen.setImageURI(miPath);
                    break;
                case COD_FOTO:

                    MediaScannerConnection.scanFile(getContext(), new String[]{path}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String s, Uri uri) {
                                    Log.i("Ruta de almacenamiento","path: "+path);
                                }
                            });
                    //Bitmap bitmap = BitmapFactory.decodeFile(path);
                    //imagen.setImageBitmap(bitmap);
                    Bitmap bitmap = (Bitmap) data.getExtras().get(String.valueOf(data));


                    break;
            }


        }
    }
    public void tomarFotografia(){
        File fileImagen = new File(Environment.getExternalStorageDirectory(),RUTA_IMAGEN);
        boolean isCreada = fileImagen.exists();
        String nombreImagen="";

        if(isCreada==false){
            isCreada=fileImagen.mkdir();
        }
        if(isCreada==true){
            nombreImagen = (System.currentTimeMillis()/1000)+".jpeg";

        }


        /******************/
        path = Environment.getExternalStorageDirectory()+File.separator+
                RUTA_IMAGEN+File.separator+nombreImagen;
        File imagen = new File(path);
        /******************/

        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(imagen));
        //startActivityForResult(intent, COD_FOTO);
        if(intent.resolveActivity(getActivity().getPackageManager())!= null){
            startActivityForResult(intent, COD_FOTO);
        }

    }
    public void guardarImagen(){

        OutputStream fos = null;
        File file = null;


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q){
            ContentResolver resolver = getActivity().getContentResolver();
            ContentValues values = new ContentValues();

            String nombreImagen = (System.currentTimeMillis()/100)+".jpeg";

            values.put(MediaStore.Images.Media.DISPLAY_NAME, nombreImagen);
            values.put(MediaStore.Images.Media.MIME_TYPE,"image/jpg");
            values.put(MediaStore.Images.Media.RELATIVE_PATH,"Pictures/MyApp");
            values.put(MediaStore.Images.Media.IS_PENDING,1);

            Uri collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY);
            Uri imageUri = resolver.insert(collection, values);

            try {
                fos = resolver.openOutputStream(imageUri);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }
            values.clear();
            values.put(MediaStore.Images.Media.IS_PENDING,0);
            resolver.update(imageUri,values,null,null);
        }else {
            String imageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString();
            String nombreImagen = System.currentTimeMillis()+".jpeg";

            file = new File(imageDir,nombreImagen);
            try {
                fos = new FileOutputStream(nombreImagen);
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }

        }

        boolean saved;

        bitmap.compress(Bitmap.CompressFormat.JPEG,100, fos);
        /*if(saved){
            Toast.makeText(getContext(),"Guardado!", Toast.LENGTH_SHORT).show();
        }*/
        if(fos!=null){
            try {
                fos.flush();
                fos.close();
            }catch (IOException e){
                e.printStackTrace();
            }
        }

        if(file != null){//api < 29
            MediaScannerConnection.scanFile(getContext(), new String[]{file.toString()},null,null);
        }
    }

    public void takePicture(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getActivity().getPackageManager())!= null){
            startActivityForResult(intent, COD_FOTO);
        }
    }
}