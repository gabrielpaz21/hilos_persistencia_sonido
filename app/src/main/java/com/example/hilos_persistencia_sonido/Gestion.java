package com.example.hilos_persistencia_sonido;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;

import androidx.appcompat.app.AppCompatActivity;

public class Gestion extends AppCompatActivity {

    private Partida partida;
    private int dificultal;
    private static final int FPS = 30;
    private Handler temporizador;
    private int botes;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        botes = 0;
        Bundle extras = getIntent().getExtras();
        dificultal = extras.getInt("DIFICULTAD");
        partida = new Partida(this, null, dificultal);
        setContentView(partida);
        temporizador = new Handler();
        temporizador.postDelayed(elHilo,2000);
    }

    private final Runnable elHilo = new Runnable() {
        @Override
        public void run() {
            if (partida.movimientoBola()) {
                fin();
            } else {
                partida.invalidate();
                temporizador.postDelayed(elHilo,1000/FPS);
            }
        }
    };

    public boolean onTouchEvent(MotionEvent evento){
        int x = (int) evento.getX();
        int y = (int) evento.getY();
        if(partida.toque(x,y)) botes++;
        return false;
    }

    public void fin(){
        temporizador.removeCallbacks(elHilo);
        Intent in = new Intent();
        in.putExtra("PUNTUACION",botes);
        setResult(RESULT_OK,in);
        finish();
    }

}
