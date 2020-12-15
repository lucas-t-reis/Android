package com.example.gestaocheckin;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;


public class PlacesAdapter extends ArrayAdapter<String> {
    public PlacesAdapter(Context context, List<String> places) {
        super(context, 0, places);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        String info = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_list, parent, false);
        }

        TextView place = (TextView) convertView.findViewById(R.id.item_list_place);
        TextView N = (TextView) convertView.findViewById(R.id.item_list_N);

        String name = info.split("_")[0];
        String nVisited = info.split("_")[1];

        place.setText(name);
        N.setText(nVisited);

        return convertView;
    }
}