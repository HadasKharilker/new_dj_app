package com.example.dj.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.dj.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;//firebase login with email and password
    SharedPreferences sharedPreferences;
    private final String KEY1 = "LoginKeyName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();



    }



    public void funcLogIn(View view) {

        //finding the email Editiew to t
        EditText t =findViewById(R.id.email1);
        //casting the text view to string
        String email=t.getText().toString();
        //finding the email Editiew to t
        t =findViewById(R.id.password1);
        //casting the text view to string
        String password=t.getText().toString();

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
                            editor.putString("keyUser" , email);
                            editor.putString("keyPass" , password);
                            editor.apply();


                            FirebaseUser user = mAuth.getCurrentUser();
                            //moving to calc layout if the login is succesfull
                            // funcButtonToMainActivity( );




                        } else {
                            // If sign in fails, display a message to the user.

                            Toast.makeText(LoginActivity.this, "Email or Password is incoreect.",
                                    Toast.LENGTH_LONG).show();

                        }

                        // ...
                    }
                });



    }



    //moving to new layout when clicking on Register button
    public void funcButtonToSignUp(View view) {


        Intent intent = new Intent(this, SignUpActivity.class);




        startActivity(intent);

    }

}