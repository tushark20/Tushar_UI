package com.chefling.project.tushar_ui;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomAdapter extends ArrayAdapter<String> {

    Typeface custom_font;

    public CustomAdapter(Context context, String[] items, Typeface tf) {
        super(context, R.layout.custom_row , items);
        custom_font = tf;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater tushInflator = LayoutInflater.from(getContext());
        View customView = tushInflator.inflate(R.layout.custom_row, parent, false);

        String singleItems = getItem(position);
        TextView tushText = (TextView) customView.findViewById(R.id.tushText);
        tushText.setTypeface(custom_font);
        tushText.setText(singleItems);

        return  customView;
    }
}
