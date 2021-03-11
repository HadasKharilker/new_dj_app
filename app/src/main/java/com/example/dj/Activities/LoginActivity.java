package com.example.dj.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.dj.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.regex.Pattern;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;//firebase login with email and password
    SharedPreferences sharedPreferences;
    private final String KEY1 = "LoginKeyName";
    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^" +
                    "(?=.*[0-9])" +  // at list 1 digit
                    "(?=\\S+$)" +  // no white spaces
                    ".{6,}" + // at list 6 characters minimum
                    "$");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        checkForPreferences(this);


    }


    public void funcLogIn(View view) {

        //finding the email Editiew to t
        EditText t = findViewById(R.id.email1);
        //casting the text view to string
        String email = t.getText().toString();
        //finding the email Editiew to t
        //Email Validate
        if (email.isEmpty()) {
            t.setError("Email can't be empty");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            t.setError("Please enter a valid email address");
        }

        t = findViewById(R.id.password1);
        //casting the text view to string
        String password = t.getText().toString();




        //PassWord Validate
        if (password.isEmpty()) {
            t.setError("Password can't be empty");
        } else if (!PASSWORD_PATTERN.matcher(password).matches()) {
            t.setError("Password is not valid");
        }


        if (!email.isEmpty() && !password.isEmpty()) {


            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Toast.makeText(LoginActivity.this, "Login Successfull.",
                                        Toast.LENGTH_LONG).show();

                                //save the email and password to to cookie (shared preferences)
                                sharedPreferences = getPreferences(MODE_PRIVATE);

                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("keyUser", email);
                                editor.putString("keyPass", password);
                                editor.apply();


                                FirebaseUser user = mAuth.getCurrentUser();
                                String uid = user.getUid();
                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

                                // Read User Object from the database
                                myRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // This method is called once with the initial value and again
                                        // whenever data at this location is updated.
                                        User value = dataSnapshot.getValue(User.class);
                                        if (value.getUserType() == "Clubber") {
                                            funcButtonToClubber1Activity();

                                        } else {
                                            funcButtonToDjActivity(uid);
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        // Failed to read value
                                        Toast.makeText(LoginActivity.this, "DB reading  failed.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });


                            } else {
                                // If sign in fails, display a message to the user.

                                Toast.makeText(LoginActivity.this, "Email or Password is incoreect.",
                                        Toast.LENGTH_LONG).show();

                            }

                            // ...
                        }
                    });


        }
    }

    private void funcButtonToDjActivity(String uid) {
        Intent intent = new Intent(this, DjActivity.class);
        intent.putExtra("KEY1",uid);


        startActivity(intent);
    }



    //moving to new layout when clicking on Register button
    public void funcButtonToSignUp(View view) {


        Intent intent = new Intent(this, SignUpActivity.class);



        startActivity(intent);

    }


    //moving to main activity of the app when clicking
    public void funcButtonToClubber1Activity() {//CHANGE TO MOVING TO CLUBBER1ACTIVITY


        Intent intent = new Intent(this, Clubber1Activity.class);


        startActivity(intent);

    }


    public void checkForPreferences(LoginActivity view) {


        sharedPreferences = getPreferences(MODE_PRIVATE);

        //checking for valid cookie, if it exists then logining in automaticly to firebase and mooving to Main Activity layout
        if (sharedPreferences.getString("keyUser", null) != null) {
            //sign in to firebase
            mAuth.signInWithEmailAndPassword(sharedPreferences.getString("keyUser", null), sharedPreferences.getString("keyPass", null))
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                //get the user object
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                                //extract the user id
                                String uid = user.getUid();

                                DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users").child(uid);

                                // Read User Object from the database
                                myRef.addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        // This method is called once with the initial value and again
                                        // whenever data at this location is updated.
                                        User value = dataSnapshot.getValue(User.class);
                                        if(value.getUserType().equals("Clubber")){
                                            funcButtonToClubber1Activity();

                                        }
                                        else{
                                            funcButtonToDjActivity(value.getId());
                                        }
                                    }

                                    @Override
                                    public void onCancelled(DatabaseError error) {
                                        // Failed to read value
                                        Toast.makeText(LoginActivity.this, "DB reading  failed.",
                                                Toast.LENGTH_LONG).show();
                                    }
                                });



                                Toast.makeText(LoginActivity.this, uid,
                                        Toast.LENGTH_LONG).show();


                            }
                        }
                    });
        }


    }
}