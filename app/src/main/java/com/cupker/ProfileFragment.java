package com.cupker;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.auth.options.AuthSignUpOptions;
import com.amplifyframework.core.Amplify;

/**
 * This defines the profile page
 */
public class ProfileFragment extends Fragment {

    // Keys
    private static final String TAG = "===PROF FRAG===";

    // UI & Controllers
    private View profileView;
    public ProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        profileView = inflater.inflate(R.layout.fragment_profile, container, false);
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

        Amplify.Auth.signIn(
                "qi.ye@live.com",
                "Password123",
                result -> Log.i("AuthQuickstart", result.isSignInComplete() ? "Sign in succeeded" : "Sign in not complete"),
                error -> Log.e("AuthQuickstart", error.toString())
        );
        // Inflate the layout for this fragment
        return profileView;
    }
}