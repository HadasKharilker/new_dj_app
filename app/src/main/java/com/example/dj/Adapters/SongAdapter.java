package com.example.dj.Adapters;

import android.content.Context;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.dj.R;

import java.util.List;

public class SongAdapter extends ArrayAdapter<String>{
    private static final String TAG = SongAdapter.class.getSimpleName();

    List<String> songsList;

    public SongAdapter(Context context, int resource, List<String> objects) {
        super(context, resource, objects);

        songsList = objects;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        String currentSong = songsList.get(position);

        if(listItemView == null){
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.requested_song_list_item, parent, false);
        }

        TextView nameTextView = (TextView) listItemView.findViewById(R.id.song_text_view);
        nameTextView.setText(currentSong);




        return listItemView;
    }
}
