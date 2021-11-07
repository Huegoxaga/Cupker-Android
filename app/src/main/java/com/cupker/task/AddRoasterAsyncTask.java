package com.cupker.task;
/**
 * Ye Qi, 000792058
 */
import android.os.AsyncTask;
import android.util.Log;

/**
 * An async task class to add roaster (not in use)
 */
public class AddRoasterAsyncTask extends AsyncTask<String, Void, String> {
    public static final String TAG = "==AddRoasterAsyncTask==";

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(String result) {
        super.onPostExecute(result);
        Log.d(TAG, result);
    }

    /**
     * Runs in a nonblocking background thread for you.
     * @param strings parameters passed in from execute
     * @return value sent back to onPostExecute()
     */
    @Override
    protected String doInBackground(String... strings) {
        String result = "Finished Job\n";
        for (String s : strings) {
            result += s + "\n";
        }
        return result;
    }
}