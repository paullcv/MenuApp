package com.example.agendapersonalbeta.Modelo.Prestamo;

public class ClasePrestamo {

    private int id;
    private double monto;

    private String fechaPrestamo;

    private String fechaVencimientoPrestamo;

    private int contacto_id;
    private int categoria_id;

    public ClasePrestamo(){

    }

    public ClasePrestamo(int id, double monto, String fechaPrestamo, String fechaVencimientoPrestamo, int contacto_id, int categoria_id){
        this.id = id;
        this.monto = monto;
        this.fechaPrestamo = fechaPrestamo;
        this.fechaVencimientoPrestamo = fechaVencimientoPrestamo;
        this.contacto_id = contacto_id;
        this.categoria_id = categoria_id;
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

    public String getFechaPrestamo() {
        return fechaPrestamo;
    }

    public void setFechaPrestamo(String fechaPrestamo) {
        this.fechaPrestamo = fechaPrestamo;
    }

    public String getFechaVencimientoPrestamo() {
        return fechaVencimientoPrestamo;
    }

    public void setFechaVencimientoPrestamo(String fechaVencimientoPrestamo) {
        this.fechaVencimientoPrestamo = fechaVencimientoPrestamo;
    }

    public int getContacto_id() {
        return contacto_id;
    }

    public void setContacto_id(int contacto_id) {
        this.contacto_id = contacto_id;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }
}
