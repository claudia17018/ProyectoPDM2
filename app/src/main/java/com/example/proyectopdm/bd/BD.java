package com.example.proyectopdm.bd;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;
import android.provider.Settings;

import com.example.proyectopdm.AccesoUsuario;
import com.example.proyectopdm.Area;
import com.example.proyectopdm.Carrera;
//import androidx.room.Update;

import com.example.proyectopdm.DT;
import com.example.proyectopdm.DetalleServicio;
import com.example.proyectopdm.Proyecto;
import com.example.proyectopdm.Record;
import com.example.proyectopdm.ResumenServicio;
import com.example.proyectopdm.Usuario;
import com.example.proyectopdm.docente.Docente;
import com.example.proyectopdm.docente.Estudiante;
import com.example.proyectopdm.docente.OpcionCrud;
import com.example.proyectopdm.entidades.Actividad;
import com.example.proyectopdm.entidades.Bitacora;
import com.example.proyectopdm.entidades.DetalleBitacora;
import com.example.proyectopdm.entidades.DetalleResumenServicio;
import com.example.proyectopdm.entidades.EstudianteProyecto;
import com.example.proyectopdm.entidades.ResumenServicioSocial;

import java.util.ArrayList;
import java.util.List;

public class BD {
    private static final String[]usuario = new String []{"usuario","contrasena"};
    private static final String[]carrera=new String[]{"idArea,NombreCarrera,NombreEscuela"};
    private static final String[] camposDT =new String[]{"IDDT","IDUSER"};
    private final Context context;
    private  String recibirMensaje ="";
    ArrayList<Usuario> lista;
    ArrayList<Docente> listaDocentes = new ArrayList<>();
    private SQLiteDatabase db;
    private DataBaseHelper DBHelper;
    //String db="BD";
    //Secuencia sql
    // String tabla = "create table if not exists usuario (id integer primary key autoincrement, usuario text, contraseña text)";

    public BD(Context ctx){
        this.context=ctx;
        DBHelper=new DataBaseHelper(context);
    }

