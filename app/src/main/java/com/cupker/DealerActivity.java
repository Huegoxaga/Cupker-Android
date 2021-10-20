package com.cupker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import com.amplifyframework.auth.AuthUserAttribute;
import com.amplifyframework.auth.AuthUserAttributeKey;
import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Dealer;
import com.amplifyframework.datastore.generated.model.Roaster;
import com.amplifyframework.datastore.generated.model.Session;
import com.amplifyframework.datastore.generated.model.Status;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DealerActivity extends AppCompatActivity {

    // Keys
    private static final String TAG = "===DEALER ACTIVITY===";


    // UI & Controllers
    private ListView dealerList;
    private DealerActivity self = this;
    private DealerListAdapter dealerListAdapter;
    private MenuItem menuItemDelete;
    private MenuItem menuItemAdd;

    // Data
    private ArrayList<Dealer> dealerObjs;



    @Override
    public void onResume() {
        super.onResume();
        dealerObjs = new ArrayList<>();
        Amplify.DataStore.query(Dealer.class,
                Where.matches(Dealer.STATUS.eq(Status.ACTIVE)),
                queryMatches -> {
                    while (queryMatches.hasNext()) {
                        Dealer dealer = queryMatches.next();
                        dealerObjs.add(dealer);
                        handler.post(updateView);
                    }
                },
                error -> Log.e(TAG, "Error retrieving posts", error)
        );
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dealer);

        // assigning ID of the toolbar to a variable
        Toolbar toolbar = findViewById(R.id.dealer_activity_toolbar);
        dealerList = findViewById(R.id.dealer_activity_list);

        // using toolbar as ActionBar
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    public void updateList(Dealer newDealer) {
        dealerObjs.add(newDealer);
        dealerListAdapter = new DealerListAdapter(self, dealerObjs);
        dealerList.setAdapter(dealerListAdapter);
    }
    private final Handler handler = new Handler();

    private final Runnable updateView = new Runnable() {
        @Override
        public void run() {
            if (dealerList != null) {
                dealerListAdapter = new DealerListAdapter(self, dealerObjs);
                dealerList.setAdapter(dealerListAdapter);
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
                NewDealerProfileFragment newDealerFrag = new NewDealerProfileFragment();
                newDealerFrag.show(getSupportFragmentManager(), null);
                break;
            case R.id.add_bean_menu_delete_button:
                dealerListAdapter.notifyDataSetChanged();
                dealerListAdapter.removeSelectedDealers();
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

    public void removeSelectedDealers(List<Dealer> selectedDealers) {
        dealerObjs.removeAll(selectedDealers);

        for (Dealer dealer : selectedDealers) {
            Amplify.DataStore.query(Dealer.class, Where.id(dealer.getId()),
                    match -> {
                        if (match.hasNext()) {
                            Dealer getDealer = match.next();

                            Dealer updatedBean = getDealer.copyOfBuilder()
                                    .status(Status.INACTIVE)
                                    .build();
                            Amplify.DataStore.save(updatedBean,
                                    updated -> Log.i(TAG, "Inactivated a Dealer."),
                                    failure -> Log.e(TAG, "Update failed.", failure)
                            );
                            Log.d(TAG, "Inactivate Dealer =====" + getDealer.getName());
                        }
                    },
                    failure -> Log.e(TAG, "Query failed.", failure)
            );
        }
    }
}