package com.example.sistem_pakar_cabai.ADAPTER;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.sistem_pakar_cabai.MODEL.Model_Spinner_Gejala;
import com.example.sistem_pakar_cabai.R;

import java.util.List;


public class Adapter_Spinner_Gejala extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<Model_Spinner_Gejala> item;

    public Adapter_Spinner_Gejala(Activity activity, List<Model_Spinner_Gejala> item) {
        this.activity = activity;
        this.item = item;
    }

    @Override
    public int getCount() {
        return item.size();
    }

    @Override
    public Object getItem(int location) {
        return item.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_gejala, null);

        TextView pendidikan = (TextView) convertView.findViewById(R.id.gejala);

        Model_Spinner_Gejala data;
        data = item.get(position);

        pendidikan.setText(data.getGejala());

        return convertView;
    }
}