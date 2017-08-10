package com.example.cristian.scannerpichincha;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.Result;

import java.security.Policy;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.xml.transform.sax.SAXResult;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

public class Scanner_Activity extends AppCompatActivity implements ZXingScannerView.ResultHandler{
    private ZXingScannerView scanner;
    private int requestCode;
    private Vibrator rr;
    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH-MM-yyyy dd:mm:ss");
    static SimpleDateFormat onlyDate = new SimpleDateFormat("dd-MM-yyyy");
    static SimpleDateFormat dataactual = new SimpleDateFormat("05-08-2017");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, requestCode);
        super.onCreate(savedInstanceState);
        rr = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        scanner = new ZXingScannerView(this);
        setContentView(scanner);
        scanner.setResultHandler(this);
        scanner.startCamera();
        scanner.setFlash(true);
    }
    @Override
    public void onPause(){
        super.onPause();
        scanner.stopCamera();
        scanner.setFlash(false);
    }
    @Override
    public void handleResult(Result result) {
       // Toast.makeText(this,Integer.parseInt(result.getText()),Toast.LENGTH_SHORT).show();
        PichinchaDB pichincha = new PichinchaDB(this,"PichinchaDB",null,1);
        SQLiteDatabase db = pichincha.getWritableDatabase();
        String cons_busqueda = "SELECT count (*) from credencial WHERE codigo="+"'"+ result.getText()+ "'"+ "" ;
        Cursor rcursor = db.rawQuery(cons_busqueda,null);
        rcursor.moveToFirst();
        int valor_result = rcursor.getInt(0);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Escaneado");
        builder.setMessage(verificarRespuesta(valor_result,result.getText()));
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id){
                ResumeCamera();
                dialog.dismiss();            }
        });
        builder.setCancelable(false);
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
        rr.vibrate(350);
        //scanner.resumeCameraPreview(this);
    }
    public String verificarRespuesta(int valor, String result){
        Calendar ca = Calendar.getInstance();
        Date d = new Date(2017,8,05);
        PichinchaDB pichincha = new PichinchaDB(this,"PichinchaDB",null,1);
        SQLiteDatabase db = pichincha.getWritableDatabase();
        String respuesta="NO EXITE CODIGO \n \n " + ""+"***"+ result+"***";
        Toast.makeText(this,onlyDate.format(ca.getTime()),Toast.LENGTH_SHORT).show();
        if(valor==1){
            String cons_repetido = "SELECT count (*) from registro WHERE codigo="+"'"+ result+"'"+  "";
            Cursor dcursor= db.rawQuery(cons_repetido,null);
            dcursor.moveToFirst();
            int valor_repetido= dcursor.getInt(0);
            if(valor_repetido>=1){
                respuesta = "CODIGO YA REGISTRADO \n \n "+"***"+ result+"***";
            }
            else{
                Calendar c = Calendar.getInstance();
                SimpleDateFormat f = new SimpleDateFormat();
                c.set(Calendar.HOUR, 17);
                c.set(Calendar.MINUTE, 30);
                c.set(Calendar.SECOND, 2);
                String cons_insercion= "INSERT INTO registro(fecha_hora,codigo) VALUES("+"'"+ simpleDateFormat.format(c.getTime())+"'"+","+"'"+result+ "'"+ ")";
                db.execSQL(cons_insercion);
                respuesta = "REGISTRADO EXITOSAMENTE\n \n "+"***"+ result+"***";
            }
        }
        return respuesta;
    }
    public void ResumeCamera(){
        scanner.resumeCameraPreview(this);
        scanner.setFlash(true);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();
        if(id== R.id.action_help){
            Toast.makeText(this,"hola como estas",Toast.LENGTH_SHORT).show();
        }
        if(id == R.id.action_exit){

            Toast.makeText(this,"jaja no sale .I. .I.",Toast.LENGTH_SHORT).show();
        }
        if(id == android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
