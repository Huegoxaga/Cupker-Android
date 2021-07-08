package com.cupker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Roaster;

import java.util.ArrayList;
import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CuppingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CuppingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "===CUPPING FRAGMENT===";
    private static final String PREFERENCE = "CUPPING FRAGMENT PREFERENCE";
    private static final String SAMPLE_NUMBER = "SAMPLE NUMBER";
    private static final String SESSION_NAME = "SESSION NAME";
    private static final String ROAST_DATE = "ROAST DATE";
    private static final String ROASTER_CHOICE = "ROASTER CHOICE";

    private ArrayList<String> roastersString = new ArrayList<String>();
    private ArrayList<Roaster> roastersObj = new ArrayList<Roaster>();
    private EditText roastInput;
    private View cuppingFragView;
    private int sampleNum;
    private TextView sampleLabel;
    private EditText sessionInput;
    private Spinner roasterSpinner;
    private SharedPreferences sharedPreferences;


    public CuppingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CuppingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CuppingFragment newInstance(String param1, String param2) {
        CuppingFragment fragment = new CuppingFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
//            mParam1 = getArguments().getString(ARG_PARAM1);
//            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        Amplify.DataStore.query(Roaster.class,
                queryRoaster -> {
                    while (queryRoaster.hasNext()) {
                        Roaster roaster = queryRoaster.next();
                        roastersObj.add(roaster);
                        roastersString.add(roaster.getName());
                        Log.i(TAG, "Get Roaster Name: " + roaster.getName());
                    }
                },
                error -> Log.e(TAG,  "Error retrieving roasters", error)
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cuppingFragView = inflater.inflate(R.layout.fragment_cupping, container, false);
        sharedPreferences = cuppingFragView.getContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);

        roasterSpinner = cuppingFragView.findViewById(R.id.cupping_roaster_spinner);
        sessionInput = cuppingFragView.findViewById(R.id.cupping_session_input);
        roasterSpinner.setSelection(0, false);

        roasterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                Log.d(TAG, roastersString.get(position) + " Selected in onItemSelected");
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
                Log.d(TAG, "No Selected in onItemSelected");
            }
        });

        //Creating the ArrayAdapter instance having the roaster list
        ArrayAdapter rosterArray = new ArrayAdapter(cuppingFragView.getContext(), android.R.layout.simple_spinner_item, roastersString);
        rosterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        roasterSpinner.setAdapter(rosterArray);

        sampleLabel = cuppingFragView.findViewById(R.id.cupping_sample_label);
        sampleNum = Integer.parseInt(sampleLabel.getText().toString());
        sampleLabel.setText(String.valueOf(sampleNum));
        Log.d(TAG, String.format("Sample Number: %d in onCreateView", sampleNum));

        roastInput = cuppingFragView.findViewById(R.id.cupping_roast_date_input);
        cuppingFragView.findViewById(R.id.cupping_roast_date_button).setOnClickListener(v -> showDatePickerDialog(cuppingFragView));
        cuppingFragView.findViewById(R.id.cupping_roaster_button).setOnClickListener(v -> {
            NewRoasterFragment newRoasterFrag = new NewRoasterFragment();
            newRoasterFrag.show(this.getChildFragmentManager(), null);
        });
        cuppingFragView.findViewById(R.id.cupping_sample_add_button).setOnClickListener(this::updateSampleNum);
        cuppingFragView.findViewById(R.id.cupping_sample_minus_button).setOnClickListener(this::updateSampleNum);
        cuppingFragView.findViewById(R.id.cupping_start_button).setOnClickListener(v -> {
            Intent startCuppingIntent = new Intent(getActivity(), CuppingActivity.class);
            startCuppingIntent.putExtra(ROAST_DATE, roastInput.getText().toString());
            startCuppingIntent.putExtra(SESSION_NAME, sessionInput.getText().toString());
            startCuppingIntent.putExtra(SAMPLE_NUMBER, Integer.parseInt(sampleLabel.getText().toString()));
            startCuppingIntent.putExtra(ROASTER_CHOICE, roastersObj.get(roasterSpinner.getSelectedItemPosition()).getId());
            Log.d(TAG, roasterSpinner.getSelectedItemPosition() + "");

//            startActivity(startCuppingIntent);
            startActivityForResult(startCuppingIntent, 1);
        });

        return cuppingFragView;
    }

    public void updateRoaster(String roasterName, Roaster newRoasterObj){
        roastersString.add(roasterName);
        roastersObj.add(newRoasterObj);
        roasterSpinner.setSelection(roastersString.size() - 1, false);
    }

    public void showDatePickerDialog(View view){
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                view.getContext(),
                new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        month++;
                        roastInput.setText(String.format("%04d/%02d/%02d", year, month, dayOfMonth));
                        Log.d(TAG, String.format("%04d/%02d/%02d selected in onDateSet", year, month, dayOfMonth));
                    }
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    public void updateSampleNum(View view){
        if (view.getId() == R.id.cupping_sample_add_button){
            if (sampleNum + 1 > 12) {
                Toast.makeText(getActivity().getApplicationContext(),"Too many samples" , Toast.LENGTH_LONG).show();
            }else {
                sampleNum++;
            }
        } else{
            if (sampleNum - 1 < 1) {
                Toast.makeText(getActivity().getApplicationContext(),"At least one sample is required" , Toast.LENGTH_LONG).show();
            }else {
                sampleNum--;
            }
        }
        sampleLabel.setText(String.valueOf(sampleNum));
        Log.d(TAG, String.format("Sample Number: %d in updateSampleNum", sampleNum));
    }

    @Override
    public void onResume() {
        super.onResume();
        roastInput.setText(sharedPreferences.getString(ROAST_DATE, ""));
        sessionInput.setText(sharedPreferences.getString(SESSION_NAME, ""));
        sampleNum = sharedPreferences.getInt(SAMPLE_NUMBER, 0);
        sampleLabel.setText(String.valueOf(sampleNum));
        roasterSpinner.setSelection(sharedPreferences.getInt(ROASTER_CHOICE, 0));
    }
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ROAST_DATE, roastInput.getText().toString());
        editor.putString(SESSION_NAME, sessionInput.getText().toString());
        editor.putInt(SAMPLE_NUMBER, Integer.parseInt(sampleLabel.getText().toString()));
        editor.putInt(ROASTER_CHOICE, roasterSpinner.getSelectedItemPosition());
        editor.apply();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(ROAST_DATE, "");
        editor.putString(SESSION_NAME, "");
        editor.putInt(SAMPLE_NUMBER, 6);
        editor.putInt(ROASTER_CHOICE, 0);
        editor.apply();
    }
}