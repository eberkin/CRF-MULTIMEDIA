package com.example.erronka1.ui;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.example.erronka1.ui.DatabaseHelper;

public class SQLiteDataHandler {
    private Context context;
    private DatabaseHelper dbHelper;
    private SQLiteDatabase database;

    public SQLiteDataHandler(Context context) {
        this.context = context;
    }

    // Metatutako datu-basearen manipulazioa egiteko konexioa ireki.
    public void open() throws SQLException {
        dbHelper = new DatabaseHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Datu-basearen konexioa itxi.
    public void close() {
        dbHelper.close();
    }

    // Bezeroa datu-basean sartu.
    public long insertBezeroa(String name, String email, String mobile, String commercialCompanyName) {
        ContentValues values = new ContentValues();
        values.put("email", email);
        values.put("izena", name);
        values.put("enpresa", commercialCompanyName);
        values.put("mobile", mobile);

        return database.insert("bezeroak", null, values);
    }

    // Komertziala datu-basean sartu.
    public long insertKomertziala(String name, String email) {
        ContentValues values = new ContentValues();
        values.put("emaila", email);
        values.put("izena", name);

        return database.insert("komertzialak", null, values);
    }

    // Produktua datu-basean sartu.
    public long insertProduktua(String name, String list_price) {
        ContentValues values = new ContentValues();
        values.put("prezioa", list_price + "€");
        values.put("izena", name);

        return database.insert("produktuak", null, values);
    }

    // Datu-baseko taula guztietako datu guztiak ezabatu.
    public void deleteAllData() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // "bezeroak" taulako erregistro guztiak ezabatu
        db.delete("bezeroak", null, null);
        // "komertzialak" taulako erregistro guztiak ezabatu
        db.delete("komertzialak", null, null);
        // "produktuak" taulako erregistro guztiak ezabatu

        db.delete("produktuak", null, null);

        // Datu-basearen konexioa itxi
        db.close();
    }
}
