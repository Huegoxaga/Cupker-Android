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
            Log.d(TAG, beanID);
            Amplify.DataStore.query(Bean.class, Where.id(beanID),
                    match -> {
                        if (match.hasNext()) {
                            bean = match.next();
                            Amplify.DataStore.query(Dealer.class, Where.id(bean.getDealer()),
                                    matchDealer -> {
                                        if (matchDealer.hasNext()) {
                                            dealer = matchDealer.next();
                                            handler.post(updateView);
                                        }
                                    },
                                    failure -> Log.e(TAG, "Query failed.", failure));
                        }
                    },
                    failure -> Log.e(TAG, "Query failed.", failure)
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
     * Create edit menu btn
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
            dealerName.setText(dealer.getName());
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
}