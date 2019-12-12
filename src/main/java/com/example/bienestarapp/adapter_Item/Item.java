package com.example.bienestarapp.adapter_Item;

import java.io.Serializable;
import java.util.Date;

public class Item implements Serializable {

    String nombre, prestado_a;
    Date hora_entrega;

    public Item() {
    }

    public Item(String nombre, String prestado_a, Date hora_entrega) {
        this.nombre = nombre;
        this.prestado_a = prestado_a;
        this.hora_entrega = hora_entrega;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getPrestado_a() {
        return prestado_a;
    }

    public void setPrestado_a(String prestado_a) {
        this.prestado_a = prestado_a;
    }

    public Date getHora_entrega() {
        return hora_entrega;
    }

    public void setHora_entrega(Date hora_entrega) {
        this.hora_entrega = hora_entrega;
    }
}
