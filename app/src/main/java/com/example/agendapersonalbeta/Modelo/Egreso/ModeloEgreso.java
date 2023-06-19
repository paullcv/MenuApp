package com.example.agendapersonalbeta.Modelo.Egreso;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import androidx.annotation.Nullable;

import com.example.agendapersonalbeta.Modelo.Ingreso.ClaseIngreso;
import com.example.agendapersonalbeta.Modelo.Ingreso.ModeloTransaccion;

import java.util.ArrayList;
import java.util.List;

public class ModeloEgreso extends ModeloTransaccion {

    Context context;

    public ModeloEgreso(@Nullable Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void insertarRegistro(SQLiteDatabase bd, ContentValues valores) {
        bd.insert("EGRESO", null, valores);
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

    @Override
    protected void editarOperacion(SQLiteDatabase bd, Integer id, Double monto, String nombre, String fecha, Integer categoriaId) {
        ContentValues valores = new ContentValues();
        valores.put("MONTO", monto);
        valores.put("NOMBRE", nombre);
        valores.put("FECHA", fecha);
        valores.put("CATEGORIA_ID", categoriaId);
        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(id)};
        bd.update("EGRESO", valores, whereClause, whereArgs);
    }

    @Override
    protected void eliminarOperacion(SQLiteDatabase bd, Integer id) {
        String whereClause = "ID = ?";
        String[] whereArgs = {String.valueOf(id)};
        bd.delete("EGRESO", whereClause, whereArgs);
    }

    @Override
    protected String obtenerNombreTabla() {
        return "EGRESO";
    }
}
