package com.example.dj.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
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

import java.util.HashMap;
import java.util.Map;

public class DjActivity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private final String KEY1 = "LoginKeyName";
    private Spinner spinnerClubs;
    private String selectedClub;
    TextView textView;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dj);
        userId = getIntent().getStringExtra("KEY1");


        //display welcome username message in the userName textview
        setWelcomeMessage();
        //init clubs spinner with values
        initClubSpinner();
        //set the on updateClub listener


        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("data", userId);//put string, int, etc in bundle with a key value
        DjsFeedbacksListFragment fragInfo = new DjsFeedbacksListFragment();
        fragInfo.setArguments(data);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.add(R.id.fragment_dj, fragInfo).commit();
    }

    public void loadRequestedSongssList(View view) {
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("data", userId);//put string, int, etc in bundle with a key value
        DjsRequestedSongsFragment fragInfo = new DjsRequestedSongsFragment();
        fragInfo.setArguments(data);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_dj, fragInfo).commit();
    }

    public void loadFeedbacksView(View view) {
        Bundle data = new Bundle();//Use bundle to pass data
        data.putString("data", userId);//put string, int, etc in bundle with a key value
        DjsFeedbacksListFragment fragInfo = new DjsFeedbacksListFragment();
        fragInfo.setArguments(data);
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_dj, fragInfo).commit();
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
                e.setText("Welcome DJ " + value.getFullName());
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

    public void initClubSpinner() {
        //init spinner values
        spinnerClubs = (Spinner) findViewById(R.id.spinnerclubs);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.clubs_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerClubs.setAdapter(adapter);


        //on item selected listener
        spinnerClubs.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedClub = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedClub = "Havana Music Club";
            }

        });


    }

    public void updateClubButton(View view) {

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        //going to the relevant branch in the db
        final DatabaseReference myRef = database.getReference("djClub");
        ValueEventListener eventListener1 = (new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //check if a club already exist
                if (snapshot.hasChild(userId)) {
                    for (DataSnapshot ds : snapshot.getChildren()) {
                        if(!selectedClub.equals("choose from list")){

                            database.getReference("djClub").child(userId).setValue(selectedClub);
                            Toast.makeText(DjActivity.this, "Your home club has been updated!", Toast.LENGTH_SHORT).show();

                        }
                        else{
                            Toast.makeText(DjActivity.this, "Please Select a valid club from the list", Toast.LENGTH_SHORT).show();
                        }




                    }


                }
                //in case this is the first playlist
                else {
                    if(!selectedClub.equals("choose from list")){
                        //sending the playlist object to database
                        database.getReference("djClub").child(userId).setValue(selectedClub);
                        Toast.makeText(DjActivity.this, "Your home club is set!", Toast.LENGTH_SHORT).show();

                    }
                    else {
                        Toast.makeText(DjActivity.this, "Please Select a valid club from the list", Toast.LENGTH_SHORT).show();
                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        myRef.addListenerForSingleValueEvent(eventListener1);
    }
}