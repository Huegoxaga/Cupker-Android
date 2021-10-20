package com.cupker;

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
import com.amplifyframework.datastore.generated.model.Dealer;
import com.amplifyframework.datastore.generated.model.Roaster;
import com.amplifyframework.datastore.generated.model.Status;

import java.util.ArrayList;
import java.util.List;

public class RoasterActivity extends AppCompatActivity {

    // Keys
    private static final String TAG = "===ROASTER ACTIVITY===";


    // UI & Controllers
    private ListView roasterList;
    private RoasterActivity self = this;
    private RoasterListAdapter roasterListAdapter;
    private MenuItem menuItemDelete;
    private MenuItem menuItemAdd;

    // Data
    private ArrayList<Roaster> roasterObjs;

    @Override
    public void onResume() {
        super.onResume();
        roasterObjs = new ArrayList<>();
        Amplify.DataStore.query(Roaster.class,
                Where.matches(Roaster.STATUS.eq(Status.ACTIVE)),
                queryMatches -> {
                    while (queryMatches.hasNext()) {
                        Roaster roaster = queryMatches.next();
                        roasterObjs.add(roaster);
                        handler.post(updateView);
                    }

                },
                error -> Log.e(TAG, "Error retrieving roasters", error)
        );
    }

    public void updateList(Roaster newRoaster) {
        roasterObjs.add(newRoaster);
        roasterListAdapter = new RoasterListAdapter(self, roasterObjs);
        roasterList.setAdapter(roasterListAdapter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_roaster);

        // assigning ID of the toolbar to a variable
        Toolbar toolbar = findViewById(R.id.roaster_activity_toolbar);
        roasterList = findViewById(R.id.roaster_activity_list);

        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }


    private final Handler handler = new Handler();

    private final Runnable updateView = new Runnable() {
        @Override
        public void run() {
            if (roasterList != null) {
                roasterListAdapter = new RoasterListAdapter(self, roasterObjs);
                roasterList.setAdapter(roasterListAdapter);
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
                NewRoasterProfileFragment newRoasterFrag = new NewRoasterProfileFragment();
                newRoasterFrag.show(getSupportFragmentManager(), null);
                break;
            case R.id.add_bean_menu_delete_button:
                roasterListAdapter.notifyDataSetChanged();
                roasterListAdapter.removeSelectedRoasters();
                showDeleteMenu(false);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    // create an action bar button
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

    public void removeSelectedRoaster(ArrayList<Roaster> selectedRoasters) {
        roasterObjs.removeAll(selectedRoasters);

        for (Roaster dealer : selectedRoasters) {
            Amplify.DataStore.query(Roaster.class, Where.id(dealer.getId()),
                    match -> {
                        if (match.hasNext()) {
                            Roaster getDealer = match.next();

                            Roaster updatedRoaster = getDealer.copyOfBuilder()
                                    .status(Status.INACTIVE)
                                    .build();
                            Amplify.DataStore.save(updatedRoaster,
                                    updated -> Log.i(TAG, "Inactivated a roaster."),
                                    failure -> Log.e(TAG, "Update failed.", failure)
                            );
                            Log.d(TAG, "Inactivate Roaster =====" + getDealer.getName());
                        }
                    },
                    failure -> Log.e(TAG, "Query failed.", failure)
            );
        }
    }

}