package com.cupker;
/**
 * Ye Qi, 000792058
 */
import android.util.Log;
import android.app.Application;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;
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
