package com.example.agendapersonalbeta.Modelo.Ingreso;

public class ClaseIngreso {

    private int id;
    private double monto;
    private String nombre;
    private String fecha;

    private int categoria_id;

    public ClaseIngreso(int id, double monto, String nombre, String fecha, int categoria_id) {
        this.id = id;
        this.monto = monto;
        this.nombre = nombre;
        this.fecha = fecha;
        this.categoria_id = categoria_id;
    }

    public ClaseIngreso() {

    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
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

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }
}
