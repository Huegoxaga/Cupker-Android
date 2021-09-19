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
            menuItemDelete.setOnMenuItemClickListener(item -> {
                beanListAdapter.notifyDataSetChanged();

                beanListAdapter.removeSelectedBeans();
                showDeleteMenu(false);
                return true;
            });
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
        // default handler
        return super.onOptionsItemSelected(item);
    }

    public void showDeleteMenu(boolean show) {
        menuItemDelete.setVisible(show);
        menuItemAdd.setVisible(!show);
    }

    public void removeSelectedBeans(List<Bean> selectedBeans) {
        beanObjs.removeAll(selectedBeans);

        for (Bean bean : selectedBeans) {
            Amplify.DataStore.query(Bean.class, Where.matches(Bean.ID.eq(bean.getId())),
                    match -> {
                        if (match.hasNext()) {
                            Bean getBean = match.next();
                            Log.d(TAG, "Bean for deletion: =====" + getBean.getName());
                            Amplify.DataStore.delete(getBean,
                                    deleted -> Log.i(TAG, "Deleted a bean."),
                                    failure -> Log.e(TAG, "Delete failed.", failure)
                            );
                        }
                    },
                    failure -> Log.e(TAG, "Query failed.", failure)
            );
        }
    }
}