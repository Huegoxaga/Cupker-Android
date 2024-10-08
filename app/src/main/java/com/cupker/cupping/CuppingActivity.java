package com.cupker.cupping;
/**
 * Ye Qi, 000792058
 */
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Sample;
import com.amplifyframework.datastore.generated.model.Session;
import com.amplifyframework.datastore.generated.model.Status;
import com.cupker.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This is the main cupping grading page in full Screen
 */
public class CuppingActivity extends AppCompatActivity {

    // Keys
    private static final String TAG = "===CUPPING ACTIVITY===";
    private static final int DONE_CUPPING = 1;
    private static final String SAMPLE_NUMBER = "SAMPLE NUMBER";
    private static final String SESSION_NAME = "SESSION NAME";
    private static final String ROASTER_ID = "ROASTER ID";
    private static final String ROAST_TIME = "ROAST TIME";

    // UI & Controllers
    private ListView cuppingListView;
    private CuppingListAdapter cuppingListAdapter;
    private LinearLayout mainFrame;
    private ImageButton timerToggleButton, resetTimerButton;
    private TextView titleText;
    private Button saveButton;

    // Data
    private List<Sample> samples;
    private Session newSession;
    private String sessionName;
    private String roastTimeString;
    private Timer timer;
    private boolean timerStarted = false;
    private TimerTask timerTask;
    private Double time = 0.0;
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
        setContentView(R.layout.activity_cupping);
        mainFrame = findViewById(R.id.cupping_activity_main_frame);
        cuppingListView = findViewById(R.id.cupping_activity_list);
        timerToggleButton = findViewById(R.id.cupping_activity_timer_toggle_btn);
        Intent intent = getIntent();
        LayoutInflater layoutInflater = getLayoutInflater();
        Toolbar toolBar = findViewById(R.id.cupping_activity_toolbar);
        titleText = findViewById(R.id.cupping_toolbar_title);
//        View cuppingListViewHeader = layoutInflater.inflate(R.layout.activity_cupping_list_header, cuppingListView, false);
//        View cuppingListViewFooter = layoutInflater.inflate(R.layout.activity_cupping_list_footer, cuppingListView, false);
//        cuppingListView.addHeaderView(cuppingListViewHeader);
//        cuppingListView.addFooterView(cuppingListViewFooter);
//        TextView titleDate = findViewById(R.id.cupping_activity_title_date); // View exists only after adding header
//        TextView titleRoaster = findViewById(R.id.cupping_activity_title_roaster); // View exists only after adding header
        final View decorView = getWindow().getDecorView();

