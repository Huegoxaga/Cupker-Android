package com.cupker;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.datastore.generated.model.Bean;
import com.google.gson.Gson;

import java.util.ArrayList;
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
    private List<Bean> beans;
    private List<Bean> selectedBeans;
    private List<View> selectedRows;

    public BeanListAdapter(BeansFragment fragment, List<Bean> beans) {
        // Init Data
        this.beansFragment = fragment;
        this.beans = beans;
        this.selectedBeans = new ArrayList<>();
        this.selectedRows = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return beans.size();
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
        beansListView.setOnLongClickListener(view -> {
            // highlight and add to newly selected items to list, reset existing item from list
            if(selectedRows.contains(view)){
                selectedRows.remove(view);
                selectedBeans.remove(beans.get(position));
                view.setBackgroundResource(R.color.white);
            }else{
                selectedBeans.add(beans.get(position));
                selectedRows.add(view);
                view.setBackgroundResource(R.color.secondary_brown_light);
            }
            if(selectedBeans.size() > 0){
                beansFragment.showDeleteMenu(true);
            }else{
                beansFragment.showDeleteMenu(false);
            }

            return true;
        });

        return beansListView;

    }

    public void removeSelectedBeans(){
        beansFragment.removeSelectedBeans(selectedBeans);
        selectedBeans.clear();
        for(View view : selectedRows)
            view.setBackgroundResource(R.color.white);
        selectedRows.clear();
    }

}

