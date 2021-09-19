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
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Dealer;
import com.amplifyframework.datastore.generated.model.Flavor;
import com.amplifyframework.datastore.generated.model.Roaster;
import com.amplifyframework.datastore.generated.model.Sample;
import com.amplifyframework.datastore.generated.model.Session;

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