package com.example.hilos_persistencia_sonido;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private int record;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();
        leerecord();
    }

    private void guardarecord() {
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor mieditor = datos.edit();
        mieditor.putInt("RECORD", record);
        mieditor.apply();
    }

    private void leerecord() {
        SharedPreferences datos = PreferenceManager.getDefaultSharedPreferences(this);
        record = datos.getInt("RECORD", 0);
        TextView caja = (TextView) findViewById(R.id.record);
        caja.setText("Record: " + record);
    }

    public void ayuda(View view) {
        Intent intent = new Intent(this, AyudaActivity.class);
        startActivity(intent);
    }

    public void dificultad(View vista) {

        String dific = (String) ((Button) vista).getText();
        int dificultad = 1;
        if (dific.equals(getString(R.string.medio))) dificultad = 2;
        if (dific.equals(getString(R.string.dificil))) dificultad = 3;
        Intent in = new Intent(this, Gestion.class);
        in.putExtra("DIFICULTAD", dificultad);
        startActivityForResult(in, 1);
    }

    @Override
    protected void onActivityResult(int peticion, int codigo, @Nullable Intent puntuacion) {
        super.onActivityResult(peticion, codigo, puntuacion);
        if (peticion != 1 || codigo != RESULT_OK) return;
        int resultado = puntuacion.getIntExtra("PUNTUACION", 0);
        if (resultado > record) {
            record = resultado;
            TextView caja = (TextView) findViewById(R.id.record);
            caja.setText("Record: " + record);
            guardarecord();
        } else{
            String puntuacionpartida = " " + resultado;
            Toast miToast = Toast.makeText(this,puntuacionpartida,Toast.LENGTH_SHORT);
            miToast.setGravity(Gravity.CENTER,0,0);
            miToast.show();
        }
    }
}