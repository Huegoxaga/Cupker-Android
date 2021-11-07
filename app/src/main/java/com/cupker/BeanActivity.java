package com.cupker;
/**
 * Ye Qi, 000792058
 */

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.ImageView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Dealer;
import com.amplifyframework.datastore.generated.model.Sample;
import com.amplifyframework.datastore.generated.model.Status;

import java.util.ArrayList;
import java.util.Locale;

/**
 * This defines bean detail page
 */
public class BeanActivity extends AppCompatActivity {

    // Keys
    private static final String TAG = "===BEAN ACTIVITY===";
    private static final String BEAN_ID = "BEAN ID";

    // UI & Controllers
    private ImageView beanImage;
    private TextView titleText;
    private MenuItem editBtn;
    private TextView origin;
    private TextView region;
    private TextView name;
    private TextView variety;
    private TextView altitude;
    private TextView density;
    private TextView moisture;
    private TextView grade;
    private TextView dealerName;
    private TextView average;
    private TextView process;


    // Data
    private Bean bean;
    private Dealer dealer;
    private boolean editMode = false;
    private ArrayList<Double> scores = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init View
        setContentView(R.layout.activity_bean);
        Toolbar toolbar = findViewById(R.id.bean_toolbar);
        beanImage = findViewById((R.id.bean_image));
        Intent intent = getIntent();
        origin = findViewById(R.id.bean_origin_input);
        altitude = findViewById(R.id.bean_altitude_input);
        variety = findViewById(R.id.bean_variety_input);
        region = findViewById(R.id.bean_region_input);
        density = findViewById(R.id.bean_density_input);
        moisture = findViewById(R.id.bean_moisture_input);
        grade = findViewById(R.id.bean_grade_input);
        dealerName = findViewById(R.id.bean_dealer_input);
        average = findViewById(R.id.bean_average);
        process = findViewById(R.id.bean_process_input);
        name = findViewById(R.id.bean_name_input);
        titleText = findViewById(R.id.bean_toolbar_title);

        // Fetch Data
        if (intent != null && intent.hasExtra(BEAN_ID)) {
            String beanID = intent.getStringExtra(BEAN_ID);

            Amplify.DataStore.query(Bean.class, Where.id(beanID),
                    match -> {
                        if (match.hasNext()) {
                            bean = match.next();
                            Log.d(TAG, "BEAN: " + bean.getDealer());
                            Log.d(TAG, "DEALER: " + bean.getDealer());

                            if (!bean.getDealer().isEmpty()) {
                                Amplify.DataStore.query(Dealer.class, Where.id(bean.getDealer()),
                                        matchDealer -> {
                                            if (matchDealer.hasNext()) {
                                                dealer = matchDealer.next();
                                                handler.post(updateView);
                                            }
                                        },
                                        failure -> Log.e(TAG, "Query failed.", failure));
                            } else {
                                handler.post(updateView);
                            }
                        }
                    },
                    failure -> Log.e(TAG, "Query failed.", failure)
            );

            Amplify.DataStore.query(Sample.class, Where.matches(Sample.BEAN_ID.eq(beanID)),
                    match -> {
                        while (match.hasNext()) {
                            Sample sample = match.next();
                            scores.add(getScore(sample));
                        }
                        handler.post(updateScore);
                    },
                    failure -> Log.e(TAG, "Query Samples failed.", failure)
            );
        }

        // Setup
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // Listener
    }

    /**
     * Get total score of a type of bean
     *
     * @param sample
     * @return
     */
    private Double getScore(Sample sample) {
        Double total = sample.getAroma() + sample.getFlavor() + sample.getAfterTaste() + sample.getAcidity() + sample.getBody() + sample.getOverall() + sample.getBalance();
        total += 30 - 2 * (positionToCup(sample.getUniformity()) + positionToCup(sample.getCleanCup()) + positionToCup(sample.getSweetness()));
        total -= positionToCup(sample.getDefectCount()) * sample.getDefectType();

        return total;

    }

    /**
     * Generate position value from binary value
     *
     * @param positionCode
     * @return
     */
    private double positionToCup(Double positionCode) {
        String scorePosition = Integer.toBinaryString(positionCode.intValue());
        return scorePosition.length() - scorePosition.replace("1", "").length();

    }

    /**
     * Create edit menu btn
     *
     * @param menu existing menu btn
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (menu != null) {
            getMenuInflater().inflate(R.menu.toolbar_with_edit, menu);
            editBtn = menu.findItem(R.id.toolbar_edit_btn);
        }
        return true;
    }


    /**
     * Menu btm listener
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.toolbar_edit_btn) {
            return true;
        }
        // default handler
        return super.onOptionsItemSelected(item);
    }

    /**
     * Back buttom listener
     *
     * @return
     */
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private void changeMode() {
        int layout = editMode ? R.layout.activity_bean : R.layout.activity_new_bean;

//        View C = findViewById(R.id.C);
//        ViewGroup parent = (ViewGroup) C.getParent();
//        int index = parent.indexOfChild(C);
//        parent.removeView(C);
//        C = getLayoutInflater().inflate(optionId, parent, false);
//        parent.addView(C, index);
    }

    private final Handler handler = new Handler();

    /**
     * Update view
     */
    private final Runnable updateView = new Runnable() {
        @Override
        public void run() {
            // Populate data
            titleText.setText(bean.getName());
            name.setText(bean.getName());
            origin.setText(bean.getOrigin());
            region.setText(bean.getRegion());
            variety.setText(bean.getVariety());
            density.setText(bean.getDensity());
            moisture.setText(bean.getMoisture());
            grade.setText(bean.getGrade());
            if (dealer != null) dealerName.setText(dealer.getName());
            process.setText(bean.getProcess());

            if (!bean.getAltitudeLow().isEmpty() && !bean.getAltitudeHigh().isEmpty()) {
                altitude.setText(String.format(bean.getAltitudeLow() + " - " + bean.getAltitudeHigh()));
            } else if (!bean.getAltitudeLow().isEmpty()) {
                altitude.setText(bean.getAltitudeLow());
            } else if (!bean.getAltitudeHigh().isEmpty()) {
                altitude.setText(bean.getAltitudeHigh());
            }


            // Decode image string
            if (bean.getImage() != null) {
                byte[] imageBytes = Base64.decode(bean.getImage(), Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                beanImage.setImageBitmap(decodedImage);
            }

        }
    };

    /**
     * Update view
     */
    private final Runnable updateScore = new Runnable() {
        @Override
        public void run() {
            average.setText(String.format(Locale.getDefault(), "%.2f", scores.stream().mapToDouble(i -> i).average().orElse(0)));
        }
    };
}