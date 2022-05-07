package com.cupker.history;
/**
 * Ye Qi, 000792058
 */

import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Roaster;
import com.amplifyframework.datastore.generated.model.Sample;
import com.amplifyframework.datastore.generated.model.Session;
import com.amplifyframework.datastore.generated.model.Status;
import com.cupker.BuildConfig;
import com.cupker.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
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
    private TextView infoText;
    private TextView headerText;
    private HistoryListAdapter cuppingListAdapter;
    private View cuppingListViewHeader;

    private View cuppingListViewFooter;

    // Data
    private Session session;
    private Roaster roaster;
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
        cuppingListView.setDivider(ContextCompat.getDrawable(this, R.drawable.linear_layout_divider_vertical_brown_light));
        Intent intent = getIntent();
        Toolbar toolBar = findViewById(R.id.history_activity_toolbar);
        titleText = findViewById(R.id.history_toolbar_title);
        cuppingListViewFooter = getLayoutInflater().inflate(R.layout.activity_history_list_footer, cuppingListView, false);
        cuppingListView.addFooterView(cuppingListViewFooter);
        infoText = findViewById(R.id.history_activity_session_info);
        // header is only created for screenshot, remove after setText
        cuppingListViewHeader = getLayoutInflater().inflate(R.layout.activity_history_list_header, cuppingListView, false);
        cuppingListView.addHeaderView(cuppingListViewHeader);
        headerText = findViewById(R.id.cupping_history_list_title);

        final View decorView = getWindow().getDecorView();

        // Init Data
        context = this;
        samples = new ArrayList<>();
        // Add bean list first record
        beanObjs.add(Bean.builder()
                .status(Status.INACTIVE)
                .id("00000000-0000-0000-0000-000000000000")
                .name(context.getString(R.string.blind_taste))
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
//                                            Log.d(TAG, s.toString());
                                        }
                                        editMode = false;
                                        // sort samples by sample order
                                        Collections.sort(samples, new Comparator<Sample>() {
                                            @Override
                                            public int compare(Sample s1, Sample s2) {
                                                return s1.getSampleOrder().compareTo(s2.getSampleOrder());
                                            }
                                        });
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
                            Amplify.DataStore.query(Roaster.class, Where.id(session.getRoasterId()),
                                    matchRoaster -> {
                                        if (matchRoaster.hasNext()) {
                                            roaster = matchRoaster.next();
                                            handler.post(updateInfo);
                                        }
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
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.back);
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
                cuppingListAdapter = new HistoryListAdapter(context, samples, editMode, beanObjs);
                cuppingListView.setAdapter(cuppingListAdapter);
                titleText.setText(session.getName());
                headerText.setText(session.getName());
                cuppingListView.removeHeaderView(cuppingListViewHeader);
            }
        }
    };

    private final Runnable updateInfo = new Runnable() {
        @Override
        public void run() {
            if (cuppingListView != null && infoText != null) {
                infoText.setText(getSessionInfoStr(session, roaster));
            }
        }
    };

    private String getSessionInfoStr(Session session, Roaster roaster) {
        String info = "My History Cupping Session";
        String cuppingDateStr = "";
        String roasterDateStr = "";
        if (session != null && roaster != null) {
            if (session.getRoastTime() != null) roasterDateStr = new SimpleDateFormat("yyyy-MM-dd").format(session.getRoastTime().toDate());
            if (session.getCreatedAt() != null) cuppingDateStr = new SimpleDateFormat("yyyy-MM-dd").format(session.getCreatedAt().toDate());
            info = String.format("Created on %s\nRoasted by %s on %s", cuppingDateStr, roaster.getName(), roasterDateStr);
        }

        return info;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_with_share, menu);
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
        } else if (item.getItemId() == R.id.menu_btn_share) {
            verifyStoragePermission(HistoryActivity.this);
            takeScreenShot(getWindow().getDecorView());
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

    /**
     * Set the bean name, called from list adapter
     *
     * @param listPosition
     * @param bean
     */
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

    /**
     * @param view
     */
    private void takeScreenShot(View view) {

        //This is used to provide file name with Date a format
        Date date = new Date();
        CharSequence format = DateFormat.format("MM-dd-yyyy_hh:mm:ss", date);

        //It will make sure to store file to given below Directory and If the file Directory doesn't exist then it will create it.
        try {
            File mainDir = new File(
                    this.getExternalFilesDir(Environment.DIRECTORY_PICTURES), "FilShare");
            if (!mainDir.exists()) {
                boolean mkdir = mainDir.mkdir();
            }

            //Providing file name along with Bitmap to capture screenview
            String path = mainDir + "/" + "Session" + "-" + format + ".jpeg";
//            view.setDrawingCacheEnabled(true);
//            Bitmap bitmap = Bitmap.createBitmap(view.getDrawingCache());
//            view.setDrawingCacheEnabled(false);

            // Get bitmap
            ListView listview = cuppingListView;
            ListAdapter adapter = cuppingListAdapter;
            int itemscount = adapter.getCount();
            int totalHeight = 0;
            List<Bitmap> bmps = new ArrayList<Bitmap>();


            // Add Header
            cuppingListView.addHeaderView(cuppingListViewHeader);

            cuppingListViewHeader.measure(View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            cuppingListViewHeader.layout(0, 0, cuppingListViewHeader.getMeasuredWidth(), cuppingListViewHeader.getMeasuredHeight());
            cuppingListViewHeader.setDrawingCacheEnabled(true);
            cuppingListViewHeader.buildDrawingCache();
            bmps.add(cuppingListViewHeader.getDrawingCache());
            totalHeight += cuppingListViewHeader.getMeasuredHeight();
            cuppingListView.removeHeaderView(cuppingListViewHeader);

            // Add List
            for (int i = 0; i < itemscount; i++) {

                View childView = adapter.getView(i, null, listview);
                childView.measure(View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

                childView.layout(0, 0, childView.getMeasuredWidth(), childView.getMeasuredHeight());
                childView.setDrawingCacheEnabled(true);
                childView.buildDrawingCache();
                bmps.add(childView.getDrawingCache());
                totalHeight += childView.getMeasuredHeight();
            }

            // Add Footer
            cuppingListViewFooter.measure(View.MeasureSpec.makeMeasureSpec(listview.getWidth(), View.MeasureSpec.EXACTLY),
                    View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));

            cuppingListViewFooter.layout(0, 0, cuppingListViewFooter.getMeasuredWidth(), cuppingListViewFooter.getMeasuredHeight());
            cuppingListViewFooter.setDrawingCacheEnabled(true);
            cuppingListViewFooter.buildDrawingCache();
            bmps.add(cuppingListViewFooter.getDrawingCache());
            totalHeight += cuppingListViewFooter.getMeasuredHeight();


            Bitmap longBitmap = Bitmap.createBitmap(listview.getMeasuredWidth(), totalHeight, Bitmap.Config.ARGB_8888);
            Canvas bigCanvas = new Canvas(longBitmap);

            Paint paint = new Paint();
            int iHeight = 0;

            for (int i = 0; i < bmps.size(); i++) {
                Bitmap bmp = bmps.get(i);
                bigCanvas.drawBitmap(bmp, 0, iHeight, paint);
                iHeight += bmp.getHeight();

//                bmp.recycle();
//                bmp = null;
            }

            //This logic is used to save file at given location with the given filename and compress the Image Quality.
            File imageFile = new File(path);
            FileOutputStream fileOutputStream = new FileOutputStream(imageFile);
            longBitmap.compress(Bitmap.CompressFormat.PNG, 90, fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();

            // Save to gallery
            MediaStore.Images.Media.insertImage(getContentResolver(), longBitmap, "Session Record - " + session.getName(), getSessionInfoStr(session, roaster));

            // Show Alert
            AlertDialog.Builder resetAlert = new AlertDialog.Builder(this);
            resetAlert.setTitle(this.getString(R.string.share_session));
            resetAlert.setMessage(this.getString(R.string.share_prompt));
            resetAlert.setPositiveButton(this.getString(R.string.confirm_share), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    shareScreenShot(imageFile);
                }
            });

            resetAlert.setNeutralButton(this.getString(R.string.cancel_button), new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    //do nothing
                }
            });

            resetAlert.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Share Screenshot
     *
     * @param imageFile
     */
    private void shareScreenShot(File imageFile) {

        //Using sub-class of Content provider
        Uri uri = FileProvider.getUriForFile(
                this,
                BuildConfig.APPLICATION_ID + ".provider",
                imageFile);

        //Explicit intent
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_SEND);
        intent.setType("image/*");
        intent.putExtra(android.content.Intent.EXTRA_TEXT, "Share my cupping history");
        intent.putExtra(Intent.EXTRA_STREAM, uri);

        // Setup chooser
        Intent chooser = Intent.createChooser(intent, "Share File");

        // Add permission to all share options
        List<ResolveInfo> resInfoList = this.getPackageManager().queryIntentActivities(chooser, PackageManager.MATCH_DEFAULT_ONLY);

        for (ResolveInfo resolveInfo : resInfoList) {
            String packageName = resolveInfo.activityInfo.packageName;
            this.grantUriPermission(packageName, uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }

        //It will show the application which are available to share Image; else Toast message will throw.
        try {
            this.startActivity(chooser);
        } catch (ActivityNotFoundException e) {
            Toast.makeText(this, "No App Available", Toast.LENGTH_SHORT).show();
        }
    }

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static final String[] PERMISSION_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
    };


    /**
     * Verifies the permission
     *
     * @param activity
     */
    public static void verifyStoragePermission(Activity activity) {
        int permission = ActivityCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE);

        if (permission != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(
                    activity,
                    PERMISSION_STORAGE,
                    REQUEST_EXTERNAL_STORAGE);
        }
    }
}