        // Init Data
        if (intent != null
                && intent.hasExtra(SESSION_NAME)
                && intent.hasExtra(ROASTER_ID)
                && intent.hasExtra(ROAST_TIME)
                && intent.hasExtra(SAMPLE_NUMBER)) {
            sessionName = intent.getStringExtra(SESSION_NAME);
            String roasterID = intent.getStringExtra(ROASTER_ID);
            roastTimeString = intent.getStringExtra(ROAST_TIME);
            int sampleNum = intent.getIntExtra(SAMPLE_NUMBER, 0);

            newSession = Session.builder()
                    .name(sessionName)
                    .roasterId(roasterID)
                    .roastTime(new Temporal.DateTime(roastTimeString))
                    .status(Status.ACTIVE)
                    .build();

            Log.i(TAG, "Got Session: " + newSession);
            samples = new ArrayList<>();
            for (int i = 0; i < sampleNum; i++) {
                Sample newSample = Sample.builder()
                        .sessionId(newSession.getId())
                        .beanId("00000000-0000-0000-0000-000000000000")
                        .sampleOrder(i)
                        .aroma(6.0)
                        .flavor(6.0)
                        .acidity(6.0)
                        .body(6.0)
                        .balance(6.0)
                        .uniformity(0.0)
                        .cleanCup(0.0)
                        .afterTaste(6.0)
                        .sweetness(0.0)
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
            cuppingListAdapter = new CuppingListAdapter(this, samples, true);
        }

        // Setup
        mainFrame.setSystemUiVisibility(flags);
        cuppingListView.setAdapter(cuppingListAdapter);
        cuppingListView.setDivider(ContextCompat.getDrawable(this, R.drawable.linear_layout_divider_vertical_brown_light));
        setSupportActionBar(toolBar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        timer = new Timer();
        titleText.setText(sessionName);
//        titleDate.setText(roastTimeString);

        // onClick listener for the "SAVE" button

        saveButton = findViewById(R.id.cupping_activity_save_button);
        saveButton.setEnabled(false);
        saveButton.setOnClickListener(v -> {
            // Set result code for redirect
            setResult(DONE_CUPPING);

            // Upload completed cupping Data
            Amplify.DataStore.save(newSession,
                    savedSession -> {
                        for (Sample sample : samples) {
                            Log.i(TAG, "Saving sample: " + sample.toString());
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


        decorView.setOnSystemUiVisibilityChangeListener(visibility -> {
            if ((visibility & View.SYSTEM_UI_FLAG_FULLSCREEN) == 0) {
                decorView.setSystemUiVisibility(flags);
            }
        });

        cuppingListView.setOnTouchListener((v, event) -> {

            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(mainFrame.getWindowToken(), 0);

            return false;
        });

//        https://stackoverflow.com/questions/25350137/check-if-android-listview-is-scrolled-down-to-the-last-item
        cuppingListView.setOnScrollListener(new AbsListView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (cuppingListView.getAdapter() == null)
                    return ;

                if (cuppingListView.getAdapter().getCount() == 0)
                    return ;

                int l = visibleItemCount + firstVisibleItem;
                if (l >= totalItemCount && !saveButton.isEnabled()) {
                    saveButton.setEnabled(true);
                    // It is time to add new data. We call the listener
//                    Log.i(TAG, "onScroll: last one");

                }
            }
        });

        resetTimerButton =  findViewById(R.id.cupping_activity_timer_reset_btn);
        resetTimerButton.setVisibility(View.INVISIBLE);
        resetTimerButton.setOnClickListener(v -> {

            AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
            resetAlert.setTitle(this.getString(R.string.reset_timer));
            resetAlert.setMessage(this.getString(R.string.timer_prompt));
            resetAlert.setPositiveButton(this.getString(R.string.reset), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (timerTask != null) {
                        timerTask.cancel();
                        timerToggleButton.setImageResource(R.drawable.play);
                        time = 0.0;
                        timerStarted = false;
                        titleText.setText(sessionName);
                        resetTimerButton.setVisibility(View.INVISIBLE);
                    }
                }
            });

            resetAlert.setNeutralButton(this.getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //do nothing
                }
            });

            resetAlert.show();
        });
        findViewById(R.id.cupping_activity_timer_toggle_btn).setOnClickListener(v -> {

            if (!timerStarted) {
                timerStarted = true;
                timerToggleButton.setImageResource(R.drawable.pause);

                startTimer();
            } else {
                timerStarted = false;
                timerToggleButton.setImageResource(R.drawable.play);
                timerTask.cancel();
            }

        });
//        mainFrame.setOnTouchListener((view, motionEvent) -> {
//            getSupportActionBar().hide();
//            return false;
//        });
//        getSupportActionBar().addOnMenuVisibilityListener((isVisible) -> {
//            Log.e(TAG, "UPUPUPUP");
//        });
    }


