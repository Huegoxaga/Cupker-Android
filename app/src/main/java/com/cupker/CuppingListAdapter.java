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

public class CuppingListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;
    private static final String TAG = "CUPPING LIST ADAPTER";


    //    int[] animalImages = { R.drawable.bear, R.drawable.bird, R.drawable.cat,
//            R.drawable.cow, R.drawable.dolphin, R.drawable.fish, R.drawable.fox,
//            R.drawable.horse, R.drawable.lion, R.drawable.tiger };
    String[] animalNames = { "Bear", "Bird", "Cat", "Cow", "Dolphin",
            "Fish", "Fox", "Horse", "Lion", "Tiger" };
    int[] animalsPower = { 200, 20, 50, 150, 50, 10, 70, 400, 250, 220 };
    int[] animalsSpeed = { 50, 80, 60, 10, 50, 40, 80, 350, 200, 100 };
    private String[] bean = { "Bean A", "Bean B", "Bean C", "Bean D"};
    private String[] roastLevel = { "Roast Level A", "Roast Level B", "Roast Level C", "Roast Level D"};
    private Spinner beanSpinner;
    private Spinner roastLevelSpinner;



    public CuppingListAdapter(Context context) {

        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return 10 ;
    }

    @Override
    public Object getItem(int position) {
        return animalNames[position];
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

        CuppingGridAdapter cuppingGridAdapter = new CuppingGridAdapter(cupppingListView.getContext());
        cuppingGridView.setAdapter(cuppingGridAdapter);

        sampleName.setText(context.getResources().getString(R.string.cupping_list_title_label, position + 1));

        cupppingListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "The name of the Sample is" + sampleName.getText(), Toast.LENGTH_SHORT).show();

            }
        });

        return cupppingListView;
    }

}

