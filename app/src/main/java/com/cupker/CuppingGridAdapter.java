package com.cupker;
/**
 * Ye Qi, 000792058
 */
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.amplifyframework.datastore.generated.model.Sample;

import java.util.Locale;

/**
 * Grid for each sample in cupping list
 */
public class CuppingGridAdapter extends BaseAdapter {
    // Keys
    private static final String TAG = "===Cup Grid Adapter===";

    // UI & Controller
    private final FragmentActivity context;

    // Data
    private final Sample sample;
    public static String[] gradingTitles;
    private final int listPosition;
    private final boolean editable;

    public CuppingGridAdapter(Context context, Sample sample, int listPosition, Boolean editable) {
        // Init UI & Controller
        this.context = (FragmentActivity) context;
        // Init Data
        this.listPosition = listPosition;
        this.sample = sample;
        this.editable = editable;
        this.gradingTitles = context.getResources().getStringArray(R.array.grading);
    }

    @Override
    public int getCount() {
        return gradingTitles.length;
    }

    @Override
    public Object getItem(int position) {
        return gradingTitles[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View gradingView, ViewGroup parent) {

        // Init
        if (gradingView == null) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            gradingView = layoutInflater.inflate(R.layout.activity_cupping_grading_grid, parent, false);
//            DisplayMetrics metrics = this.getResources().getDisplayMetrics();
//            int screenWidth = metrics.widthPixels;
            gradingView.setLayoutParams(new GridView.LayoutParams(240, 140));

        }
        TextView gradingTitleText = gradingView.findViewById(R.id.cupping_grid_grading_title);
        TextView gradingScoreText = gradingView.findViewById(R.id.cupping_grid_grading_score);

        // Setup
        gradingTitleText.setText(gradingTitles[position]);
        switch (position) {
            case 0:
                gradingScoreText.setText(String.format(Locale.getDefault(), "%.2f", sample.getAroma()));
                break;
            case 1:
                gradingScoreText.setText(String.format(Locale.getDefault(), "%.2f", sample.getFlavor()));
                break;
            case 2:
                gradingScoreText.setText(String.format(Locale.getDefault(), "%.2f", sample.getAfterTaste()));
                break;
            case 3:
                gradingScoreText.setText(String.format(Locale.getDefault(), "%.2f", sample.getAcidity()));
                break;
            case 4:
                gradingScoreText.setText(String.format(Locale.getDefault(), "%.2f", sample.getBody()));
                break;
            case 5:
                gradingScoreText.setText(String.format(Locale.getDefault(), "%.0f", getCupsScore(sample.getUniformity())));
                break;
            case 6:
                gradingScoreText.setText(String.format(Locale.getDefault(), "%.0f", getCupsScore(sample.getCleanCup())));
                break;
            case 7:
                gradingScoreText.setText(String.format(Locale.getDefault(), "%.2f", sample.getOverall()));
                break;
            case 8:
                gradingScoreText.setText(String.format(Locale.getDefault(), "%.2f", sample.getBalance()));
                break;
            case 9:
                gradingScoreText.setText(String.format(Locale.getDefault(), "%.0f", getCupsScore(sample.getSweetness())));
                break;
            case 10:
                String scorePosition = Integer.toBinaryString(sample.getDefectCount().intValue());
                double count = (scorePosition.length() - scorePosition.replace("1", "").length()) * -1;
                gradingScoreText.setTextColor(context.getResources().getColor(R.color.design_default_color_error));
                gradingScoreText.setText(String.format(Locale.getDefault(), "%.0f", count));
                break;
            case 11:
                gradingScoreText.setText(String.format(Locale.getDefault(), "%.0f", sample.getDefectType()));
                break;
        }

        // Listener
        gradingView.setOnClickListener(v -> {
            CupsDialogFragment cupsDialog;
            GradingDialogFragment gradingDialog;
            IntensityDialogFragment intensityDialog;

            Log.d(TAG, " Selected in grid ID " + position);
            if (editable) {
                switch (position) {
                    case 0:
                    case 1:
                    case 2:
                    case 3:
                    case 4:
                    case 7:
                    case 8:
                        gradingDialog = new GradingDialogFragment(listPosition, position);
                        gradingDialog.show(context.getSupportFragmentManager(), null);
                        break;
                    case 5:
                        cupsDialog = new CupsDialogFragment(listPosition, position, sample.getUniformity());
                        cupsDialog.show(context.getSupportFragmentManager(), null);
                        break;
                    case 6:
                        cupsDialog = new CupsDialogFragment(listPosition, position, sample.getCleanCup());
                        cupsDialog.show(context.getSupportFragmentManager(), null);
                        break;
                    case 9:
                        cupsDialog = new CupsDialogFragment(listPosition, position, sample.getSweetness());
                        cupsDialog.show(context.getSupportFragmentManager(), null);
                        break;
                    case 10:
                        cupsDialog = new CupsDialogFragment(listPosition, position, sample.getDefectCount());
                        cupsDialog.show(context.getSupportFragmentManager(), null);
                        break;
                    case 11:
                        intensityDialog = new IntensityDialogFragment(listPosition, position);
                        intensityDialog.show(context.getSupportFragmentManager(), null);
                        break;
                }
            }
        });

        return gradingView;
    }

    /**
     * Convert decimal position to actual cupping score
     * @param scoreCode
     * @return
     */
    private double getCupsScore(Double scoreCode) {
        String scorePosition = Integer.toBinaryString(scoreCode.intValue());
        return 10 - (scorePosition.length() - scorePosition.replace("1", "").length()) * 2;
    }
}
