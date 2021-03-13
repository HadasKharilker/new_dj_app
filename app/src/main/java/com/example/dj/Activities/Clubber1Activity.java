package com.example.dj.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.dj.Fragments.ClubsListFragment;
import com.example.dj.Fragments.DjsListFragment;
import com.example.dj.Fragments.GanreListFragment;
import com.example.dj.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Clubber1Activity extends AppCompatActivity {//replace AppCompatActivity to FragmentActivity

    private FragmentManager fragmentManager;
    private final String KEY1 = "LoginKeyName";
    private final String KEY2 = "Clubber1ActivityKeyName";
    //global text view object
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubber1);//הפעלה של activity_clubber ... ניתן להפעיל פרגמנט או LAYOUT אחר

        String result = getIntent().getStringExtra(KEY1);//accepting the activity change from login activity

        //display welcome username message in the userName textview
        textView = findViewById(R.id.name);
        textView.setText("");
        setWelcomeMessage();
        if (getIntent().getBooleanExtra("EXIT", false)) {
            finish();
        }


        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentclubber,new DjsListFragment()).commit();
    }


    //getting the full name of the current user from the DB and display it on the text view
    public void setWelcomeMessage() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String uid = user.getUid();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(uid);


        // Read User Object from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                User value = dataSnapshot.getValue(User.class);
                TextView e = findViewById(R.id.name);
                e.setText("Welcome  " +value.getFullName());
                Toast.makeText(Clubber1Activity.this, "DB reading OK.",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(Clubber1Activity.this, "DB reading  failed.",
                        Toast.LENGTH_LONG).show();
            }
        });


    }

    public void funcButtonToSignOut(View view) {
        FirebaseAuth.getInstance().signOut();

        //clean the SharedPreferences of this app
        SharedPreferences preferences = getApplicationContext().getSharedPreferences("keyUser", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
        finish();

        SharedPreferences preferences2 = getApplicationContext().getSharedPreferences("passUser", MODE_PRIVATE);
        SharedPreferences.Editor editor2 = preferences2.edit();
        editor2.clear();
        editor2.apply();
        finish();



    }


}