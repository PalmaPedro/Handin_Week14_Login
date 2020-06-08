package com.pedropalma.handin_week14_login.storage;

import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.pedropalma.handin_week14_login.ui.HomeActivity;
import com.pedropalma.handin_week14_login.ui.LoginActivity;
import com.pedropalma.handin_week14_login.ui.SignupActivity;

public class FirebaseManager {

    LoginActivity loginActivity;
    FirebaseAuth mFirebaseAuth;

    public FirebaseManager(LoginActivity loginActivity) {
        this.loginActivity = loginActivity;
    }

    public void checkAuthentication(String mail, String pwd) {
        mFirebaseAuth.signInWithEmailAndPassword(mail, pwd).addOnCompleteListener(loginActivity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (!task.isSuccessful()) {
                    Toast.makeText(loginActivity, "Login unsuccessful, please try again!", Toast.LENGTH_SHORT).show();
                } else {
                    // login successful, forwards to home activity which will be the main view of the app
                    Intent intent = new Intent(loginActivity, HomeActivity.class);
                    loginActivity.startActivity(intent);
                }
            }
        });
    }


}