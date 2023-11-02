package com.example.erronka1.ui;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "androidCRF.db";
    private static final int DATABASE_VERSION = 1;
    private Context context; // Kontextua gordetzeko aldagaia deklaratu.

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context; // Kontextua asignatu instantziari.
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            InputStream inputStream = context.getAssets().open("androidCRF.db");
            OutputStream outputStream = new FileOutputStream(db.getPath());
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Datubaseko aktualizazioak hemen adierazi
    }
}
