package com.example.android.moneymate2;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {

    private Context context;
    private int mResource;
    private int dropDownResource;
    private List<String> items;

    public CustomAdapter(@NonNull Context context, int mResource, int dropDownResource, List<String> items) {
        super(context, mResource, items);
        this.context = context;
        this.mResource = mResource;
        this.dropDownResource = dropDownResource;
        this.items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(mResource, parent, false);
        TextView spinnerCurrency = view.findViewById(R.id.spinnerTitle);
        spinnerCurrency.setText(items.get(position));
        spinnerCurrency.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        spinnerCurrency.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        return view;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(dropDownResource, parent, false);
        TextView spinnerCurrency = view.findViewById(R.id.spinnerTitle);
        spinnerCurrency.setText(items.get(position));
        spinnerCurrency.setTextColor(ContextCompat.getColor(context, R.color.colorPrimaryDark));
        spinnerCurrency.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPrimary));
        return view;
    }
}
