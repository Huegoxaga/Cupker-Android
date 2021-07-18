package com.cupker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Bean;

import java.util.List;

public class BeanListAdapter extends BaseAdapter {

    private final Context context;
    private static final String TAG = "===BEAN LIST ADAPTER===";
    private final int beanNum;
    private final List<Bean> beans;

    public BeanListAdapter(Context context, List<Bean> beans) {

        this.context = context;
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
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            beansListView = layoutInflater.inflate(R.layout.fragment_beans_list, parent, false);
        }
        TextView beanName = beansListView.findViewById(R.id.bean_list_name);

        // Setup
        beanName.setText(beans.get(position).getName());
        Log.d(TAG, beans.get(position).getName());

        // Listener
        beansListView.setOnClickListener(view -> Log.d(TAG, "Selected Bean Details " + beans.get(position)));
        return beansListView;

    }

}

