package com.cupker;

import android.os.Bundle;

import androidx.fragment.app.DialogFragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Roaster;

/**
 * This is the add roaster fragment for the roaster drop down in create new cupping session page
 */
public class NewRoasterFragment extends DialogFragment{

    // Init Keys
    private static final String TAG = "===New Roaster Frag===";

    // Init UI objects
    private View addRoasterView;
    private Button addButton;
    private Button cancelButton;

    public NewRoasterFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Init
        addRoasterView = inflater.inflate(R.layout.fragment_new_roaster, container, false);
        addButton = addRoasterView.findViewById(R.id.new_roaster_add_button);
        cancelButton = addRoasterView.findViewById(R.id.new_roaster_cancel_button);

        // Listeners
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
            dismiss();
        });

        cancelButton.setOnClickListener(v->dismiss());

        return addRoasterView;
    }
}