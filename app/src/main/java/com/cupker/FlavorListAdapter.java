package com.cupker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Dealer;
import com.amplifyframework.datastore.generated.model.Flavor;

import java.util.ArrayList;

/**
 * This defines the settings list in profile page
 */
public class FlavorListAdapter extends BaseAdapter {

    // Keys
    private static final String TAG = "===FLAVOR LIST ADPT===";

    // UI & Controllers
    private final FlavorActivity flavorActivity;

    // Data
    private final ArrayList<Flavor> flavorObjs;
    private ArrayList<Flavor> selectedFlavors;
    private ArrayList<View> selectedRows;

    public FlavorListAdapter(FlavorActivity flavorActivity, ArrayList<Flavor> flavorObjs) {
        // Init data
        this.flavorActivity = flavorActivity;
        this.flavorObjs = flavorObjs;
        this.selectedFlavors = new ArrayList<>();
        this.selectedRows = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return flavorObjs.size();
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
    public View getView(int position, View settingsListView, ViewGroup parent) {

        // Init
        if (settingsListView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(flavorActivity.getApplicationContext());
            settingsListView = layoutInflater.inflate(R.layout.activity_flavor_list, parent, false);
        }
        TextView flavorName = settingsListView.findViewById(R.id.flavor_list_name);

        // Setup
        flavorName.setText(flavorObjs.get(position).getName());

        // Listener
        settingsListView.setOnLongClickListener(view -> {
            // highlight and add to newly selected items to list, reset existing item from list
            if(selectedRows.contains(view)){
                selectedRows.remove(view);
                selectedFlavors.remove(flavorObjs.get(position));
                view.setBackgroundResource(R.color.white);
            }else{
                selectedFlavors.add(flavorObjs.get(position));
                selectedRows.add(view);
                view.setBackgroundResource(R.color.secondary_brown_light);
            }
            if(selectedFlavors.size() > 0){
                flavorActivity.showDeleteMenu(true);
            }else{
                flavorActivity.showDeleteMenu(false);
            }

            return true;
        });

        return settingsListView;
    }
    public void removeSelectedFlavors() {
        flavorActivity.removeSelectedFlavor(selectedFlavors);
        selectedFlavors.clear();
        for (View view : selectedRows)
            view.setBackgroundResource(R.color.white);
        selectedRows.clear();
    }
}

