package com.cupker;
/**
 * Ye Qi, 000792058
 */
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.FileProvider;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Dealer;
import com.amplifyframework.datastore.generated.model.Status;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * This defines add new bean form page
 */
public class NewBeanActivity extends AppCompatActivity {

    // Keys
    private static final String TAG = "===NEW BEAN ACTIVITY===";
    private static final int DONE_ADD_BEAN = 2;

    // UI & Controllers
    private ImageButton newImageButton;
    private String currentPhotoPath;
    private Spinner dealerSpinner;

    // Data
    private String imageStr = null;
    private ArrayList<String> dealerString = new ArrayList<>();
    private ArrayList<Dealer> dealerObjs = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init
        setContentView(R.layout.activity_new_bean);
        Toolbar toolbar = findViewById(R.id.new_bean_toolbar);
        newImageButton = findViewById((R.id.add_bean_new_image));
        dealerSpinner = findViewById(R.id.new_bean_dealer_input);
        ArrayAdapter<String> dealerArray = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dealerString);


        // Query & populate Bean data
        dealerString.add(this.getString(R.string.select));
        dealerObjs.add(null);
        Amplify.DataStore.query(Dealer.class,
                Where.matches(Dealer.STATUS.eq(Status.ACTIVE)),
                queryRoaster -> {
                    while (queryRoaster.hasNext()) {
                        Dealer dealer = queryRoaster.next();
                        dealerString.add(dealer.getName());
                        dealerObjs.add(dealer);
                        Log.i(TAG, "Get Bean Name: " + dealer.getName());
                    }
                },
                error -> Log.e(TAG, "Error retrieving beans", error)
        );

        // Setup
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dealerSpinner.setAdapter(dealerArray);
        dealerSpinner.setSelection(0, false);

        // Listener
        newImageButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View arg0, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        newImageButton.setBackgroundColor(Color.parseColor("#bdbdbd"));
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        dispatchTakePictureIntent();
                    }
                    default: {
                        newImageButton.setBackgroundColor(Color.parseColor("#D8D8D8"));

                    }
                }
                return true;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_with_save, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 3 && resultCode == RESULT_OK) {
            setPic();
        }
    }

    private void setPic() {
        // Get the dimensions of the View
        // int targetW = newImageButton.getWidth();
        int targetW = 640;
        int targetH = newImageButton.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;

        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.max(1, Math.min(photoW / targetW, photoH / targetH));

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(currentPhotoPath, bmOptions);

        // Display
        newImageButton.setScaleType(ImageView.ScaleType.CENTER_CROP);
        newImageButton.setImageBitmap(bitmap);

        // Generate Base64 String
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        imageStr = Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private void dispatchTakePictureIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 3);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private final Handler handler = new Handler();

    private final Runnable goBack = this::finish;

    /**
     * Handle save bean btn
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.add_bean_menu_save_button:
                setResult(DONE_ADD_BEAN);
                String dealerID = dealerSpinner.getSelectedItemPosition() == 0 ? "" : dealerObjs.get(dealerSpinner.getSelectedItemPosition()).getId();
                Bean bean = Bean.builder()
                        .status(Status.ACTIVE)
                        .name(((EditText) findViewById(R.id.new_bean_name_input)).getText().toString())
                        .process(((EditText) findViewById(R.id.new_bean_process_input)).getText().toString())
                        .origin(((EditText) findViewById(R.id.new_bean_origin_input)).getText().toString())
                        .altitudeHigh(((EditText) findViewById(R.id.new_bean_altitude_high)).getText().toString())
                        .altitudeLow(((EditText) findViewById(R.id.new_bean_altitude_low)).getText().toString())
                        .variety(((EditText) findViewById(R.id.new_bean_variety_input)).getText().toString())
                        .region(((EditText) findViewById(R.id.new_bean_region_input)).getText().toString())
                        .density(((EditText) findViewById(R.id.new_bean_density_input)).getText().toString())
                        .moisture(((EditText) findViewById(R.id.new_bean_moisture_input)).getText().toString())
                        .grade(((EditText) findViewById(R.id.new_bean_grade_input)).getText().toString())
                        .dealer(dealerID)
                        .image(imageStr)
                        .build();
                Amplify.DataStore.save(bean,
                        success ->
                        {
                            Log.i(TAG, "Saved item: " + success.item().getName());
                            handler.post(goBack);
                        },
                        error -> Log.e(TAG, "Could not save item to DataStore", error)
                );

                return true;

            default:
                // Default handler
                return super.onOptionsItemSelected(item);

        }
    }
}