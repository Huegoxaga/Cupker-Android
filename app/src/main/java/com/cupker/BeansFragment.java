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

public class BeansFragment extends Fragment {

    private static final String TAG = "===BEAN FRAGMENT===";

    private ArrayList<Bean> beanObjs;
    private View view;
    private ListView beanList;
    private BeansFragment self = this;

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
        beanObjs = new ArrayList<>();

        Amplify.DataStore.query(Bean.class,
                queryRoaster -> {
                    while (queryRoaster.hasNext()) {
                        Bean bean = queryRoaster.next();
                        beanObjs.add(bean);
                        Log.i(TAG, "Get Bean Name: " + bean.getName());
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
            if (view != null && beanList != null){
                BeanListAdapter beanListAdapter = new BeanListAdapter(self, beanObjs);
                beanList.setAdapter(beanListAdapter);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_beans, container, false);
        Toolbar toolbar = view.findViewById(R.id.bean_frag_toolbar);
        beanList = view.findViewById(R.id.bean_frag_list);
//        Log.d(TAG, "list length" + beanObjs.size());
        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        if (menu != null && inflater != null){
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
        // If we got here, the user's action was not recognized.
        // Invoke the superclass to handle it.
        return super.onOptionsItemSelected(item);
    }
}