package com.example.agendapersonalbeta.Modelo.Prestamo;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.agendapersonalbeta.Modelo.AgendaBD;

import java.util.ArrayList;
import java.util.List;

public class ModeloPrestamo extends AgendaBD {

    Context context;

    public ModeloPrestamo(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    public void agregarPrestamo(Integer id, double monto, String fechaPrestamo, String fechaVencimientoPrestamo, Integer contactoId, Integer categoriaId) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            ContentValues valores = new ContentValues();
            valores.put("ID", id);
            valores.put("MONTO", monto);
            valores.put("FECHAPRESTAMO", fechaPrestamo);
            valores.put("FECHAVENCIMIENTOPRESTAMO", fechaVencimientoPrestamo);
            valores.put("CONTACTO_ID", contactoId);
            valores.put("CATEGORIA_ID", categoriaId);
            bd.insert("PRESTAMO", null, valores);
            bd.close();
        }
    }

    public List<ClasePrestamo> mostrarPrestamos() {
        SQLiteDatabase bd = getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM PRESTAMO", null);
        List<ClasePrestamo> prestamos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                prestamos.add(new ClasePrestamo(
                        Integer.valueOf(cursor.getString(0)),
                        Double.valueOf(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        Integer.valueOf(cursor.getString(4)),
                        Integer.valueOf(cursor.getString(5))
                ));
            } while (cursor.moveToNext());
        }
        return prestamos;
    }

    public void buscarPrestamo(ClasePrestamo prestamo, Integer id) {
        SQLiteDatabase bd = getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM PRESTAMO WHERE ID='" + id + "' ", null);
        if (cursor.moveToFirst()) {
            do {
                prestamo.setMonto(Double.valueOf(cursor.getString(1)));
                prestamo.setFechaPrestamo(cursor.getString(2));
                prestamo.setFechaVencimientoPrestamo(cursor.getString(3));
                prestamo.setContacto_id(Integer.valueOf(cursor.getString(4)));
                prestamo.setCategoria_id(Integer.valueOf(cursor.getString(5)));
            } while (cursor.moveToNext());
        }
    }

    public void editarPrestamo(Integer id, double monto,  String fechaPrestamo, String fechaVencimientoPrestamo, Integer contactoId, Integer categoriaId) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            ContentValues valores = new ContentValues();
            valores.put("MONTO", monto);
            valores.put("FECHAPRESTAMO", fechaPrestamo);
            valores.put("FECHAVENCIMIENTOPRESTAMO", fechaVencimientoPrestamo);
            valores.put("CONTACTO_ID", contactoId);
            valores.put("CATEGORIA_ID", categoriaId);
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.update("PRESTAMO", valores, whereClause, whereArgs);
            bd.close();
        }
    }

    public void eliminarPrestamo(Integer id) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("PRESTAMO", whereClause, whereArgs);
            bd.close();
        }
    }

}
