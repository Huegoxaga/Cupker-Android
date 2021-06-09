package com.cupker;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class CuppingGridAdapter extends BaseAdapter {


    String[] animalNames = { "Bear", "Bird", "Cat", "Cow", "Dolphin",
            "Fish", "Fox", "Horse", "Lion", "Tiger" };

    Context context;

    public CuppingGridAdapter(Context context) {
        this.context = context;
    }



    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return animalNames[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View cuppingGridView, ViewGroup parent) {

        TextView newText = new TextView(context);
        newText.setText(animalNames[position]);
        newText.setLayoutParams(new GridView.LayoutParams(150, 150));
        newText.setPadding(10, 10, 10, 10);
        newText.setTextColor(Color.RED);
        newText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "The Name of the Animal is: " + animalNames[position],
                        Toast.LENGTH_SHORT).show();
            }
        });
        return newText;

    }


}
