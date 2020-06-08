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
import com.google.firebase.auth.FirebaseUser;
import com.pedropalma.handin_week14_login.R;
import com.pedropalma.handin_week14_login.storage.FirebaseManager;

public class LoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btnLogin;
    TextView tvSignUp;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseAuth.AuthStateListener mAuthStateListener;
    private FirebaseManager firebaseManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        firebaseManager = new FirebaseManager(this);

        viewBuilder();
        checkIfUserLogged();

        //handle login button clicked
        btnLogin.setOnClickListener(new View.OnClickListener() {
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
                    firebaseManager.checkAuthentication(mail, pwd);
                } else {
                    Toast.makeText(LoginActivity.this, "Error occurred", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //handle click text view
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }


    private void validateFields() {
        // error message if both fields are left empty
        Toast.makeText(LoginActivity.this, "Fields are empty", Toast.LENGTH_SHORT).show();
    }

    private void validatePassword() {
        // error message if password input field is left empty
        password.setError("Please enter user password");
        password.requestFocus();
    }

    private void validateEmail() {
        // error message if email input field  is left empty
        email.setError("Please enter user email");
        email.requestFocus();
    }

    private void checkIfUserLogged() {
        mAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
                if (mFirebaseUser != null) {
                    Toast.makeText(LoginActivity.this, "You are logged in", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(LoginActivity.this, "Please Login", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void viewBuilder() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        email = findViewById(R.id.editText);
        password = findViewById(R.id.editText2);
        btnLogin = findViewById(R.id.button2);
        tvSignUp = findViewById(R.id.textView);
    }

}
