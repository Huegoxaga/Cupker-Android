package com.cupker.history;
/**
 * Ye Qi, 000792058
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Sample;
import com.cupker.R;
import com.cupker.cupping.CuppingGridAdapter;
import com.cupker.cupping.CuppingGridView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * The history session list controller
 */
public class HistoryListAdapter extends BaseAdapter {

    // Keys
    private static final String TAG = "===CUP LIST ADAPTER===";

    // UI
    private final Context context;

    // Data
    private final int sampleNum;
    private final ArrayList<Bean> beanObjs;
    private final ArrayList<String> beansString; // related array to store names for dropdown
    private final ArrayList<String> beanIds; // related array to match sample's bean selection
    private final double[] roastLevel = {95, 85, 75, 65, 55, 45, 35, 25};
    private final String[] roastLevelStr = {"# 95", "# 85", "# 75", "# 65", "# 55", "# 45", "# 35", "# 25"};
    private final List<Sample> samples;
    private final Boolean editable;

    /**
     * This holds the history list for all samples in one session
     *
     * @param context context
     * @param samples list of Sample data objects
     */
    public HistoryListAdapter(Context context, List<Sample> samples, Boolean editable, ArrayList<Bean> beans) {

        // Init Data
        this.editable = editable;

        // Assign data
        this.beanObjs = beans;
        this.beansString = new ArrayList<>(); // related array to store names for dropdown
        this.beanIds = new ArrayList<>(); // related array to match sample's bean selection
        this.context = context;
        this.sampleNum = samples.size();
        this.samples = samples;
        for (Bean bean : beans) {
            beansString.add(bean.getName());
            beanIds.add(bean.getId());
        }
    }


    @Override
    public int getCount() {
        return sampleNum;
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
    public View getView(int position, View cuppingListView, ViewGroup parent) {

        // Init
        if (cuppingListView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            cuppingListView = layoutInflater.inflate(R.layout.activity_history_list, parent, false);
        }
        TextView sampleName = cuppingListView.findViewById(R.id.cupping_list_title_label);
        TextView totalScore = cuppingListView.findViewById(R.id.history_session_list_total);
        Spinner beanSpinner = cuppingListView.findViewById(R.id.cupping_list_bean_spinner);
        Spinner roastLevelSpinner = cuppingListView.findViewById(R.id.cupping_list_roast_level_spinner);
        EditText notesInput = cuppingListView.findViewById(R.id.cupping_list_note_input);
        notesInput.setEnabled(false);
        ArrayAdapter<String> beanArray = new ArrayAdapter<>(cuppingListView.getContext(), android.R.layout.simple_spinner_item, beansString);
        ArrayAdapter<String> rosterLevelArray = new ArrayAdapter<>(cuppingListView.getContext(), android.R.layout.simple_spinner_item, roastLevelStr);
        CuppingGridView cuppingGridView = cuppingListView.findViewById(R.id.cupping_grid);

        // Init Data
        Sample sample = samples.get(position);
        int selectedBeanIdx = beanIds.indexOf(sample.getBeanId());
        int selectedRoastLevelIdx = Collections.singletonList(roastLevel).indexOf(sample.getBeanId());

        // Setup
        notesInput.setText(sample.getNotes());
        notesInput.setEnabled(false);
        beanArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        beanSpinner.setEnabled(false);
//        beanSpinner.setClickable(false);
        beanSpinner.setAdapter(beanArray);
        beanSpinner.setSelection(selectedBeanIdx, false);
        rosterLevelArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roastLevelSpinner.setEnabled(false);
        roastLevelSpinner.setClickable(false);
        roastLevelSpinner.setAdapter(rosterLevelArray);
        roastLevelSpinner.setSelection(selectedRoastLevelIdx, false);
        CuppingGridAdapter cuppingGridAdapter = new CuppingGridAdapter(cuppingListView.getContext(), samples.get(position), position, editable);
        cuppingGridView.setAdapter(cuppingGridAdapter);
        sampleName.setText(context.getResources().getString(R.string.cupping_list_title_label, position + 1));
        totalScore.setText(String.format(Locale.getDefault(), "%.2f", getScore(position)));

        // Listener
        beanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int selectedPosition, long id) {
                HistoryActivity main = (HistoryActivity) context;
                main.setBean(position, beanObjs.get(selectedPosition));
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
//        roastLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> arg0, View arg1, int selectedPosition, long id) {
//                HistoryActivity main = (HistoryActivity) context;
//                Log.d(TAG, position + " Selected in list item id");
//                main.setRoastLevel(position, roastLevel[selectedPosition]);
//            }
//            @Override
//            public void onNothingSelected(AdapterView<?> arg0) {
//            }
//        });

//        cuppingListView.setOnClickListener(view -> Toast.makeText(context, "The name of the Sample is " + sampleName.getText(), Toast.LENGTH_SHORT).show());

        return cuppingListView;
    }

    /**
     * Calculate total score
     * @param listPosition
     * @return
     */
    private double getScore(int listPosition) {
        Sample sample = samples.get(listPosition);
        double total = sample.getAroma() + sample.getFlavor() + sample.getAfterTaste() + sample.getAcidity() + sample.getBody() + sample.getOverall() + sample.getBalance();
        total += 30 - 2 * (positionToCup(sample.getUniformity()) + positionToCup(sample.getCleanCup()) + positionToCup(sample.getSweetness()));
        total -= positionToCup(sample.getDefectCount()) * sample.getDefectType();

        return total;
    }

    /**
     * Generate position value from binary value
     * @param positionCode
     * @return
     */
    private double positionToCup(Double positionCode) {
        String scorePosition = Integer.toBinaryString(positionCode.intValue());
        return scorePosition.length() - scorePosition.replace("1", "").length();

    }

}

