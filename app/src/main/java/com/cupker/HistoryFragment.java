package com.cupker;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Session;

import java.util.ArrayList;

public class HistoryFragment extends Fragment {

    private static final String TAG = "===HISTORY FRAGMENT===";

    private final ArrayList<Session> sessionObjs;
    private View view;
    private ListView sessionList;

    public HistoryFragment() {
        // Required empty public constructor
        sessionObjs = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Amplify.DataStore.query(Session.class,
                queryRoaster -> {
                    while (queryRoaster.hasNext()) {
                        Session sessions = queryRoaster.next();
                        sessionObjs.add(sessions);
                        Log.i(TAG, "Get Session Name: " + sessions.getName());
                    }
                    handler.post(r);
                },
                error -> Log.e(TAG,  "Error retrieving sessions", error)
        );
    }

    private final Handler handler = new Handler();

    private final Runnable r = new Runnable() {
        @Override
        public void run() {
            if (view != null && sessionList != null){
                HistoryListAdapter historyListAdapter = new HistoryListAdapter(view.getContext(), sessionObjs);
                sessionList.setAdapter(historyListAdapter);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_history, container, false);
        Toolbar toolbar = view.findViewById(R.id.history_frag_toolbar);
        sessionList = view.findViewById(R.id.history_frag_list);
//        Log.d(TAG, "list length" + sessions.size());
//        setHasOptionsMenu(true);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayShowHomeEnabled(true);
        return view;
    }
}