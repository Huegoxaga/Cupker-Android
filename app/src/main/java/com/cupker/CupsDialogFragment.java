package com.cupker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

/**
 * The grading popup
 */
public class CupsDialogFragment extends DialogFragment {

    // Key
    private static final String TAG = "===CUPS DIA FRAG===";

    // Declare Data
    private final int listPosition;
    private final int gridPosition;
    private double scores;
    private String scorePosition;
    private CuppingActivity main;
    private ImageButton doneBtn;
    private ImageView cupOne;
    private ImageView cupTwo;
    private ImageView cupThree;
    private ImageView cupFour;
    private ImageView cupFive;

    public CupsDialogFragment(int listPosition, int gridPosition, double scores) {

        // Init Data
        this.listPosition = listPosition;
        this.gridPosition = gridPosition;
        this.scores = scores;
        this.scorePosition = getPosition(scores);
        this.main = (CuppingActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Init
        View cupsDialogView = inflater.inflate(R.layout.fragment_cups_dialog, container, false);
        TextView titleText = cupsDialogView.findViewById(R.id.cups_dialog_title);
        titleText.setText(String.format("#%d - %s", (listPosition + 1), CuppingGridAdapter.gradingTitles[gridPosition]));
        cupOne = cupsDialogView.findViewById(R.id.cups_dialog_cup_one);
        cupTwo = cupsDialogView.findViewById(R.id.cups_dialog_cup_two);
        cupThree = cupsDialogView.findViewById(R.id.cups_dialog_cup_three);
        cupFour = cupsDialogView.findViewById(R.id.cups_dialog_cup_four);
        cupFive = cupsDialogView.findViewById(R.id.cups_dialog_cup_five);
        doneBtn = cupsDialogView.findViewById(R.id.cups_dialog_done);
        if (scorePosition.charAt(0) == '1') cupOne.setImageResource(R.drawable.bad_cup);
        if (scorePosition.charAt(1) == '1') cupTwo.setImageResource(R.drawable.bad_cup);
        if (scorePosition.charAt(2) == '1') cupThree.setImageResource(R.drawable.bad_cup);
        if (scorePosition.charAt(3) == '1') cupFour.setImageResource(R.drawable.bad_cup);
        if (scorePosition.charAt(4) == '1') cupFive.setImageResource(R.drawable.bad_cup);
        // Setup


        // Listener
        cupOne.setOnClickListener(this::changePosition);
        cupTwo.setOnClickListener(this::changePosition);
        cupThree.setOnClickListener(this::changePosition);
        cupFour.setOnClickListener(this::changePosition);
        cupFive.setOnClickListener(this::changePosition);
        doneBtn.setOnClickListener(this::saveScore);

        return cupsDialogView;
    }

    private String getPosition(double scores) {
//        Log.d(TAG, String.format("%5s", Integer.toBinaryString((int) scores)).replace(' ', '0'));
        return String.format("%5s", Integer.toBinaryString((int) scores)).replace(' ', '0');
    }

    private double getScore(String scorePosition) {
        return Double.valueOf(Integer.parseInt(scorePosition, 2));
    }

    public void saveScore(View v) {
        if (main == null) main = (CuppingActivity) getActivity();
        main.setScore(listPosition, gridPosition, getScore(scorePosition));
        dismiss();
    }

    public void changePosition(View v) {
        StringBuilder newPosition = new StringBuilder(scorePosition);
        switch (v.getId()) {
            case R.id.cups_dialog_cup_one:
                if (newPosition.charAt(0) == '0') {
                    newPosition.setCharAt(0, '1');
                    cupOne.setImageResource(R.drawable.bad_cup);
                } else {
                    newPosition.setCharAt(0, '0');
                    cupOne.setImageResource(R.drawable.good_cup);
                }
                break;
            case R.id.cups_dialog_cup_two:
                if (newPosition.charAt(1) == '0') {
                    newPosition.setCharAt(1, '1');
                    cupTwo.setImageResource(R.drawable.bad_cup);
                } else {
                    newPosition.setCharAt(1, '0');
                    cupTwo.setImageResource(R.drawable.good_cup);
                }
                break;
            case R.id.cups_dialog_cup_three:
                if (newPosition.charAt(2) == '0') {
                    newPosition.setCharAt(2, '1');
                    cupThree.setImageResource(R.drawable.bad_cup);
                } else {
                    newPosition.setCharAt(2, '0');
                    cupThree.setImageResource(R.drawable.good_cup);
                }
                break;
            case R.id.cups_dialog_cup_four:
                if (newPosition.charAt(3) == '0') {
                    newPosition.setCharAt(3, '1');
                    cupFour.setImageResource(R.drawable.bad_cup);
                } else {
                    newPosition.setCharAt(3, '0');
                    cupFour.setImageResource(R.drawable.good_cup);
                }
                break;
            case R.id.cups_dialog_cup_five:
                if (newPosition.charAt(4) == '0') {
                    newPosition.setCharAt(4, '1');
                    cupFive.setImageResource(R.drawable.bad_cup);
                } else {
                    newPosition.setCharAt(4, '0');
                    cupFive.setImageResource(R.drawable.good_cup);
                }
                break;
        }
        scorePosition = newPosition.toString();
    }
}