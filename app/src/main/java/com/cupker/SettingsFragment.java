package com.cupker;
/**
 * A reference class
 */

import android.os.Bundle;

import androidx.preference.PreferenceFragmentCompat;

/**
 * This defines the settings page
 */
public class SettingsFragment extends PreferenceFragmentCompat {

    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey);
    }
}