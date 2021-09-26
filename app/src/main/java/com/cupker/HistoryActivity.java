package com.cupker;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.NavUtils;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Sample;
import com.amplifyframework.datastore.generated.model.Session;
import com.amplifyframework.datastore.generated.model.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * It defines a history session
 */
public class HistoryActivity extends AppCompatActivity {

    // Keys
    private static final String TAG = "===HISTORY ACTIVITY===";
    private static final String SESSION_ID = "SESSION ID";

    // UI & Controllers
    private Context context;
    private ListView cuppingListView;
    private TextView titleText;

    // Data
    private Session session;
    private List<Sample> samples;
    private Boolean editMode;
    private final ArrayList<Bean> beanObjs = new ArrayList<>();
    private final int flags = View.SYSTEM_UI_FLAG_LOW_PROFILE
            | View.SYSTEM_UI_FLAG_FULLSCREEN
            | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init Views
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_history);
        LinearLayout mainFrame = findViewById(R.id.history_activity_main_frame);
        cuppingListView = findViewById(R.id.history_activity_list);
        Intent intent = getIntent();
//        LayoutInflater layoutInflater = getLayoutInflater();
        Toolbar toolBar = findViewById(R.id.history_activity_toolbar);
        titleText = findViewById(R.id.history_toolbar_title);

//        View cuppingListViewHeader = layoutInflater.inflate(R.layout.activity_cupping_list_header, cuppingListView, false);
//        cuppingListView.addHeaderView(cuppingListViewHeader);
//        titleText = findViewById(R.id.cupping_activity_title_date); // View exists only after adding header
        final View decorView = getWindow().getDecorView();

        // Init Data
        context = this;
        samples = new ArrayList<>();
        // Add bean list first record
        beanObjs.add(Bean.builder()
                .status(Status.INACTIVE)
                .id("00000000-0000-0000-0000-000000000000")
                .name("Blind Taste")
                .build());

        if (intent != null && intent.hasExtra(SESSION_ID)) {
            String sessionID = intent.getStringExtra(SESSION_ID);
            Amplify.DataStore.query(Session.class, Where.id(sessionID),
                    match -> {
                        if (match.hasNext()) {
                            session = match.next();
                            Amplify.DataStore.query(Sample.class, Where.matches(Sample.SESSION_ID.eq(sessionID)),
                                    matches -> {
                                        while (matches.hasNext()) {
                                            Sample s = matches.next();
                                            samples.add(s);
                                            Log.d(TAG, s.toString());
                                        }
                                        editMode = false;
                                        Amplify.DataStore.query(Bean.class,
                                                Where.matches(Bean.STATUS.eq(Status.ACTIVE)),
                                                queryRoaster -> {
                                                    while (queryRoaster.hasNext()) {
                                                        Bean bean = queryRoaster.next();
                                                        beanObjs.add(bean);
                                                        Log.i(TAG, "Get Bean Name: " + bean.getName());
                                                    }
                                                    handler.post(updateView);
                                                },
                                                error -> Log.e(TAG, "Error retrieving beans", error)
                                        );
                                    },
                                    failure -> Log.e(TAG, "Query failed.", failure)
                            );
                        }
                    },
                    failure -> Log.e(TAG, "Query failed.", failure)
            );
        }

        // Setup
        mainFrame.setSystemUiVisibility(flags);
        setSupportActionBar(toolBar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowTitleEnabled(false);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
//        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable);

        // Listener
        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                decorView.setSystemUiVisibility(flags);
            }
        });
    }

    private final Handler handler = new Handler();

    private final Runnable updateView = new Runnable() {
        @Override
        public void run() {
            if (cuppingListView != null && titleText != null) {
                HistoryListAdapter cuppingListAdapter = new HistoryListAdapter(context, samples, editMode, beanObjs);
                cuppingListView.setAdapter(cuppingListAdapter);
                titleText.setText(session.getName());
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.toolbar_with_edit, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_bean_menu_save_button) {
            editMode = !editMode;
            handler.post(updateView);
            Log.d(TAG, "Editing");
        } else if (item.getItemId() == android.R.id.home) {
//            finish();
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setRoastLevel(int listPosition, double newRoastLevel) {
        Sample sample = samples.get(listPosition);
        if (sample.getRoastLevel() != newRoastLevel) {
            Log.i(TAG, "Updated a sample roast level" + sample.getRoastLevel() + "  " + newRoastLevel);
            Sample editedSample = sample.copyOfBuilder()
                    .roastLevel(newRoastLevel)
                    .build();
            Amplify.DataStore.save(editedSample,
                    updated -> Log.i(TAG, "Updated a sample roast level."),
                    failure -> Log.e(TAG, "Update failed.", failure)
            );
            Log.d(TAG, newRoastLevel + "");
            samples.set(listPosition, editedSample);
        }
    }

    public void setBean(int listPosition, Bean bean) {
        Sample sample = samples.get(listPosition);
        if (!sample.getBeanId().equals(bean.getId())) {
            Sample editedSample = sample.copyOfBuilder()
                    .beanId(bean.getId())
                    .build();
            Amplify.DataStore.save(editedSample,
                    updated -> Log.i(TAG, "Updated a sample bean."),
                    failure -> Log.e(TAG, "Update failed.", failure)
            );
            Log.d(TAG, editedSample + "Edited in list index" + listPosition);
            samples.set(listPosition, editedSample);
        }
    }
}