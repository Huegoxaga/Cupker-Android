package com.cupker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Session;

import java.util.List;

public class HistoryListAdapter extends BaseAdapter {

    private final Context context;
    private static final String TAG = "===HISTORY LIST ADPT===";
    private final int sampleNum;
    private final List<Session> sessions;

    public HistoryListAdapter(Context context, List<Session> sessions) {

        this.context = context;
        this.sampleNum = sessions.size();
        this.sessions = sessions;
    }

    @Override
    public int getCount() {
        return sampleNum ;
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
            beansListView = layoutInflater.inflate(R.layout.fragment_history_list, parent, false);
        }
        TextView beanName = beansListView.findViewById(R.id.history_list_name);

        // Setup
        beanName.setText(sessions.get(position).getName());
        Log.d(TAG, sessions.get(position).getName());
        return beansListView;
    }

}

