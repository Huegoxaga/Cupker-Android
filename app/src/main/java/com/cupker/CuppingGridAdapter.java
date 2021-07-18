package com.cupker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;

import androidx.fragment.app.FragmentActivity;

import com.amplifyframework.datastore.generated.model.Sample;

import java.util.Locale;

public class CuppingGridAdapter extends BaseAdapter {
//    private static final String TAG = "===Cup Grid Adapter===";
    private final String[] gradingTitles = { "Fragrance/Aroma", "Flavor", "Aftertaste", "Acidity", "Body",
            "Uniformity", "Clean Cup", "Overall", "Balance", "Sweetness", "Defect Cups", "Intensity" };
    private final FragmentActivity context;
    private final Sample sample;
    private final int listPosition;

    public CuppingGridAdapter(Context context, Sample sample, int listPosition) {
        this.context = (FragmentActivity) context;
        this.listPosition = listPosition;
        this.sample = sample;
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
        }
        TextView gradingTitleText = gradingView.findViewById(R.id.cupping_grid_grading_title);
        TextView gradingScoreText = gradingView.findViewById(R.id.cupping_grid_grading_score);

        // Setup
        gradingTitleText.setText(gradingTitles[position]);
        switch (position) {
            case 0 :
                gradingScoreText.setText(String.format(Locale.getDefault(),"%.2f", sample.getAroma()));
                break;
            case 1 :
                gradingScoreText.setText(String.format(Locale.getDefault(),"%.2f", sample.getFlavor()));
                break;
            case 2 :
                gradingScoreText.setText(String.format(Locale.getDefault(),"%.2f", sample.getAfterTaste()));
                break;
            case 3 :
                gradingScoreText.setText(String.format(Locale.getDefault(),"%.2f", sample.getAcidity()));
                break;
            case 4 :
                gradingScoreText.setText(String.format(Locale.getDefault(),"%.2f", sample.getBody()));
                break;
            case 5 :
                gradingScoreText.setText(String.format(Locale.getDefault(),"%.0f", sample.getUniformity()));
                break;
            case 6 :
                gradingScoreText.setText(String.format(Locale.getDefault(),"%.0f", sample.getCleanCup()));
                break;
            case 7 :
                gradingScoreText.setText(String.format(Locale.getDefault(),"%.2f", sample.getOverall()));
                break;
            case 8 :
                gradingScoreText.setText(String.format(Locale.getDefault(),"%.2f", sample.getBalance()));
                break;
            case 9 :
                gradingScoreText.setText(String.format(Locale.getDefault(),"%.0f", sample.getSweetness()));
                break;
            case 10 :
                gradingScoreText.setText(String.format(Locale.getDefault(),"%.0f", sample.getDefectCount()));
                break;
            case 11 :
                gradingScoreText.setText(String.format(Locale.getDefault(),"%.0f", sample.getDefectType()));
                break;
        }
        gradingView.setLayoutParams(new GridView.LayoutParams(150, 150));

        // Listener
        gradingView.setOnClickListener(v -> {
            GradingDialogFragment gradingDialog = new GradingDialogFragment(listPosition , position);
            gradingDialog.show(context.getSupportFragmentManager(), null);
        });
        return gradingView;
    }
}
