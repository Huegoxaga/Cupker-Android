package com.cupker;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Sample;
import com.amplifyframework.datastore.generated.model.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * It defines a history session
 */
public class HistoryActivity extends AppCompatActivity {

    // TODO: Auto hide back buttons
    // TODO: Add menu bar

    // Keys
    private static final String TAG = "===HISTORY ACTIVITY===";
    private static final String SESSION_ID = "SESSION ID";

    // UI & Controllers
    private ListView cuppingListView;
    private TextView titleText;
    private CuppingListAdapter cuppingListAdapter;

    // Data
    private Session session;
    private List<Sample> samples;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init Views
        setContentView(R.layout.activity_history);
        FrameLayout mainFrame = findViewById(R.id.cupping_activity_main_frame);
        cuppingListView = findViewById(R.id.cupping_activity_list);
        Intent intent = getIntent();
        LayoutInflater layoutInflater = getLayoutInflater();
        View cuppingListViewHeader = layoutInflater.inflate(R.layout.activity_cupping_list_header, cuppingListView, false);
        cuppingListView.addHeaderView(cuppingListViewHeader);
        titleText = findViewById(R.id.cupping_activity_title_label); // View exists only after adding header

        // Init Data
        samples = new ArrayList<>();
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
                                        }
                                        cuppingListAdapter = new CuppingListAdapter(this, samples);
                                        handler.post(updateView);
                                    },
                                    failure -> Log.e(TAG, "Query failed.", failure)
                            );
                        }
                    },
                    failure -> Log.e(TAG, "Query failed.", failure)
            );
        }

        // Setup
        mainFrame.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private final Handler handler = new Handler();

    private final Runnable updateView = new Runnable() {
        @Override
        public void run() {
            if (cuppingListView != null && cuppingListAdapter != null){
                cuppingListView.setAdapter(cuppingListAdapter);
                titleText.setText(session.getName());
            }
        }
    };

}