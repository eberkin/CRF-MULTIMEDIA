package com.example.erronka1.ui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import android.content.Context;
import android.widget.Toast;

import com.example.erronka1.MainActivity;

public class Konexioa {
    private static final String DB_URL = "jdbc:postgresql://10.23.29.12:5432/crf_datubasea";
    private static String USER;
    private static String PASSWORD;
    private final Context context;
    public boolean status = false;

    public Konexioa(Context context, String user, String password) {
        this.context = context;
        this.USER = user;
        this.PASSWORD = password;
        connect();
    }

    // SQLite datu-basean datuak gorde
    public static Connection connectToDatabase() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failure.");
            e.printStackTrace();
        }
        return connection;
    }

    // PostgreSQL datu-basearekin konexioa sortu
    private void connect() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("org.postgresql.Driver");
                    Connection connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);

                    // PostgreSQL datu-baseatik datuak berreskuratu
                    ArrayList<Bezeroa> bezeroZerrenda = B_DatuakGorde();
                    ArrayList<Komertziala> komertzialZerrenda = K_DatuakGorde();
                    ArrayList<Produktua> produktuZerrenda = P_DatuakGorde();

                    // SQLite datu-basean datuak sartu
                    SQLiteDataHandler sqliteDataHandler = new SQLiteDataHandler(context);
                    sqliteDataHandler.open();

                    for (Bezeroa bezeroa : bezeroZerrenda) {
                        sqliteDataHandler.insertBezeroa(bezeroa.getName(), bezeroa.getEmail(), bezeroa.getMobile(), bezeroa.getCommercialCompanyName());
                    }
                    for (Komertziala komertziala : komertzialZerrenda) {
                        sqliteDataHandler.insertKomertziala(komertziala.getName(), komertziala.getEmail());
                    }
                    for (Produktua produktua : produktuZerrenda) {
                        sqliteDataHandler.insertProduktua(produktua.getName(), produktua.getPrezioa());
                    }

                    sqliteDataHandler.close();
                    status = true;
                } catch (Exception e) {
                    status = false;
                    System.out.print(e.getMessage());
                    e.printStackTrace();
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

    // Bezeroen datuak berreskuratu (id 11, 12, 15, 17 baino)
    public ArrayList<Bezeroa> B_DatuakGorde() {
        String Sql = "SELECT * FROM public.res_partner WHERE id IN (11, 12, 15, 17)";
        Connection konex = null;
        ArrayList<Bezeroa> bezeroZerrenda = new ArrayList<>();
        try {
            konex = DriverManager.getConnection(DB_URL, USER, PASSWORD);
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

    // Komertzialen datuak berreskuratu (id 7, 18 baino)
    public ArrayList<Komertziala> K_DatuakGorde() {
        String Sql = "SELECT * FROM public.res_partner WHERE id IN (7,18)";
        Connection konex = null;
        ArrayList<Komertziala> komertzialZerrenda = new ArrayList<>();
        try {
            konex = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement statement = konex.createStatement();

            ResultSet resultSet = statement.executeQuery(Sql);
            while (resultSet.next()) {
                Komertziala komertziala = new Komertziala(
                        resultSet.getString("name"),
                        resultSet.getString("email")
                );
                komertzialZerrenda.add(komertziala);
            }

        } catch (Exception e) {
            Toast.makeText(context, "Ezin izan dira komertzialen datuak eguneratu", Toast.LENGTH_SHORT).show();
        }
        return komertzialZerrenda;
    }

    // Produktuen datuak berreskuratu
    public ArrayList<Produktua> P_DatuakGorde() {
        String Sql = "SELECT name,list_price  FROM public.product_template";
        Connection konex = null;
        ArrayList<Produktua> produktuZerrenda = new ArrayList<>();
        try {
            konex = DriverManager.getConnection(DB_URL, USER, PASSWORD);
            Statement statement = konex.createStatement();

            ResultSet resultSet = statement.executeQuery(Sql);
            while (resultSet.next()) {
                Produktua produktua = new Produktua(
                        resultSet.getString("name"),
                        resultSet.getString("list_price")

                );
                produktuZerrenda.add(produktua);
            }

        } catch (Exception e) {
            Toast.makeText(context, "Ezin izan dira bezeroen datuak eguneratu", Toast.LENGTH_SHORT).show();
        }
        return produktuZerrenda;
    }
}

