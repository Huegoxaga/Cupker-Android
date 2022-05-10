package com.cupker.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;

public class AWSUtils {
    /**
     *
     * helper method to determined whether an app is installed on the device.
     *
     * @param packageName ie "org.mozilla.firefox", "come.android.chrome"
     * @param packageManager
     *
     * @see {https://stackoverflow.com/questions/18752202/check-if-application-is-installed-android}
     * @return true if app corresponds to the given package name is installed, else otherwise
     */
    // https://stackoverflow.com/questions/18752202/check-if-application-is-installed-android
    public static boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    /**
     * get an installed browser's package name that is used by Amplify login web ui.
     *
     * @return a package name which represents an installed browser app
     */
    public static String getBrowserPackageName(Context context) {
//        get device's default browser's package name
//        https://stackoverflow.com/questions/23611548/how-to-find-default-browser-set-on-android-device
        Intent browserIntent = new Intent("android.intent.action.VIEW", Uri.parse("http://"));
        ResolveInfo resolveInfo = context.getPackageManager().resolveActivity(browserIntent, PackageManager.MATCH_DEFAULT_ONLY);
        String defaultBrowserPackageName = resolveInfo.activityInfo.packageName;


        String chromePackageName = "com.android.chrome";
        String firefoxPackageName = "org.mozilla.firefox";

        PackageManager pm = context.getPackageManager();
        boolean isFireFoxInstalled = isPackageInstalled(firefoxPackageName, pm);
        boolean isChromeInstalled = isPackageInstalled(chromePackageName, pm);

        if(isChromeInstalled) { // chrome first
            defaultBrowserPackageName = chromePackageName;
        }else if(isFireFoxInstalled) { // firefox for alternative
            defaultBrowserPackageName = firefoxPackageName;
        }
        return defaultBrowserPackageName;
    }
}
