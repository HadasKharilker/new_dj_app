package com.example.dj.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dj.Fragments.DjsFeedbacksListFragment;
import com.example.dj.Fragments.FeedbackFragment;
import com.example.dj.Fragments.SongRequestFragment;
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

public class Clubber2Activity extends AppCompatActivity {
    private FragmentManager fragmentManager;
    private final String KEY2 = "Clubber1ActivityKeyName";
    private String nameOfDj;
    String djID;
    TextView textView;
    private static String userName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_clubber2);

        nameOfDj = getIntent().getStringExtra("KEY2");//getting the name of the Dj , that the user choose in the djs list fragment
        textView = findViewById(R.id.nameOfDJ);
        textView.setText("DJ " + nameOfDj);

        setDjRating();//display the current average dj rating
        moveToFeedbackFragment();//setting default fragment to feedback fragment


    }


    public void inputfeedback(View view) {
        //get the user object
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        //extract the user id
        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(uid);


        // Read User Object from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                fragmentManager = getSupportFragmentManager();
                User value = dataSnapshot.getValue(User.class);
                userName = value.getFullName();
                Bundle data = new Bundle();//Use bundle to pass data
                data.putString("data", userName);//put string, int, etc in bundle with a key value
                FeedbackFragment fragInfo = new FeedbackFragment();
                fragInfo.setArguments(data);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_dj, fragInfo).commit();


            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });

    }

    public void songrequest(View view) {
        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_dj, new SongRequestFragment()).commit();
    }

    public String getNameOfDj() {

        return nameOfDj;
    }

    public void moveToFeedbackFragment() {
        //get the current user object
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        //extract the user id
        String uid = user.getUid();

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("users").child(uid);


        // Read User Object from the database
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                fragmentManager = getSupportFragmentManager();
                User value = dataSnapshot.getValue(User.class);
                userName = value.getFullName();
                Bundle data = new Bundle();//Use bundle to pass data
                data.putString("data", userName);//put string, int, etc in bundle with a key value
                FeedbackFragment fragInfo = new FeedbackFragment();//creating new instance of feedback Fragment
                fragInfo.setArguments(data);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_dj, fragInfo).commit();


            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });


    }

    public void setDjRating() {

        DatabaseReference rootRef = FirebaseDatabase.getInstance().getReference();
        DatabaseReference songsRef = rootRef.child("users");//users branch reference from firebase database

        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for (DataSnapshot ds : dataSnapshot.getChildren()) {//for each LOOP running through all songs

                    String fullName = ds.child("fullName").getValue(String.class);
                    if (fullName.equals(nameOfDj)) { //
                        String djUID;
                        djUID = ds.child("id").getValue(String.class); //extrecting dj id


                        //instance of db
                        FirebaseDatabase database = FirebaseDatabase.getInstance();

                        //going to the relevant branch in the db
                        final DatabaseReference myRef = database.getReference("djFeedback");
                        DatabaseReference feedbackRoot = myRef.child(djUID);
                        ValueEventListener eventListener1 = (new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                    int count = 0;
                                    int totalStars = 0;
                                    float djAverage = 0;
                                    if(snapshot.hasChildren()) {


                                        for (DataSnapshot ds : snapshot.getChildren()) {
                                            Map<String, Object> mHashmap = new HashMap<>();
                                            mHashmap = (Map<String, Object>) ds.getValue();
                                            String rating = String.valueOf(mHashmap.get("rating"));

                                            totalStars += Integer.valueOf(rating);
                                            count += 1;

                                        }
                                        djAverage = totalStars / count;
                                        RatingBar rating = (RatingBar) findViewById(R.id.rating);
                                        rating.setRating(djAverage);

                                        textView=findViewById(R.id.numraters);
                                        textView.setText("("+String.valueOf(count)+" Clubbers)");
                                    }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        feedbackRoot.addListenerForSingleValueEvent(eventListener1);


                        //
                       final  DatabaseReference myRef2 = database.getReference("djClub");
                        //DatabaseReference clubRoot = myRef2.child(djUID);
                        ValueEventListener eventListener2 = (new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                textView=findViewById(R.id.club);

                                if(snapshot.hasChildren()) {
                                    for (DataSnapshot ds : snapshot.getChildren()) {
                                        if(ds.getKey().equals(djUID)){
                                            String club=(String)ds.getValue();
                                            textView.setText(club);
                                        }
                                    }
                                }
                                else{
                                    textView.setText("");

                                }


                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                        myRef2.addListenerForSingleValueEvent(eventListener2);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };
        songsRef.addListenerForSingleValueEvent(eventListener);


    }
}