package com.cupker;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * This defines the session list in history page
 */
public class HistorySessionListAdapter extends BaseAdapter{

    // Keys
    private static final String TAG = "===HISTORY LIST ADPT===";
    private static final int START_HISTORY_ACTIVITY = 2;
    private static final String SESSION_ID = "SESSION ID";

    // UI & Controllers
    private final HistoryFragment historyFragment;

    // Data
    private ArrayList<Session> sessions;
    private ArrayList<Session> selectedSessions;
    private ArrayList<View> selectedRows;

    public HistorySessionListAdapter(HistoryFragment fragment, ArrayList<Session> sessions) {
        // Init data
        this.historyFragment = fragment;
        this.sessions = sessions;
        this.selectedSessions = new ArrayList<>();
        this.selectedRows = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return sessions.size();
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
            Intent startHistoryIntent = new Intent(historyFragment.getActivity(), HistoryActivity.class);
            startHistoryIntent.putExtra(SESSION_ID, sessions.get(position).getId());
            historyFragment.startActivityForResult(startHistoryIntent, START_HISTORY_ACTIVITY);
        });

        sessionsListView.setOnLongClickListener(view -> {
            // highlight and add to newly selected items to list, reset existing item from list
            if (selectedRows.contains(view)) {
                selectedRows.remove(view);
                selectedSessions.remove(sessions.get(position));
                view.setBackgroundResource(R.color.white);
            } else {
                selectedSessions.add(sessions.get(position));
                selectedRows.add(view);
                view.setBackgroundResource(R.color.light_blue_600);
            }
            if (selectedSessions.size() > 0) {
                historyFragment.showDeleteMenu(true);
            } else {
                historyFragment.showDeleteMenu(false);
            }

            return true;
        });

        return sessionsListView;
    }

    public void removeSelectedSessions() {
        historyFragment.removeSelectedSessions(selectedSessions);
        selectedSessions.clear();
        for (View view : selectedRows)
            view.setBackgroundResource(R.color.white);
        selectedRows.clear();
    }

}

