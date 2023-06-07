package com.example.agendapersonalbeta.Modelo.Evento;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.agendapersonalbeta.Modelo.AgendaBD;
import com.example.agendapersonalbeta.Modelo.Evento.ClaseEvento;

import java.util.ArrayList;
import java.util.List;

public class ModeloEvento extends AgendaBD {

    Context context;

    public ModeloEvento(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    public void agregarEvento(Integer id, String nombre, String fecha,String hora){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null){
            ContentValues valores =  new ContentValues();
            valores.put("ID", id);
            valores.put("NOMBRE", nombre);
            valores.put("FECHA", fecha);
            valores.put("HORA", hora);
            bd.insert("EVENTO", null, valores);
            bd.close();
        }
    }

    public List<ClaseEvento> mostrarEventos() {
        SQLiteDatabase bd = getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM EVENTO", null);
        List<ClaseEvento> eventos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                eventos.add(new ClaseEvento(Integer.valueOf(cursor.getString(0)), cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            } while (cursor.moveToNext());
        }
        return eventos;
    }

    public void buscarEvento(ClaseEvento evento, Integer id) {
        SQLiteDatabase bd = getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM EVENTO WHERE ID='" + id + "' ", null);
        if (cursor.moveToFirst()) {
            do {
                evento.setNombre(cursor.getString(1));
                evento.setFecha(cursor.getString(2));
                evento.setHora(cursor.getString(3));
            } while (cursor.moveToNext());
        }
    }

    public void editarEvento(Integer id, String nombre, String fecha, String hora) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            ContentValues valores = new ContentValues();
            valores.put("NOMBRE", nombre);
            valores.put("FECHA", fecha);
            valores.put("HORA", hora);
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.update("EVENTO", valores, whereClause, whereArgs);
            bd.close();
        }
    }

    public void eliminarEvento(Integer id) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("EVENTO", whereClause, whereArgs);
            bd.close();
        }
    }
}
