package com.example.cristian.scannerpichincha;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Timer;
import java.util.TimerTask;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class MainActivity extends AppCompatActivity{
    private ZXingScannerView ScannerView;
    public String codigo;
    public int cod_control;
    public int cod_producto;
    public String fecha;
    public int estado;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PichinchaDB pichincha= new PichinchaDB(this,"PichinchaDB",null,1);
        SQLiteDatabase db = pichincha.getWritableDatabase();
        String count = "SELECT count (*) from credencial";
        Cursor mcursor = db.rawQuery(count,null);
        mcursor.moveToFirst();
        int valor_count = mcursor.getInt(0);
        if (valor_count == 0){
            //db.execSQL("INSERT INTO" + " credencial VALUES('H1M0ZO',1,1,'27/06/2017',1)");
            //Integer valor=0;
            String linea;
            InputStream is = this.getResources().openRawResource(R.raw.cvspichincha);
            BufferedReader reader= new BufferedReader(new InputStreamReader(is));
            if(is!=null){
                try {
                    while ((linea = reader.readLine())!=null){
                      db.execSQL("INSERT INTO" + " credencial VALUES("+"'"+(linea.split(";")[0])+"'"+","+Integer.parseInt(linea.split(";")[1])+
                              ","+Integer.parseInt(linea.split(";")[2])+","+"'"+(linea.split(";")[3])+"'"+","+Integer.parseInt(linea.split(";")[4])+")");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Toast.makeText(this,"Cargando base de datos",Toast.LENGTH_LONG).show();
            }
        }
        else{
            Toast.makeText(this,"base de datos cargada",Toast.LENGTH_SHORT).show();
        }
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),Start_Activity.class);
                startActivity(i);
                finish();
            }
        },2500);
    }
}
