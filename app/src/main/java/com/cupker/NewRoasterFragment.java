package com.cupker;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Roaster;
import com.cupker.task.AddRoasterAsyncTask;

/**
 * For a Dialog we must extend the DialogFragment Class
 * We implement OnClickListener to handle button click events
 */
public class NewRoasterFragment extends DialogFragment{
    private static final String TAG = "===New Roaster Frag===";

    public NewRoasterFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        // We saved the inflated layout in our myview variable
        View addRoasterView = inflater.inflate(R.layout.fragment_new_roaster, container, false);
        Button addButton = addRoasterView.findViewById(R.id.new_roaster_add_button);
        addButton.setOnClickListener(v->{
            EditText newRoasterInput = getView().findViewById(R.id.new_roaster_name_input);
            String newRoasterName = newRoasterInput.getText().toString();

            Roaster newRoaster = Roaster.builder()
                    .name(newRoasterName)
                    .build();

            CuppingFragment parentFrag = ((CuppingFragment)this.getParentFragment());
            parentFrag.updateRoaster(newRoasterName, newRoaster);

            Amplify.DataStore.save(newRoaster,
                    success -> Log.i(TAG, "Saved item: " + success.item().getName()),
                    error -> Log.e(TAG, "Could not save item to DataStore", error)
            );
//            AddRoasterAsyncTask addRoasterAsyncTask = new AddRoasterAsyncTask();
//            addRoasterAsyncTask.execute(newRoasterName, "parameterB", "parameterC");
            dismiss();
        });



        Button cancelButton = addRoasterView.findViewById(R.id.new_roaster_cancel_button);
        cancelButton.setOnClickListener(v->dismiss());
        return addRoasterView;
    }
}