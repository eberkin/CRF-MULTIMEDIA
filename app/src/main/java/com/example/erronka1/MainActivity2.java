package com.example.erronka1;


import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Menu;
import android.widget.ListView;

import com.example.erronka1.ui.Bezeroa;
import com.example.erronka1.ui.CustomAdapter;
import com.example.erronka1.ui.CustomData;
import com.example.erronka1.ui.DatabaseHelper;
import com.example.erronka1.ui.Konexioa;
import com.example.erronka1.ui.PostgresKonexioa;
import com.example.erronka1.ui.SQLiteDataHandler;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.erronka1.databinding.ActivityMain2Binding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity2 extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMain2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);


        // Sortu DatabaseHelper instantzia eta lortu datu-basearen instantzia.
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        SQLiteDatabase db = dbHelper.getReadableDatabase();


        // Lortu "bezeroak" taularen datuak datu-baseatik
        List<CustomData> data = lortuBezeroakTaulakoDatuak(db);

        // Sortu pertsonalizatutako adaptatzailearen instantzia
        CustomAdapter customAdapter = new CustomAdapter(this, data);

        // Lortu ListView erreferentzia
        ListView listView = findViewById(R.id.listView);

        // Ezarri pertsonalizatutako adaptatzailea ListView-an
        listView.setAdapter(customAdapter);

        binding.appBarMain.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ordezkatu zure ekintza", Snackbar.LENGTH_LONG)
                        .setAction("Ekintza", null).show();
            }
        });
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Menu bakoitzeko menu ID-ak ezarri, menu bakoitzak goialde-harrera bezala hartu behar dira.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Menua nabigatzen badu, ekintza-barruan elementuak gehitu.
        getMenuInflater().inflate(R.menu.main_activity2, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @SuppressLint("Range")
    private List<CustomData> lortuBezeroakTaulakoDatuak(SQLiteDatabase db) {

        List<CustomData> dataList = new ArrayList<>();

        try {
            // Eskatu berreskuratu nahi dituzun zutabeak
            String[] projection = {"id", "email", "izena", "enpresa"};

            // Burutu SQL kontsulta "bezeroak" taulan
            Cursor cursor = db.query("bezeroak", projection, null, null, null, null, null);

            // Kurtsorean iteratu eta datuak zerrendan gehitu
            while (cursor.moveToNext()) {
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("id"));
                Map<String, Object> fields = new HashMap<>();
                fields.put("email", cursor.getString(cursor.getColumnIndex("email")));
                fields.put("izena", cursor.getString(cursor.getColumnIndex("izena")));
                fields.put("enpresa", cursor.getString(cursor.getColumnIndex("enpresa")));

                CustomData customData = new CustomData(id, fields);
                dataList.add(customData);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}