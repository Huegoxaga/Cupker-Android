package com.cupker;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.cupker.ui.login.LoginExampleActivity;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * This defines the profile page
 */
public class ProfileFragment extends Fragment {

    // Keys
    private static final String TAG = "===PROF FRAG===";
    private ArrayList<String> settingsTitles;

    // UI & Controllers
    private final ProfileFragment self = this;
    private View profileView;
    private ListView settingsList;
    private ProfileSettingsListAdapter profileSettingsListAdapter;
    private Button loginBtn;
    private TextView usernameLabel;
    private String usernameStr;
    private boolean guestMode;


    public ProfileFragment() {
    }

    @Override
    public void onStart() {
        super.onStart();
        settingsTitles = new ArrayList<>(Arrays.asList("My Dealers", "My Roasters", "My Flavors", "Logout"));
        Amplify.Auth.fetchAuthSession(
                result -> {
                    Log.d(TAG, "SIGN IN STATUS: " + result.toString());
                    if (result.isSignedIn()) {
                        Amplify.Auth.fetchUserAttributes(
                                attributes -> {
                                    Log.i(TAG, "User attributes = " + attributes.toString());
                                    String email = "";
                                    for (AuthUserAttribute attribute : attributes) {
                                        if (attribute.getKey().equals(AuthUserAttributeKey.email()))
                                            email = attribute.getValue();
                                    }
                                    setGuestMode(false, "Account ID: " + email);
                                },
                                error -> Log.e(TAG, "Failed to fetch user attributes.", error)
                        );
                    } else {
                        setGuestMode(true, "Guest User");
                    }
                },
                error -> Log.e(TAG, error.toString())
        );
//        this.onCreate(null);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        Amplify.Auth.signIn(
//                "qi.ye@live.com",
//                "Password123",
//                result -> {
//                    if (result.isSignInComplete()) {
//                        Log.i(TAG, "Sign in succeeded");
//                    } else {
//                        Log.i(TAG, "Sign in not complete");
//                    }
//                },
//                error -> Log.e(TAG, error.toString())
//        );

    }

    private final Handler handler = new Handler();

    public void setGuestMode(boolean newMode, String username) {
        guestMode = newMode;
        usernameStr = username;
        handler.post(updateView);
    }

    private final Runnable updateView = new Runnable() {
        @Override
        public void run() {
            if (profileView != null) {
                if (guestMode) {
                    int lastIdx = settingsTitles.indexOf("Logout");
                    if (lastIdx != -1) settingsTitles.remove(lastIdx);
                    loginBtn.setVisibility(View.VISIBLE);
                    usernameLabel.setText(usernameStr);
                } else {
                    usernameLabel.setText(usernameStr);
                    loginBtn.setVisibility(View.INVISIBLE);
                }
                profileSettingsListAdapter = new ProfileSettingsListAdapter(self, settingsTitles);
                settingsList.setAdapter(profileSettingsListAdapter);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        profileView = inflater.inflate(R.layout.fragment_profile, container, false);
        settingsList = profileView.findViewById(R.id.profile_frag_listview);
        loginBtn = profileView.findViewById(R.id.profile_frag_login_btn);
        usernameLabel = profileView.findViewById(R.id.profile_frag_username_text);

        loginBtn.setOnClickListener(view -> {
//            Intent startNewBeamIntent = new Intent(getActivity(), LoginActivity.class);
//            startActivity(startNewBeamIntent);
            Amplify.Auth.signInWithWebUI(getActivity(),
                    result -> {
                        Log.i(TAG, "SIGN IN COMPLETE " + result.toString());
                        onStart();
                    },
                    error -> {
                        Log.e(TAG, error.toString());
                    }
            );
        });

        // Auth test
//        AuthSignUpOptions options = AuthSignUpOptions.builder()
//                .userAttribute(AuthUserAttributeKey.email(), "qi.ye@live.com")
//                .build();
//        Amplify.Auth.signUp("qi.ye@live.com", "Password123", options,
//                result -> Log.i(TAG, "Result: " + result.toString()),
//                error -> Log.e(TAG, "Sign up failed", error)
//        );

//        Amplify.Auth.confirmSignUp(
//                "qi.ye@live.com",
//                "257499",
//                result -> Log.i(TAG, result.isSignUpComplete() ? "Confirm signUp succeeded" : "Confirm sign up not complete"),
//                error -> Log.e(TAG, error.toString())
//        );

//        Amplify.DataStore.clear(
//                () -> Log.i("MyAmplifyApp", "DataStore cleared"),
//                error -> Log.e("MyAmplifyApp", "Error clearing DataStore", error)
//        );

//        Amplify.DataStore.query(Roaster.class, Where.matches(Roaster.NAME.eq("Roaster A")),
//            match -> {
//                if (match.hasNext()) {
//                    Roaster d = match.next();
//                    Log.d(TAG, "Bean for deletion: =====" + d.toString());
//                    Amplify.DataStore.delete(d,
//                            deleted -> Log.i(TAG, "Deleted a bean."),
//                            failure -> Log.e(TAG, "Delete failed.", failure)
//                    );
//                }
//            },
//            failure -> Log.e(TAG, "Query failed.", failure)
//        );

//        Amplify.DataStore.query(Session.class, Where.id("22d50708-8989-45d7-9ee5-40f5345c1b10"),
//                match -> {
//                    if (match.hasNext()) {
//                        Session session = match.next();
//                        Amplify.DataStore.query(Sample.class, Where.matches(Sample.SESSION_ID.eq(session.getId())),
//                                matches -> {
//                                    while (matches.hasNext()) {
//                                        Sample s = matches.next();
//                                        Log.d(TAG, s.toString());
//                                    }
//                                },
//                                failure -> Log.e(TAG, "Query failed.", failure)
//                        );
//                    }
//                },
//                failure -> Log.e(TAG, "Query failed.", failure)
//        );
//
//        Amplify.DataStore.query(Session.class, Where.matches(Session.ID.eq("8805c61f-b9d0-444e-9f6d-e49509b683c8")),
//                match -> {
//                    if (match.hasNext()) {
//                        Session getBean = match.next();
//                        Log.d(TAG, "Bean for deletion: =====" + getBean.getId());
//                        Amplify.DataStore.delete(getBean,
//                                deleted -> Log.i(TAG, "Deleted a bean."),
//                                failure -> Log.e(TAG, "Delete failed.", failure)
//                        );
//                    }
//                },
//                failure -> Log.e(TAG, "Query failed.", failure)
//        );
//        Dealer f = Dealer.builder()
//                .name("Test")
//                .email("info@dealer.com")
//                .build();
//
//        Amplify.DataStore.save(f,
//                saved -> {
//                    Log.i(TAG, "Dealer saved.");
//                },
//                failure -> Log.e("MyAmplifyApp", "Post not saved.", failure)
//        );
//                Amplify.DataStore.query(Dealer.class, Where.matches(Dealer.NAME.eq("Test")),
//                match -> {
//                    if (match.hasNext()) {
//                        Dealer d = match.next();
//                        Log.d(TAG, "Bean for deletion: =====" + d.toString());
//                        Amplify.DataStore.delete(d,
//                                deleted -> Log.i(TAG, "Deleted a bean."),
//                                failure -> Log.e(TAG, "Delete failed.", failure)
//                        );
//                    }
//                },
//                failure -> Log.e(TAG, "Query failed.", failure)
//        );


        // Inflate the layout for this fragment
        return profileView;
    }
}