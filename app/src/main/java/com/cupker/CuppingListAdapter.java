package com.cupker;

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
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Bean;
import com.amplifyframework.datastore.generated.model.Sample;

import java.util.ArrayList;
import java.util.List;

public class CuppingListAdapter extends BaseAdapter {

    private final Context context;
    private static final String TAG = "===CUP LIST ADAPTER===";
    private final int sampleNum;
    private final ArrayList<Bean> beanObjs;
    private final ArrayList<String> beansString;
    private final double[] roastLevel = { 95, 85, 75, 65, 55, 45, 35, 25};
    private final String[] roastLevelStr = { "# 95", "# 85", "# 75", "# 65", "# 55", "# 45", "# 35", "# 25"};

    private final List<Sample> samples;

    public CuppingListAdapter(Context context, List<Sample> samples) {
        this.beanObjs = new ArrayList<>();
        this.beansString = new ArrayList<>();
        this.context = context;
        this.sampleNum = samples.size();
        this.samples = samples;


        beanObjs.add(null);
        beansString.add("Blind Taste");
        Amplify.DataStore.query(Bean.class,
                queryRoaster -> {
                    while (queryRoaster.hasNext()) {
                        Bean bean = queryRoaster.next();
                        beanObjs.add(bean);
                        beansString.add(bean.getName());
                        Log.i(TAG, "Get Roaster Name: " + bean.getName());
                    }
                },
                error -> Log.e(TAG,  "Error retrieving roasters", error)
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
        EditText notesInput = cuppingListView.findViewById(R.id.cupping_list_note_input);
        ArrayAdapter<String> beanArray = new ArrayAdapter<>(cuppingListView.getContext(), android.R.layout.simple_spinner_item, beansString);
        ArrayAdapter<String> rosterLevelArray = new ArrayAdapter<>(cuppingListView.getContext(), android.R.layout.simple_spinner_item, roastLevelStr);
        CuppingGridView cuppingGridView = cuppingListView.findViewById(R.id.cupping_grid);

        // Setup
        beanArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        beanSpinner.setAdapter(beanArray);
        beanSpinner.setSelection(0, false);
        rosterLevelArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roastLevelSpinner.setAdapter(rosterLevelArray);
        roastLevelSpinner.setSelection(3, false);

        CuppingGridAdapter cuppingGridAdapter = new CuppingGridAdapter(cuppingListView.getContext(), samples.get(position), position);
        cuppingGridView.setAdapter(cuppingGridAdapter);
        sampleName.setText(context.getResources().getString(R.string.cupping_list_title_label, position + 1));

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
        cuppingListView.setOnClickListener(view -> Toast.makeText(context, "The name of the Sample is " + sampleName.getText(), Toast.LENGTH_SHORT).show());

        return cuppingListView;
    }

}

