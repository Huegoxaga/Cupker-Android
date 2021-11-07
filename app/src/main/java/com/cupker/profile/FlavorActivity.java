package com.cupker.profile;
/**
 * Ye Qi, 000792058
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Flavor;
import com.amplifyframework.datastore.generated.model.Status;
import com.cupker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This defines the flavor list page
 */
public class FlavorActivity extends AppCompatActivity {

    // Keys
    private static final String TAG = "===FLAVOR ACTIVITY===";


    // UI & Controllers
    private ListView flavorList;
    private FlavorActivity self = this;
    private FlavorListAdapter flavorListAdapter;
    private MenuItem menuItemDelete;
    private MenuItem menuItemAdd;

    // Data
    private ArrayList<Flavor> flavorObjs;

    @Override
    public void onResume() {
        super.onResume();
        flavorObjs = new ArrayList<>();
        Amplify.DataStore.query(Flavor.class,
                Where.matches(Flavor.STATUS.eq(Status.ACTIVE)),
                queryMatches -> {
                    while (queryMatches.hasNext()) {
                        Flavor flavor = queryMatches.next();
                        flavorObjs.add(flavor);
                        handler.post(updateView);
                    }
                },
                error -> Log.e(TAG, "Error retrieving posts", error)
        );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flavor);

        // assigning ID of the toolbar to a variable
        Toolbar toolbar = findViewById(R.id.flavor_activity_toolbar);
        flavorList = findViewById(R.id.flavor_activity_list);

        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    public void updateList(Flavor newFlavor) {
        flavorObjs.add(newFlavor);
        flavorListAdapter = new FlavorListAdapter(self, flavorObjs);
        flavorList.setAdapter(flavorListAdapter);
    }

    private final Handler handler = new Handler();

    private final Runnable updateView = new Runnable() {
        @Override
        public void run() {
            if (flavorList != null) {
                flavorListAdapter = new FlavorListAdapter(self, flavorObjs);
                flavorList.setAdapter(flavorListAdapter);
            }
        }
    };


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.add_bean_menu_save_button:
                NewFlavorProfileFragment newFlavorFrag = new NewFlavorProfileFragment();
                newFlavorFrag.show(getSupportFragmentManager(), null);
                break;
            case R.id.add_bean_menu_delete_button:
                flavorListAdapter.notifyDataSetChanged();
                flavorListAdapter.removeSelectedFlavors();
                showDeleteMenu(false);
                break;
        }
        return super.onOptionsItemSelected(item);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_with_add_delete, menu);
        menuItemDelete = menu.findItem(R.id.add_bean_menu_delete_button);
        menuItemAdd = menu.findItem(R.id.add_bean_menu_save_button);
        menuItemDelete.setVisible(false);
        return super.onCreateOptionsMenu(menu);
    }

    public void showDeleteMenu(boolean show) {
        menuItemDelete.setVisible(show);
        menuItemAdd.setVisible(!show);
    }

    /**
     * Set selected flavor inactive
     * @param selectedFlavors
     */
    public void removeSelectedFlavor(List<Flavor> selectedFlavors) {
        flavorObjs.removeAll(selectedFlavors);

        for (Flavor dealer : selectedFlavors) {
            Amplify.DataStore.query(Flavor.class, Where.id(dealer.getId()),
                    match -> {
                        if (match.hasNext()) {
                            Flavor getFlavor = match.next();

                            Flavor updatedFlavor = getFlavor.copyOfBuilder()
                                    .status(Status.INACTIVE)
                                    .build();
                            Amplify.DataStore.save(updatedFlavor,
                                    updated -> Log.i(TAG, "Inactivated a flavor."),
                                    failure -> Log.e(TAG, "Update failed.", failure)
                            );
                            Log.d(TAG, "Inactivate Flavor =====" + getFlavor.getName());
                        }
                    },
                    failure -> Log.e(TAG, "Query failed.", failure)
            );
        }
    }
}