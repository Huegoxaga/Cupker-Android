package com.cupker;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.cupker.task.AddRoasterAsyncTask;

import org.w3c.dom.Text;

/**
 * For a Dialog we must extend the DialogFragment Class
 * We implement OnClickListener to handle button click events
 */
public class GradingDialogFragment extends DialogFragment{

    private int listPosition;
    private int gridPosition;
    public GradingDialogFragment(int listPosition, int gridPosition) {
        this.listPosition = listPosition;
        this.gridPosition = gridPosition;
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // We saved the inflated layout in our myview variable
        View gradingDialogView = inflater.inflate(R.layout.fragment_grading_dialog, container, false);

        gradingDialogView.findViewById(R.id.grading_dialog_score_6_0).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_6_25).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_6_5).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_6_75).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_7_0).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_7_25).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_7_5).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_7_75).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_8_0).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_8_25).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_8_5).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_8_75).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_9_0).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_9_25).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_9_5).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.grading_dialog_score_9_75).setOnClickListener(this::saveScore);
        return gradingDialogView;
    }

    public void saveScore(View v) {
        TextView scoreTextView = (TextView) v;
        double score = Double.parseDouble(scoreTextView.getText().toString());
        CuppingActivity main = (CuppingActivity) getActivity();
        main.setScore(listPosition, gridPosition, score);
        dismiss();
    }
}