    private void startTimer() {
        resetTimerButton.setVisibility(View.VISIBLE);
        timerTask = new TimerTask()
        {
            @Override
            public void run()
            {
                runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        time++;
                        titleText.setText(getTimerText());
                    }
                });
            }

        };
        timer.scheduleAtFixedRate(timerTask, 0 ,1000);
    }


    private String getTimerText() {
        int rounded = (int) Math.round(time);

        int seconds = ((rounded % 86400) % 3600) % 60;
        int minutes = ((rounded % 86400) % 3600) / 60;

        return formatTime(seconds, minutes);
    }

    private String formatTime(int seconds, int minutes) {
        return String.format("%02d",minutes) + " : " + String.format("%02d",seconds);
    }

    /**
     * Update the score, called from the grid adapter
     * @param listPosition
     * @param gridPosition
     * @param newScore
     */
    public void setScore(int listPosition, int gridPosition, double newScore) {
//        Log.d(TAG, samples.toString());
        Sample sample = samples.get(listPosition);
        Sample editedSample;

        switch (gridPosition) {
            case 0:
                editedSample = sample.copyOfBuilder()
                        .aroma(newScore)
                        .build();
                break;
            case 1:
                editedSample = sample.copyOfBuilder()
                        .flavor(newScore)
                        .build();
                break;
            case 2:
                editedSample = sample.copyOfBuilder()
                        .afterTaste(newScore)
                        .build();
                break;
            case 3:
                editedSample = sample.copyOfBuilder()
                        .acidity(newScore)
                        .build();
                break;
            case 4:
                editedSample = sample.copyOfBuilder()
                        .body(newScore)
                        .build();
                break;
            case 5:
                editedSample = sample.copyOfBuilder()
                        .uniformity(newScore)
                        .build();
                break;
            case 6:
                editedSample = sample.copyOfBuilder()
                        .cleanCup(newScore)
                        .build();
                break;
            case 7:
                editedSample = sample.copyOfBuilder()
                        .overall(newScore)
                        .build();
                break;
            case 8:
                editedSample = sample.copyOfBuilder()
                        .balance(newScore)
                        .build();
                break;
            case 9:
                editedSample = sample.copyOfBuilder()
                        .sweetness(newScore)
                        .build();
                break;
            case 10:
                editedSample = sample.copyOfBuilder()
                        .defectCount(newScore)
                        .build();
                break;
            case 11:
                editedSample = sample.copyOfBuilder()
                        .defectType(newScore)
                        .build();
                break;
            default:
                editedSample = sample.copyOfBuilder().build();
        }
        Log.i(TAG, "Score set: " + newScore);
        samples.set(listPosition, editedSample);
        cuppingListAdapter.notifyDataSetChanged();
    }

    /**
     * get a sample's particular field's score
     *
     * method is designed for highlighting GradingDialogFragment's text view, for better UX only
     * @param listPosition
     * @param gridPosition
     * @return
     */
    public double getScore(int listPosition, int gridPosition) {
        Sample target = samples.get(listPosition);

        if(target != null) {
            switch (gridPosition) {
                case 0:
                    return target.getAroma();
                case 1:
                    return target.getFlavor();
                case 2:
                    return target.getAfterTaste();
                case 3:
                    return target.getAcidity();
                case 4:
                    return target.getBody();
                case 5:
                    return target.getUniformity();
                case 6:
                    return target.getCleanCup();
                case 7:
                    return target.getOverall();
                case 8:
                    return target.getBalance();
                case 9:
                    return target.getSweetness();
                case 10:
                    return target.getDefectCount();
                case 11:
                    return target.getDefectType();
                default:
                    return 0;
            }
        }
        return 0;
    }

    /**
     * Set note data called from list adapter
     * @param listPosition
     * @param newNote
     */
    public void setNote(int listPosition, String newNote) {
        Sample sample = samples.get(listPosition);
        Sample editedSample = sample.copyOfBuilder()
                .notes(newNote)
                .build();
        samples.set(listPosition, editedSample);
        Log.d(TAG, newNote);
    }

    /**
     * Set roast level data called from list adapter
     * @param listPosition
     * @param newRoastLevel
     */
    public void setRoastLevel(int listPosition, double newRoastLevel) {
        Sample sample = samples.get(listPosition);
        Sample editedSample = sample.copyOfBuilder()
                .roastLevel(newRoastLevel)
                .build();
        samples.set(listPosition, editedSample);
        Log.d(TAG, newRoastLevel + "");
    }

    /**
     * Set bean selection called from list adapter
     * @param listPosition
     * @param bean
     */
    public void setBean(int listPosition, Bean bean) {
        Sample sample = samples.get(listPosition);
        Sample editedSample = sample.copyOfBuilder()
                .beanId(bean.getId())
                .build();
        samples.set(listPosition, editedSample);
        Log.d(TAG, editedSample + "Edited in list index" + listPosition);
    }
}