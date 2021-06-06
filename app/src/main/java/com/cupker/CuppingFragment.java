package com.cupker;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

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
    private static final String TAG = "CUPPING FRAGMENT";
    private static final String PREFERENCE = "CUPPING FRAGMENT PREFERENCE";
    private static final String SAMPLE_NUMBER = "SAMPLE NUMBER";
    private static final String SESSION_NAME = "SESSION NAME";
    private static final String ROAST_DATE = "ROAST DATE";
    private static final String ROASTER_CHOICE = "ROASTER CHOICE";

    private String[] roasters = { "Roaster A", "Roaster B", "Roaster C", "Roaster D"};
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        cuppingFragView = inflater.inflate(R.layout.fragment_cupping, container, false);

        roasterSpinner = (Spinner) cuppingFragView.findViewById(R.id.cupping_roaster_spinner);
        roasterSpinner.setSelection(0, false);

        sessionInput = cuppingFragView.findViewById(R.id.cupping_session_input);

        roasterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                Log.d(TAG, roasters[position] + " Selected in onItemSelected");
            }
            @Override
            public void onNothingSelected(AdapterView<?> arg0) {
            }
        });

        //Creating the ArrayAdapter instance having the rosater list
        ArrayAdapter rosterArray = new ArrayAdapter(cuppingFragView.getContext(), android.R.layout.simple_spinner_item, roasters);
        rosterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        roasterSpinner.setAdapter(rosterArray);

        sampleLabel = cuppingFragView.findViewById(R.id.cupping_sample_label);
        sampleNum = Integer.parseInt(sampleLabel.getText().toString());
        Log.d(TAG, String.format("Sample Number: %d in onCreateView", sampleNum));

        roastInput = cuppingFragView.findViewById(R.id.cupping_roast_date_input);
        cuppingFragView.findViewById(R.id.cupping_roast_date_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(cuppingFragView);
            }
        });

        cuppingFragView.findViewById(R.id.cupping_sample_add_button).setOnClickListener(this::updateSampleNum);
        cuppingFragView.findViewById(R.id.cupping_sample_minus_button).setOnClickListener(this::updateSampleNum);

        return cuppingFragView;
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
        sharedPreferences = cuppingFragView.getContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        roastInput.setText(sharedPreferences.getString(ROAST_DATE, ""));
        sessionInput.setText(sharedPreferences.getString(SESSION_NAME, ""));
        sampleLabel.setText(String.valueOf(sharedPreferences.getInt(SAMPLE_NUMBER, 0)));
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



}