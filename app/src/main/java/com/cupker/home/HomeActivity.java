package com.cupker.home;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.cognito.options.AWSCognitoAuthWebUISignInOptions;
import com.amplifyframework.core.Amplify;
import com.cupker.Cupker;
import com.cupker.R;
import com.cupker.utils.AWSUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

public class HomeActivity extends AppCompatActivity {

    // Keys
    private static final String TAG = "===HOME ACTIVITY===";
    // Activity return codes
    private static final int DONE_CUPPING = 1;
    private static final int DONE_ADD_BEAN = 2;

    // UI & Controller
    private BottomNavigationView bottomNav;
    private Fragment selectedFragment;
    private Dialog initialDataStoreDialog;
    private TimerTask checkDataStoreTimerTask;
    private Cupker appInstance;
    private Timer checkDataStoreTimer;

    // Data
    private List<AuthUserAttribute> profile = null;

    public void setProfile(List<AuthUserAttribute> profile) {
        this.profile = profile;
    }

    public List<AuthUserAttribute> getProfile() {
        return this.profile;
    }


    public void checkLogin() {
        Log.d(TAG, "updateProfile");
        Amplify.Auth.fetchAuthSession(
                result -> {
                    Log.d(TAG, "SIGN IN STATUS: " + result.toString());
                    if (!result.isSignedIn()) {
                        forceLogin();
                    } else {
                        loadProfile(false);
                    }
                },
                error -> Log.e(TAG, error.toString())
        );
    }

    private void loadingUI() {
        Amplify.DataStore.start(
                () -> {
                    Log.i(TAG, "DataStore started");
                    appInstance.setDataStoreReady(false);
                    this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            // as data store takes some time to finish
                            // show dialog before it is ready
                            // there would not be data before it is ready
                            // so UI does not work for user
                            initialDataStoreDialog.show();
                        }
                    });
                    starTime();
                },
                error -> Log.e(TAG, "Error starting DataStore", error)
        );
    }

    public void loadProfile(boolean needWait) {
        Amplify.Auth.fetchUserAttributes(
                attributes -> {
                    this.setProfile(attributes);
                    Log.i(TAG, "User attributes = " + profile.toString());
                    if (needWait) {
                        loadingUI();
                    }
                },
                error -> Log.e(TAG, "Failed to fetch user attributes.", error)
        );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init
        bottomNav = findViewById(R.id.bottom_navigation);
        initialDataStoreDialog = new Dialog(HomeActivity.this);
        initialDataStoreDialog.setContentView(R.layout.dialog_loading);
        // disabled click outside to dismiss
        initialDataStoreDialog.setCancelable(false);

        //Setup
        //Keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CuppingFragment()).commit();
        }

        appInstance = (Cupker) (getApplication());

        checkDataStoreTimerTask = new TimerTask() {
            @Override
            public void run() {
                try {
                    if (appInstance.isDataStoreReady()) {
                        initialDataStoreDialog.dismiss();
                        stopTimer();
                    }
                } catch (NullPointerException e) {
                    Log.e(TAG, "run: Error in checking data store readiness, message: " + e.getMessage());
                    initialDataStoreDialog.dismiss();
                    stopTimer();
                }
            }
        };
        checkLogin();

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

    public void forceLogin() {
        String browserPackageName = AWSUtils.getBrowserPackageName(HomeActivity.this);
        Amplify.Auth.signInWithWebUI(this,
                AWSCognitoAuthWebUISignInOptions.builder().browserPackage(browserPackageName).build(),
                result -> {
                    loadProfile(true);
                    Log.i(TAG, "SIGN IN COMPLETE " + result.toString());
                },
                error -> {
                    Log.e(TAG, error.toString());
                }
        );
    }

    private void starTime() {
        if (checkDataStoreTimer == null) {
            checkDataStoreTimer = new Timer();
        }
        checkDataStoreTimer.scheduleAtFixedRate(checkDataStoreTimerTask, 0, 1000);

    }

    private void stopTimer() {
        if (checkDataStoreTimer != null) {
            checkDataStoreTimer.cancel();
            checkDataStoreTimer = null;
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        stopTimer();
    }
}