package com.cupker.home;
/**
 * Ye Qi, 000792058
 */

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.core.model.query.Where;
import com.amplifyframework.datastore.generated.model.Roaster;
import com.amplifyframework.datastore.generated.model.Status;
import com.cupker.R;
import com.cupker.cupping.CuppingActivity;
import com.cupker.cupping.NewRoasterFragment;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

/**
 * This is the create new cupping session page
 */
public class CuppingFragment extends Fragment {

    // Init keys
    private static final String TAG = "===CUPPING FRAGMENT===";
    private static final String SAMPLE_NUMBER = "SAMPLE NUMBER";
    private static final String SESSION_NAME = "SESSION NAME";
    private static final String ROASTER_ID = "ROASTER ID";
    private static final String ROAST_TIME = "ROAST TIME";
    private static final int START_CUPPING_ACTIVITY = 1;

    // Init variables
    private final ArrayList<String> roastersString;
    private final ArrayList<Roaster> roastersObj;

    // Init UI objects
    private EditText roastDateInput;
    private View cuppingFragView;
    private int sampleNum = 6;
    private TextView sampleLabel;
    private EditText sessionInput;
    private Spinner roasterSpinner;
    private Button startButton;
    private String dateString;

    public CuppingFragment() {
        // Assign variables
        roastersObj = new ArrayList<>();
        roastersString = new ArrayList<>();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Init data
        // Add first roaster record
        roastersObj.add(null);
        roastersString.add(getString(R.string.select));

        // Query and assign rest roaster records
        Amplify.DataStore.query(Roaster.class,
                Where.matches(Roaster.STATUS.eq(Status.ACTIVE)),
                queryRoaster -> {
                    while (queryRoaster.hasNext()) {
                        Roaster roaster = queryRoaster.next();
                        roastersObj.add(roaster);
                        roastersString.add(roaster.getName());
                        Log.i(TAG, "Get Roaster Name: " + roaster.getName());
                    }
                },
                error -> Log.e(TAG, "Error retrieving roasters", error)
        );

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Init
        cuppingFragView = inflater.inflate(R.layout.fragment_cupping, container, false);
        roasterSpinner = cuppingFragView.findViewById(R.id.cupping_roaster_spinner);
        startButton = cuppingFragView.findViewById(R.id.cupping_start_button);
        sessionInput = cuppingFragView.findViewById(R.id.cupping_session_input);

        configSessionInput();

        ArrayAdapter<String> rosterArray = new ArrayAdapter<>(cuppingFragView.getContext(), android.R.layout.simple_spinner_item, roastersString);
        sampleLabel = cuppingFragView.findViewById(R.id.cupping_sample_label);


        /*===================================================================== roast date input setup START =====================================================================*/
        roastDateInput = cuppingFragView.findViewById(R.id.cupping_roast_date_input);

        // update input text and date time string with information of the current date
        updateDateTimeInput(null);


        roastDateInput.setOnClickListener(new View.OnClickListener() {
            // in the xml, property focusable = false is set so that default input keyboard would not be shown
            @Override
            public void onClick(View view) {
                showDatePickerDialog(view);
            }
        });

        /*===================================================================== roast date input setup END =====================================================================*/


        // Setup
        roasterSpinner.setSelection(0, false);
        rosterArray.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        roasterSpinner.setAdapter(rosterArray);
        sampleLabel.setText(String.valueOf(sampleNum));
        Log.d(TAG, String.format("Sample Number: %d in onCreateView", sampleNum));

        // Listener
        cuppingFragView.setOnTouchListener((v, event) -> {

            InputMethodManager imm = (InputMethodManager) cuppingFragView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(cuppingFragView.getWindowToken(), 0);

            return false;
        });

        roasterSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int position, long id) {
                toggleStartButton();
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
        startButton.setOnClickListener(v -> {
            if (roastDateInput.getText().toString().isEmpty() || sessionInput.getText().toString().isEmpty()
                    || roastersObj.get(roasterSpinner.getSelectedItemPosition()) == null) {
                Toast.makeText(getContext(), "Please complete all fields", Toast.LENGTH_LONG).show();

            } else {
                Intent startCuppingIntent = new Intent(getActivity(), CuppingActivity.class);
//                Session newSession = Session.builder()
//                        .name(sessionInput.getText().toString())
//                        .roaster(roastersObj.get(roasterSpinner.getSelectedItemPosition()))
//                        .roastTime(new Temporal.DateTime(dateString))
//                        .build();

                startCuppingIntent.putExtra(SESSION_NAME, sessionInput.getText().toString());
                startCuppingIntent.putExtra(SAMPLE_NUMBER, sampleNum);
                startCuppingIntent.putExtra(ROASTER_ID, roastersObj.get(roasterSpinner.getSelectedItemPosition()).getId());
                startCuppingIntent.putExtra(ROAST_TIME, dateString);

                startActivityForResult(startCuppingIntent, START_CUPPING_ACTIVITY);
            }
        });
        toggleStartButton();

        return cuppingFragView;
    }

