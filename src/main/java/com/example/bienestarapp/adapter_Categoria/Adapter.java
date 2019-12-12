package com.example.bienestarapp.adapter_Categoria;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bienestarapp.R;
import com.example.bienestarapp.Util.DBManager;
import com.example.bienestarapp.Util.Hora;

import java.util.List;

public class Adapter extends RecyclerView.Adapter<Adapter.itemViewHolder> {

    List<Categoria> categorias;

    public Adapter(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    @NonNull
    @Override
    public itemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_lista,parent,false);
        itemViewHolder cat = new itemViewHolder(v);
        return cat;
    }

    @Override
    public void onBindViewHolder(@NonNull final itemViewHolder holder, final int position) {
        final String nombre = categorias.get(position).nombre;
        final int disp = categorias.get(position).disponibles;
        //holder.imagen.src=categorias.get(position).img;
        holder.nombre.setText(nombre);
        holder.cantidad.setText(disp+"");
        if(DBManager.isLoggedAsAdmin()){
            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    DBManager.buscarItems(nombre, holder.itemView.getContext());
                }
            });
        }else{
            holder.cv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(disp > 0) {
                        abrirDialogoSolicitar(holder, nombre);
                    }else{
                        Toast.makeText(holder.itemView.getContext(), "No hay elementos disponibles en este momento",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return categorias.size();
    }

    public static class itemViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        ImageView imagen;
        TextView nombre,cantidad;

        public itemViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cuadrito_body);
            imagen = itemView.findViewById(R.id.cuadrito_img);
            nombre = itemView.findViewById(R.id.cuadrito_nombre);
            cantidad = itemView.findViewById(R.id.cuadrito_cant);
        }
    }

    private void abrirDialogoSolicitar(itemViewHolder holder, final String nombre){
        final Context context = holder.itemView.getContext();
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.fragment_dialog_solicitar);
        dialog.setTitle("Solicitar artículo");

        TextView txtNombre = dialog.findViewById(R.id.dialog_solicitar_nombre);
        txtNombre.setText(nombre);

        final EditText txtHora = dialog.findViewById(R.id.et_mostrar_hora_picker);
        ImageButton btnHora = dialog.findViewById(R.id.ib_obtener_hora);
        btnHora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Hora.obtenerHora(context,txtHora);
            }
        });

        Button cancelButton = dialog.findViewById(R.id.dialog_solicitar_cancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        Button okButton = dialog.findViewById(R.id.dialog_solicitar_ok);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DBManager.solicitar(context,nombre,txtHora.getText().toString());
                dialog.dismiss();
            }
        });

        dialog.show();
    }


}
