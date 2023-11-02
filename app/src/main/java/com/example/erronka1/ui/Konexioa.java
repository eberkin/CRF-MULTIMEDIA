package com.example.erronka1.ui;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class Konexioa {
    //Konexioa burutzeko aldagaiak adierazi
    private static final String DB_URL = "jdbc:postgresql://10.23.29.12:5432/crf_datubasea";
    private static String USER;
    private static String PASSWORD;

    public  Konexioa (String user, String password){

        this.USER = user;
        this.PASSWORD = password;
    }
    public static Connection connectToDatabase() {
        Connection connection = null;

        try {
            // Postgre erabiltzeko driverra adierazi
            Class.forName("org.postgresql.Driver");

            // Datubasera konexioa inizializatu
            connection = DriverManager.getConnection(DB_URL, USER, PASSWORD);
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

