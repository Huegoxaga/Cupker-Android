package com.cupker.data;

import android.util.Log;

import com.amplifyframework.core.Amplify;
import com.cupker.ProfileFragment;
import com.cupker.data.model.LoggedInUser;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
public class LoginDataSource {

    private static final String TAG = "===LOGIN DATA SOURCE===";

    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            Amplify.Auth.signIn(
                    username,
                    password,
                    result -> {
                        if (result.isSignInComplete()) {
                            Log.i(TAG, "Sign in succeeded");
                        } else {
                            Log.i(TAG, "Sign in not complete");
                        }
                    },
                    error -> Log.e(TAG, error.toString())
            );
            LoggedInUser fakeUser =
                    new LoggedInUser(
                            java.util.UUID.randomUUID().toString(),
                            username);
            return new Result.Success<>(fakeUser);


        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    public void logout() {
        // TODO: revoke authentication
    }
}