    /**
     * add on edit listener to the {@link CuppingFragment#sessionInput}
     * for better UX only
     */
    private void configSessionInput() {
        sessionInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                toggleStartButton();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void toggleStartButton() {
        boolean hasInvalidInput = roastDateInput.getText().toString().isEmpty() || sessionInput.getText().toString().isEmpty()
                || roastersObj.get(roasterSpinner.getSelectedItemPosition()) == null;

        startButton.setEnabled(!hasInvalidInput);
    }


    /**
     * method to update the input text of the roastInput UI element, and the
     * string value of the dateString property
     * @param calendar calendar instance that contains the selected datetime information.
     */
    private void updateDateTimeInput(Calendar calendar) {
        Calendar myCalendar = calendar;
        int year, month, date;

        if (myCalendar == null) {
            // default to current date time
            myCalendar = Calendar.getInstance();
        }
        year = myCalendar.get(Calendar.YEAR);
        month = myCalendar.get(Calendar.MONTH);
        date = myCalendar.get(Calendar.DATE);
        myCalendar.set(year, month, date, 0, 0, 0);
        myCalendar.set(Calendar.MILLISECOND, 0);


        dateString = com.amazonaws.util.DateUtils.formatISO8601Date(myCalendar.getTime());
        roastDateInput.setText(String.format(Locale.CANADA, "%04d/%02d/%02d", year, month + 1, date));
        Log.d(TAG, String.format("%04d/%02d/%02d selected in onDateSet", year, month + 1, date));
        Log.d(TAG, dateString);
    }
    /**
     * function that called by instance of NewRoasterFragment.
     * <p>
     * When new roaster is added, by user, then sent to Amplify with status "success", this function
     * would be called
     *
     * @param roasterName
     * @param newRoasterObj
     * @see NewRoasterFragment
     */
    public void updateRoaster(String roasterName, Roaster newRoasterObj) {
        roastersString.add(roasterName);
        roastersObj.add(newRoasterObj);
        // show the added roster name automatically
        roasterSpinner.setSelection(roastersString.size() - 1, false);
    }

    public void showDatePickerDialog(View view) {
        DatePickerDialog datePickerDialog = new DatePickerDialog(
                view.getContext(),
                (view1, year, month, dayOfMonth) -> {
                    Calendar selectedCalendar = Calendar.getInstance();
                    selectedCalendar.set(year, month, dayOfMonth, 0, 0, 0);
                    updateDateTimeInput(selectedCalendar);
//                    Date selectedDate = selectedCalendar.getTime();
//                    roastInput.setText(String.format(Locale.CANADA, "%04d/%02d/%02d", year, month + 1, dayOfMonth));
//                    dateString = com.amazonaws.util.DateUtils.formatISO8601Date(selectedDate);
//                    Log.d(TAG, String.format("%04d/%02d/%02d selected in onDateSet", year, month + 1, dayOfMonth));
//                    Log.d(TAG, dateString);
                },
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.getDatePicker().setMaxDate(Calendar.getInstance().getTimeInMillis());
        datePickerDialog.show();
    }

    /**
     * Incre and Decre the sample num input counter
     *
     * @param view
     */
    public void updateSampleNum(View view) {
        InputMethodManager imm = (InputMethodManager) cuppingFragView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(cuppingFragView.getWindowToken(), 0);
        if (view.getId() == R.id.cupping_sample_add_button) {
            if (sampleNum + 1 > 12) {
//                Toast.makeText(requireActivity().getApplicationContext(),"Too many samples" , Toast.LENGTH_LONG).show();
            } else {
                sampleNum++;
            }
        } else {
            if (sampleNum - 1 < 1) {
//                Toast.makeText(requireActivity().getApplicationContext(),"At least one sample is required" , Toast.LENGTH_LONG).show();
            } else {
                sampleNum--;
            }
        }
        sampleLabel.setText(String.valueOf(sampleNum));
        Log.d(TAG, String.format("Sample Number: %d in updateSampleNum", sampleNum));
    }

}