package com.example.erronka1.ui.slideshow;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.erronka1.R;
import com.example.erronka1.databinding.FragmentSlideshowBinding;
import com.example.erronka1.ui.CustomAdapter;
import com.example.erronka1.ui.CustomData;
import com.example.erronka1.ui.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SlideshowFragment extends Fragment {

    private FragmentSlideshowBinding binding;
    private CustomAdapter customAdapter;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        SlideshowViewModel slideshowViewModel =
                new ViewModelProvider(this).get(SlideshowViewModel.class);

        // Infla eta konfiguratzen ditu "FragmentSlideshowBinding" objektua
        binding = FragmentSlideshowBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        // Listview eta CustomAdapter objektuak hasieratzen dira
        listView = root.findViewById(R.id.listView3);
        customAdapter = new CustomAdapter(getContext(), obtenerDatosDeLaTablaProduktuak());
        listView.setAdapter(customAdapter);

        // Aurreko orriaren kontsolatzeko fragment-a itxi
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // "FragmentSlideshowBinding" objektua hutsik uzten da
        binding = null;
    }

    @SuppressLint("Range")
    private List<CustomData> obtenerDatosDeLaTablaProduktuak() {
        List<CustomData> dataList = new ArrayList<>();
        try {
            // "DatabaseHelper" eta "SQLiteDatabase" objektuak sortzen dira
            DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Hautatu nahi dituzun zutabeak definitu
            String[] projection = {"id", "izena", "prezioa"};

            // "produktuak" taulan SQL kontsulta egin
            Cursor cursor = db.query("produktuak", projection, null, null, null, null, null);

            // Kurtsorea mugituz, datuak listan sartu
            while (cursor.moveToNext()) {
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("id"));
                Map<String, Object> fields = new HashMap<>();
                fields.put("izena", cursor.getString(cursor.getColumnIndex("izena")));
                fields.put("prezioa", cursor.getString(cursor.getColumnIndex("prezioa")));

                CustomData customData = new CustomData(id, fields);
                dataList.add(customData);
            }

            // Kurtsorea itxi
            cursor.close();
            // Datu-basearen konexioa itxi
            db.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dataList;
    }

}
