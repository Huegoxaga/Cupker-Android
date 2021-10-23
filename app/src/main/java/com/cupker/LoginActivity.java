package com.cupker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "===LOGIN ACTIVITY===";


//    private Button loginBtn;
//    private EditText usernameInput;
//    private EditText passwordInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Amplify.Auth.signInWithWebUI(
                this,
                result -> {
                    Log.i(TAG, result.toString());
                    finish();
                },
                error -> {
                    Log.e(TAG, error.toString());
                    finish();
                }
        );

//        setContentView(R.layout.activity_login);
//        loginBtn = findViewById(R.id.login_activity_btn);
//        usernameInput = findViewById(R.id.login_activity_username);
//        passwordInput = findViewById(R.id.login_activity_password);
//
//
//        loginBtn.setOnClickListener(view -> {
//            Amplify.Auth.signIn(
//                    usernameInput.getText().toString(),
//                    passwordInput.getText().toString(),
//                    result -> {
//                        if (result.isSignInComplete()) {
//                            Log.i(TAG, "Sign in succeeded");
//                            finish();
//                        } else {
//                            Log.i(TAG, "Sign in not complete");
//                        }
//                    },
//                    error -> Log.e(TAG, error.toString())
//            );
//        });
    }
}