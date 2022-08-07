package com.cupker.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.core.Amplify;
import com.cupker.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    // Keys
    private static final String TAG = "===HOME ACTIVITY===";
    // Activity return codes
    private static final int DONE_CUPPING = 1;
    private static final int DONE_ADD_BEAN = 2;

    // UI & Controller
    private BottomNavigationView bottomNav;
    private Fragment selectedFragment;

    // Data
    private List<AuthUserAttribute> profile = null;

    @Override
    public void onStart() {
        super.onStart();
        updateProfile();
    }

    public void setProfile(List<AuthUserAttribute> profile) {
        this.profile = profile;
    }

    private void updateProfile(){
        Amplify.Auth.fetchAuthSession(
                result -> {
                    Log.d(TAG, "SIGN IN STATUS: " + result.toString());
                    if (result.isSignedIn()) {
                        Amplify.Auth.fetchUserAttributes(
                                attributes -> {
                                    profile = attributes;
                                    Amplify.DataStore.start(
                                            () -> Log.i(TAG, "DataStore started"),
                                            error -> Log.e(TAG, "Error starting DataStore", error)
                                    );
                                },
                                error -> Log.e(TAG, "Failed to fetch user attributes.", error)
                        );
                    }
                    else{
                        Amplify.Auth.signInWithWebUI(
                                this,
                                signRes -> {
                                    Log.i(TAG, signRes.toString());
                                    Amplify.DataStore.start(
                                            () -> Log.i(TAG, "DataStore started"),
                                            error -> Log.e(TAG, "Error starting DataStore", error)
                                    );
//                                    updateProfile();
                                },
                                error -> {
                                    Log.e(TAG, error.toString());
                                    finish();
                                }
                        );
                    }
                },
                error -> Log.e(TAG, error.toString())
        );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init
        bottomNav = findViewById(R.id.bottom_navigation);

        //Setup
        //Keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CuppingFragment()).commit();
        }

        // Listener
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_cupping:
                    selectedFragment = new CuppingFragment();
                    break;
                case R.id.nav_beans:
                    selectedFragment = new BeansFragment();
                    break;
                case R.id.nav_history:
                    selectedFragment = new HistoryFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment(profile);
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        });

    }

    public void setBottomNav(int selectedID) {
        bottomNav.setSelectedItemId(selectedID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Return after saving cupping result
        if (resultCode == DONE_CUPPING) {
            bottomNav.setSelectedItemId(R.id.nav_history);
        } else if (resultCode == DONE_ADD_BEAN) {
            bottomNav.setSelectedItemId(R.id.nav_beans);
        }
    }
}