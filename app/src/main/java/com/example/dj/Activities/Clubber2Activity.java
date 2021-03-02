package com.example.dj.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.dj.Fragments.DjsListFragment;
import com.example.dj.Fragments.FeedbackFragment;
import com.example.dj.Fragments.SongRequestFragment;
import com.example.dj.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Clubber2Activity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private final String KEY2 = "Clubber1ActivityKeyName";
    private String nameOfDj;
    private String djID;
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubber2);

        nameOfDj = getIntent().getStringExtra("KEY2");//getting the name of the Dj , that the user choose in the djs list fragment
        textView = findViewById(R.id.nameOfDJ);
        textView.setText("DJ "+nameOfDj);

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

    //get dj UID from database' based on username
    public String getDjUserId(){
        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference usersRef = rootRef.child("users");//users branch reference from firebase database
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot ds : dataSnapshot.getChildren()) {//for each LOOP running on all users


                    if((ds.child("fullName").getValue(String.class))==nameOfDj) {
                        djID = String.valueOf(ds);

                    }

                }


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        usersRef.addListenerForSingleValueEvent(eventListener);
        return djID;
    }


}