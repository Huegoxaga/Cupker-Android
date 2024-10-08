package com.cupker.cupping;
/**
 * Ye Qi, 000792058
 */
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cupker.R;

/**
 * The grading popup
 */
public class GradingDialogFragment extends DialogFragment {

    // Key
    private static final String TAG = "===GRADING DIA FRAG===";

    // Declare Data
    private final int listPosition;
    private final int gridPosition;
    private CuppingActivity main;

    public GradingDialogFragment(int listPosition, int gridPosition) {

        // Init Data
        this.listPosition = listPosition;
        this.gridPosition = gridPosition;
        this.main = (CuppingActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View gradingDialogView = inflater.inflate(R.layout.fragment_grading_dialog, container, false);
        TextView titleText = gradingDialogView.findViewById(R.id.grading_dialog_title);
        titleText.setText(String.format("#%d - %s", (listPosition + 1), CuppingGridAdapter.gradingTitles[gridPosition]));

        int [] ids = {
                R.id.grading_dialog_score_6_0,
                R.id.grading_dialog_score_6_25,
                R.id.grading_dialog_score_6_5,
                R.id.grading_dialog_score_6_75,
                R.id.grading_dialog_score_7_0,
                R.id.grading_dialog_score_7_25,
                R.id.grading_dialog_score_7_5,
                R.id.grading_dialog_score_7_75,
                R.id.grading_dialog_score_8_0,
                R.id.grading_dialog_score_8_25,
                R.id.grading_dialog_score_8_5,
                R.id.grading_dialog_score_8_75,
                R.id.grading_dialog_score_9_0,
                R.id.grading_dialog_score_9_25,
                R.id.grading_dialog_score_9_5,
                R.id.grading_dialog_score_9_75,
        };

        if (main == null) main = (CuppingActivity) getActivity();
        double r = main.getScore(listPosition, gridPosition);

        for (int viewId : ids) {
            TextView textView = gradingDialogView.findViewById(viewId);
            textView.setOnClickListener(this::saveScore);

            String content = textView.getText().toString();

            boolean hasIt = content.startsWith(r+"");

            if(hasIt) {
                textView.setBackgroundColor(getResources().getColor(R.color.main_brown_light));
                textView.setTextColor(Color.WHITE);
            }
        }
        return gradingDialogView;
    }    
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        if (main == null) main = (CuppingActivity) getActivity();
//        double r = main.getScore(listPosition, gridPosition);
//        View gradingDialogView = inflater.inflate(R.layout.fragment_grading_dialog, container, false);
//        TextView titleText = gradingDialogView.findViewById(R.id.grading_dialog_title);
//        titleText.setText(String.format("#%d - %s", (listPosition + 1), CuppingGridAdapter.gradingTitles[gridPosition]));
//        gradingDialogView.findViewById(R.id.grading_dialog_score_6_0).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_6_25).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_6_5).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_6_75).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_7_0).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_7_25).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_7_5).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_7_75).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_8_0).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_8_25).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_8_5).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_8_75).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_9_0).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_9_25).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_9_5).setOnClickListener(this::saveScore);
//        gradingDialogView.findViewById(R.id.grading_dialog_score_9_75).setOnClickListener(this::saveScore);
//        return gradingDialogView;
//    }

    /**
     * Get the score and call the activity set score method
     * @param v
     */
    public void saveScore(View v) {
        if (main == null) main = (CuppingActivity) getActivity();
        TextView scoreTextView = (TextView) v;
        double score = Double.parseDouble(scoreTextView.getText().toString());
        main.setScore(listPosition, gridPosition, score);
        dismiss();
    }
}