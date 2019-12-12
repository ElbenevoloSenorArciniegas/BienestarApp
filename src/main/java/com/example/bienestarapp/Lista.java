package com.example.bienestarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.bienestarapp.adapter_Categoria.*;

import java.util.List;

public class Lista extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);
        RecyclerView rv = findViewById(R.id.rv_categorias);
        rv.setLayoutManager(new LinearLayoutManager(this));
        rv.setAdapter(new Adapter((List<Categoria>) getIntent().getSerializableExtra("Categorias")));
    }

}
