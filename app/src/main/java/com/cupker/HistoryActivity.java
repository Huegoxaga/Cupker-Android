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
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class HistoryActivity extends AppCompatActivity {
//    /**
//     * Whether or not the system UI should be auto-hidden after
//     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
//     */
//    private static final boolean AUTO_HIDE = true;

//    /**
//     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
//     * user interaction before hiding the system UI.
//     */
//    private static final int AUTO_HIDE_DELAY_MILLIS = 500;

//    /**
//     * Some older devices needs a small delay between UI widget updates
//     * and a change of the status and navigation bar.
//     */
//    private static final int UI_ANIMATION_DELAY = 300;

    //    private View mContentView;
//    private View mControlsView;
//    private boolean mVisible;
    private static final String TAG = "===HISTORY ACTIVITY===";
    private static final String SESSION_ID = "SESSION ID";


    private ListView cuppingListView;
    private List<Sample> samples;
    private Session session;
    private TextView titleText;
    private CuppingListAdapter cuppingListAdapter;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init
        setContentView(R.layout.activity_history);
        samples = new ArrayList<>();

//        mVisible = true;
        FrameLayout mainFrame = findViewById(R.id.cupping_activity_main_frame);
        cuppingListView = findViewById(R.id.cupping_activity_list);
        Intent intent = getIntent();
        LayoutInflater layoutInflater = getLayoutInflater();
        View cuppingListViewHeader = layoutInflater.inflate(R.layout.activity_cupping_list_header, cuppingListView, false);
        View cuppingListViewFooter = layoutInflater.inflate(R.layout.activity_cupping_list_footer, cuppingListView, false);
        cuppingListView.addHeaderView(cuppingListViewHeader);
        cuppingListView.addFooterView(cuppingListViewFooter);
        //    private Button saveButton;
        titleText = findViewById(R.id.cupping_activity_title_label);

        // Fetch Data
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

        // Listener
//        mContentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
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
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
//        delayedHide(100);
    }

//    /**
//     * Touch listener to use for in-layout UI controls to delay hiding the
//     * system UI. This is to prevent the jarring behavior of controls going away
//     * while interacting with activity UI.
//     */
//    private final View.OnTouchListener mDelayHideTouchListener = (view, motionEvent) -> {
//        switch (motionEvent.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                if (AUTO_HIDE) {
//                    delayedHide(AUTO_HIDE_DELAY_MILLIS);
//                }
//                break;
//            case MotionEvent.ACTION_UP:
//                view.performClick();
//                break;
//            default:
//                break;
//        }
//        return false;
//    };

//    private void toggle() {
//        if (mVisible) {
//            hide();
//        } else {
//            show();
//        }
//    }

//    private void hide() {
//        // Hide UI first
//        ActionBar actionBar = getSupportActionBar();
//        if (actionBar != null) {
//            actionBar.hide();
//        }
//        mControlsView.setVisibility(View.GONE);
//        mVisible = false;
//
//        // Schedule a runnable to remove the status and navigation bar after a delay
//        mHideHandler.removeCallbacks(mShowPart2Runnable);
//        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
//    }

//    private final Runnable mHidePart2Runnable = new Runnable() {
//        @SuppressLint("InlinedApi")
//        @Override
//        public void run() {
//            // Delayed removal of status and navigation bar
//
//            // Note that some of these constants are new as of API 16 (Jelly Bean)
//            // and API 19 (KitKat). It is safe to use them, as they are inlined
//            // at compile-time and do nothing on earlier devices.
//            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
//                    | View.SYSTEM_UI_FLAG_FULLSCREEN
//                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
//        }
//    };

//    private void show() {
//        // Show the system bar
//        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
//        mVisible = true;
//
//        // Schedule a runnable to display UI elements after a delay
//        mHideHandler.removeCallbacks(mHidePart2Runnable);
//        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
//    }

//    private final Runnable mShowPart2Runnable = new Runnable() {
//        @Override
//        public void run() {
//            // Delayed display of UI elements
//            ActionBar actionBar = getSupportActionBar();
//            if (actionBar != null) {
//                actionBar.show();
//            }
//            mControlsView.setVisibility(View.VISIBLE);
//        }
//    };

//    private final Handler mHideHandler = new Handler();
//    private final Runnable mHideRunnable = this::hide;

//    /**
//     * Schedules a call to hide() in delay milliseconds, canceling any
//     * previously scheduled calls.
//     */
//    private void delayedHide(int delayMillis) {
//        mHideHandler.removeCallbacks(mHideRunnable);
//        mHideHandler.postDelayed(mHideRunnable, delayMillis);
//    }
}