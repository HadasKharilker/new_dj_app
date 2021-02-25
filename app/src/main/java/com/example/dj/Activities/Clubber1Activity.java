package com.example.dj.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dj.Fragments.ClubsListFragment;
import com.example.dj.Fragments.DjsListFragment;
import com.example.dj.Fragments.GanreListFragment;
import com.example.dj.R;
import android.os.Bundle;
import android.view.View;

public class Clubber1Activity extends AppCompatActivity {//replace AppCompatActivity to FragmentActivity

    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubber1);//הפעלה של activity_clubber ... ניתן להפעיל פרגמנט או LAYOUT אחר

      fragmentManager = getSupportFragmentManager();// transit fragment to activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentclubber,new DjsListFragment()).commit();

    }

    public void loadDJsList(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentclubber,new DjsListFragment()).addToBackStack(null).commit();
    }

    public void loadClubsList(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();//פתיחת טרנזקציה
        fragmentTransaction.replace(R.id.fragmentclubber,new ClubsListFragment()).addToBackStack(null).commit();
    }

    public void loadGanreList(View view) {
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentclubber,new GanreListFragment()).addToBackStack(null).commit();
    }
}