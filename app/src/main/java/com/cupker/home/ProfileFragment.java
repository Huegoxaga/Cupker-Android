package com.cupker.home;
/**
 * Ye Qi, 000792058
 */

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
import com.amplifyframework.auth.cognito.options.AWSCognitoAuthWebUISignInOptions;
import com.amplifyframework.core.Amplify;
import com.cupker.R;
import com.cupker.profile.ProfileSettingsListAdapter;
import com.cupker.utils.AWSUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

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
    private List<AuthUserAttribute> profile;
    private boolean newLogin = false;


    public ProfileFragment(List<AuthUserAttribute> profile) {
        this.profile = profile;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (profile != null && !newLogin) {
            Log.i(TAG, "User attributes = " + profile.toString());
            String email = "";
            for (AuthUserAttribute attribute : profile) {
                if (attribute.getKey().equals(AuthUserAttributeKey.email()))
                    email = attribute.getValue();
            }
            setGuestMode(false, self.getResources().getString(R.string.account_id, email));
        } else if (newLogin) {
            updateProfile();
            newLogin = false;
        } else {
            setGuestMode(true, "Guest User");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        settingsTitles = new ArrayList<>(Arrays.asList(getResources().getStringArray(R.array.profile_list)));

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
                int lastIdx = settingsTitles.indexOf("Logout");
                if (guestMode) {
                    if (lastIdx != -1) settingsTitles.remove(lastIdx);
                    loginBtn.setVisibility(View.VISIBLE);
                    usernameLabel.setText(usernameStr);
                } else {
                    if (lastIdx == -1) settingsTitles.add("Logout");
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
//            Amplify.Auth.signInWithWebUI(getActivity(),
//                    result -> {
//                        Log.i(TAG, "SIGN IN COMPLETE " + result.toString());
//                        newLogin = true;
//                        onStart();
//                    },
//                    error -> {
//                        Log.e(TAG, error.toString());
//                    }
//            );

            String browserPackageName = AWSUtils.getBrowserPackageName(getContext());

            Amplify.Auth.signInWithWebUI(requireActivity(),
                    //https://github.com/aws-amplify/amplify-android/issues/678
                    AWSCognitoAuthWebUISignInOptions.builder().browserPackage(browserPackageName).build(),
//                    AWSCognitoAuthWebUISignInOptions.builder().browserPackage("org.mozilla.firefox").build(),
                    result -> {
                        Log.i(TAG, "SIGN IN COMPLETE " + result.toString());
                        newLogin = true;
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


//    /**
//     *
//     * helper method to determined whether an app is installed on the device.
//     *
//     * @param packageName ie "org.mozilla.firefox", "come.android.chrome"
//     * @param packageManager
//     *
//     * @see {https://stackoverflow.com/questions/18752202/check-if-application-is-installed-android}
//     * @return true if app corresponds to the given package name is installed, else otherwise
//     */
//    // https://stackoverflow.com/questions/18752202/check-if-application-is-installed-android
//    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
//        try {
//            packageManager.getPackageInfo(packageName, 0);
//            return true;
//        } catch (PackageManager.NameNotFoundException e) {
//            return false;
//        }
//    }
//
//
//    /**
//     * get an installed browser's package name that is used by Amplify login web ui.
//     *
//     * @return a package name which represents an installed browser app
//     */
//    private String getBrowserPackageName() {
////        get device's default browser's package name
////        https://stackoverflow.com/questions/23611548/how-to-find-default-browser-set-on-android-device
//        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://"));
//        ResolveInfo resolveInfo = requireContext().getPackageManager().resolveActivity(browserIntent, PackageManager.MATCH_DEFAULT_ONLY);
//        String defaultBrowserPackageName = resolveInfo.activityInfo.packageName;
//
//
//        String chromePackageName = "com.android.chrome";
//        String firefoxPackageName = "org.mozilla.firefox";
//
//        PackageManager pm = requireContext().getPackageManager();
//        boolean isFireFoxInstalled = isPackageInstalled(firefoxPackageName, pm);
//        boolean isChromeInstalled = isPackageInstalled(chromePackageName, pm);
//
//        if(isChromeInstalled) { // chrome first
//            defaultBrowserPackageName = chromePackageName;
//        }else if(isFireFoxInstalled) { // firefox for alternative
//            defaultBrowserPackageName = firefoxPackageName;
//        }
//        return defaultBrowserPackageName;
//    }

    public void updateProfile() {
        Log.d(TAG, "updateProfile");
        Amplify.Auth.fetchAuthSession(
                result -> {
                    HomeActivity homeActivity = (HomeActivity) getActivity();
                    Log.d(TAG, "SIGN IN STATUS: " + result.toString());
                    if (result.isSignedIn()) {
                        Amplify.Auth.fetchUserAttributes(
                                attributes -> {
                                    profile = attributes;
                                    homeActivity.setProfile(attributes);
                                    Log.i(TAG, "User attributes = " + profile.toString());
                                    String email = "";
                                    for (AuthUserAttribute attribute : profile) {
                                        if (attribute.getKey().equals(AuthUserAttributeKey.email()))
                                            email = attribute.getValue();
                                    }
                                    setGuestMode(false, self.getResources().getString(R.string.account_id, email));
                                    Amplify.DataStore.start(
                                            () -> Log.i(TAG, "DataStore started"),
                                            error -> Log.e(TAG, "Error starting DataStore", error)
                                    );
                                },
                                error -> Log.e(TAG, "Failed to fetch user attributes.", error)
                        );
                    } else {
                        profile = null;
                        homeActivity.setProfile(null);
                    }
                },
                error -> Log.e(TAG, error.toString())
        );
    }
}