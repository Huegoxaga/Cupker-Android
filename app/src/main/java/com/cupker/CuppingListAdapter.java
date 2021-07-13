package com.cupker;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.datastore.generated.model.Sample;

import java.util.List;

public class CuppingListAdapter extends BaseAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private static final String TAG = "===CUPP LIST ADAPTER===";
    private int sampleNum;

    private String[] bean = { "Bean A", "Bean B", "Bean C", "Bean D"};
    private String[] roastLevel = { "Roast Level A", "Roast Level B", "Roast Level C", "Roast Level D"};
    private Spinner beanSpinner;
    private Spinner roastLevelSpinner;
    private List<Sample> samples;



    public CuppingListAdapter(Context context, List<Sample> samples) {

        this.context = context;
        this.sampleNum = samples.size();
        this.samples = samples;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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
    public View getView(int position, View cupppingListView, ViewGroup parent) {

        cupppingListView = layoutInflater.inflate(R.layout.activity_cupping_list, null);

        TextView sampleName = cupppingListView.findViewById(R.id.cupping_list_title_label);
        beanSpinner = cupppingListView.findViewById(R.id.cupping_list_bean_spinner);
        roastLevelSpinner = cupppingListView.findViewById(R.id.cupping_list_roast_level_spinner);
        beanSpinner.setSelection(0, false);
        roastLevelSpinner.setSelection(0, false);
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
        ArrayAdapter beanArray = new ArrayAdapter(cupppingListView.getContext(), android.R.layout.simple_spinner_item, bean);
        beanArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        beanSpinner.setAdapter(beanArray);
        ArrayAdapter rosterLevelArray = new ArrayAdapter(cupppingListView.getContext(), android.R.layout.simple_spinner_item, roastLevel);
        rosterLevelArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        roastLevelSpinner.setAdapter(rosterLevelArray);


        CuppingGridView cuppingGridView = cupppingListView.findViewById(R.id.cupping_grid);

        CuppingGridAdapter cuppingGridAdapter = new CuppingGridAdapter(cupppingListView.getContext(), samples.get(position), position);
        cuppingGridView.setAdapter(cuppingGridAdapter);

        sampleName.setText(context.getResources().getString(R.string.cupping_list_title_label, position + 1));

        cupppingListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "The name of the Sample is " + sampleName.getText(), Toast.LENGTH_SHORT).show();
            }
        });

        return cupppingListView;
    }

}

