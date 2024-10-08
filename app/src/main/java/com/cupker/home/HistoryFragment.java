package com.cupker.home;
/**
 * Ye Qi, 000792058
 */
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
import android.widget.SearchView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Session;
import com.amplifyframework.datastore.generated.model.Status;
import com.cupker.R;
import com.cupker.history.HistorySessionListAdapter;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * This defines the history page
 */
public class HistoryFragment extends Fragment {

    // Keys
    private static final String TAG = "===HISTORY FRAGMENT===";

    // UI & Controllers
    private View view;
    private ListView sessionList;
    private final HistoryFragment self = this;
    private MenuItem menuItemDelete;
    private MenuItem menuItemAdd;
    private HistorySessionListAdapter historySessionListAdapter;
    private SearchView searchView;

    // Data
    private ArrayList<Session> sessionObjs;
    private String currentSearchText = "";


    public HistoryFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init Data
        sessionObjs = new ArrayList<>();
        Amplify.DataStore.query(Session.class,
                Where.matches(Session.STATUS.eq(Status.ACTIVE)),
                queryRoaster -> {
                    while (queryRoaster.hasNext()) {
                        Session sessions = queryRoaster.next();
                        sessionObjs.add(sessions);
                        Log.i(TAG, "Get Session Name: " + sessions.getName());
                    }
                    handler.post(r);
                },
                error -> Log.e(TAG, "Error retrieving sessions", error)
        );
    }

    private final Handler handler = new Handler();

    private final Runnable r = new Runnable() {
        @Override
        public void run() {
            if (view != null && sessionList != null) {
                historySessionListAdapter = new HistorySessionListAdapter(self, sessionObjs);
                sessionList.setAdapter(historySessionListAdapter);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Init
        view = inflater.inflate(R.layout.fragment_history, container, false);
        Toolbar toolbar = view.findViewById(R.id.history_frag_toolbar);
        sessionList = view.findViewById(R.id.history_frag_list);
        searchView = view.findViewById(R.id.history_frag_search);

        // Setup
        setHasOptionsMenu(true);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);

        // Listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                currentSearchText = s;
                ArrayList<Session> filteredSessions = new ArrayList<>();
                Log.d(TAG, s);
                for (Session session : sessionObjs) {

                    if (session.getName().toLowerCase().contains(s.toLowerCase())) {
                        Log.d(TAG, session.toString());
                        filteredSessions.add(session);
                    }
                }
                historySessionListAdapter = new HistorySessionListAdapter(self, filteredSessions);
                sessionList.setAdapter(historySessionListAdapter);
                return false;
            }
        });
        return view;
    }


    public void showDeleteMenu(boolean show) {
        menuItemDelete.setVisible(show);
        menuItemAdd.setVisible(!show);
    }

    @Override
    public void onCreateOptionsMenu(@NotNull Menu menu, @NotNull MenuInflater inflater) {
        if (menu != null && inflater != null) {
            inflater.inflate(R.menu.toolbar_with_add_delete, menu);
            menuItemDelete = menu.findItem(R.id.add_bean_menu_delete_button);
            menuItemDelete.setVisible(false);
            menuItemAdd = menu.findItem(R.id.add_bean_menu_save_button);
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_bean_menu_save_button) {
            HomeActivity homeActivity = (HomeActivity) getActivity();
            homeActivity.setBottomNav(R.id.nav_cupping);
        } else {
            historySessionListAdapter.notifyDataSetChanged();
            historySessionListAdapter.removeSelectedSessions();
            showDeleteMenu(false);
        }
        // default handler
        return super.onOptionsItemSelected(item);
    }

    /**
     * Make selected session inactive, called from list adapter
     * @param selectedSessions
     */
    public void removeSelectedSessions(List<Session> selectedSessions) {
        sessionObjs.removeAll(selectedSessions);

        for (Session session : selectedSessions) {
            Amplify.DataStore.query(Session.class, Where.id(session.getId()),
                    match -> {
                        if (match.hasNext()) {
                            Session getSession = match.next();

                            Session updatedSession = getSession.copyOfBuilder()
                                    .status(Status.INACTIVE)
                                    .build();
                            Amplify.DataStore.save(updatedSession,
                                    updated -> Log.i(TAG, "Updated a bean."),
                                    failure -> Log.e(TAG, "Update failed.", failure)
                            );
                            Log.d(TAG, "Inactivate Bean =====" + getSession.getName());
                        }
                    },
                    failure -> Log.e(TAG, "Query failed.", failure)
            );
        }
    }
}