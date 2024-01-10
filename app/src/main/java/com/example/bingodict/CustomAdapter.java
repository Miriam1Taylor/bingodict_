package com.example.bingodict;

// CustomAdapter.java
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends ArrayAdapter<String> {

    public CustomAdapter(Context context, List<String> data) {
        super(context, R.layout.list_item_music, data);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            view = inflater.inflate(R.layout.list_item_music, parent, false);
        }

        ImageView iconImageView = view.findViewById(R.id.iconImageView);
        TextView musicTitleTextView = view.findViewById(R.id.musicTitleTextView);

        // Set the icon and text for each item
        iconImageView.setImageResource(R.mipmap.music_list); //音乐列表图标
        musicTitleTextView.setText(getItem(position));

        return view;
    }
}
