package com.example.erronka1.ui.gallery;

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
import com.example.erronka1.databinding.FragmentGalleryBinding;
import com.example.erronka1.ui.CustomAdapter;
import com.example.erronka1.ui.CustomData;
import com.example.erronka1.ui.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GalleryFragment extends Fragment {

    private FragmentGalleryBinding binding;
    private CustomAdapter customAdapter;
    private ListView listView;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        GalleryViewModel galleryViewModel =
                new ViewModelProvider(this).get(GalleryViewModel.class);

        binding = FragmentGalleryBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        listView = root.findViewById(R.id.listView2);
        customAdapter = new CustomAdapter(getContext(), obtenerDatosDeLaTablaKomertzialak());
        listView.setAdapter(customAdapter);

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    // Komertzialen datuak jasotzeko funtzioa
    @SuppressLint("Range")
    private List<CustomData> obtenerDatosDeLaTablaKomertzialak() {
        List<CustomData> dataList = new ArrayList<>();
        try {
            DatabaseHelper dbHelper = new DatabaseHelper(requireContext());
            SQLiteDatabase db = dbHelper.getReadableDatabase();

            // Eskatzen den zutabeak zehaztu
            String[] projection = {"id", "izena", "emaila"};

            // "komertzialak" taulan egin SQL kontsulta
            Cursor cursor = db.query("komertzialak", projection, null, null, null, null, null);

            // Kurtsorearen bidez iteratu eta datuak lista batera gehitu
            while (cursor.moveToNext()) {
                @SuppressLint("Range") long id = cursor.getLong(cursor.getColumnIndex("id"));
                Map<String, Object> fields = new HashMap<>();
                fields.put("izena", cursor.getString(cursor.getColumnIndex("izena")));
                fields.put("emaila", cursor.getString(cursor.getColumnIndex("emaila")));

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
