package com.example.erronka1.ui;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "androidCRF.db";
    private static final int DATABASE_VERSION = 1;
    private Context context; // Agrega una variable de instancia para almacenar el contexto.

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context; // Asigna el contexto al campo de instancia.
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Intenta crear la tabla hornitzaileak
            db.execSQL("CREATE TABLE hornitzaileak (" +
                    "id INTEGER PRIMARY KEY," +
                    "cif TEXT," +
                    "izena TEXT," +
                    "helbidea TEXT," +
                    "herria TEXT" +
                    ");");

            // Intenta crear la tabla bezeroak
            db.execSQL("CREATE TABLE bezeroak (" +
                    "id INTEGER PRIMARY KEY," +
                    "nan TEXT," +
                    "izena TEXT," +
                    "abizena TEXT" +
                    ");");

            // Intenta crear la tabla komertzialak
            db.execSQL("CREATE TABLE komertzialak (" +
                    "id INTEGER PRIMARY KEY," +
                    "izena TEXT," +
                    "abizena TEXT" +
                    ");");
        } catch (SQLException e) {
            // Captura la excepción si las tablas ya existen, y maneja el caso según tus necesidades.
            e.printStackTrace(); // Puedes imprimir un mensaje o realizar otras acciones aquí.
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Si necesitas realizar actualizaciones en la base de datos, hazlo aquí.
    }
}
