package com.cupker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.InputType;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Session;

import java.io.ByteArrayOutputStream;

/**
 * This defines bean detail page
 */
public class BeanActivity extends AppCompatActivity {

    // Keys
    private static final String TAG = "===BEAN ACTIVITY===";
    private static final String BEAN_ID = "BEAN ID";

    // UI & Controllers

    // Data
    private Bean bean;
    private Session session;
    ImageView beanImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init View
        setContentView(R.layout.activity_bean);
        Toolbar toolbar = findViewById(R.id.bean_toolbar);
        beanImage = findViewById((R.id.add_bean_new_image));
        Intent intent = getIntent();

        // Fetch Data
        if (intent != null && intent.hasExtra(BEAN_ID)) {
            String beanID = intent.getStringExtra(BEAN_ID);
            Log.d(TAG, beanID);
            Amplify.DataStore.query(Bean.class, Where.matches(Bean.ID.eq(beanID)),
                    match -> {
                        if (match.hasNext()) {
                            bean = match.next();
                            handler.post(updateView);
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

    // TODO: make this edit bean handler
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_bean_menu_save_button) {
            Intent startNewBeamIntent = new Intent(this, NewBeanActivity.class);
            startActivity(startNewBeamIntent);
            return true;
        }
        // default handler
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    private final Handler handler = new Handler();

    private final Runnable updateView = new Runnable() {
        @Override
        public void run() {
            // Populate data
            // TODO: create new UI for this page
            ((EditText) findViewById(R.id.new_bean_name_input)).setText(bean.getName());
            ((EditText) findViewById(R.id.new_bean_name_input)).setInputType(InputType.TYPE_NULL);
            ((EditText) findViewById(R.id.new_bean_name_input)).setBackgroundResource(android.R.color.transparent);
            ((EditText) findViewById(R.id.new_bean_origin_input)).setText(bean.getOrigin());
            ((EditText) findViewById(R.id.new_bean_altitude_low)).setText(bean.getAltitudeLow());
            ((EditText) findViewById(R.id.new_bean_altitude_high)).setText(bean.getAltitudeHigh());
            ((EditText) findViewById(R.id.new_bean_variety_input)).setText(bean.getVariety());
            ((EditText) findViewById(R.id.new_bean_region_input)).setText(bean.getRegion());
            ((EditText) findViewById(R.id.new_bean_density_input)).setText(bean.getDensity());
            ((EditText) findViewById(R.id.new_bean_moisture_input)).setText(bean.getMoisture());
            ((EditText) findViewById(R.id.new_bean_grade_input)).setText(bean.getGrade());
            // Decode image string
            byte[] imageBytes = Base64.decode(bean.getImage(), Base64.DEFAULT);
            Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            beanImage.setImageBitmap(decodedImage);
        }
    };
}