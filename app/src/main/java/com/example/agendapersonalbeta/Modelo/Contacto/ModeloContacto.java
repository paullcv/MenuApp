package com.example.agendapersonalbeta.Modelo.Contacto;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.agendapersonalbeta.Modelo.AgendaBD;

import java.util.ArrayList;
import java.util.List;

public class ModeloContacto extends AgendaBD implements IModeloContacto {

    Context context;

    public ModeloContacto(@Nullable Context context){
        super(context);
        this.context = context;
    }

    @Override
    public void agregarContacto(Integer id, String nombre,String correo, Integer telefono, String direccion){
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null){
            ContentValues valores = new ContentValues();
            valores.put("ID", id);
            valores.put("NOMBRE", nombre);
            valores.put("CORREO", correo);
            valores.put("TELEFONO", telefono);
            valores.put("DIRECCION", direccion);
            bd.insert("CONTACTO", null, valores);
            bd.close();
        }
    }

    @Override
    public List<ClaseContacto> mostrarContactos(){
        SQLiteDatabase bd = getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM CONTACTO", null);
        List<ClaseContacto> contactos = new ArrayList<>();
        if (cursor.moveToFirst()){
            do {
                contactos.add(new ClaseContacto(Integer.valueOf(cursor.getString(0)),cursor.getString(1),cursor.getString(2),Integer.valueOf(cursor.getString(3)),cursor.getString(4)));
            } while (cursor.moveToNext());
        }
        return contactos;
    }


    @Override
    public void buscarContacto(ClaseContacto contacto, Integer id){
        SQLiteDatabase bd = getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM CONTACTO WHERE ID='"+id+"' ", null);
        if (cursor.moveToFirst()){
            do {
                contacto.setNombre(cursor.getString(1));
                contacto.setCorreo(cursor.getString(2));
                contacto.setTelefono(Integer.valueOf(cursor.getString(3)));
                contacto.setDireccion(cursor.getString(4));
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void editarContacto(Integer id, String nombre, String correo, Integer telefono, String direccion) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            ContentValues valores = new ContentValues();
            valores.put("NOMBRE", nombre);
            valores.put("CORREO", correo);
            valores.put("TELEFONO", telefono);
            valores.put("DIRECCION", direccion);
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.update("CONTACTO", valores, whereClause, whereArgs);
            bd.close();
        }
    }

    @Override
    public void eliminarContacto(Integer id) {
        SQLiteDatabase bd = getWritableDatabase();
        if (bd != null) {
            String whereClause = "ID = ?";
            String[] whereArgs = {String.valueOf(id)};
            bd.delete("CONTACTO", whereClause, whereArgs);
            bd.close();
        }
    }



}
