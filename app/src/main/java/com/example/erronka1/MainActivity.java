package com.example.erronka1;

import static java.security.AccessController.getContext;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.erronka1.ui.DatabaseHelper;
import com.example.erronka1.ui.Konexioa;
import com.example.erronka1.ui.SQLiteDataHandler;

import java.sql.Connection;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Sortu SQLiteDataHandler instantzia eta ireki datu-basearen instantzia.
        SQLiteDataHandler sqliteDataHandler = new SQLiteDataHandler(this);
        sqliteDataHandler.open();

        // Deitu metodoa taulako datu guztiak ezabatzeko.
        sqliteDataHandler.deleteAllData();

        // Itxi SQLite datubasea
        sqliteDataHandler.close();

        Button loginButton = findViewById(R.id.loginButton);
        final EditText usernameEditText = findViewById(R.id.editTextText);
        final EditText passwordEditText = findViewById(R.id.editTextTextPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                try {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);

                    // Sortu Konexioa instantzia, eman oraindik erabiltzaile-izena eta pasahitza.
                    Konexioa konexioa = new Konexioa(MainActivity.this, username, password);

                    if (konexioa.status){
                        startActivity(intent);
                    }
                    else {
                        Toast.makeText(MainActivity.this, "Erabiltzaile edo pasahitz okerra", Toast.LENGTH_SHORT).show();
                    }

                }
                catch (Exception e){
                    Toast.makeText(MainActivity.this, "Erabiltzaile edo pasahitz desegokia", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}