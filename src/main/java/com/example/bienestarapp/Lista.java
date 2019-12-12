package com.example.bienestarapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bienestarapp.adapter_Categoria.*;

import java.util.ArrayList;
import java.util.List;

public class Lista extends AppCompatActivity {

    RecyclerView rv;
    TextView emptyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista);

        rv = findViewById(R.id.rv_categorias);
        emptyView = findViewById(R.id.empty_view);

        ArrayList<Categoria> categorias = (ArrayList<Categoria>) getIntent().getSerializableExtra("Categorias");

        if (categorias.isEmpty()) {
            rv.setVisibility(View.GONE);
            emptyView.setVisibility(View.VISIBLE);
        }
        else {
            rv.setVisibility(View.VISIBLE);
            emptyView.setVisibility(View.GONE);
            rv.setLayoutManager(new LinearLayoutManager(this));
            rv.setAdapter(new Adapter(categorias));
        }
    }

}
