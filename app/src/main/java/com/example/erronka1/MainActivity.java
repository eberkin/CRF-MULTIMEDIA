package com.example.erronka1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.erronka1.ui.Konexioa;

import java.sql.Connection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button loginButton = findViewById(R.id.loginButton);
        final EditText usernameEditText = findViewById(R.id.editTextText);
        final EditText passwordEditText = findViewById(R.id.editTextTextPassword);

        loginButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String username = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();

                try {
                    Intent intent = new Intent(MainActivity.this, MainActivity2.class);

                    Konexioa konexioa = new Konexioa(username,password);
                    Connection konexioEgokia = konexioa.connectToDatabase();

                    if (konexioEgokia != null){
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