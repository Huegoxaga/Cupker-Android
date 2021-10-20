package com.cupker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Dealer;

import java.util.ArrayList;
import java.util.List;

/**
 * This defines the settings list in profile page
 */
public class DealerListAdapter extends BaseAdapter {

    // Keys
    private static final String TAG = "===DEALER LIST ADPT===";

    // UI & Controllers
    private final DealerActivity dealerActivity;

    // Data
    private final ArrayList<Dealer> dealerObjs;
    private ArrayList<Dealer> selectedDealers;
    private ArrayList<View> selectedRows;

    public DealerListAdapter(DealerActivity dealerActivity, ArrayList<Dealer> dealerObjs) {
        // Init data
        this.dealerActivity = dealerActivity;
        this.dealerObjs = dealerObjs;
        this.selectedDealers = new ArrayList<>();
        this.selectedRows = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return dealerObjs.size();
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
            LayoutInflater layoutInflater = LayoutInflater.from(dealerActivity.getApplicationContext());
            settingsListView = layoutInflater.inflate(R.layout.activity_dealer_list, parent, false);
        }
        TextView dealerName = settingsListView.findViewById(R.id.dealer_list_name);

        // Setup
        dealerName.setText(dealerObjs.get(position).getName());

        // Listener
        settingsListView.setOnLongClickListener(view -> {
            // highlight and add to newly selected items to list, reset existing item from list
            if(selectedRows.contains(view)){
                selectedRows.remove(view);
                selectedDealers.remove(dealerObjs.get(position));
                view.setBackgroundResource(R.color.white);
            }else{
                selectedDealers.add(dealerObjs.get(position));
                selectedRows.add(view);
                view.setBackgroundResource(R.color.light_blue_600);
            }
            if(selectedDealers.size() > 0){
                dealerActivity.showDeleteMenu(true);
            }else{
                dealerActivity.showDeleteMenu(false);
            }

            return true;
        });

        return settingsListView;
    }

    public void removeSelectedDealers(){
        dealerActivity.removeSelectedDealers(selectedDealers);
        selectedDealers.clear();
        for(View view : selectedRows)
            view.setBackgroundResource(R.color.white);
        selectedRows.clear();
    }
}

