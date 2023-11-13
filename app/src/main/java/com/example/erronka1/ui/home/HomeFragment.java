package com.example.erronka1.ui.home;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.erronka1.R;
import com.example.erronka1.databinding.FragmentHomeBinding;
import com.example.erronka1.ui.CustomAdapter;
import com.example.erronka1.ui.CustomData;
import com.example.erronka1.ui.DatabaseHelper;
import com.example.erronka1.ui.PostgresKonexioa;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;
    private CustomAdapter customAdapter;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // ListView konfiguratu eta datuak jasotzeko funtzioa deitu
        listView = root.findViewById(R.id.listView);
        customAdapter = new CustomAdapter(getContext(), obtenerDatosDeLaTablaBezeroak());
        listView.setAdapter(customAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Bezeroen datuak jasotzeko funtzioa
    @SuppressLint("Range")
    private List<CustomData> obtenerDatosDeLaTablaBezeroak() {
        List<CustomData> dataList = new ArrayList<>();
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Eskatzen den zutabeak zehaztu
            String[] projection = {"id", "email", "izena", "enpresa"};

            // "bezeroak" taulan egin SQL kontsulta
            Cursor cursor = db.query("bezeroak", projection, null, null, null, null, null);

            // Kurtsorearen bidez iteratu eta datuak lista batera gehitu
            while (cursor.moveToNext()) {
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("id"));
                Map<String, Object> fields = new HashMap<>();
                fields.put("email", cursor.getString(cursor.getColumnIndex("email")));
                fields.put("izena", cursor.getString(cursor.getColumnIndex("izena")));
                fields.put("enpresa", cursor.getString(cursor.getColumnIndex("enpresa")));

                CustomData customData = new CustomData(id, fields);
                dataList.add(customData);
            }

            cursor.close();
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }
}
