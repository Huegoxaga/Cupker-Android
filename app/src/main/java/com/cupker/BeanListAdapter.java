package com.cupker;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Bean;
import com.google.gson.Gson;

import java.util.List;

/**
 * This defines the bean list in bean page
 */
public class BeanListAdapter extends BaseAdapter {

    // Keys
    private static final String TAG = "===BEAN LIST ADAPTER===";
    private static final String BEAN_ID = "BEAN ID";
    private static final int START_BEAN_ACTIVITY = 3;

    // UI & Controllers
    private final BeansFragment beansFragment;

    // Data
    private final int beanNum;
    private final List<Bean> beans;

    public BeanListAdapter(BeansFragment fragment, List<Bean> beans) {
        // Init Data
        this.beansFragment = fragment;
        this.beanNum = beans.size();
        this.beans = beans;
    }

    @Override
    public int getCount() {
        return beanNum ;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View beansListView, ViewGroup parent) {

        // Init
        if (beansListView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(beansFragment.getContext());
            beansListView = layoutInflater.inflate(R.layout.fragment_beans_list, parent, false);
        }
        TextView beanName = beansListView.findViewById(R.id.bean_list_name);

        // Setup
        beanName.setText(beans.get(position).getName());

        // Listener
        beansListView.setOnClickListener(view -> {
            Intent startHistoryIntent = new Intent(beansFragment.getActivity(), BeanActivity.class);
            startHistoryIntent.putExtra(BEAN_ID, beans.get(position).getId());
            beansFragment.startActivityForResult(startHistoryIntent, START_BEAN_ACTIVITY);
        });
        return beansListView;

    }

}

