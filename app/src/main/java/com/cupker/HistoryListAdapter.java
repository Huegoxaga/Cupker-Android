package com.cupker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Session;

import java.util.List;

/**
 * This defines the session list in history page
 */
public class HistoryListAdapter extends BaseAdapter {

    // Keys
    private static final String TAG = "===HISTORY LIST ADPT===";
    private static final int START_HISTORY_ACTIVITY = 2;
    private static final String SESSION_ID = "SESSION ID";

    // UI & Controllers
    private final HistoryFragment historyFragment;

    // Data
    private final List<Session> sessions;
    private final int sampleNum;

    public HistoryListAdapter(HistoryFragment fragment, List<Session> sessions) {
        // Init data
        this.historyFragment = fragment;
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
    public View getView(int position, View sessionsListView, ViewGroup parent) {

        // Init
        if (sessionsListView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(historyFragment.getContext());
            sessionsListView = layoutInflater.inflate(R.layout.fragment_history_list, parent, false);
        }
        TextView beanName = sessionsListView.findViewById(R.id.history_list_name);

        // Setup
        beanName.setText(sessions.get(position).getName());

        // Listener
        sessionsListView.setOnClickListener(view -> {
            Log.d(TAG, "Selected Session Details " + sessions.get(position));
            Intent startHistoryIntent = new Intent(historyFragment.getActivity(), HistoryActivity.class);
            startHistoryIntent.putExtra(SESSION_ID, sessions.get(position).getId());
            historyFragment.startActivityForResult(startHistoryIntent, START_HISTORY_ACTIVITY);
        });

        return sessionsListView;
    }

}

