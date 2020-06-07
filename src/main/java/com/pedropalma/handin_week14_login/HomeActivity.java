package com.pedropalma.handin_week14_login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity {

    Button btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        btnLogout = findViewById(R.id.logout);

        // handle sign out button click
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // logs out and forwards user to Main Activity
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(HomeActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
    }
}
