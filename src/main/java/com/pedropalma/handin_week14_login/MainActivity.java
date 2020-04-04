package com.pedropalma.handin_week14_login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
//todo
// implement authentication for notes app
// use delete icon or delete swipe in recycler view instead of delete button in details activity
// include up button to come back to parent activity
// main activity is the signup activity. new user added to firestore
// login activity if user already exists
// home activity will be accessible if login is successful. it will be the main view (recycler view
// list)of the app
// status for logged in and have a log out option available in entire app (create toolbar and implement feature there)

public class MainActivity extends AppCompatActivity {
    EditText email, password;
    Button btnSignUp;
    TextView tvSignIn;
    FirebaseAuth mFirebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnSignUp = findViewById(R.id.button2);
        tvSignIn = findViewById(R.id.textView);
        //handle sign up button click
        signUp();
        //handle sign in click
        signIn();

    }

    private void signUp() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pwd = password.getText().toString();
                if (mail.isEmpty()) {
                    // error message if email input field  is left empty
                    email.setError("Please enter user email");
                    email.requestFocus();
                } else if (pwd.isEmpty()) {
                    // error message if password input field is left empty
                    password.setError("Please enter user password");
                    password.requestFocus();
                } else if (mail.isEmpty() && pwd.isEmpty()) {
                    // error message if both fields are left empty
                    Toast.makeText(MainActivity.this, "Fields are empty",
                            Toast.LENGTH_SHORT).show();
                } else if (!(mail.isEmpty() && pwd.isEmpty())) {
                    // create user and save it to firebase
                    mFirebaseAuth.createUserWithEmailAndPassword(mail, pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(MainActivity.this, "Singup unsuccessful, please try again!", Toast.LENGTH_SHORT).show();
                            } else {
                                // signup successful, forwards to home activity which will and creates a new user in firebase
                                // be the main view of the app
                                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                                startActivity(intent);
                            }
                        }
                    });
                } else {
                    Toast.makeText(MainActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void signIn() {
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }
}


