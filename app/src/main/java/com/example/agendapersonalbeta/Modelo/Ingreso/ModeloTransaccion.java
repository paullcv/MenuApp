package com.example.agendapersonalbeta.Modelo.Ingreso;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.agendapersonalbeta.Modelo.AgendaBD;

public abstract class ModeloTransaccion extends AgendaBD {

    Context context;

    public ModeloTransaccion(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public void realizarTransaccion(Integer id, Double monto, String nombre, String fecha, Integer categoria_id) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            ContentValues valores = new ContentValues();
            valores.put("ID", id);
            valores.put("MONTO", monto);
            valores.put("NOMBRE", nombre);
            valores.put("FECHA", fecha);
            valores.put("CATEGORIA_ID", categoria_id);
            insertarRegistro(bd, valores);
            bd.close();
        }
    }

    public void editarTransaccion(Integer id, double monto, String nombre, String fecha, Integer categoriaId) {
        SQLiteDatabase bd = getWritableDatabase();
        editarOperacion(bd, id, monto, nombre, fecha, categoriaId);
        bd.close();
    }

    public void eliminarTransaccion(Integer id) {
        SQLiteDatabase bd = getWritableDatabase();
        eliminarOperacion(bd, id);
        bd.close();
    }




    protected abstract void insertarRegistro(SQLiteDatabase bd, ContentValues valores);
    protected abstract void editarOperacion(SQLiteDatabase bd, Integer id, Double monto, String nombre, String fecha, Integer categoriaId);
    protected abstract void eliminarOperacion(SQLiteDatabase bd, Integer id);

    protected abstract String obtenerNombreTabla();
}
