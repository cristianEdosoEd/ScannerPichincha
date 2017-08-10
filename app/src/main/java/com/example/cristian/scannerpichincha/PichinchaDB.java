package com.example.cristian.scannerpichincha;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by cristian on 27/06/2017.
 */
public class PichinchaDB extends SQLiteOpenHelper{
    //tabla credencial
    public final static String NAME_TABLE_CREDENCIAL = "credencial";
    //columnas de la tabla credencial
    public final static String T_CRE_COLUMNA_CODIGO = "codigo";
    public final static String T_CRE_COLUMNA_COD_CONTROL= "cod_control";
    public final static String T_CRE_COLUMNA_COD_PRODUCTO= "cod_producto";
    public final static String T_CRE_COLUMNA_COD_FECHA = "fecha";
    public final static String T_CRE_COLUMNA_COD_ESTADO = "estado";
    private final static String SQL_CREATE_TABLE_CREDENCIAL = "create table "+ NAME_TABLE_CREDENCIAL + "(" + T_CRE_COLUMNA_CODIGO + " text primary key," +
            T_CRE_COLUMNA_COD_CONTROL + " integer," + T_CRE_COLUMNA_COD_PRODUCTO + " integer not null," + T_CRE_COLUMNA_COD_FECHA + " date not null," +
            T_CRE_COLUMNA_COD_ESTADO + " boolean);";
    //tabla registro
    public final static String NAME_TABLE_REGISTRO = "registro";
    //columnas de la tabla registro
    public final static String T_REG_COLUMNA_NUM_REG = "num_reg";
    public final static String T_REG_COLUMNA_FECHA_HORA = "fecha_hora";
    public final static String T_REG_COLUMNA_CODIGO = "codigo";
    private final static String SQL_CREATE_TABLE_REGISTRO = "create table "+ NAME_TABLE_REGISTRO + "(" + T_REG_COLUMNA_NUM_REG + " integer primary key autoincrement," +
            T_REG_COLUMNA_FECHA_HORA + " fecha_hora datetime not null," + T_REG_COLUMNA_CODIGO + " integer," +
            "FOREIGN KEY ("+T_REG_COLUMNA_CODIGO+") REFERENCES " + NAME_TABLE_CREDENCIAL + "("+T_CRE_COLUMNA_CODIGO+"));";
    public PichinchaDB(Context context, String DATABASE_NAME,SQLiteDatabase.CursorFactory factory, int DATABASE_VERSION){
        super(context,DATABASE_NAME,factory,DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        db.execSQL(SQL_CREATE_TABLE_CREDENCIAL);
        db.execSQL(SQL_CREATE_TABLE_REGISTRO);
    }
    @Override
    public void onUpgrade(SQLiteDatabase DB,int oldVersion, int newVersion){

    }
    public ArrayList llenar_lv(){
        ArrayList<String> lista= new ArrayList<>();
        SQLiteDatabase database = this.getWritableDatabase();
        String sql = "SELECT * FROM registro";
        Cursor registros = database.rawQuery(sql,null);
        if(registros.moveToFirst()){
            do{
                lista.add(registros.getString(0));
            }while (registros.moveToFirst());
        }
        return lista;
    }
}
