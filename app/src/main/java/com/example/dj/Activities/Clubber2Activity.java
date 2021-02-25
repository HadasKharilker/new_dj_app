package com.example.dj.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.example.dj.Fragments.DjsListFragment;
import com.example.dj.Fragments.FeedbackFragment;
import com.example.dj.Fragments.SongRequestFragment;
import com.example.dj.R;

public class Clubber2Activity extends AppCompatActivity {
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubber2);

        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.request,new FeedbackFragment()).commit();
    }


    public void inputfeedback(View view) {
        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.request,new FeedbackFragment()).commit();
    }

    public void songrequest(View view) {
        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.request,new SongRequestFragment()).commit();
    }
}