    public List<Docente> getAllDocentes(){
        ArrayList<Docente> listaDocentes =new ArrayList<Docente>();
        listaDocentes.clear();
        Cursor cursor = db.rawQuery("select * from docente", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                Docente docente= new Docente();
                docente.setNombreTutor(cursor.getString(2));
                docente.setEmailTutor(cursor.getString(4));

                listaDocentes.add(docente);
            }while (cursor.moveToNext());
        }
        return listaDocentes;
    }

    public static class DataBaseHelper extends SQLiteOpenHelper{
        private static final String Base_Datos= "Base.s3db";
        private static final int VERSION =1;
        public DataBaseHelper(Context context){
            super(context,Base_Datos,null,VERSION);
        }
        DataBaseHelper base;
        @Override
        public void onCreate(SQLiteDatabase db) {
            try {
                db.execSQL("create table USUARIO \n" +
                        "(\n" +
                        "   IDUSUARIO            INTEGER              not null,\n" +
                        "   NOMBREUSUARIO        VARCHAR2(30)         not null,\n" +
                        "   CLAVE                CHAR(5)              not null,\n" +
                        "   constraint PK_USUARIO primary key (IDUSUARIO)\n" +
                        ")");
                db.execSQL("create table ACCESOUSUARIO \n" +
                        "(\n" +
                        "   IDOPTION             INTEGER              not null,\n" +
                        "   IDUSUARIO            INTEGER              not null,\n" +
                        "   constraint PK_ACCESOUSUARIO primary key (IDOPTION, IDUSUARIO)\n" +
                        ")");
                db.execSQL("create table ACTIVIDAD \n" +
                        "(\n" +
                        "   IDACTIVIDAD          INTEGER              not null,\n" +
                        "   NOMBREACTIVIDAD      VARCHAR2(100)        not null,\n" +
                        "   DESCRIPCIONTIPOACTIVIDAD VARCHAR2(200)        not null,\n" +
                        "   FECHAACTIVIDAD       DATE                 not null,\n" +
                        "   NUMHORASACTIVIDAD    INTEGER              not null,\n" +
                        "   constraint PK_ACTIVIDAD primary key (IDACTIVIDAD)\n" +
                        ")");
                db.execSQL("create table AREACARRERA \n" +
                        "(\n" +
                        "   IDAREA               INTEGER              not null,\n" +
                        "   DESCRIPCIONAREA      VARCHAR2(60),\n" +
                        "   NOMBREAREA           VARCHAR2(40)         not null,\n" +
                        "   constraint PK_AREACARRERA primary key (IDAREA)\n" +
                        ")");
                db.execSQL("CREATE TABLE BITACORA \n" +
                        "(\n" +
                        "    IDBITACORA INTEGER NOT NULL CONSTRAINT PK_BITACORA PRIMARY KEY AUTOINCREMENT,\n" +
                        "    IDESTUDIANTEPROYECTO INTEGER NOT NULL,\n" +
                        "    CICLO INTEGER NOT NULL,\n" +
                        "    MES VARCHAR2(30) NOT NULL,\n" +
                        "    ANIO INTEGER NOT NULL\n" +
                        ")");
                db.execSQL("create table CARRERA \n" +
                        "(\n" +
                        "   IDCARRERA            INTEGER              not null,\n" +
                        "   IDAREA               INTEGER,\n" +
                        "   NOMBREESCUELA        VARCHAR2(40)         not null,\n" +
                        "   NOMBRECARRERA        VARCHAR2(40),\n" +
                        "   constraint PK_CARRERA primary key (IDCARRERA)\n" +
                        ")");
                db.execSQL("create table CATEGORIA \n" +
                        "(\n" +
                        "   IDCATEGORIA          INTEGER              not null,\n" +
                        "   NOMBRECATEGORIA      VARCHAR2(50)         not null,\n" +
                        "   constraint PK_CATEGORIA primary key (IDCATEGORIA),\n" +
                        "   constraint AK_IDENTIFIER_2_CATEGORI unique (IDCATEGORIA)\n" +
                        ")");
                db.execSQL("create table DETALLEBITACORA \n" +
                        "(\n" +
                        "   IDDETALLEBITACORA    INTEGER              not null,\n" +
                        "   IDACTIVIDAD          INTEGER,\n" +
                        "   IDBITACORA           INTEGER,\n" +
                        "   FECHACREACION        DATE                 not null,\n" +
                        "   constraint PK_DETALLEBITACORA primary key (IDDETALLEBITACORA)\n" +
                        ")");
                db.execSQL("create table DETALLERESUMENSERVICIO \n" +
                        "(\n" +
                        "   IDDETALLERESUMEN     INTEGER              not null,\n" +
                        "   IDRESUMEN            INTEGER,\n" +
                        "   HORASASIGNADAS       NUMBER               not null,\n" +
                        "   MONTO                REAL               not null,\n" +
                        "   BENEFICIARIOSDIRECTOS INTEGER              not null,\n" +
                        "   BENEFICIARIOSINDIRECTOS INTEGER              not null,\n" +
                        "   OBSERVACIONES        VARCHAR2(200)        not null,\n" +
                        "   ESTADODERESUMEN      VARCHAR2(50)         not null,\n" +
                        "   constraint PK_DETALLERESUMENSERVICIO primary key (IDDETALLERESUMEN)\n" +
                        ")");
                db.execSQL("create table DOCENTE \n" +
                        "(\n" +
                        "   IDDOCENTE            INTEGER              not null,\n" +
                        "   DUITUTOR             VARCHAR2(10)         not null,\n" +
                        "   IDUSUARIO            INTEGER,\n" +
                        "   NOMBRETUTOR          VARCHAR2(80)         not null,\n" +
                        "   APELLIDOTUTOR        VARCHAR2(80)         not null,\n" +
                        "   EMAILTUTOR           VARCHAR2(30)         not null,\n" +
                        "   TELEFONOTUTOR        VARCHAR2(15)         not null,\n" +
                        "   NITDOCENTE           CHAR(18)             not null,\n" +
                        "   constraint PK_DOCENTE primary key (IDDOCENTE)\n" +
                        ")");
                db.execSQL("create table ESTUDIANTE \n" +
                        "(\n" +
                        "   CARNET               CHAR(10)             not null,\n" +
                        "   IDUSUARIO            INTEGER,\n" +
                        "   NOMBREESTUDIANTE     VARCHAR2(30)         not null,\n" +
                        "   APELLIDOESTUDIANTE   VARCHAR2(30)         not null,\n" +
                        "   TELEFONO             CHAR(20)             not null,\n" +
                        "   EMAIL                VARCHAR2(30)         not null,\n" +
                        "   DUI                  CHAR(15),\n" +
                        "   DOMICILIO            VARCHAR2(40)         not null,\n" +
                        "   NITESTUDIANTE        VARCHAR2(20)         not null,\n" +
                        "   constraint PK_ESTUDIANTE primary key (CARNET)\n" +
                        ")");
                db.execSQL("create table ESTUDIANTEPROYECTO \n" +
                        "(\n" +
                        "   IDESTUDIANTEPROYECTO INTEGER              not null,\n" +
                        "   CARNET               CHAR(10),\n" +
                        "   IDPROYECTO           INTEGER,\n" +
                        "   IDDETALLERESUMEN     INTEGER,\n" +
                        "   NUMEROTOTALHORASTRABAJADAS INTEGER              not null,\n" +
                        "   constraint PK_ESTUDIANTEPROYECTO primary key (IDESTUDIANTEPROYECTO)\n" +
                        ")");
                db.execSQL("create table MODALIDAD \n" +
                        "(\n" +
                        "   IDMODALIDAD          INTEGER              not null,\n" +
                        "   NOMBREMODALIDAD      VARCHAR2(50)         not null,\n" +
                        "   constraint PK_MODALIDAD primary key (IDMODALIDAD)\n" +
                        ")");
                db.execSQL("create table OPCIOCRUD \n" +
                        "(\n" +
                        "   IDOPTION             INTEGER              not null,\n" +
                        "   DESOPCION            VARCHAR2(30)         not null,\n" +
                        "   NUMCRUD              INTEGER              not null,\n" +
                        "   constraint PK_OPCIOCRUD primary key (IDOPTION)\n" +
                        ")");
                db.execSQL("create table PROYECTO \n" +
                        "(\n" +
                        "   IDPROYECTO           INTEGER              not null,\n" +
                        "   IDMODALIDAD          INTEGER,\n" +
                        "   DUITUTOR             VARCHAR2(10),\n" +
                        "   IDCATEGORIA          INTEGER,\n" +
                        "   NOMBREPROYECTO       VARCHAR2(50)         not null,\n" +
                        "   DESCRIPCIONPROYECTO  VARCHAR2(100)        not null,\n" +
                        "   LUGAR                VARCHAR2(100)        not null,\n" +
                        "   RESPONSABLEINSTITUCION VARCHAR2(50)         not null,\n" +
                        "   NUMHORASPROPUESTAS   INTEGER              not null,\n" +
                        "   NUMESTUDIANTESREQUERIDOS INTEGER              not null,\n" +
                        "   ESTADOPROYECTO       VARCHAR2(40)         not null,\n" +
                        "   constraint PK_PROYECTO primary key (IDPROYECTO)\n" +
                        ")");
                db.execSQL("create table RECORACADEMICO \n" +
                        "(\n" +
                        "   IDRECORD             INTEGER              not null,\n" +
                        "   IDCARRERA            INTEGER,\n" +
                        "   CARNET               CHAR(10),\n" +
                        "   UNIDADESVALORATIVAS  INTEGER              not null,\n" +
                        "   PROMEDIO             NUMBER               not null,\n" +
                        "   CUM                  NUMBER               not null,\n" +
                        "   PROGRESO             NUMBER               not null,\n" +
                        "   constraint PK_RECORACADEMICO primary key (IDRECORD)\n" +
                        ")");
                db.execSQL("create table RESUMENSERVICIOTOTAL \n" +
                        "(\n" +
                        "   IDRESUMEN            INTEGER              not null,\n" +
                        "   CARNET               CHAR(10),\n" +
                        "   IDDOCENTE            INTEGER              not null,\n" +
                        "   FECHAAPERTURAEXPEDIENTE DATE                 not null,\n" +
                        "   FECHAEMISIONCERTIFICADO DATE                 ,\n" +
                        "   constraint PK_RESUMENSERVICIOTOTAL primary key (IDRESUMEN)\n" +
                        ")");
                db.execSQL("create table DT \n" +
                        "(\n" +
                        "   IDDT          INTEGER              not null,\n" +
                        "   IDUSER        INTEGER                not null,\n" +
                        "   constraint PK_DT primary key (IDDT)\n" +
                        ")");
                db.execSQL("CREATE TRIGGER EstudianteUsuario\n" +
                        "AFTER INSERT ON USUARIO\n" +
                        " BEGIN\n" +
                        " UPDATE ESTUDIANTE SET IDUSUARIO = NEW.IDUSUARIO WHERE ESTUDIANTE.IDUSUARIO =0 ;\n" +
                        " END");

                db.execSQL("CREATE TRIGGER DocenteUsuario\n" +
                        "AFTER INSERT ON USUARIO\n" +
                        " BEGIN\n" +
                        " UPDATE DOCENTE SET IDUSUARIO = NEW.IDUSUARIO WHERE DOCENTE.IDUSUARIO =0 ;\n" +
                        " END");
                db.execSQL("CREATE TRIGGER AccesoUsuarioId\n" +
                        "AFTER INSERT ON USUARIO\n" +
                        " BEGIN\n" +
                        " UPDATE ACCESOUSUARIO SET IDUSUARIO = NEW.IDUSUARIO WHERE ACCESOUSUARIO.IDUSUARIO =0 ;\n" +
                        " END");
                db.execSQL("INSERT INTO OPCIOCRUD VALUES(1,'ADMIN',1); ");
                db.execSQL("INSERT INTO OPCIOCRUD VALUES(2,'DOCENTE',2); ");
                db.execSQL("INSERT INTO OPCIOCRUD VALUES(3,'ESTUDIANTE',3); ");

                db.execSQL("INSERT INTO MODALIDAD VALUES(1,'Presencial'); ");
                db.execSQL("INSERT INTO MODALIDAD VALUES(2,'Remoto'); ");
                db.execSQL("INSERT INTO MODALIDAD VALUES(3,'Semipresencial'); ");

                db.execSQL("INSERT INTO CATEGORIA VALUES(1,'Ayudantia'); ");
                db.execSQL("INSERT INTO CATEGORIA VALUES(2,'Proyecto'); ");
                db.execSQL("INSERT INTO CATEGORIA VALUES(3,'Pasantia social'); ");
                db.execSQL("INSERT INTO CATEGORIA VALUES(4,'Curso propedeutico'); ");



                db.execSQL("CREATE TRIGGER delete_actividad AFTER DELETE ON ACTIVIDAD\n" +
                        "FOR EACH ROW\n" +
                        " BEGIN\n" +
                        "    DELETE \n" +
                        "      FROM DETALLEBITACORA\n" +
                        "     WHERE DETALLEBITACORA.IDACTIVIDAD = old.IDACTIVIDAD;\n" +
                        "END");



            }catch (SQLException e){
                e.printStackTrace();
            }

        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int i, int i1) {

            db.execSQL("DROP TABLE USUARIO ");
            db.execSQL("DROP TABLE ACCESOUSUARIO ");
            db.execSQL("DROP TABLE ACTIVIDAD ");
            db.execSQL("DROP TABLE AREACARRERA ");
            db.execSQL("DROP TABLE BITACORA ");
            db.execSQL("DROP TABLE CARRERA ");
            db.execSQL("DROP TABLE CATEGORIA ");
            db.execSQL("DROP TABLE DETALLEBITACORA ");
            db.execSQL("DROP TABLE DETALLERESUMENSERVICIO ");
            db.execSQL("DROP TABLE DOCENTE ");
            db.execSQL("DROP TABLE ESTUDIANTE ");
            db.execSQL("DROP TABLE ESTUDIANTEPROYECTO ");
            db.execSQL("DROP TABLE MODALIDAD ");
            db.execSQL("DROP TABLE OPCIOCRUD ");
            db.execSQL("DROP TABLE PROYECTO ");
            db.execSQL("DROP TABLE RECORACADEMICO ");
            db.execSQL("DROP TABLE RESUMENSERVICIOTOTAL ");
            db.execSQL("DROP TABLE DT");
            db.execSQL("DROP TRIGGER delete_actividad");
            onCreate(db);
        }
    }
    public void abrir() throws SQLException{
        db = DBHelper.getWritableDatabase();
        return;
    }
    public void cerrar(){
        DBHelper.close();
    }

    public void llenarID(){
        Usuario user = new Usuario();
         if(insertUsuario(user) == true){

         }
    }

    public void llenarDB(){

        /*************Usuario*********************/
        final String[] VUnombre = {"Admin","Daniel","Roxana","Luis"};
        final String[] VUclave ={"1","1","1","1"};

        /*************Bitacora*********************/
        final int[] bCiclo={1,1,1,1};
        final int[] bid={1,2,1,2};
        final String[] bMes={"Junio","Junio","Junio","Junio"};
        final int[] banio={2022,2022,2022,2022};
        final int[] bsp={1,1,1,1};

        /*************Actividad*********************/
        final int[] aid={1,2,3,4};
        final String[] afecha={"01/05/2022","01/05/2022","02/05/2022","02/05/2022"};
        final String[] aa= {"Analisis","Diseno","Mockup","Pruebas"};
        final int[] ahoras={2,3,4,2};
        final String[] ades= {"Analisis","Diseno","Mockup","Pruebas"};

        /*************DetalleBitacora*********************/


        /************Carrera*********************/
        final int[] iIdArea={1,1,1};
        final String[] iNomCarrera={"Ingenieria en Sistemas Informaticos","Ingenieria Industrial","Ingenieria Quimica"};
        final String[] iNomEscuela={"Escuela de Ingenieria en Sistemas Informaticos","Escuela de Ingenieria Industrial","Escuela de Ingenieria Quimica"};


      /********** Area **************/
        final String[] iNomArea={"Facultad de Ingenieria y Arquitectura","Facultad de Agronomía","Facultad de Ciencias Naturales y Matemática"};
        final String[] iDesArea={"Es una institución formadora de profesionales competentes","Se capacita en las áreas agroalimentarias y el manejo sostenible ","Alcanzar la Excelencia Académica de la Facultad "};
        //Para OpcionCrud
        final String[] VdesOpcion = {"Administrador del sistema",
                "Docente y tutor del servicio","Estudiante del servicio social"};
        final int[] VnumCrud = {0, 1, 2};


        /*************Docente ****************/
        final int[] dIdUsuario={1,2};
        final String[] dDui={"6565656659","5454548484"};
        final String[] dNom={"Administrador","Daniel"};
        final String[] dApe={"Admin","Ayala"};
        final String[] dEma={"admin@gmail.com","ayala@gmail.com"};
        final String[] dTel={"75894223","78457912"};
        final String[] dNit={"95565659","4638484"};


        /*************Estudiante ****************/
        final int[] eIdUsuario={3,4};
        final String[] eCarne={"ML17025","LP13022"};
        final String[] eNom={"Roxana","Luis"};
        final String[] eApe={"Mendoza","Flores"};
        final String[] eEma={"ml17025@ues.edu.sv","lp13022@ues.edu.sv"};
        final String[] eTel={"62894223","68457912"};
        final String[] eNit={"95565659","4638484"};
        final String[] eDui={"015565659","094638484"};
        final String[] eDo={"San Salvador","Apopa"};

        /*************AccesoUsuario ****************/
        final int[] auIdop={1,2,3,3};
        final int[] auIus={1,2,3,4};

        /*************Total ****************/

        final String[] dtCarnet={"AB17018","ML17018"};
        final String[] dtDuiTu={"5454548484","5454548484"};
        final int[] dtId={1,2};
        final String[] dtFechaA={"01/01/22","05/02/22"};
        final String[] dtFechaC={"30/05/22","25/04/22"};

        /*************Detalle res ****************/
        final int[] drIdRes={1,2};
        final int[] drHa={500,400};
        final int[] drBD={10,4};
        final double[] drMonto={ 10.2, 20.21};
        final int[] drBI={2,1};
        final String[] drFI= {"10/11/2022","10/10/2022"};
        final String[] drFF= {"10/01/2022","10/12/2022"};
        final String[] drObser={"Cumplio con las horas establecidas","Llevar control de todas las actividades"};
        final String[] drEstado={"Aprobado","Pendiente"};

        /***************** Record ******************/

        final int[] recIdCar={1,2};
        final String[] recCarnet={"ML17025","LP13022"};
        final int[] recUv={140,120};
        final double[] recPro={8,6.5};
        final double[] recCum={8,7};
        final double[] recProg={80,65};


        /*alu.put("IDCARRERA",rec.getIdCarrera());
        alu.put("CARNET",rec.getCarnet());
        alu.put("UNIDADESVALORATIVAS",rec.getuV());
        alu.put("PROMEDIO",rec.getPromedio());
        alu.put("CUM",rec.getCum());
        alu.put("PROGRESO",rec.getProceso());*/



        /*****************PROYECTO ************************/


        final int[] proMo={1,2};
        final String[] proDui={"5454548484","5454548484"};
        final int[] proIdca={1,3};
        final String[] proNo={"Desarrollo de sistema medico","Desarrollo de sistema inventario"};
        final String[] proDes={"Se necesita un sistema para llevar un mejor control de los pacientes","El sistema debe de facilitar el control de los recursos con los que se cuenta"};
        final String[] proLu={"San Salvador","Apopa"};
        final String[] resPro={"Mauricio","Rud"};
        final int[] proHo={800,1000};
        final int[] proEs={5,8};
        final String[] estado={"Iniciado","Iniciado"};


       /************* LLENADO DE BASE DE DATOS************************/



        abrir();

        if(selectUsuarios().isEmpty()) {
            db.execSQL("DELETE FROM USUARIO");


            Usuario us = new Usuario();
            for (int i = 0; i < 4; i++) {
                us.setUsuario(VUnombre[i]);
                us.setContraseña(VUclave[i]);
                insertUsuario(us);
            }

            Carrera ca = new Carrera();
            for (int i = 0; i < 3; i++) {

                ca.setIdArea(iIdArea[i]);
                ca.setNomEscuela(iNomEscuela[i]);
                ca.setNomCarrera(iNomCarrera[i]);
                insertarCarrera(ca);
            }
            Area a = new Area();
            for (int i = 0; i < 3; i++) {

                a.setNomArea(iNomArea[i]);
                a.setDesArea(iDesArea[i]);
                insertarAreaCarrera(a);
            }

            Docente doce = new Docente();
            for (int i = 0; i < 2; i++) {

                doce.setIdUsuario(dIdUsuario[i]);
                doce.setNombreTutor(dNom[i]);
                doce.setApellidosTutor(dApe[i]);
                doce.setDuiTutor(dDui[i]);
                doce.setNitTutor(dNit[i]);
                doce.setEmailTutor(dEma[i]);
                doce.setTelefonoTutor(dTel[i]);
                insertar(doce);
            }
            Estudiante estu = new Estudiante();
            for (int i = 0; i < 2; i++) {

                estu.setIdUsuario(eIdUsuario[i]);
                estu.setNombreEstudiante(eNom[i]);
                estu.setApellidoEstudiante(eApe[i]);
                estu.setEmailEstudinate(eEma[i]);
                estu.setDuiEstudiante(eDui[i]);
                estu.setDomicilioEstudiante(eDo[i]);
                estu.setTelefono(eTel[i]);
                estu.setNitEstudiante(eNit[i]);
                estu.setCarnet(eCarne[i]);

                insertarEstudiante(estu);
            }

            AccesoUsuario au = new AccesoUsuario();
            for (int i = 0; i < 4; i++) {
                au.setIdOpcion(auIdop[i]);
                au.setIdUsuario(auIus[i]);
                insertarAccesoUsuario(au);
            }

            ResumenServicioSocial reSe = new ResumenServicioSocial();
            for (int i = 0; i < 2; i++) {
                reSe.setCarnet(dtCarnet[i]);
                reSe.setIdDocente(dtId[i]);
                reSe.setFechaAperturaExpediente(dtFechaA[i]);
                //reSe.setFechaCertificado(dtFechaC[i]);
                insertarDetalleResumen(reSe);
            }

            DetalleResumenServicio deSe = new DetalleResumenServicio();
            for (int i = 0; i < 2; i++) {
                deSe.setIdResumen(drIdRes[i]);
                deSe.setHorasAsignadas(drHa[i]);
                deSe.setBeneficiariosDirectos(drBD[i]);
                deSe.setBeneficiariosIndirectos(drBI[i]);
                deSe.setMonto(drMonto[i]);
               // deSe.setFechaInicio(drFI[i]);
                //deSe.setFechaFinal(drFF[i]);
                deSe.setObservaciones(drObser[i]);
                deSe.setEstadoDeResumen(drEstado[i]);
                insertarDetalleServicio(deSe);
            }
            Record rec = new Record();
            for (int i = 0; i < 2; i++) {
                rec.setIdCarrera(recIdCar[i]);
                rec.setCarnet(recCarnet[i]);
                rec.setuV(recUv[i]);
                rec.setPromedio(recPro[i]);
                rec.setCum(recCum[i]);
                rec.setProceso(recProg[i]);
                insertarRecord(rec);
            }
            Proyecto proy = new Proyecto();
            for (int i = 0; i < 2; i++) {
                proy.setDuiTu(proDui[i]);
                proy.setIdMod(proMo[i]);
                proy.setIdCat(proIdca[i]);
                proy.setNomPro(proNo[i]);
                proy.setDesPro(proDes[i]);
                proy.setLugar(proLu[i]);
                proy.setRespoIns(resPro[i]);
                proy.setNumHoras(proHo[i]);
                proy.setNumEst(proEs[i]);
                proy.setEstadoPro(estado[i]);


                insertarProyecto(proy);
            }

            Bitacora bitacora=new Bitacora();
            for(int i=0;i<4;i++){
                bitacora.setCiclo(bCiclo[i]);
                bitacora.setIdEstudianteProyecto(bsp[i]);
                bitacora.setAnio(banio[i]);
                bitacora.setMes(bMes[i]);
                insertarBitacora(bitacora);
            }

            Actividad actividad = new Actividad();
            DetalleBitacora detalleBitacora=new DetalleBitacora();
            for(int i=0;i<4;i++){
                actividad.setFechaActividad(afecha[i]);
                actividad.setNombreActividad(aa[i]);
                actividad.setDescripcionTipoActividad(ades[i]);
                actividad.setNumHorasActividades(ahoras[i]);
                insertarActividad(actividad);
                detalleBitacora.setFechaCreacion(afecha[i]);
                detalleBitacora.setIdBitacora(bid[i]);
                detalleBitacora.setIdActividad(aid[i]);
                insertarDetalleBitacora(detalleBitacora);

            }
        }
        cerrar();

    }
    //Para OpcionCrud
    public boolean insertOpcionCrud(OpcionCrud opcionCrud) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("DESOPCION", opcionCrud.getDesOpcion());
        contentValues.put("NUMCRUD", opcionCrud.getNumCrud());

        return (db.insert("OPCIOCRUD",null,contentValues)>0);
    }


    /*



    Metodos utilizados para inicio de secion






    */
    //Permite ingresar un nuevo usuario siempre y no haya ningun otro con dicho nombre de usuario
    public boolean insertUsuario(Usuario usuario){

        if(buscar(usuario.getUsuario())==0){
            ContentValues contentValues = new ContentValues();
            contentValues.put("NOMBREUSUARIO ",usuario.getUsuario());
            contentValues.put("CLAVE  ",usuario.getContraseña());

            return (db.insert("USUARIO",null,contentValues)>0);
        }else{
            return false;
        }

    }
    //Busca los usuarios
    public int buscar(String usuario){
        int x=0;
        lista=selectUsuarios();
        for (Usuario u:lista) {
            if(u.getUsuario().equals(usuario)){
                x++;
            }

        }
        return x;

    }
    //Retorna todos los usuarios de la DB
    public ArrayList<Usuario> selectUsuarios(){
        ArrayList<Usuario> lista =new ArrayList<Usuario>();
        lista.clear();
        Cursor cursor = db.rawQuery("select * from usuario", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                Usuario usuario= new Usuario();
                usuario.setId(cursor.getInt(0));
                usuario.setUsuario(cursor.getString(1));
                usuario.setContraseña(cursor.getString(2));
                lista.add(usuario);
            }while (cursor.moveToNext());
        }
        return lista;
    }
    //Inicio de secion
    public int login(String u,String p){
        int a=0;
        Cursor cursor = db.rawQuery("select * from usuario", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                if(cursor.getString(1).equals(u)&&cursor.getString(2).equals(p)){
                    a++;
                }
            }while (cursor.moveToNext());

        }
        return a;
    }
    public Usuario getUsuario(String u,String p){
        lista=selectUsuarios();
        for (Usuario usu:lista) {
            if(usu.getUsuario().equals(u)&&usu.getContraseña().equals(p)){
                return usu;
            }
        }
        return null;

    }
    public Usuario getUsuarioId(int id){
        lista=selectUsuarios();
        for (Usuario usu:lista) {
            if(usu.getId()==id){
                return usu;
            }
        }
        return null;
    }
    /******************* CRUD DOCENTE ***********************/
    public String insertar(Docente docente){

        String regInsertados="Registro Insertado Nº= ";
        long contador=0;


        ContentValues docent = new ContentValues();
        docent.put("IDUSUARIO", docente.getIdUsuario());
        docent.put("DUITUTOR", docente.getDuiTutor());
        docent.put("NOMBRETUTOR", docente.getNombreTutor());
        docent.put("APELLIDOTUTOR", docente.getApellidosTutor());
        docent.put("EMAILTUTOR", docente.getEmailTutor());
        docent.put("TELEFONOTUTOR", docente.getTelefonoTutor());
        docent.put("NITDOCENTE", docente.getNitTutor());
        //docent.put("IDDOCENTE", docente.getIdDocente());
        contador=db.insert("DOCENTE", null, docent);

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    public String actualizarDocente(Docente bitacora){
        String[] id= {String.valueOf(bitacora.getIdDocente())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("DUITUTOR",bitacora.getDuiTutor());
        contentValues.put("NOMBRETUTOR",bitacora.getNombreTutor());
        contentValues.put("APELLIDOTUTOR",bitacora.getApellidosTutor());
        contentValues.put("EMAILTUTOR",bitacora.getEmailTutor());
        contentValues.put("TELEFONOTUTOR",bitacora.getTelefonoTutor());
        contentValues.put("NITDOCENTE",bitacora.getNitTutor());
        db.update("DOCENTE",contentValues,"IDDOCENTE = ? ",id);

        return "Registros actualizados correctamente";
    }


    public String eliminarDocente(int idDocente){

        String regAfectados= "";

        if(idDocente==1){
            regAfectados="No se puede eliminar el administrador";
        }else {
            db.delete("DOCENTE",
                    "IDDOCENTE='"+idDocente+"'", null);
            regAfectados="Registro eliminado";

        }

        return  regAfectados;

    }
    /*******************CRUD USUARIO************************/


    public String insertarUsuario(Usuario usuario){
        String regInsertados = "Registro Insertado N°= ";
        long contador;
        ContentValues user1 = new ContentValues();
        user1.put("NOMBREUSUARIO", usuario.getUsuario());
        user1.put("CLAVE", usuario.getContraseña());
        contador=db.insert("USUARIO",null, user1);

        if(contador == -1 || contador ==0){
            regInsertados = "Error al insertar registro, Registro duplicado. Verificar insercion";
        }else{
            regInsertados=regInsertados+contador;
        }

        return regInsertados;
    }

    //Recuperar ID de ultimo usuario ingresado
    public int recuperarUltimoUsuario(){
        int idU =0;
        Cursor cursor = db.rawQuery("SELECT * FROM USUARIO;",null);
        if(cursor.moveToLast()){
            idU = cursor.getInt(0);//to get id, 0 is the column index
        }
        cursor.close();

        return idU;
    }

    public int recuperarIdEstudiante(String carnet){
        int idEP=0;
        String[] carnetEP= {carnet};
        Cursor c= db.rawQuery("SELECT * FROM ESTUDIANTEPROYECTO WHERE CARNET =?", carnetEP);
        if(c.moveToLast()){
            idEP = c.getInt(0);//to get id, 0 is the column index
        }
        c.close();
        return  idEP;
    }
    /********************CRUD ESTUDIANTE***********************/
    public String insertarEstudiante(Estudiante estudiante){
        String regInsertados = "Registro Insertado N°= ";
        long contador;
        int idUltimoUsuario=0;

        ContentValues estu = new ContentValues();

        idUltimoUsuario=recuperarUltimoUsuario();

        estu.put("CARNET", estudiante.getCarnet());
        estu.put("IDUSUARIO",idUltimoUsuario);
        estu.put("NOMBREESTUDIANTE", estudiante.getNombreEstudiante());
        estu.put("APELLIDOESTUDIANTE", estudiante.getApellidoEstudiante());
        estu.put("TELEFONO", estudiante.getTelefono());
        estu.put("EMAIL", estudiante.getEmailEstudinate());
        estu.put("DUI", estudiante.getDuiEstudiante());
        estu.put("DOMICILIO", estudiante.getDomicilioEstudiante());
        estu.put("NITESTUDIANTE", estudiante.getNitEstudiante());

        contador=db.insert("ESTUDIANTE",null, estu);

        if(contador == -1 || contador ==0){
            regInsertados = "Error al insertar registro, Registro duplicado. Verificar insercion";
        }else{
            regInsertados=regInsertados+contador;
        }

        return regInsertados;
    }

    public void eliminarEstudiante(String carnet){
        String regAfectados="filas afectadas= ";
        int contador=0;
        contador+=db.delete("ESTUDIANTE",
                "CARNET='"+carnet+"'", null);
        regAfectados+=contador;

    }
    /******************* Estudiante Proyecto ***********************/

    public String insertarEP(EstudianteProyecto ep){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        ContentValues bit = new ContentValues();
        int h = 0;
        bit.put("CARNET",ep.getCarnet());
        bit.put("NUMEROTOTALHORASTRABAJADAS",h);
        contador=db.insert("ESTUDIANTEPROYECTO", null, bit);

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        System.out.println(regInsertados);
        return regInsertados;
    }

    /******************* CRUD BITACORA ***********************/

    public String insertarBitacora(Bitacora bitacora){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;

        ContentValues bit = new ContentValues();

        bit.put("IDESTUDIANTEPROYECTO",bitacora.getIdEstudianteProyecto());
        bit.put("CICLO",bitacora.getCiclo());
        bit.put("MES",bitacora.getMes());
        bit.put("ANIO",bitacora.getAnio());
        contador=db.insert("BITACORA", null, bit);

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public int eliminar(int bitacora){
        String regAfectados="filas afectadas= ";
        int contador=0;
        if(verificarIntegridadBitacora(bitacora)){
                return contador;
        }else{
            contador+=db.delete("BITACORA",
                    "IDBITACORA='"+bitacora+"'", null);
            regAfectados+=contador;

            return contador;
        }
    }

    public String actualizarBitacora(Bitacora bitacora){
        String[] id= {String.valueOf(bitacora.getIdBitacora())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("CICLO",bitacora.getCiclo());
        contentValues.put("ANIO",bitacora.getAnio());
        contentValues.put("MES",bitacora.getMes());
        db.update("BITACORA",contentValues,"IDBITACORA = ? ",id);

        return "Registros actualizados correctamente";
    }
    /******************* CRUD DETALLEBITACORA ***********************/
    public int recuperarIdActividad(){
        int idA =0;
        Cursor cursor = db.rawQuery("SELECT * FROM ACTIVIDAD;",null);
        if(cursor.moveToLast()){
            idA = cursor.getInt(0);//to get id, 0 is the column index
        }
        cursor.close();

        return idA;
    }

    public String insertarDetalleBitacora(DetalleBitacora detalleBitacora){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;
        int idAct = 0;

        idAct = recuperarIdActividad();
        ContentValues detbit = new ContentValues();
        detbit.put("IDBITACORA",detalleBitacora.getIdBitacora());
        detbit.put("IDACTIVIDAD",idAct);
        detbit.put("FECHACREACION",detalleBitacora.getFechaCreacion());
        contador=db.insert("DETALLEBITACORA", null, detbit);

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }
    /******************* CRUD ACTIVIDAD ***********************/
    public String insertarActividad(Actividad actividad){
        String regInsertados="Registro Insertado Nº= ";
        long contador=0;

        ContentValues act = new ContentValues();

        act.put("NOMBREACTIVIDAD",actividad.getNombreActividad());
        act.put("DESCRIPCIONTIPOACTIVIDAD",actividad.getDescripcionTipoActividad());
        act.put("NUMHORASACTIVIDAD",actividad.getNumHorasActividades());
        act.put("FECHAACTIVIDAD",actividad.getFechaActividad());
        contador=db.insert("ACTIVIDAD", null, act);

        if(contador==-1 || contador==0)
        {
            regInsertados= "Error al Insertar el registro, Registro Duplicado. Verificar inserción";
        }
        else {
            regInsertados=regInsertados+contador;
        }
        return regInsertados;
    }

    public int eliminarActividad(int bitacora){
        String regAfectados="filas afectadas= ";
        int contador=0;
        contador+=db.delete("ACTIVIDAD",
                "IDACTIVIDAD='"+bitacora+"'", null);
        regAfectados+=contador;

        return contador;
    }

    public String actualizarActividad(Actividad actividad){
        String[] id= {String.valueOf(actividad.getIdActividad())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("NOMBREACTIVIDAD",actividad.getNombreActividad());
        contentValues.put("DESCRIPCIONTIPOACTIVIDAD",actividad.getDescripcionTipoActividad());
        contentValues.put("FECHAACTIVIDAD",actividad.getFechaActividad());
        contentValues.put("NUMHORASACTIVIDAD",actividad.getNumHorasActividades());
        db.update("ACTIVIDAD",contentValues,"IDACTIVIDAD = ? ",id);

        return "Registros actualizados correctamente";
    }

    //Verficar integridad para eliminar bitacora

    public boolean verificarIntegridadBitacora(int idB){
        String id = String.valueOf(idB);
        String[] IDBITACORA = {"IDBITACORA"};
        String selection = "IDBITACORA = ?";
        String[] selectionArg = {id};
        Cursor c = db.query(true,"DETALLEBITACORA", IDBITACORA,selection,selectionArg,null,null,null,null,null);
        if(c.moveToFirst())
            return true;
        else
            return false;
    }

    /** DT **/
    public void insertarDT(DT dt){
        db.execSQL("DELETE FROM DT");
        long contador=0;
        ContentValues b = new ContentValues();
        b.put("IDUSER",dt.getIdU());
        contador=db.insert("DT", null, b);
    }
    public DT activo(){
        String[] r ={"1"};
        int p;
        Cursor cursor =db.query("DT",camposDT,"IDDT=?",r,null,null,null);
        if(cursor.moveToFirst()){
            DT dt =new DT();
            dt.setId(cursor.getInt(0));
            dt.setIdU(cursor.getInt(1));

            return dt;
        }
        return null;
    }


    /********************* CRUD CARRERA******************************/
    public String insertarCarrera(Carrera carrera){
        long contador=0;
        ContentValues alu = new ContentValues();

        alu.put("IDAREA",carrera.getIdArea());
        alu.put("NOMBREESCUELA",carrera.getNomEscuela());
        alu.put("NOMBRECARRERA",carrera.getNomCarrera());
        contador=db.insert("CARRERA",null,alu);
        if(contador==-1||contador==0){
            return "Error al insertar los registro";
        }

        return "Registro insertado correctamente";
    }
    public void eliminarCarrera(int carrera){
        String regAfectados="filas afectadas= ";
        int contador=0;
        contador+=db.delete("CARRERA",
                "IDCARRERA='"+carrera+"'", null);
        regAfectados+=contador;


    }


    public String actualizarCarrera(Carrera carrera){
        String[] id= {String.valueOf(carrera.getIdCarrera())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDAREA",carrera.getIdArea());
        contentValues.put("NOMBREESCUELA",carrera.getNomEscuela());
        contentValues.put("NOMBRECARRERA",carrera.getNomCarrera());
        db.update("CARRERA",contentValues,"IDCARRERA = ? ",id);

        return "Registros actualizados correctamente";

    }
    public String consultarNomCarrera(int nomArea) {
        String id2="";

        Cursor cursor = db.rawQuery("select * from CARRERA", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                if(cursor.getInt(0)==nomArea){
                    id2=cursor.getString(3);
                }
            }while (cursor.moveToNext());

        }
        return id2;
    }

    public ArrayList<Carrera> consultarCarrera() {


        Cursor cursor = db.rawQuery("SELECT * FROM CARRERA",null);
        ArrayList<Carrera> listadoCarrera = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listadoCarrera.add(new Carrera(cursor.getInt(0),cursor.getInt(1), cursor.getString(2),cursor.getString(3)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return listadoCarrera;
    }


    /********************* CRUD AREA CARRERA******************************/

    public String insertarAreaCarrera(Area area){
        long contador=0;
        ContentValues alu = new ContentValues();


        alu.put("DESCRIPCIONAREA ",area.getDesArea());
        alu.put("NOMBREAREA",area.getNomArea());
        contador=db.insert("AREACARRERA",null,alu);
        if(contador==-1||contador==0){
            return "Error al insertar los registro";
        }

        return "Registro insertado correctamente";
    }
    public String eliminarAreaCarrera(int area){
        String regAfectados= "";
        int cont=0;
        Cursor cursor = db.rawQuery("SELECT * FROM CARRERA",null);


        if (cursor.moveToFirst()) {
            do {

                if(cursor.getInt(1)==area){

                    cont++;

                }
            } while (cursor.moveToNext());

        }
        cursor.close();
        if(cont==0){
            db.delete("AREACARRERA",
                    "IDAREA='"+area+"'", null);
            regAfectados="Registro eliminado";
        }else {
            regAfectados="No se puede eliminar el area pues tiene una carrera asociada";
        }


       return regAfectados;
    }

    public ArrayList<Area> consultarArea() {


        Cursor cursor = db.rawQuery("SELECT * FROM AREACARRERA",null);
        ArrayList<Area> listadoArea = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listadoArea.add(new Area(cursor.getInt(0), cursor.getString(1),cursor.getString(2)));
            } while (cursor.moveToNext());

        }
        cursor.close();
        return listadoArea;
    }
    public int  consultarIdArea(String nomArea) {
        int id2=0;

        Cursor cursor = db.rawQuery("select * from AREACARRERA", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                System.out.println("\n\n\n\n"+"esto deberia imprimirse:  "+cursor.getString(2).equals(nomArea)+"\n\n");
               // System.out.println("\n\n\n\n"+"Variables---- id: "+cursor.getInt(0)+"---- Nombre:"+cursor.getString(1)+"---- Des:"+cursor.getString(3)+"\n\n");
                if(cursor.getString(2).equals(nomArea)){
                    System.out.println("\n\n\n\n"+"********************id****************:  "+cursor.getInt(0)+"\n\n");
                    id2=cursor.getInt(0);
                }
            }while (cursor.moveToNext());

        }
        return id2;
    }
    public String consultarNomArea(int nomArea) {
        String id2="";

        Cursor cursor = db.rawQuery("select * from AREACARRERA", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                    if(cursor.getInt(0)==nomArea){
                        id2=cursor.getString(2);
                     }
            }while (cursor.moveToNext());

        }
        return id2;
    }

    public String actualizarArea(Area area){
        String[] id= {String.valueOf(area.getIdArea())};
        ContentValues contentValues = new ContentValues();

        contentValues.put("DESCRIPCIONAREA",area.getDesArea());
        contentValues.put("NOMBREAREA",area.getNomArea());
        db.update("AREACARRERA",contentValues,"IDAREA = ? ",id);

        return "Registros actualizados correctamente";
    }

    /******************  CRUD ACCESO USUARO *********************/

    public String insertarAccesoUsuario(AccesoUsuario aus){
        long contador=0;
        ContentValues alu = new ContentValues();


        alu.put("IDOPTION ",aus.getIdOpcion());
        alu.put("IDUSUARIO",aus.getIdUsuario());
        contador=db.insert("ACCESOUSUARIO",null,alu);
        if(contador==-1||contador==0){
            return "Error al insertar los registro";
        }

        return "Registro insertado correctamente";
    }

    public int  consultarNivelAcceso(int idUs) {
        int opcion=0;

        Cursor cursor = db.rawQuery("select * from ACCESOUSUARIO", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{

                if(cursor.getInt(1)==idUs){
                         opcion=cursor.getInt(0);
                }
            }while (cursor.moveToNext());

        }
        return opcion;
    }

    public String  obtenerNombreEstudiante(int idUs) {
        String opcion="";

        Cursor cursor = db.rawQuery("select * from ESTUDIANTE", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{

                if(cursor.getInt(1)==idUs){
                    opcion=cursor.getString(2);
                }
            }while (cursor.moveToNext());

        }
        return opcion;
    }
    public String  obtenerNombreDocente(int idUs) {
        String opcion="";

        Cursor cursor = db.rawQuery("select * from DOCENTE", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{

                if(cursor.getInt(2)==idUs){
                    opcion=cursor.getString(3);
                }
            }while (cursor.moveToNext());

        }
        return opcion;
    }



    /*************** DETALLE RESUMEN *****************************/

    /*************** RESUMENSERVICIOSOCIAL*****************************/


    public String insertarDetalleResumen(ResumenServicioSocial rs){
        long contador=0;
        ContentValues alu = new ContentValues();

        alu.put("CARNET",rs.getCarnet());
        alu.put("IDDOCENTE",rs.getIdDocente());
        alu.put("FECHAAPERTURAEXPEDIENTE",rs.getFechaAperturaExpediente());
        //alu.put("FECHAEMISIONCERTIFICADO",rs.getFechaCertific());
        contador=db.insert("RESUMENSERVICIOTOTAL",null,alu);
        if(contador==-1||contador==0){
            return "Error al insertar los registro";
        }

        return "Registro insertado correctamente";
    }


    public boolean validarCarnet(){
        return true;
    }
    public void eliminarResumenServicio(int resumen){

        int contador=0;
                db.delete("RESUMENSERVICIOTOTAL",
                "IDRESUMEN='"+resumen+"'", null);



    }
    /*************** DETALLERESUMENSERVICIO *****************************/

    public String insertarDetalleServicio(DetalleResumenServicio ds){
        long contador=0;
        ContentValues alu = new ContentValues();

        alu.put("IDRESUMEN",ds.getIdResumen());
        alu.put("HORASASIGNADAS",ds.getHorasAsignadas());
        alu.put("BENEFICIARIOSDIRECTOS",ds.getBeneficiariosDirectos());
        alu.put("MONTO",ds.getMonto());
       // alu.put("FECHAINICIO",ds.getFechaInicio());
       // alu.put("FECHAFINAL",ds.getFechaFinal());
        alu.put("BENEFICIARIOSINDIRECTOS",ds.getBeneficiariosIndirectos());
        alu.put("OBSERVACIONES",ds.getObservaciones());
        alu.put("ESTADODERESUMEN",ds.getEstadoDeResumen());

        contador=db.insert("DETALLERESUMENSERVICIO",null,alu);
        if(contador==-1||contador==0){
            return "Error al insertar los registro";
        }

        return "Registro insertado correctamente";
    }

    public void eliminarDetalleServicio(int resumenD){

        int contador=0;
        db.delete("DETALLERESUMENSERVICIO",
                "IDRESUMEN='"+resumenD+"'", null);

    }
    public String consultarDocente(String idDocente) {
        String id2="";

        Cursor cursor = db.rawQuery("select * from DOCENTE", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                if(cursor.getString(1).equals(idDocente)){
                    id2=cursor.getString(3);
                }
            }while (cursor.moveToNext());

        }
        return id2;
    }
    public int consultarca(String idca) {
        int id4=0;

        Cursor cursor = db.rawQuery("select * from CARRERA", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                if(cursor.getString(3).equals(idca)){
                    id4=cursor.getInt(0);
                }
            }while (cursor.moveToNext());

        }
        return id4;
    }
    /***************  RECORD ACADEMICO *********************/
    public String insertarRecord(Record rec){
        long contador=0;
        ContentValues alu = new ContentValues();

        alu.put("IDCARRERA",rec.getIdCarrera());
        alu.put("CARNET",rec.getCarnet());
        alu.put("UNIDADESVALORATIVAS",rec.getuV());
        alu.put("PROMEDIO",rec.getPromedio());
        alu.put("CUM",rec.getCum());
        alu.put("PROGRESO",rec.getProceso());

        contador=db.insert("RECORACADEMICO",null,alu);
        if(contador==-1||contador==0){
            return "Error al insertar los registro";
        }

        return "Registro insertado correctamente";
    }
    public void eliminarRecord(int recD){

        int contador=0;
        db.delete("RECORACADEMICO",
                "IDRECORD='"+recD+"'", null);

    }
    public String actualizarrecord(Record bitacora){
        String[] id= {String.valueOf(bitacora.getIdRecord())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDCARRERA",bitacora.getIdCarrera());
        contentValues.put("CARNET",bitacora.getCarnet());
        contentValues.put("UNIDADESVALORATIVAS",bitacora.getuV());
        contentValues.put("PROMEDIO",bitacora.getPromedio());
        contentValues.put("CUM",bitacora.getCum());
        contentValues.put("PROGRESO",bitacora.getProceso());
        db.update("RECORACADEMICO",contentValues,"IDRECORD = ? ",id);

        return "Registros actualizados correctamente";
    }
    /************CRUD PROYECTO **********************/
    public String insertarProyecto(Proyecto rec){
        long contador=0;
        ContentValues alu = new ContentValues();

        alu.put("IDMODALIDAD",rec.getIdMod());
        alu.put("DUITUTOR",rec.getDuiTu());
        alu.put("IDCATEGORIA",rec.getIdCat());
        alu.put("NOMBREPROYECTO",rec.getNomPro());
        alu.put("DESCRIPCIONPROYECTO",rec.getDesPro());
        alu.put("LUGAR",rec.getLugar());
        alu.put("RESPONSABLEINSTITUCION",rec.getRespoIns());
        alu.put("NUMHORASPROPUESTAS",rec.getNumHoras());
        alu.put("NUMESTUDIANTESREQUERIDOS",rec.getNumEst());
        alu.put("ESTADOPROYECTO",rec.getEstadoPro());


        contador=db.insert("PROYECTO",null,alu);
        if(contador==-1||contador==0){
            return "Error al insertar los registro";
        }

        return "Registro insertado correctamente";
    }

    public String actualizarProyecto(Proyecto bitacora){
        String[] id= {String.valueOf(bitacora.getIdPro())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("IDMODALIDAD",bitacora.getIdMod());
        contentValues.put("DUITUTOR",bitacora.getDuiTu());
        contentValues.put("IDCATEGORIA",bitacora.getIdCat());
        contentValues.put("NOMBREPROYECTO",bitacora.getNomPro());
        contentValues.put("LUGAR",bitacora.getLugar());
        contentValues.put("RESPONSABLEINSTITUCION",bitacora.getRespoIns());
        contentValues.put("DESCRIPCIONPROYECTO",bitacora.getDesPro());
        contentValues.put("NUMHORASPROPUESTAS",bitacora.getNumHoras());
        contentValues.put("NUMESTUDIANTESREQUERIDOS",bitacora.getNumEst());
        contentValues.put("ESTADOPROYECTO",bitacora.getEstadoPro());
        db.update("PROYECTO",contentValues,"IDPROYECTO = ? ",id);

        return "Registros actualizados correctamente";
    }

    public void eliminarProyecto(int id){
        String regAfectados="filas afectadas= ";
        int contador=0;
        contador+=db.delete("PROYECTO",
                "IDPROYECTO='"+id+"'", null);
        regAfectados+=contador;

    }
    public String consultarCategoria(int nomArea) {
        String id2="";

        Cursor cursor = db.rawQuery("select * from CATEGORIA", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                if(cursor.getInt(0)==nomArea){
                    id2=cursor.getString(1);
                }
            }while (cursor.moveToNext());

        }
        return id2;
    }
    public String consultarModalidad(int nomArea) {
        String id2="";

        Cursor cursor = db.rawQuery("select * from MODALIDAD", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                if(cursor.getInt(0)==nomArea){
                    id2=cursor.getString(1);
                }
            }while (cursor.moveToNext());

        }
        return id2;
    }

    public ArrayList<Docente> consultarListDocentes() {


        Cursor cursor = db.rawQuery("SELECT * FROM DOCENTE",null);
        ArrayList<Docente> listadoCarrera = new ArrayList<>();

        if (cursor.moveToFirst()) {
            do {
                listadoCarrera.add(new Docente(cursor.getInt(0),cursor.getString(1),cursor.getInt(2),cursor.getString(3),cursor.getString(4), cursor.getString(5),cursor.getString(6),cursor.getString(7)));

            } while (cursor.moveToNext());

        }
        cursor.close();
        return listadoCarrera;
    }

    public int  consultarIdModalidad(String nomArea) {
        int id2=0;

        Cursor cursor = db.rawQuery("select * from MODALIDAD", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                //System.out.println("\n\n\n\n"+"esto deberia imprimirse:  "+cursor.getString(2).equals(nomArea)+"\n\n");
                // System.out.println("\n\n\n\n"+"Variables---- id: "+cursor.getInt(0)+"---- Nombre:"+cursor.getString(1)+"---- Des:"+cursor.getString(3)+"\n\n");
                if(cursor.getString(1).equals(nomArea)){
                  //  System.out.println("\n\n\n\n"+"********************id****************:  "+cursor.getInt(0)+"\n\n");
                    id2=cursor.getInt(0);
                }
            }while (cursor.moveToNext());

        }
        return id2;
    }

    public int  consultarIdCategoria(String nomArea) {
        int id2=0;

        Cursor cursor = db.rawQuery("select * from CATEGORIA", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
                //System.out.println("\n\n\n\n"+"esto deberia imprimirse:  "+cursor.getString(2).equals(nomArea)+"\n\n");
                // System.out.println("\n\n\n\n"+"Variables---- id: "+cursor.getInt(0)+"---- Nombre:"+cursor.getString(1)+"---- Des:"+cursor.getString(3)+"\n\n");
                if(cursor.getString(1).equals(nomArea)){
                   // System.out.println("\n\n\n\n"+"********************id****************:  "+cursor.getInt(0)+"\n\n");
                    id2=cursor.getInt(0);
                }
            }while (cursor.moveToNext());

        }
        return id2;
    }
    public String  consultarDuiDocente(String nomArea) {
        String id2="";

        Cursor cursor = db.rawQuery("select * from DOCENTE", null );
        if(cursor!=null&&cursor.moveToFirst()){
            do{
               // System.out.println("\n\n\n\n"+"esto deberia imprimirse:  "+cursor.getString(2).equals(nomArea)+"\n\n");
                // System.out.println("\n\n\n\n"+"Variables---- id: "+cursor.getInt(0)+"---- Nombre:"+cursor.getString(1)+"---- Des:"+cursor.getString(3)+"\n\n");
                if(cursor.getString(3).equals(nomArea)){
                   // System.out.println("\n\n\n\n"+"********************id****************:  "+cursor.getInt(0)+"\n\n");
                    id2=cursor.getString(1);
                }
            }while (cursor.moveToNext());

        }
        return id2;
    }



    public String actualizarDetalle(DetalleResumenServicio detalleResumenServicio){
        String[] id= {String.valueOf(detalleResumenServicio.getIdDetalleResumen())};
        ContentValues contentValues = new ContentValues();
        contentValues.put("HORASASIGNADAS",detalleResumenServicio.getHorasAsignadas());
        contentValues.put("BENEFICIARIOSDIRECTOS",detalleResumenServicio.getBeneficiariosDirectos());
        contentValues.put("MONTO",detalleResumenServicio.getMonto());
        // alu.put("FECHAINICIO",ds.getFechaInicio());
        // alu.put("FECHAFINAL",ds.getFechaFinal());
        contentValues.put("BENEFICIARIOSINDIRECTOS",detalleResumenServicio.getBeneficiariosIndirectos());
        contentValues.put("OBSERVACIONES",detalleResumenServicio.getObservaciones());
        contentValues.put("ESTADODERESUMEN",detalleResumenServicio.getEstadoDeResumen());
        db.update("DETALLERESUMENSERVICIO",contentValues,"IDDETALLERESUMEN = ? ",id);

        return "Registros actualizados correctamente";
    }

}
