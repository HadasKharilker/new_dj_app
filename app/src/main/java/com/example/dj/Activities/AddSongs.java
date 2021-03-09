package com.example.dj.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.dj.R;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddSongs extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_songs);
        addSong();
    }

    protected void addSong() {

        FirebaseDatabase database = FirebaseDatabase.getInstance();

        DatabaseReference myRef = database.getReference("songs").child("10");
        myRef.setValue("Jay Z - 99 Problems<");






    }




}