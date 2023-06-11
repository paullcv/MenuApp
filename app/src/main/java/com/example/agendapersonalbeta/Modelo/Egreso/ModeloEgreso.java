package com.example.agendapersonalbeta.Modelo.Egreso;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.agendapersonalbeta.Modelo.AgendaBD;
import com.example.agendapersonalbeta.Modelo.Egreso.ClaseEgreso;

import java.util.ArrayList;
import java.util.List;

public class ModeloEgreso extends AgendaBD {

    Context context;

    public ModeloEgreso(@Nullable Context context){
        super(context);
        this.context = context;
    }

    public void AgregarEgreso(Integer id, Double monto, String nombre, String fecha, Integer categoria_id){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null){
            ContentValues valores = new ContentValues();
            valores.put("ID", id);
            valores.put("MONTO",monto );
            valores.put("NOMBRE",nombre );
            valores.put("FECHA",fecha );
            valores.put("CATEGORIA_ID", categoria_id);
            bd.insert("EGRESO", null, valores);
            bd.close();
        }
    }

    public List<ClaseEgreso> mostrarEgresos() {
        SQLiteDatabase bd = getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM EGRESO", null);
        List<ClaseEgreso> egresos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                egresos.add(new ClaseEgreso(
                        Integer.valueOf(cursor.getString(0)),
                        Double.valueOf(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        Integer.valueOf(cursor.getString(4))
                ));
            } while (cursor.moveToNext());
        }
        return egresos;
    }

    public void buscarEgreso(ClaseEgreso egreso, Integer id) {
        SQLiteDatabase bd = getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM EGRESO WHERE ID='" + id + "' ", null);
        if (cursor.moveToFirst()) {
            do {
                egreso.setMonto(Double.valueOf(cursor.getString(1)));
                egreso.setNombre(cursor.getString(2));
                egreso.setFecha(cursor.getString(3));
                egreso.setCategoria_id(Integer.valueOf(cursor.getString(4)));
            } while (cursor.moveToNext());
        }
    }

    public void editarEgreso(Integer id, double monto,  String nombre, String fecha, Integer categoriaId) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            ContentValues valores = new ContentValues();
            valores.put("MONTO", monto);
            valores.put("NOMBRE", nombre);
            valores.put("FECHA", fecha);
            valores.put("CATEGORIA_ID", categoriaId);
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.update("EGRESO", valores, whereClause, whereArgs);
            bd.close();
        }
    }

    public void eliminarEgreso(Integer id) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("EGRESO", whereClause, whereArgs);
            bd.close();
        }
    }
}
