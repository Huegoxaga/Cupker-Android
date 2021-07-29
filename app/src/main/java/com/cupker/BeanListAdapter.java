package com.cupker;

import android.content.Context;
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

public class BeanListAdapter extends BaseAdapter {

    private static final String TAG = "===BEAN LIST ADAPTER===";
    private static final int START_BEAN_ACTIVITY = 3;
    private static final String BEAN_OBJ = "BEAN OBJECT";

    private final int beanNum;
    private final List<Bean> beans;
    private final BeansFragment beansFragment;

    public BeanListAdapter(BeansFragment fragment, List<Bean> beans) {

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
        Log.d(TAG, beans.get(position).getName());

        // Listener
        beansListView.setOnClickListener(view -> {
            Gson gson = new Gson();
            Log.d(TAG, beans.get(position).getName());
            String beanObjStr = gson.toJson(beans.get(position));
            Intent startHistoryIntent = new Intent(beansFragment.getActivity(), BeanActivity.class);
            //TODO: image string to large
//            startHistoryIntent.putExtra(BEAN_OBJ, beanObjStr);
            beansFragment.startActivityForResult(startHistoryIntent, START_BEAN_ACTIVITY);
        });
        return beansListView;

    }

}

