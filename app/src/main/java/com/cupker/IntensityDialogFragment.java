package com.cupker;
/**
 * Ye Qi, 000792058
 */
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.DialogFragment;

/**
 * The intensity selection popup
 */
public class IntensityDialogFragment extends DialogFragment {

    // Key
    private static final String TAG = "===GRADING DIA FRAG===";

    // Declare Data
    private final int listPosition;
    private final int gridPosition;
    private CuppingActivity main;

    public IntensityDialogFragment(int listPosition, int gridPosition) {

        // Init Data
        this.listPosition = listPosition;
        this.gridPosition = gridPosition;
        this.main = (CuppingActivity) getActivity();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View gradingDialogView = inflater.inflate(R.layout.fragment_intensity_dialog, container, false);
        TextView titleText = gradingDialogView.findViewById(R.id.intensity_dialog_title);
        titleText.setText(String.format("#%d - %s", (listPosition + 1), CuppingGridAdapter.gradingTitles[gridPosition]));
        gradingDialogView.findViewById(R.id.intensity_dialog_taint).setOnClickListener(this::saveScore);
        gradingDialogView.findViewById(R.id.intensity_dialog_fault).setOnClickListener(this::saveScore);
        return gradingDialogView;
    }

    /**
     * Save the intensity score
     * @param v
     */
    public void saveScore(View v) {
        if (main == null) main = (CuppingActivity) getActivity();
        if (v.getId() == R.id.intensity_dialog_taint) main.setScore(listPosition, gridPosition, 2);
        else main.setScore(listPosition, gridPosition, 4);
        dismiss();
    }
}