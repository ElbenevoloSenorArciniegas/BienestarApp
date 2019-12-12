package com.example.bienestarapp.adapter_Item;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bienestarapp.Util.DBManager;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.example.bienestarapp.R.*;

public class Adapter extends RecyclerView.Adapter<Adapter.ItemViewHolder> {

    List<Item> Items;

    public Adapter(List<Item> Items) {
        this.Items = Items;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(layout.fragment_items,parent,false);
        ItemViewHolder cat = new ItemViewHolder(v);
        return cat;
    }

    @Override
    public void onBindViewHolder(@NonNull final ItemViewHolder holder, final int position) {
        final String nombre = Items.get(position).nombre;
        final String prestado_a = Items.get(position).prestado_a;
        Date hora = Items.get(position).hora_entrega;

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");

        //holder.imagen.src=Items.get(position).img;
        holder.nombre.setText(nombre);
        holder.prestado_a.setText(prestado_a);
        holder.hora_entrega.setText(simpleDateFormat.format(hora));
        //La idea es controlar que no se atrasen, y notificar a la gente que tiene que devolder en tanto tiempo y cosas así

        if(!prestado_a.equals("N/A")){
            holder.btn.setBackgroundColor(color.colorSecondary);
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBManager.devolver(holder.itemView.getContext(),nombre);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return Items.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView nombre,prestado_a,hora_entrega;
        Button btn;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(id.item_body);
            prestado_a = itemView.findViewById(id.item_prestado_a);
            nombre = itemView.findViewById(id.item_nombre);
            hora_entrega = itemView.findViewById(id.item_hora);
            btn = itemView.findViewById(id.item_btn_devolver);
        }
    }

}
