package com.example.agendapersonalbeta.Modelo.Categoria;

public class ClaseCategoria {

    private int id;
    private String titulo;
    private String descripcion;

    public ClaseCategoria(){
    }

    public ClaseCategoria(int id, String titulo, String descripcion){
        this.id = id;
        this.titulo = titulo;
        this.descripcion = descripcion;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
