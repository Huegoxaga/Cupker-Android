package com.cupker;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Bean;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * This is the bean list page
 */
public class BeansFragment extends Fragment {

    // Keys
    private static final String TAG = "===BEAN FRAGMENT===";

    // UI & Controllers
    private View view;
    private ListView beanList;
    private BeansFragment self = this;

    // Data
    private ArrayList<Bean> beanObjs;

    public BeansFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        // Update data
        beanObjs = new ArrayList<>();
        Amplify.DataStore.query(Bean.class,
                queryBean -> {
                    while (queryBean.hasNext()) {
                        Bean bean = queryBean.next();
                        beanObjs.add(bean);
                        Log.i(TAG, "Get Bean Name: " + bean.toString());
                    }
                    handler.post(r);
                },
                error -> Log.e(TAG,  "Error retrieving roasters", error)
        );
    }

    private final Handler handler = new Handler();

    private final Runnable r = new Runnable() {
        @Override
        public void run() {
            if (view != null && beanList != null) {
                BeanListAdapter beanListAdapter = new BeanListAdapter(self, beanObjs);
                beanList.setAdapter(beanListAdapter);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Init UI
        view = inflater.inflate(R.layout.fragment_beans, container, false);
        Toolbar toolbar = view.findViewById(R.id.bean_frag_toolbar);
        beanList = view.findViewById(R.id.bean_frag_list);

        // Setup
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        if (menu != null && inflater != null) {
            inflater.inflate(R.menu.toolbar_with_add, menu);
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_bean_menu_save_button) {
            Intent startNewBeamIntent = new Intent(getActivity(), NewBeanActivity.class);
            startActivity(startNewBeamIntent);
            return true;
        }
        // default handler
        return super.onOptionsItemSelected(item);
    }
}