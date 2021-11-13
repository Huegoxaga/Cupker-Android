package com.cupker.profile;
/**
 * Ye Qi, 000792058
 */
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Status;
import com.cupker.cupping.CuppingActivity;
import com.cupker.home.HomeActivity;
import com.cupker.home.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "===LOGIN ACTIVITY===";

    private boolean loggedIn = false;
    private int attempt;

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
                    this.loggedIn = true;
                    Amplify.DataStore.start(
                            () -> Log.i(TAG, "DataStore started"),
                            error -> Log.e(TAG, "Error starting DataStore", error)
                    );
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