package com.example.dj.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dj.Fragments.DjsFeedbacksListFragment;
import com.example.dj.Fragments.DjsListFragment;
import com.example.dj.Fragments.DjsRequestedSongsFragment;
import com.example.dj.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class DjActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private final String KEY1 = "LoginKeyName";
    TextView textView;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dj);
        userId = getIntent().getStringExtra("KEY1");


        //display welcome username message in the userName textview
        setWelcomeMessage();

        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("data", userId);//put string, int, etc in bundle with a key value
        DjsFeedbacksListFragment fragInfo= new DjsFeedbacksListFragment();
        fragInfo.setArguments(data);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_dj,fragInfo).commit();
    }

    public void loadRequestedSongssList(View view) {
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("data", userId);//put string, int, etc in bundle with a key value
        DjsRequestedSongsFragment fragInfo= new DjsRequestedSongsFragment();
        fragInfo.setArguments(data);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_dj,fragInfo).commit();
    }

    public void loadFeedbacksView (View view) {
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("data", userId);//put string, int, etc in bundle with a key value
        DjsFeedbacksListFragment fragInfo= new DjsFeedbacksListFragment();
        fragInfo.setArguments(data);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_dj,fragInfo).commit();
    }

    private void setWelcomeMessage() {
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
                TextView e = findViewById(R.id.nameOfDJ);
                e.setText("Welcome DJ " +value.getFullName());
                Toast.makeText(DjActivity.this, "DB reading OK.",
                        Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Toast.makeText(DjActivity.this, "DB reading  failed.",
                        Toast.LENGTH_LONG).show();
            }
        });


    }

    public String getUserId() {
        return userId;
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