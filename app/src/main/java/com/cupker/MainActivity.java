package com.cupker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    // Keys
//    private static final String TAG = "===MAIN ACTIVITY===";
    // Activity return codes
    private static final int DONE_CUPPING = 1;
    private static final int DONE_ADD_BEAN = 2;

    // UI & Controller
    private BottomNavigationView bottomNav;
    private Fragment selectedFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Init
        bottomNav = findViewById(R.id.bottom_navigation);

        //Setup
        //Keep the selected fragment when rotating the device
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new CuppingFragment()).commit();
        }

        // Listener
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.nav_cupping:
                    selectedFragment = new CuppingFragment();
                    break;
                case R.id.nav_beans:
                    selectedFragment = new BeansFragment();
                    break;
                case R.id.nav_history:
                    selectedFragment = new HistoryFragment();
                    break;
                case R.id.nav_profile:
                    selectedFragment = new ProfileFragment();
                    break;
            }
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    selectedFragment).commit();
            return true;
        });

    }

    public void setBottomNav(int selectedID){
        bottomNav.setSelectedItemId(selectedID);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Return after saving cupping result
        if (resultCode == DONE_CUPPING){
            bottomNav.setSelectedItemId(R.id.nav_history);
        } else if (resultCode == DONE_ADD_BEAN) {
            bottomNav.setSelectedItemId(R.id.nav_beans);
        }
    }
}