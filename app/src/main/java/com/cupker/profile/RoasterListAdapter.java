package com.cupker.profile;
/**
 * Ye Qi, 000792058
 */
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Roaster;
import com.cupker.R;
import com.cupker.profile.RoasterActivity;

import java.util.ArrayList;

/**
 * This defines the roaster list in profile page
 */
public class RoasterListAdapter extends BaseAdapter {

    // Keys
    private static final String TAG = "===ROASTER LIST ADPT===";

    // UI & Controllers
    private final RoasterActivity roasterActivity;

    // Data
    private final ArrayList<Roaster> roasterObjs;
    private ArrayList<Roaster> selectedRoasters;
    private ArrayList<View> selectedRows;

    public RoasterListAdapter(RoasterActivity roasterActivity, ArrayList<Roaster> roasterObjs) {
        // Init data
        this.roasterActivity = roasterActivity;
        this.roasterObjs = roasterObjs;
        this.selectedRoasters = new ArrayList<>();
        this.selectedRows = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return roasterObjs.size();
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
            LayoutInflater layoutInflater = LayoutInflater.from(roasterActivity.getApplicationContext());
            settingsListView = layoutInflater.inflate(R.layout.activity_roaster_list, parent, false);
        }
        TextView roasterName = settingsListView.findViewById(R.id.roaster_list_name);

        // Setup
        roasterName.setText(roasterObjs.get(position).getName());

        // Listener
        settingsListView.setOnLongClickListener(view -> {
            // highlight and add to newly selected items to list, reset existing item from list
            if(selectedRows.contains(view)){
                selectedRows.remove(view);
                selectedRoasters.remove(roasterObjs.get(position));
                view.setBackgroundResource(R.color.white);
            }else{
                selectedRoasters.add(roasterObjs.get(position));
                selectedRows.add(view);
                view.setBackgroundResource(R.color.secondary_brown_light);
            }
            if(selectedRoasters.size() > 0){
                roasterActivity.showDeleteMenu(true);
            }else{
                roasterActivity.showDeleteMenu(false);
            }

            return true;
        });

        return settingsListView;
    }

    public void removeSelectedRoasters() {
        roasterActivity.removeSelectedRoaster(selectedRoasters);
        selectedRoasters.clear();
        for (View view : selectedRows)
            view.setBackgroundResource(R.color.white);
        selectedRows.clear();
    }
}

