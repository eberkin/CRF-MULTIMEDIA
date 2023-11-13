package com.example.erronka1.ui;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.erronka1.ui.home.HomeFragment;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "androidCRF.db";
    private static final int DATABASE_VERSION = 1;
    private Context context; // Kontextua gordetzeko aldagaia deklaratu.

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context; // Kontextua asignatu instantziari.
    }

    // Datu-basea sortzen da, tabelak sortuz
    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            // Produktuen taula sortu
            db.execSQL("CREATE TABLE produktuak (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "izena TEXT," +
                    "prezioa TEXT" +
                    ");");

            // Bezeroen taula sortu
            db.execSQL("CREATE TABLE bezeroak (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "email TEXT ," +
                    "izena TEXT," +
                    "enpresa TEXT," +
                    "mobile INTEGER" +
                    ");");

            // Komertzialen taula sortu
            db.execSQL("CREATE TABLE komertzialak (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "izena TEXT," +
                    "emaila TEXT" +
                    ");");
        } catch (SQLException e) {
            // Taulak dagoeneko existitzen badira, salbuespena jaso eta behar bada kudeatu.
            e.printStackTrace(); // Hemen mezu bat inprimatu dezakezu edo beste ekintza batzuk egin.
        }
    }

    // Datu-basea eguneratzen da, bertsio aldaketa bat egiten bada
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Hemen datu-basearen eguneraketak adierazi
    }
}
