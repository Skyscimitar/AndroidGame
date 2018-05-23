package com.example.danieltaylor.androidgame;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    String TAG = "Login";
    Button btnSignIn;
    Button btnSignUp;
    EditText emailTextInput;
    EditText passwordTextInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        setContentView(R.layout.activity_login);
        btnSignIn = findViewById(R.id.btn_signin);
        btnSignUp = findViewById(R.id.btn_signup);
        emailTextInput = findViewById(R.id.user_email);
        passwordTextInput = findViewById(R.id.user_password);


        //TODO control input to ensure it isn't void
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(emailTextInput.getText().toString(), passwordTextInput.getText().toString());
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpUser(emailTextInput.getText().toString(), passwordTextInput.getText().toString());
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();

        if (currentUser != null) {
            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
            startActivity(intent);
        }
    }


    private void signUpUser(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "created new user");
                            Intent intent= new Intent(LoginActivity.this, MainMenuActivity.class);
                        }
                        else {
                            Log.d(TAG, "User creation failed");
                            Log.d(TAG, task.getResult().toString());
                            Toast.makeText(getApplicationContext(), "Failed to create an account", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    private void signInUser(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "signed in successfully");
                            Intent intent = new Intent(LoginActivity.this, MainMenuActivity.class);
                        }
                        else {
                            Log.d(TAG, "failed to sign in");
                            Toast.makeText(getApplicationContext(), "Failed to sign in", Toast.LENGTH_SHORT).show();
                            Log.d(TAG, task.getResult().toString());
                        }
                    }
                });
    }
}
