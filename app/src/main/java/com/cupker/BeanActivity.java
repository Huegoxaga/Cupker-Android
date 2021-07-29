package com.cupker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Sample;
import com.amplifyframework.datastore.generated.model.Session;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class BeanActivity extends AppCompatActivity {


    private static final String TAG = "===BEAN ACTIVITY===";
    private static final String BEAN_OBJ = "BEAN OBJECT";

    private ListView cuppingListView;
    private Bean bean;
    private Session session;
    private CuppingListAdapter cuppingListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bean);
        Toolbar toolbar = findViewById(R.id.bean_toolbar);
//        image = findViewById((R.id.add_bean_new_image));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Init
        Intent intent = getIntent();

        // Fetch Data
        if (intent != null && intent.hasExtra(BEAN_OBJ)) {
            String beanObjStr = intent.getStringExtra(BEAN_OBJ);
            Log.d(TAG, beanObjStr);
            Gson gson = new Gson();
            bean = gson.fromJson(beanObjStr, Bean.class);
        }

        // Listener
 }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_bean_menu_save_button) {
            Intent startNewBeamIntent = new Intent(this, NewBeanActivity.class);
            startActivity(startNewBeamIntent);
            return true;
        }
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}