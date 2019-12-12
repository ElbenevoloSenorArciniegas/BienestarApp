package com.example.bienestarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.bienestarapp.Util.DBManager;
import com.google.android.material.textfield.TextInputEditText;

public class Main extends AppCompatActivity {

    CardView deportes, musica;
    TextInputEditText busqueda;
    ImageButton btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        deportes = findViewById(R.id.deportes);
        musica = findViewById(R.id.musica);
        busqueda = findViewById(R.id.busqueda);
        btnSearch = findViewById(R.id.btnSearch);

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

        busqueda.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    btnSearch.setImageDrawable(ContextCompat.getDrawable(Main.this, R.drawable.ic_arrow_forward_black_24dp));
                    btnSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            DBManager.buscarPorKeyword(busqueda.getText().toString(),Main.this);
                        }
                    });
                }else{
                    btnSearch.setImageDrawable(ContextCompat.getDrawable(Main.this, R.drawable.search));
                    btnSearch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            busqueda.requestFocus();
                        }
                    });
                }
            }
        });
    }

    private void buscar() {
        Toast.makeText(Main.this, "Esta opción aún no se encuentra disponible.",
                Toast.LENGTH_SHORT).show();
    }

}
