package com.example.agendapersonalbeta.Modelo.Ingreso;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;


public class ModeloIngreso extends ModeloTransaccion {

    Context context;

    public ModeloIngreso(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void insertarRegistro(SQLiteDatabase bd, ContentValues valores) {
        bd.insert("INGRESO", null, valores);
    }

    public List<ClaseIngreso> mostrarIngresos() {
        SQLiteDatabase bd = getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM INGRESO", null);
        List<ClaseIngreso> ingresos = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                ingresos.add(new ClaseIngreso(
                        Integer.valueOf(cursor.getString(0)),
                        Double.valueOf(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3),
                        Integer.valueOf(cursor.getString(4))
                ));
            } while (cursor.moveToNext());
        }
        return ingresos;
    }

    public void buscarIngreso(ClaseIngreso ingreso, Integer id) {
        SQLiteDatabase bd = getWritableDatabase();
        Cursor cursor = bd.rawQuery("SELECT * FROM INGRESO WHERE ID='" + id + "' ", null);
        if (cursor.moveToFirst()) {
            do {
                ingreso.setMonto(Double.valueOf(cursor.getString(1)));
                ingreso.setNombre(cursor.getString(2));
                ingreso.setFecha(cursor.getString(3));
                ingreso.setCategoria_id(Integer.valueOf(cursor.getString(4)));
            } while (cursor.moveToNext());
        }
    }

    @Override
    protected void editarOperacion(SQLiteDatabase bd, Integer id, Double monto, String nombre, String fecha, Integer categoriaId) {
        ContentValues valores = new ContentValues();
        valores.put("MONTO", monto);
        valores.put("NOMBRE", nombre);
        valores.put("FECHA", fecha);
        valores.put("CATEGORIA_ID", categoriaId);
        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(id)};
        bd.update("INGRESO", valores, whereClause, whereArgs);
    }

    @Override
    protected void eliminarOperacion(SQLiteDatabase bd, Integer id) {
        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(id)};
        bd.delete("INGRESO", whereClause, whereArgs);
    }

}
