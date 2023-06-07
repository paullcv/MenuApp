package com.example.agendapersonalbeta.Modelo.Evento;

public class ClaseEvento {

    private int id;
    private String nombre;
    private String fecha;
    private String hora;



    public ClaseEvento() {
    }

    public ClaseEvento(int id, String nombre, String fecha, String hora) {
        this.id = id;
        this.nombre = nombre;
        this.fecha = fecha;
        this.hora = hora;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }
}
