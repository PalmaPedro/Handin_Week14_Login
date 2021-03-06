package com.pedropalma.handin_week14_login.ui;

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
import com.pedropalma.handin_week14_login.R;
import com.pedropalma.handin_week14_login.storage.FirebaseManager;

public class SignupActivity extends AppCompatActivity {
    EditText email, password;
    Button btnSignUp;
    TextView tvLogin;
    FirebaseAuth mFirebaseAuth;
    FirebaseManager firebaseManager;
    LoginActivity loginActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        firebaseManager = new FirebaseManager(this);

        viewBuilder();
        //handle sign up button click
        signUp();
        //handle sign in click
        login();

    }

    private void viewBuilder() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnSignUp = findViewById(R.id.button2);
        tvLogin = findViewById(R.id.textView);
    }

    private void signUp() {
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail = email.getText().toString();
                String pwd = password.getText().toString();
                if (mail.isEmpty()) {
                    validateEmail();
                } else if (pwd.isEmpty()) {
                    validatePassword();
                } else if (mail.isEmpty() && pwd.isEmpty()) {
                    validateFields();
                } else if (!(mail.isEmpty() && pwd.isEmpty())) {
                    createUser(mail, pwd);
                } else {
                    Toast.makeText(SignupActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    // create user and save it to firebase
    public void createUser(String mail, String pwd) {
        mFirebaseAuth.createUserWithEmailAndPassword(mail, pwd).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(SignupActivity.this, "Signup unsuccessful, please try again!", Toast.LENGTH_SHORT).show();
                } else {
                    // signup successful, forwards to home activity
                    Toast.makeText(SignupActivity.this, "User created with Firebase authentication. Please login!", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
    private void validateEmail() {
        // error message if email input field  is left empty
        email.setError("Please enter user email");
        email.requestFocus();
    }

    private void validatePassword() {
        // error message if password input field is left empty
        password.setError("Please enter user password");
        password.requestFocus();
    }

    private void validateFields() {
        // error message if both fields are left empty
        Toast.makeText(SignupActivity.this, "Fields are empty",
                Toast.LENGTH_SHORT).show();
    }

    public void login() {
        tvLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }


}


