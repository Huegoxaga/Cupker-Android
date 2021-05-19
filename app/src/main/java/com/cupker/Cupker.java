package com.cupker;
import android.util.Log;
import android.app.Application;
import com.amplifyframework.AmplifyException;
import com.amplifyframework.api.aws.AWSApiPlugin;
import com.amplifyframework.auth.cognito.AWSCognitoAuthPlugin;
import com.amplifyframework.core.Amplify;

public class Cupker extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        try {
            Amplify.addPlugin(new AWSCognitoAuthPlugin());
            Amplify.addPlugin(new AWSApiPlugin());
            Amplify.configure(getApplicationContext());
            Log.i("Cupker", "Initialized Amplify");
        } catch (AmplifyException error) {
            Log.e("Cupker", "Could not initialize Amplify", error);
        }
    }
}
