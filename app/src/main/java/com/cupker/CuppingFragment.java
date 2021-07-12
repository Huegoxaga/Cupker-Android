package com.cupker;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.temporal.Temporal;
import com.amplifyframework.datastore.generated.model.Roaster;
import com.amplifyframework.datastore.generated.model.Sample;
import com.amplifyframework.datastore.generated.model.Session;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CuppingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String TAG = "===CUPPING FRAGMENT===";
//    private static final String PREFERENCE = "CUPPING FRAGMENT PREFERENCE";
//    private static final String SAMPLE_NUMBER = "SAMPLE NUMBER";
//    private static final String SESSION_NAME = "SESSION NAME";
//    private static final String ROAST_DATE = "ROAST DATE";
//    private static final String ROASTER_CHOICE = "ROASTER CHOICE";
    private static final String SAMPLE_NUMBER = "SAMPLE NUMBER";
    private static final String SESSION_OBJ = "SESSION NAME";
    private final ArrayList<String> roastersString;
    private final ArrayList<Roaster> roastersObj;

    private EditText roastInput;
    private View cuppingFragView;
    private int sampleNum = 6;
    private TextView sampleLabel;
    private EditText sessionInput;
    private Spinner roasterSpinner;
//    private SharedPreferences sharedPreferences;
    private String dateString;

    public CuppingFragment() {
        roastersObj = new ArrayList<>();
        roastersString = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        roastersObj.add(null);
        roastersString.add("Select a Roaster");
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
    public void onResume() {
        super.onResume();
//        roastInput.setText(sharedPreferences.getString(ROAST_DATE, ""));
//        sessionInput.setText(sharedPreferences.getString(SESSION_NAME, ""));
//        sampleNum = sharedPreferences.getInt(SAMPLE_NUMBER, 6);
//        sampleLabel.setText(String.valueOf(sampleNum));
//        roasterSpinner.setSelection(sharedPreferences.getInt(ROASTER_CHOICE, 0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // init
        cuppingFragView = inflater.inflate(R.layout.fragment_cupping, container, false);
//        sharedPreferences = cuppingFragView.getContext().getSharedPreferences(PREFERENCE, Context.MODE_PRIVATE);
        roasterSpinner = cuppingFragView.findViewById(R.id.cupping_roaster_spinner);
        sessionInput = cuppingFragView.findViewById(R.id.cupping_session_input);
        ArrayAdapter<String> rosterArray = new ArrayAdapter<>(cuppingFragView.getContext(), android.R.layout.simple_spinner_item, roastersString);
        sampleLabel = cuppingFragView.findViewById(R.id.cupping_sample_label);
        roastInput = cuppingFragView.findViewById(R.id.cupping_roast_date_input);

        // setup
        roasterSpinner.setSelection(0, false);
        rosterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roasterSpinner.setAdapter(rosterArray);
        sampleLabel.setText(String.valueOf(sampleNum));
        Log.d(TAG, String.format("Sample Number: %d in onCreateView", sampleNum));

        // listener
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
        cuppingFragView.findViewById(R.id.cupping_roast_date_button).setOnClickListener(v -> showDatePickerDialog(cuppingFragView));
        cuppingFragView.findViewById(R.id.cupping_roaster_button).setOnClickListener(v -> {
            NewRoasterFragment newRoasterFrag = new NewRoasterFragment();
            newRoasterFrag.show(this.getChildFragmentManager(), null);
        });
        cuppingFragView.findViewById(R.id.cupping_sample_add_button).setOnClickListener(this::updateSampleNum);
        cuppingFragView.findViewById(R.id.cupping_sample_minus_button).setOnClickListener(this::updateSampleNum);
        cuppingFragView.findViewById(R.id.cupping_start_button).setOnClickListener(v -> {
            Intent startCuppingIntent = new Intent(getActivity(), CuppingActivity.class);
            if (roastInput.getText().toString().isEmpty() || sessionInput.getText().toString().isEmpty()
                    || roastersObj.get(roasterSpinner.getSelectedItemPosition()) == null) {
                Toast.makeText(getContext(), "Please complete all fields", Toast.LENGTH_LONG).show();
            }
            else{
                Session newSession = Session.builder()
                        .name(sessionInput.getText().toString())
                        .roaster(roastersObj.get(roasterSpinner.getSelectedItemPosition()))
                        .roastTime(new Temporal.DateTime(dateString))
                        .build();

                Gson gson = new Gson();
                String newSessionStr = gson.toJson(newSession);
                startCuppingIntent.putExtra(SESSION_OBJ, newSessionStr);
                startCuppingIntent.putExtra(SAMPLE_NUMBER, sampleNum);
                Log.d(TAG, newSession.toString());
                startActivityForResult(startCuppingIntent, 1);
            }
//            startCuppingIntent.putExtra(ROAST_DATE, roastInput.getText().toString());
//            startCuppingIntent.putExtra(SESSION_NAME, sessionInput.getText().toString());
//            startCuppingIntent.putExtra(SAMPLE_NUMBER, Integer.parseInt(sampleLabel.getText().toString()));
//            startCuppingIntent.putExtra(ROASTER_CHOICE, roastersObj.get(roasterSpinner.getSelectedItemPosition()).getId());
//            Log.d(TAG, roasterSpinner.getSelectedItemPosition() + "");
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
                (view1, year, month, dayOfMonth) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(year, month, dayOfMonth, 0, 0, 0);
                    Date selectedDate = selectedCalendar.getTime();
                    roastInput.setText(String.format(Locale.CANADA ,"%04d/%02d/%02d", year, month + 1, dayOfMonth));
                    dateString = com.amazonaws.util.DateUtils.formatISO8601Date(selectedDate);
                    Log.d(TAG, String.format("%04d/%02d/%02d selected in onDateSet", year, month + 1, dayOfMonth));
                    Log.d(TAG, dateString);
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
    public void onPause() {
        super.onPause();
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(ROAST_DATE, roastInput.getText().toString());
//        editor.putString(SESSION_NAME, sessionInput.getText().toString());
//        editor.putInt(SAMPLE_NUMBER, Integer.parseInt(sampleLabel.getText().toString()));
//        editor.putInt(ROASTER_CHOICE, roasterSpinner.getSelectedItemPosition());
//        editor.apply();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(ROAST_DATE, "");
//        editor.putString(SESSION_NAME, "");
//        editor.putInt(SAMPLE_NUMBER, 6);
//        editor.putInt(ROASTER_CHOICE, 0);
//        editor.apply();
    }
}