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

import com.amplifyframework.datastore.generated.model.Sample;

import java.util.List;

public class CuppingListAdapter extends BaseAdapter {

    private final Context context;
    private static final String TAG = "===CUP LIST ADAPTER===";
    private final int sampleNum;

    private final String[] bean = { "Bean A", "Bean B", "Bean C", "Bean D"};
    private final String[] roastLevel = { "Roast Level A", "Roast Level B", "Roast Level C", "Roast Level D"};
    private final List<Sample> samples;


    public CuppingListAdapter(Context context, List<Sample> samples) {

        this.context = context;
        this.sampleNum = samples.size();
        this.samples = samples;
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
        ArrayAdapter<String> beanArray = new ArrayAdapter<>(cuppingListView.getContext(), android.R.layout.simple_spinner_item, bean);
        ArrayAdapter<String> rosterLevelArray = new ArrayAdapter<>(cuppingListView.getContext(), android.R.layout.simple_spinner_item, roastLevel);
        CuppingGridView cuppingGridView = cuppingListView.findViewById(R.id.cupping_grid);

        // Setup
        beanSpinner.setSelection(0, false);
        roastLevelSpinner.setSelection(0, false);
        beanArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        beanSpinner.setAdapter(beanArray);
        rosterLevelArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roastLevelSpinner.setAdapter(rosterLevelArray);
        CuppingGridAdapter cuppingGridAdapter = new CuppingGridAdapter(cuppingListView.getContext(), samples.get(position), position);
        cuppingGridView.setAdapter(cuppingGridAdapter);
        sampleName.setText(context.getResources().getString(R.string.cupping_list_title_label, position + 1));

        // Listener
        beanSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                Log.d(TAG, bean[position] + " Selected in getVIew()");
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });
        roastLevelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                Log.d(TAG, roastLevel[position] + " Selected in getView()");
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

