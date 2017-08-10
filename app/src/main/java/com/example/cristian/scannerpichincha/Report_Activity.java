package com.example.cristian.scannerpichincha;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Report_Activity extends AppCompatActivity {
    private ArrayList<Registro> regsitro = new ArrayList<>();
    private ListView lista;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        lista = (ListView) findViewById(R.id.LVregistros);
        llenarLista();

    }
    public void llenarLista(){
        PichinchaDB pichincha = new PichinchaDB(Report_Activity.this,"PichinchaDB",null,1);
        if(pichincha != null){
            SQLiteDatabase db = pichincha.getWritableDatabase();
            Cursor c = db.rawQuery("SELECT * FROM registro ORDER BY num_reg DESC",null);
            if(c.moveToFirst()){
                do{
                    regsitro.add(new Registro(c.getInt(0),c.getString(1),c.getString(2)));
                }while (c.moveToNext());
            }
        }
        String[] arreglo = new String[regsitro.size()];
        for(int i=0;i<arreglo.length;i++){
            arreglo[i] = Integer.toString(regsitro.get(i).getNum_reg())+ " - "+ regsitro.get(i).getCodigo()+ " - " +regsitro.get(i).getFecha_hora();
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(Report_Activity.this,android.R.layout.simple_list_item_1,arreglo);
        lista.setAdapter(adaptador);
    }
}
