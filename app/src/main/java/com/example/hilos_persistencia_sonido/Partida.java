package com.example.hilos_persistencia_sonido;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.Display;
import android.view.WindowManager;

import androidx.core.content.ContextCompat;

import java.util.Objects;

public class Partida extends androidx.appcompat.widget.AppCompatImageView {

    private final int acel;
    private Bitmap pelota, fondo;
    private final int tam_pantX, tam_pantY;
    private int posX, posY, velX, velY;
    private final int tamPelota;
    boolean pelota_sube;

    public Partida(Context contexto, AttributeSet attrs, int nivel_dificultad) {

        super(contexto, attrs);

        WindowManager manejador_ventana = (WindowManager) contexto.getSystemService(Context.WINDOW_SERVICE);

        Display pantalla = manejador_ventana.getDefaultDisplay();

        Point maneja_coord = new Point();

        pantalla.getSize(maneja_coord);

        tam_pantX = maneja_coord.x;

        tam_pantY = maneja_coord.y;

        BitmapDrawable dibujo_fondo = (BitmapDrawable) ContextCompat.getDrawable(contexto, R.drawable.paisaje_1);

        // mirar en api getBitmap en clase BitmapDrawable. esto nos lleva a la siguiente instr.
        fondo = Objects.requireNonNull(dibujo_fondo).getBitmap();

        fondo = Bitmap.createScaledBitmap(fondo, tam_pantX, tam_pantY, false);//mirar en clase Bitmap

        BitmapDrawable objetoPelota = (BitmapDrawable) ContextCompat.getDrawable(contexto, R.drawable.pelota_1);

        pelota = Objects.requireNonNull(objetoPelota).getBitmap();

        tamPelota = tam_pantY / 3;

        pelota = Bitmap.createScaledBitmap(pelota, tamPelota, tamPelota, false);

        posX = tam_pantX / 2 - tamPelota / 2;

        posY = -tamPelota;

        acel = nivel_dificultad * (maneja_coord.y / 400);

    }

    public boolean toque(int x, int y) {

        if (y < tam_pantY / 3) return false;

        if (velY <= 0) return false;

        if (x < posX || x > posX + tamPelota) return false;

        if (y < posY || y > posY + tamPelota) return false;

        velY = -velY;

        int desplX = x - (posX + (tamPelota / 2));

        desplX = ((desplX / (tamPelota / 2)) * velY) / 2;

        velX += desplX;

        return true;
    }

    public boolean movimientoBola() {

        if (posX < -tamPelota) {

            posY = -tamPelota;

            velY = acel;
        }

        posX += velX;

        posY += velY;

        if (posY >= tam_pantY) return true;

        if (posX + tamPelota < 0 || posX > tam_pantX) return true;

        if (velY < 0) pelota_sube = true;

        if (velY > 0 && pelota_sube) {

            pelota_sube = false;

        }

        velY += acel;

        return false;
    }

    protected void onDraw(Canvas lienzo) {

        lienzo.drawBitmap(fondo, 0, 0, null);

        lienzo.drawBitmap(pelota, posX, posY, null);

    }
}
