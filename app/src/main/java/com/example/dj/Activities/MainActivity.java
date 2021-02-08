package com.example.dj.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dj.Fragments.Clubber_Fragment;
import com.example.dj.R;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {//replace AppCompatActivity to FragmentActivity

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentFrame,new Clubber_Fragment()).commit();//add for new fragment,replace for extra fragment
    }
}