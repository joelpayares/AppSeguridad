package com.appseguridad;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class UsuariosSQLiteHelper extends SQLiteOpenHelper {

    String sentencia = "CREATE TABLE Usuarios (codigo TEXT, nombre TEXT" +
            ", celular INTEGER, correo TEXT, clave TEXT)";

    public UsuariosSQLiteHelper(@Nullable Context context, @Nullable String name,
                                @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sentencia);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //Se elimina la versión anterior de la tabla
        db.execSQL("DROP TABLE IF EXISTS Usuarios");

        //Se crea la nueva versión de la tabla
        db.execSQL(sentencia);
    }

    /*public Boolean Guardar(SQLiteDatabase db, ContentValues values){
        db.insert("Usuarios", null, values);

        db.close();

        return
    }*/
}
