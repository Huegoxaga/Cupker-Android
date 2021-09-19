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
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Roaster;
import com.amplifyframework.datastore.generated.model.Session;
import com.amplifyframework.datastore.generated.model.Status;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

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
    private MenuItem menuItemDelete;
    private MenuItem menuItemAdd;
    private BeanListAdapter beanListAdapter;

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
                Where.matches(Bean.STATUS.eq(Status.ACTIVE)),
                queryBean -> {
                    while (queryBean.hasNext()) {
                        Bean bean = queryBean.next();
                        beanObjs.add(bean);
                        Log.i(TAG, "Get Bean Name: " + bean.toString());
                    }
                    handler.post(updateList);
                },
                error -> Log.e(TAG, "Error retrieving roasters", error)
        );
    }

    private final Handler handler = new Handler();

    private final Runnable updateList = new Runnable() {
        @Override
        public void run() {
            if (view != null && beanList != null) {
                beanListAdapter = new BeanListAdapter(self, beanObjs);
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
            inflater.inflate(R.menu.toolbar_with_add_delete, menu);
            menuItemDelete = menu.findItem(R.id.add_bean_menu_delete_button);
            menuItemAdd = menu.findItem(R.id.add_bean_menu_save_button);
            menuItemDelete.setVisible(false);
            super.onCreateOptionsMenu(menu, inflater);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_bean_menu_save_button) {
            Intent startNewBeamIntent = new Intent(getActivity(), NewBeanActivity.class);
            startActivity(startNewBeamIntent);
        }else {
            beanListAdapter.notifyDataSetChanged();
            beanListAdapter.removeSelectedBeans();
            showDeleteMenu(false);
        }
        // default handler
        return super.onOptionsItemSelected(item);
    }

    public void showDeleteMenu(boolean show) {
        menuItemDelete.setVisible(show);
        menuItemAdd.setVisible(!show);
    }

    public void removeSelectedBeans(List<Bean> selectedSessions) {
        beanObjs.removeAll(selectedSessions);

        for (Bean session : selectedSessions) {
            Amplify.DataStore.query(Session.class, Where.id(session.getId()),
                    match -> {
                        if (match.hasNext()) {
                            Session getBean = match.next();

                            Session updatedBean = getBean.copyOfBuilder()
                                    .status(Status.INACTIVE)
                                    .build();
                            Amplify.DataStore.save(updatedBean,
                                    updated -> Log.i(TAG, "Updated a Session."),
                                    failure -> Log.e(TAG, "Update failed.", failure)
                            );
                            Log.d(TAG, "Inactivate Session =====" + getBean.getName());
                        }
                    },
                    failure -> Log.e(TAG, "Query failed.", failure)
            );
        }
    }
}