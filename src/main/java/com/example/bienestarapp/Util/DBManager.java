package com.example.bienestarapp.Util;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.example.bienestarapp.Admin;
import com.example.bienestarapp.Lista;
import com.example.bienestarapp.Main;
import com.example.bienestarapp.adapter_Categoria.Categoria;
import com.example.bienestarapp.adapter_Item.Item;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DBManager {

    private static final String TAG = "DBManager/";
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseDatabase database  = FirebaseDatabase.getInstance();
    private static DatabaseReference myRef = database.getReference();
    private static final String UID_ADMIN = "kHT1ZU1EQKhs66ZtyCgsB014ugD2";


    public static void buscarCategorias(final String child, final Context context) {
        DatabaseReference ref = myRef.child(child);
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                ArrayList<Categoria> Categorias = new ArrayList<>();
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    int disp = Integer.parseInt(child.child("disponibles").getValue().toString());
                    Categorias.add(new Categoria(child.getKey(),"",disp));
                }
                Intent intent = new Intent(context, Lista.class);
                intent.putExtra("Categorias", Categorias);
                context.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(context, "No se ha podido leer la información",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // [END read_message]
    }

    public static void solicitar(final Context context, final String nombre, final String hora) {
        //Reconfirma en bd que disponibles > 0
        //Actualiza Disponibles--
        //Actualiza hora de entrega y UID
        //mensaje de confirmación
        //vuelve al Main

        final String uid = mAuth.getCurrentUser().getUid();

        DatabaseReference ref = myRef;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child: dataSnapshot.getChildren()) {
                    if(child.hasChild(nombre)) {
                        DataSnapshot target = child.child(nombre);
                        int disp = Integer.parseInt(target.child("disponibles").getValue().toString());
                        if(disp > 0){
                            DatabaseReference tagetRef = target.getRef();
                            tagetRef.child("disponibles").setValue(disp-1);
                            for (DataSnapshot Categoria: target.getChildren()) {
                                if(Categoria.hasChild("prestado_a")){
                                    if(Categoria.child("prestado_a").getValue().toString().equals("N/A")){
                                        DatabaseReference CategoriaRef = Categoria.getRef();
                                        CategoriaRef.child("hora_entrega").setValue(hora);
                                        CategoriaRef.child("prestado_a").setValue(uid);
                                        Toast.makeText(context, "Solicitud exitosa",
                                                Toast.LENGTH_SHORT).show();
                                        context.startActivity(new Intent(context,Main.class));
                                        break;
                                    }
                                }
                            }
                        }else{
                            Toast.makeText(context, "No hay elementos disponibles en este momento",
                                    Toast.LENGTH_SHORT).show();
                        }
                        break;
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(context, "No se ha podido leer la información",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // [END read_message]
    }

    public static void buscarItems(final String subCat_name, final Context context) {
        DatabaseReference ref = myRef;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                ArrayList<Item> Items = new ArrayList<>();
                for (DataSnapshot categoria: dataSnapshot.getChildren()) {
                    if(categoria.hasChild(subCat_name)){
                        DataSnapshot subCat = categoria.child(subCat_name);
                        for (DataSnapshot item: subCat.getChildren()) {
                            if(item.hasChild("hora_entrega")) {
                                System.out.println(item.getValue().toString());
                                SimpleDateFormat df = new SimpleDateFormat("HH:mm");
                                Date hora = null;
                                try {
                                    hora = df.parse(item.child("hora_entrega").getValue().toString());
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Items.add(new Item(item.getKey(), item.child("prestado_a").getValue().toString(), hora));
                            }
                        }
                    }
                }
                Intent intent = new Intent(context, Admin.class);
                intent.putExtra("Items", Items);
                context.startActivity(intent);
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(context, "No se ha podido leer la información",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // [END read_message]
    }

    public static void devolver(final Context context, final String nombre) {

        /*se busca a dónde pertenece el item
        se actualiza que no está prestado a nadie ni tiene hora de entrega
        se busca la cantidad de disponibles de su padre y se hace ++
        retorno al main
         */

        DatabaseReference ref = myRef;
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String value = dataSnapshot.getValue().toString();
                for (DataSnapshot categoria: dataSnapshot.getChildren()) {
                    for (DataSnapshot sub: categoria.getChildren()) {
                        if(sub.hasChild(nombre)){
                            DatabaseReference item = sub.child(nombre).getRef();
                            item.child("prestado_a").setValue("N/A");
                            item.child("hora_entrega").setValue("00:00");

                            int disp = Integer.parseInt(sub.child("disponibles").getValue().toString());
                            disp++;
                            DatabaseReference ref_SubCategoria = sub.getRef();
                            ref_SubCategoria.child("disponibles").setValue(disp);
                        }
                    }
                }
                context.startActivity(new Intent(context,Main.class));
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read value.", error.toException());
                Toast.makeText(context, "No se ha podido leer la información",
                        Toast.LENGTH_SHORT).show();
            }
        });
        // [END read_message]
    }

    public static boolean isLoggedAsAdmin(){
        return mAuth.getCurrentUser().getUid().equals(UID_ADMIN);
    }
}
