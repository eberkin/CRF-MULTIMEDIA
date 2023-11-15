package com.example.erronka1.ui;

import android.content.Context;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class PostgresKonexioa extends AppCompatActivity {
    private static final String DB_URL = "jdbc:postgresql://10.23.29.12:8069/odoo";
    private static final String DB_USER = "odoo";
    private static final String DB_PASSWORD = "odoo";
    private Context context;

    public PostgresKonexioa(Context context) {
        this.context = context;
    }

    public void connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                Connection konex = null;

                try {
                    Class.forName("org.postgresql.Driver");
                    konex = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);

                    Toast.makeText(context, "Ondo konektatu da", Toast.LENGTH_SHORT).show();

                    // Recuperar datos de PostgreSQL
                    ArrayList<Bezeroa> bezeroZerrenda = B_DatuakGorde();

                    // Insertar los datos en la base de datos SQLite
                    SQLiteDataHandler sqliteDataHandler = new SQLiteDataHandler(context);
                    sqliteDataHandler.open();

                    for (Bezeroa bezeroa : bezeroZerrenda) {
                        sqliteDataHandler.insertBezeroa(bezeroa.getName(), bezeroa.getEmail(), bezeroa.getMobile(), bezeroa.getCommercialCompanyName());
                    }

                    sqliteDataHandler.close();

                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(context, "Ezin izan da konektatu", Toast.LENGTH_SHORT).show();
                }
            }
        });
        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Bezeroa> B_DatuakGorde() {
        String Sql = "Select * from public.res_partner";
        Connection konex = null;
        ArrayList<Bezeroa> bezeroZerrenda = new ArrayList<>();
        try {
            konex = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            Statement statement = konex.createStatement();

            ResultSet resultSet = statement.executeQuery(Sql);
            while (resultSet.next()) {
                Bezeroa bezeroa = new Bezeroa(
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getString("mobile"),
                        resultSet.getString("commercial_company_name")
                );
                bezeroZerrenda.add(bezeroa);
            }

        } catch (Exception e) {
            Toast.makeText(context, "Ezin izan dira bezeroen datuak eguneratu", Toast.LENGTH_SHORT).show();
        }
        return bezeroZerrenda;
    }
}
