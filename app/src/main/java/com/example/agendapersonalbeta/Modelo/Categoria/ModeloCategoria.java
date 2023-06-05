package com.example.agendapersonalbeta.Modelo.Categoria;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.agendapersonalbeta.Modelo.AgendaBD;
import com.example.agendapersonalbeta.Modelo.Categoria.ClaseCategoria;

import java.util.ArrayList;
import java.util.List;

public class ModeloCategoria extends AgendaBD {

    Context context;

    public ModeloCategoria(@Nullable Context context) {
        super(context);
        this.context = context;
    }


    public void agregarCategoria(Integer id, String titulo, String descripcion){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null){
            ContentValues valores = new ContentValues();
            valores.put("ID", id);
            valores.put("TITULO", titulo);
            valores.put("DESCRIPCION", descripcion);
            bd.insert("CATEGORIA", null, valores);
            bd.close();
        }
    }

    public List<ClaseCategoria> mostrarCategorias(){
        SQLiteDatabase bd = getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM CATEGORIA", null);
        List<ClaseCategoria> categorias = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                categorias.add(new ClaseCategoria(Integer.valueOf(cursor.getString(0)),cursor.getString(1),cursor.getString(2)));
            } while (cursor.moveToNext());
        }
        return categorias;
    }

    public void buscarCategoria(ClaseCategoria contacto, Integer id){
        SQLiteDatabase bd = getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM CATEGORIA WHERE ID='"+id+"' ", null);
        if (cursor.moveToFirst()){
            do {
                contacto.setTitulo(cursor.getString(1));
                contacto.setDescripcion(cursor.getString(2));
            } while (cursor.moveToNext());
        }
    }

    public void editarCategoria(Integer id, String titulo, String descripcion) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            ContentValues valores = new ContentValues();
            valores.put("TITULO", titulo);
            valores.put("DESCRIPCION", descripcion);
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.update("CATEGORIA", valores, whereClause, whereArgs);
            bd.close();
        }
    }

    public void eliminarCategoria(Integer id) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("CATEGORIA", whereClause, whereArgs);
            bd.close();
        }
    }



}
