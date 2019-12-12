package com.example.bienestarapp.adapter_Categoria;

import java.io.Serializable;

public class Categoria implements Serializable {

    String nombre, img;
    int disponibles;

    public Categoria() {
    }

    public Categoria(String nombre, String img, int disponibles) {
        this.nombre = nombre;
        this.img = img;
        this.disponibles = disponibles;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getDisponibles() {
        return disponibles;
    }

    public void setDisponibles(int disponibles) {
        this.disponibles = disponibles;
    }
}
