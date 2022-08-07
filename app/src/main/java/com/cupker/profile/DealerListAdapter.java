package com.cupker.profile;
/**
 * Ye Qi, 000792058
 */
import android.content.res.TypedArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Dealer;
import com.cupker.R;
import com.cupker.profile.DealerActivity;

import java.util.ArrayList;

/**
 * This defines the dealer list in profile page
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
    private int themeBgColor;
    private TypedValue typedValue;

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
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            settingsListView = layoutInflater.inflate(R.layout.activity_dealer_list, parent, false);
        }
        TextView dealerName = settingsListView.findViewById(R.id.dealer_list_name);

        // Setup
        dealerName.setText(dealerObjs.get(position).getName());
        typedValue = new TypedValue();
        TypedArray a = dealerActivity.obtainStyledAttributes(typedValue.data, new int[] { R.attr.colorOnPrimary });
        themeBgColor = a.getColor(0, 0);
        a.recycle();

        // Listener
        settingsListView.setOnLongClickListener(view -> {
            // highlight and add to newly selected items to list, reset existing item from list
            if(selectedRows.contains(view)){
                selectedRows.remove(view);
                selectedDealers.remove(dealerObjs.get(position));
                view.setBackgroundColor(themeBgColor);
            }else{
                selectedDealers.add(dealerObjs.get(position));
                selectedRows.add(view);
                view.setBackgroundResource(R.color.secondary_brown_light);
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

    /**
     * remove selected dealer from list and call frag method to make it inactive
     */
    public void removeSelectedDealers(){
        dealerActivity.removeSelectedDealers(selectedDealers);
        selectedDealers.clear();
        for(View view : selectedRows)
            view.setBackgroundColor(themeBgColor);
        selectedRows.clear();
    }
}

