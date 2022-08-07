package com.cupker;

import android.util.Log;
import android.app.Application;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.AuthChannelEventName;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.InitializationStatus;
import com.amplifyframework.datastore.AWSDataStorePlugin;
import com.amplifyframework.datastore.DataStoreChannelEventName;
import com.amplifyframework.hub.HubChannel;

/**
 * This is the Application Class
 */
public class Cupker extends Application {
    // Keys
    private static final String TAG = "===CUPPING_ACTIVITY===";
    boolean isDataStoreReady = false;

    @Override
    public void onCreate() {
        super.onCreate();
        // Init
        try {
            // persist data locally on a device
            Amplify.addPlugin(new AWSDataStorePlugin());
            // amplify login and logout
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i(TAG, "Initialized Amplify");

            /**
             * listen datastore readiness
             * for new login purpose
             * after it is started, it takes sometime to get everything ready
             */
            Amplify.Hub.subscribe(
                    HubChannel.DATASTORE,
                    hubEvent -> DataStoreChannelEventName.READY.toString().equals(hubEvent.getName()),
                    hubEvent -> {
                        Log.i(TAG, hubEvent.getName());
                        setDataStoreReady(true);
                    }
            );
            Amplify.Hub.subscribe(HubChannel.AUTH,
                    hubEvent -> {
                        if (hubEvent.getName().equals(InitializationStatus.SUCCEEDED.toString())) {
                            Log.i(TAG, "Auth successfully initialized");
                        } else if (hubEvent.getName().equals(InitializationStatus.FAILED.toString())){
                            Log.i(TAG, "Auth failed to succeed");
                        } else {
                            switch (AuthChannelEventName.valueOf(hubEvent.getName())) {
                                case SIGNED_IN:
                                    Log.i(TAG, "Auth just became signed in.");
                                    break;
                                case SIGNED_OUT:
                                    Log.i(TAG, "Auth just became signed out.");
                                    break;
                                case SESSION_EXPIRED:
                                    Log.i(TAG, "Auth session just expired.");
                                    break;
                                case USER_DELETED:
                                    Log.i(TAG, "User has been deleted.");
                                    break;
                                default:
                                    Log.w("AuthQuickstart", "Unhandled Auth Event: " + AuthChannelEventName.valueOf(hubEvent.getName()));
                                    break;
                            }
                        }
                    }
            );
        } catch (AmplifyException error) {
            Log.e(TAG, "Could not initialize Amplify", error);
        }
    }

    public boolean isDataStoreReady() {
        return isDataStoreReady;
    }

    public void setDataStoreReady(boolean dataStoreReady) {
        isDataStoreReady = dataStoreReady;
    }
}
