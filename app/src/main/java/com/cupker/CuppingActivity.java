package com.cupker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
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
public class CuppingActivity extends AppCompatActivity {
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
    private static final String TAG = "===CUPPING ACTIVITY===";
    private static final int DONE_CUPPING = 1;
    private static final String SAMPLE_NUMBER = "SAMPLE NUMBER";
    private static final String SESSION_NAME = "SESSION NAME";
    private static final String ROASTER_ID = "ROASTER ID";
    private static final String ROAST_TIME = "ROAST TIME";

    private String sessionName;
    private ListView cuppingListView;
    private List<Sample> samples;
    private Session newSession;
    private CuppingListAdapter cuppingListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Init
        setContentView(R.layout.activity_cupping);
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
        TextView titleText = findViewById(R.id.cupping_activity_title_label);

        // Get incoming session input
        if (intent !=null
                && intent.hasExtra(SESSION_NAME)
                && intent.hasExtra(ROASTER_ID)
                && intent.hasExtra(ROAST_TIME)
                && intent.hasExtra(SAMPLE_NUMBER)) {
            sessionName = intent.getStringExtra(SESSION_NAME);
            String roasterID = intent.getStringExtra(ROASTER_ID);
            String roastTimeString = intent.getStringExtra(ROAST_TIME);
            int sampleNum = intent.getIntExtra(SAMPLE_NUMBER, 0);

            newSession = Session.builder()
                    .name(sessionName)
                    .roasterId(roasterID)
                    .roastTime(new Temporal.DateTime(roastTimeString))
                    .build();

            Log.i(TAG, "Got Session: " + newSession);
            samples = new ArrayList<>();
            for(int i = 0; i<sampleNum; i++){
                Sample newSample = Sample.builder()
                        .sessionId(newSession.getId())
                        .aroma(6.0)
                        .flavor(6.0)
                        .acidity(6.0)
                        .body(6.0)
                        .balance(6.0)
                        .uniformity(10.0)
                        .cleanCup(10.0)
                        .afterTaste(6.0)
                        .sweetness(10.0)
                        .roastLevel(65.0)
                        .defectType(2.0)
                        .defectCount(0.0)
                        .overall(6.0)
                        .build();
                samples.add(newSample);
            }

            Log.d(TAG, "Check List" + samples.toString());
            Log.d(TAG, String.format("Sample Number: %d in onCreate", samples.size()));
            Log.d(TAG, String.format("Session Name: %s in onCreate", sessionName));
            cuppingListAdapter = new CuppingListAdapter(this, samples);
        }

        // Setup
        mainFrame.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        titleText.setText(sessionName);
        cuppingListView.setAdapter(cuppingListAdapter);



        // Listener
//        mContentView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                toggle();
//            }
//        });

        findViewById(R.id.cupping_activity_save_button).setOnClickListener(v -> {
            setResult(DONE_CUPPING);
            Amplify.DataStore.save(newSession,
                    savedSession -> {
                        for (Sample sample : samples)
                        {
                            Log.i(TAG, "Saving sample: " + sample);
                            Amplify.DataStore.save(sample,
                                    success -> Log.i(TAG, "Saved item: " + success.item().getId()),
                                    error -> Log.e(TAG, "Could not save sample", error)
                            );
                        }
                    },
                    error -> Log.e(TAG, "Could not save Session", error)
            );

            finish();
        });

        // Upon interacting with UI controls, delay any scheduled hide()
        // operations to prevent the jarring behavior of controls going away
        // while interacting with the UI.
//        findViewById(R.id.dummy_button).setOnTouchListener(mDelayHideTouchListener);
    }

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

    public void setScore(int listPosition, int gridPosition, double newScore) {
        Sample sample = samples.get(listPosition);
        Sample editedSample;

        switch (gridPosition) {
            case 0 :
                editedSample = sample.copyOfBuilder()
                        .aroma(newScore)
                        .build();
                break;
            case 1 :
                editedSample = sample.copyOfBuilder()
                        .flavor(newScore)
                        .build();
                break;
            case 2 :
                editedSample = sample.copyOfBuilder()
                        .afterTaste(newScore)
                        .build();
                break;
            case 3 :
                editedSample = sample.copyOfBuilder()
                        .acidity(newScore)
                        .build();
                break;
            case 4 :
                editedSample = sample.copyOfBuilder()
                        .body(newScore)
                        .build();
                break;
            case 5 :
                editedSample = sample.copyOfBuilder()
                        .uniformity(newScore)
                        .build();
                break;
            case 6 :
                editedSample = sample.copyOfBuilder()
                        .cleanCup(newScore)
                        .build();
                break;
            case 7 :
                editedSample = sample.copyOfBuilder()
                        .overall(newScore)
                        .build();
                break;
            case 8 :
                editedSample = sample.copyOfBuilder()
                        .balance(newScore)
                        .build();
                break;
            case 9 :
                editedSample = sample.copyOfBuilder()
                        .sweetness(newScore)
                        .build();
                break;
            case 10 :
                editedSample = sample.copyOfBuilder()
                        .defectCount(newScore)
                        .build();
                break;
            case 11 :
                editedSample = sample.copyOfBuilder()
                        .defectType(newScore)
                        .build();
                break;
            default:
                editedSample = sample.copyOfBuilder().build();
        }
        samples.set(listPosition, editedSample);
        cuppingListView.setAdapter(cuppingListAdapter);
    }

    public void setNote(int listPosition, String newNote) {
        Sample sample = samples.get(listPosition);
        Sample editedSample = sample.copyOfBuilder()
                .notes(newNote)
                .build();
        samples.set(listPosition, editedSample);
        Log.d(TAG, newNote);
    }

    public void setRoastLevel(int listPosition, double newRoastLevel) {
        Sample sample = samples.get(listPosition);
        Sample editedSample = sample.copyOfBuilder()
                .roastLevel(newRoastLevel)
                .build();
        samples.set(listPosition, editedSample);
        Log.d(TAG, newRoastLevel + "");
    }

    public void setBean(int listPosition, Bean bean) {
        Sample sample = samples.get(listPosition);
        Sample editedSample = sample.copyOfBuilder()
                .bean(bean)
                .build();
        samples.set(listPosition, editedSample);
        Log.d(TAG, editedSample + "Edited in list index" + listPosition);
    }
}