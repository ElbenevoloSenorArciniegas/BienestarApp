package com.example.bienestarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.bienestarapp.Util.DBManager;
import com.google.android.material.textfield.TextInputEditText;

public class Main extends AppCompatActivity {

    CardView deportes, musica;
    TextInputEditText busqueda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deportes = findViewById(R.id.deportes);
        musica = findViewById(R.id.musica);
        busqueda = findViewById(R.id.busqueda);

        deportes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager.buscarCategorias("deportes",Main.this);
            }
        });
        musica.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager.buscarCategorias("musica",Main.this);
            }
        });
        busqueda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buscar();
            }
        });

    }

    private void buscar() {
        Toast.makeText(Main.this, "Esta opción aún no se encuentra disponible.",
                Toast.LENGTH_SHORT).show();
    }

}
