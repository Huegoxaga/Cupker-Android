package com.cupker.profile;
/**
 * Ye Qi, 000792058
 */
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.cupker.home.ProfileFragment;
import com.cupker.R;

import java.util.ArrayList;

/**
 * This defines the settings list in profile page
 */
public class ProfileSettingsListAdapter extends BaseAdapter {

    // Keys
    private static final String TAG = "===SETTING LIST ADPT===";

    // UI & Controllers
    private final ProfileFragment profileFragment;

    // Data
    private final ArrayList<String> settingsTitle;
    private Intent intent;

    public ProfileSettingsListAdapter(ProfileFragment fragment, ArrayList<String> settingsTitle) {
        // Init data
        this.profileFragment = fragment;
        this.settingsTitle = settingsTitle;
    }

    @Override
    public int getCount() {
        return settingsTitle.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View settingsListView, ViewGroup parent) {

        // Init
        if (settingsListView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(profileFragment.getContext());
            settingsListView = layoutInflater.inflate(R.layout.fragment_profile_list, parent, false);
        }
        TextView settingsName = settingsListView.findViewById(R.id.profile_list_name);

        // Setup
        settingsName.setText(settingsTitle.get(position));

        // Listener

        switch (position) {
            case 0:
                settingsListView.setOnClickListener(view -> {
                    intent = new Intent(profileFragment.getActivity(), DealerActivity.class);
                    profileFragment.startActivity(intent);
                });
                break;
            case 1:
                settingsListView.setOnClickListener(view -> {
                    intent = new Intent(profileFragment.getActivity(), RoasterActivity.class);
                    profileFragment.startActivity(intent);
                });
                break;
            case 2:
                settingsListView.setOnClickListener(view -> {
                    intent = new Intent(profileFragment.getActivity(), FlavorActivity.class);
                    profileFragment.startActivity(intent);
                });
                break;
            case 3:
                settingsListView.setOnClickListener(view -> {
                    Amplify.Auth.signOut(
                            () -> {
                                profileFragment.setGuestMode(true, "Guest User");
                                Amplify.DataStore.clear(
                                        () -> Log.i(TAG, "DataStore cleared"),
                                        error -> Log.e(TAG, "Error clearing DataStore", error)
                                );
                                profileFragment.updateProfile();
                                Log.i(TAG, "Signed out successfully");
                            },
                            error -> Log.e(TAG, error.toString())
                    );
                });
                break;
        }


        return settingsListView;
    }
}

