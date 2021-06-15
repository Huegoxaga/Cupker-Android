package com.cupker;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class CuppingGridAdapter extends BaseAdapter {
    private static final String TAG = "===Cupp Grid Adapter===";

    private String[] gradingTitles = { "Fragrance/Aroma", "Flavor", "Aftertaste", "Overall", "Acidity",
            "Body", "Balance", "Defects" };
    private String[] gradingScores = { "6.00", "6.00", "6.00", "6.00", "6.00", "6.00", "6.00", "6.00" };
    private CuppingActivity context;
    private View gradingView;
    private LayoutInflater layoutInflater;
    private TextView gradingTitleText;
    private TextView gradingScoreText;




    public CuppingGridAdapter(Context context) {
        this.context =  (CuppingActivity) context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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

        if (gradingView == null) {
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            gradingView = layoutInflater.inflate(R.layout.activity_cupping_grading_grid, null);
            Log.d(TAG, gradingTitles[position]);
        }

        gradingTitleText = gradingView.findViewById(R.id.cupping_grid_grading_title);
        gradingScoreText = gradingView.findViewById(R.id.cupping_grid_grading_score);
        gradingTitleText.setText(gradingTitles[position]);
        gradingScoreText.setText(gradingScores[position]);
        gradingView.setLayoutParams(new GridView.LayoutParams(150, 150));

        gradingView.setOnClickListener(this::openGrading);
        return gradingView;
    }

    public void openGrading(View v) {
        GradingDialogFragment gradingDialog = new GradingDialogFragment();
        gradingDialog.show(context.getSupportFragmentManager(), null);
    }
}
