package com.example.dj.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dj.Fragments.DjsListFragment;
import com.example.dj.Fragments.FeedbackFragment;
import com.example.dj.Fragments.SongRequestFragment;
import com.example.dj.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class Clubber2Activity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private final String KEY2 = "Clubber1ActivityKeyName";
    private String nameOfDj;
    String djID;
    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubber2);

        nameOfDj = getIntent().getStringExtra("KEY2");//getting the name of the Dj , that the user choose in the djs list fragment
        textView = findViewById(R.id.nameOfDJ);
        textView.setText("DJ " + nameOfDj);

        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.request, new FeedbackFragment()).commit();
    }


    public void inputfeedback(View view) {
        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.request, new FeedbackFragment()).commit();
    }

    public void songrequest(View view) {
        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.request, new SongRequestFragment()).commit();
    }

    public String getNameOfDj(){

        return nameOfDj;
    }


}
