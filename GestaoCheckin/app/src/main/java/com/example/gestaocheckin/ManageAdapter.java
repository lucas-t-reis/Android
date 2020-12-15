package com.example.gestaocheckin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;


public class ManageAdapter extends ArrayAdapter<String> {

    public ManageAdapter(Context context, List<String> places) {
        super(context, 0, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String info = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.manage_item_list, parent, false);
        }

        TextView place = (TextView) convertView.findViewById(R.id.item_list_place);

        String name = info;
        place.setText(name);

        ImageButton btn = (ImageButton) convertView.findViewById(R.id.item_list_deleteBtn);
        btn.setTag(name);

        return convertView;
    }
}