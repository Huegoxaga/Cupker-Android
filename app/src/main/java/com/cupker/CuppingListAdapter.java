package com.cupker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

public class CuppingListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater layoutInflater;

//    int[] animalImages = { R.drawable.bear, R.drawable.bird, R.drawable.cat,
//            R.drawable.cow, R.drawable.dolphin, R.drawable.fish, R.drawable.fox,
//            R.drawable.horse, R.drawable.lion, R.drawable.tiger };
    String[] animalNames = { "Bear", "Bird", "Cat", "Cow", "Dolphin",
            "Fish", "Fox", "Horse", "Lion", "Tiger" };
    int[] animalsPower = { 200, 20, 50, 150, 50, 10, 70, 400, 250, 220 };
    int[] animalsSpeed = { 50, 80, 60, 10, 50, 40, 80, 350, 200, 100 };


    public CuppingListAdapter(Context context) {

        this.context = context;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        return 10 ;
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
    public View getView(int position, View cupppingListView, ViewGroup parent) {

        cupppingListView = layoutInflater.inflate(R.layout.activity_cupping_list, null);

        TextView txtAnimalName = cupppingListView.findViewById(R.id.title);
        TextView txtAnimalPower = cupppingListView.findViewById(R.id.content);
        TextView txtAnimalSpeed = cupppingListView.findViewById(R.id.foot);

        CuppingGridView cuppingGridView = cupppingListView.findViewById(R.id.cupping_grid);

        CuppingGridAdapter cuppingGridAdapter = new CuppingGridAdapter(cupppingListView.getContext());
        cuppingGridView.setAdapter(cuppingGridAdapter);

        String oldTxtPower = txtAnimalPower.getText().toString();
        String oldTxtSpeed = txtAnimalSpeed.getText().toString();

        txtAnimalName.setText(animalNames[position]);
        txtAnimalPower.setText(oldTxtPower + ": " + animalsPower[position] + "");
        txtAnimalSpeed.setText(oldTxtSpeed + ": " + animalsSpeed[position] + "");

        cupppingListView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(context, "The name of the Animal is" + txtAnimalName.getText(), Toast.LENGTH_SHORT).show();

            }
        });

        return cupppingListView;
    }

}

