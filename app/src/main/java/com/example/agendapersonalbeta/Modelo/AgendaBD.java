package com.example.agendapersonalbeta.Modelo;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AgendaBD extends SQLiteOpenHelper {

    private static final String NOMBRE_BD = "agenda.db";
    private static final int VERSION_BD = 1;
    public static final String TABLA_CONTACTO="CREATE TABLE CONTACTO(ID INTEGER PRIMARY KEY , NOMBRE TEXT, CORREO TEXT, TELEFONO TEXT UNIQUE, DIRECCION TEXT)";

    public AgendaBD(@Nullable Context context){
        super(context, NOMBRE_BD, null, VERSION_BD );
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(TABLA_CONTACTO);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLA_CONTACTO);
        sqLiteDatabase.execSQL(TABLA_CONTACTO);
        onCreate(sqLiteDatabase);
    }
}
