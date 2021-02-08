package com.example.dj.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.dj.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;

    private final String KEY1 = "LoginKeyName";
    private Spinner spinnerU;
    private Spinner spinnerG;
    private String selectedUserType;
    private String selectedGenre;
    private ImageView mImageView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        mAuth = FirebaseAuth.getInstance();
        String result = getIntent().getStringExtra(KEY1);

        mImageView = (ImageView) findViewById(R.id.imageView2);
        mImageView.setImageResource(R.drawable.main);

        initUserTypeSpinner(this);
        initGenreSpinner(this);



    }



    //Working with realtime database
    public void funcSignUp(View view) {
        //Extracting the user data from the text view sections
        EditText t = findViewById(R.id.email2);
        String email = t.getText().toString();
        t = findViewById(R.id.password2);
        String password = t.getText().toString();
        t = findViewById(R.id.fullName);
        String fullName = t.getText().toString();



        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(SignUpActivity.this, "Sign Up OK.",
                                    Toast.LENGTH_SHORT).show();

                            //get the user object
                            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            //extract the user id
                            String uid = user.getUid();


                            // Write a message to the database
                            FirebaseDatabase database = FirebaseDatabase.getInstance();
                            //going to the relevant branch in the db
                            DatabaseReference myRef = database.getReference("users").child(uid);
                            //generate an object of type person
                            User u = new User(email, fullName, selectedUserType,selectedGenre);


                            //sending the person object to database
                            myRef.setValue(u);


                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(SignUpActivity.this, "Sign Up failed.",
                                    Toast.LENGTH_SHORT).show();

                        }

                        // ...
                    }
                });


    }


    public void initUserTypeSpinner(SignUpActivity view){
        //init spinner values
        spinnerU = (Spinner) findViewById(R.id.spinnerUserType);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.user_type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerU.setAdapter(adapter);



        //on item selected listener
        spinnerU.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedUserType = parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedUserType="DJ";
            }

        });


    }


    public void initGenreSpinner(SignUpActivity view){
        //init spinner values
        spinnerG = (Spinner) findViewById(R.id.spinnerGenre);
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.genre_type_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinnerG.setAdapter(adapter);



        //on item selected listener
        spinnerG.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                selectedGenre= parentView.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                selectedGenre="Trance";
            }

        });


    }


}

