package com.example.erronka1.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.erronka1.R;
import java.util.List;
import java.util.Map;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private List<CustomData> data;

    public CustomAdapter(Context context, List<CustomData> data) {
        this.context = context;
        this.data = data;
    }

    // Elementu kopurua itzuli
    @Override
    public int getCount() {
        return data.size();
    }

    // Posizio bateko elementua itzuli
    @Override
    public CustomData getItem(int position) {
        return data.get(position);
    }

    // Posizio bateko elementuaren identifikatzailea itzuli
    @Override
    public long getItemId(int position) {
        return data.get(position).getId(); //
    }

    // View baten konfigurazioa datuak erabiliz
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item_layout, parent, false);
        }

        // Vista datu egokiekin konfiguratu
        TextView textView1 = convertView.findViewById(R.id.text1);
        TextView textView2 = convertView.findViewById(R.id.text2);
        TextView textView3 = convertView.findViewById(R.id.text3);
        TextView textView4 = convertView.findViewById(R.id.text4);
        CustomData item = data.get(position);

        Map<String, Object> fields = item.getFields();

        // Taularen arabera, datu egokienak erakutsi
        textView1.setText((CharSequence) fields.get("izena"));
        textView2.setText((CharSequence) fields.get("enpresa"));
        textView3.setText((CharSequence) fields.get("emaila"));
        textView4.setText((CharSequence) fields.get("prezioa"));

        return convertView;
    }
}