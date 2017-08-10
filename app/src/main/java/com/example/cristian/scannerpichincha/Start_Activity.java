package com.example.cristian.scannerpichincha;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.SystemClock;
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
import android.widget.ImageButton;
import android.widget.Toast;
import android.graphics.Typeface;

public class Start_Activity extends AppCompatActivity {
    private int requestCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Typeface fontAwesomeFont = Typeface.createFromAsset(getAssets(),"fontawesome-webfont.ttf");
        Button fontawesomeBarcode = (Button) findViewById(R.id.button);
        Button fontawesomeDataBase = (Button) findViewById(R.id.button2);
        Button fontawesomeTols = (Button) findViewById(R.id.button3);
        Button fontawesomeText = (Button) findViewById(R.id.button4);
        fontawesomeBarcode.setTypeface(fontAwesomeFont);
        fontawesomeDataBase.setTypeface(fontAwesomeFont);
        fontawesomeTols.setTypeface(fontAwesomeFont);
        fontawesomeText.setTypeface(fontAwesomeFont);
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED)
            ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.CAMERA}, requestCode);
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
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
    public void ScannerClick(View v){
        Intent i = new Intent(getApplicationContext(),Scanner_Activity.class);
        startActivity(i);
    }
    public void ReportClick(View v){
        Toast.makeText(this,"hola como es llega hasta aqui",Toast.LENGTH_SHORT).show();
        Intent i = new Intent(getApplicationContext(),Report_Activity.class);
        startActivity(i);
    }
    public void reseteardb(View v){
        Toast.makeText(this,"Base de datos reseteada",Toast.LENGTH_LONG).show();
        /*PichinchaDB pichincha= new PichinchaDB(this,"PichinchaDB",null,1);
        SQLiteDatabase db = pichincha.getWritableDatabase();
        String cons = "DELETE FROM registro";
        db.execSQL(cons);
        db.close();*/
    }
}
