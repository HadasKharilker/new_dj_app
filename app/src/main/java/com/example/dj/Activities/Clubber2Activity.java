package com.example.dj.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;
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
        moveToFeedbackFragment();

        //fragmentManager = getSupportFragmentManager();// transit fragment to activity
        //FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.add(R.id.fragment_dj, new FeedbackFragment()).commit();
    }


    public void inputfeedback(View view) {
        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_dj, new FeedbackFragment()).commit();
    }

    public void songrequest(View view) {
        fragmentManager = getSupportFragmentManager();// transit fragment to activity
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_dj, new SongRequestFragment()).commit();
    }

    public String getNameOfDj(){

        return nameOfDj;
    }

    public void moveToFeedbackFragment(){
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
                userName= value.getFullName();
                Bundle data = new Bundle();//Use bundle to pass data
                data.putString("data", userName);//put string, int, etc in bundle with a key value
                FeedbackFragment fragInfo= new FeedbackFragment();
                fragInfo.setArguments(data);
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.add(R.id.fragment_dj,fragInfo).addToBackStack(null).commit();


            }


            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value

            }
        });



    }




}
