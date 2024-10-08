package com.cupker.cupping;
/**
 * Ye Qi, 000792058
 */
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Spinner;
import android.widget.TextView;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Flavor;
import com.amplifyframework.datastore.generated.model.Sample;
import com.amplifyframework.datastore.generated.model.Status;
import com.cupker.R;

import java.util.ArrayList;
import java.util.List;

/**
 * This defines the cupping session list
 */
public class CuppingListAdapter extends BaseAdapter {

    // Keys
    private static final String TAG = "===CUP LIST ADAPTER===";

    // UI
    private final Context context;
    private final int sampleNum;

    // Data
    private final ArrayList<Bean> beanObjs;
    private final ArrayList<String> beansString; // related array to store names for dropdown
    private ArrayList<String> flavorString;
    private final double[] roastLevel = { 95, 85, 75, 65, 55, 45, 35, 25};
    private final String[] roastLevelStr = { "# 95", "# 85", "# 75", "# 65", "# 55", "# 45", "# 35", "# 25"};
    private final List<Sample> samples;
    private final Boolean editable;

    /**
     * This holds the cupping list for all samples in one session
     * @param context context
     * @param samples list of Sample data objects
     */
    public CuppingListAdapter(Context context, List<Sample> samples, Boolean editable) {

        // Init Data
        this.editable = editable;

        // Assign data
        this.beanObjs = new ArrayList<>();
        this.beansString = new ArrayList<>();
        this.flavorString = new ArrayList<>();
        this.context = context;
        this.sampleNum = samples.size();
        this.samples = samples;

        // Add bean list first record
        beanObjs.add(Bean.builder()
                .status(Status.INACTIVE)
                .id("00000000-0000-0000-0000-000000000000")
                .build());
        beansString.add(context.getString(R.string.blind_taste));

        // Query & populate Bean data
        Amplify.DataStore.query(Bean.class,
                Where.matches(Bean.STATUS.eq(Status.ACTIVE)),
                queryRoaster -> {
                    while (queryRoaster.hasNext()) {
                        Bean bean = queryRoaster.next();
                        beanObjs.add(bean);
                        beansString.add(bean.getName());
                        Log.i(TAG, "Get Bean Name: " + bean.getName());
                    }
                },
                error -> Log.e(TAG,  "Error retrieving beans", error)
        );

        // Query my flavors
        Amplify.DataStore.query(Flavor.class,
                Where.matches(Flavor.STATUS.eq(Status.ACTIVE)),
                queryRoaster -> {
                    while (queryRoaster.hasNext()) {
                        Flavor flavor = queryRoaster.next();
                        flavorString.add(flavor.getName());
                        Log.i(TAG, "Get Flavor Name: " + flavor.getName());
                    }
                },
                error -> Log.e(TAG,  "Error retrieving flavor", error)
        );
    }

    @Override
    public int getCount() {
        return sampleNum ;
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
            cuppingListView = layoutInflater.inflate(R.layout.activity_cupping_list, parent, false);
        }
        TextView sampleName = cuppingListView.findViewById(R.id.cupping_list_title_label);
        Spinner beanSpinner = cuppingListView.findViewById(R.id.cupping_list_bean_spinner);
        Spinner roastLevelSpinner = cuppingListView.findViewById(R.id.cupping_list_roast_level_spinner);

        // note view
        MultiAutoCompleteTextView notesInput = cuppingListView.findViewById(R.id.cupping_list_note_input);
//        ArrayAdapter<String> beanArray = new ArrayAdapter<>(cuppingListView.getContext(), android.R.layout.simple_spinner_item, beansString);
        ArrayAdapter<String> beanArray = new ArrayAdapter<>(cuppingListView.getContext(),  R.layout.spinner_item, beansString);
//        ArrayAdapter<String> rosterLevelArray = new ArrayAdapter<>(cuppingListView.getContext(), android.R.layout.simple_spinner_item, roastLevelStr);
        ArrayAdapter<String> rosterLevelArray = new ArrayAdapter<>(cuppingListView.getContext(), R.layout.spinner_item, roastLevelStr);
        CuppingGridView cuppingGridView = cuppingListView.findViewById(R.id.cupping_grid);

        // Setup
        beanArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        beanSpinner.setAdapter(beanArray);
        beanSpinner.setSelection(0, false);
        rosterLevelArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roastLevelSpinner.setAdapter(rosterLevelArray);
        roastLevelSpinner.setSelection(3, false);

        CuppingGridAdapter cuppingGridAdapter = new CuppingGridAdapter(cuppingListView.getContext(), samples.get(position), position, editable);
        cuppingGridView.setAdapter(cuppingGridAdapter);
        sampleName.setText(context.getResources().getString(R.string.cupping_list_title_label, position + 1));

        ArrayAdapter<String> notesAdapter = new ArrayAdapter<>(context, android.R.layout.simple_list_item_1, flavorString);
        notesInput.setAdapter(notesAdapter);
        notesInput.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());

        // Listener
        beanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int selectedPosition, long id) {
                CuppingActivity main = (CuppingActivity) context;
                main.setBean(position, beanObjs.get(selectedPosition));
                Log.d(TAG, beanObjs.get(selectedPosition) + " Selected in getVIew()");
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        roastLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int selectedPosition, long id) {
                CuppingActivity main = (CuppingActivity) context;
                Log.d(TAG, position + " Selected in list item id");
                main.setRoastLevel(position, roastLevel[selectedPosition]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        notesInput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                CuppingActivity main = (CuppingActivity) context;
                main.setNote(position, s.toString());
            }
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
//        cuppingListView.setOnClickListener(view -> Toast.makeText(context, "The name of the Sample is " + sampleName.getText(), Toast.LENGTH_SHORT).show());

        return cuppingListView;
    }

}

