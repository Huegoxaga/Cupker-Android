package com.cupker;
/**
 * Ye Qi, 000792058
 */
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Flavor;
import com.amplifyframework.datastore.generated.model.Roaster;
import com.amplifyframework.datastore.generated.model.Status;

/**
 * This is the add flavor fragment for the roaster drop down in create new cupping session page
 */
public class NewFlavorProfileFragment extends DialogFragment {

    // Init Keys
    private static final String TAG = "===New Roaster Frag===";

    // Init UI objects
    private View addFlavorView;
    private Button addButton;
    private Button cancelButton;

    public NewFlavorProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Init
        addFlavorView = inflater.inflate(R.layout.fragment_new_flavor, container, false);
        addButton = addFlavorView.findViewById(R.id.new_flavor_add_button);
        cancelButton = addFlavorView.findViewById(R.id.new_flavor_cancel_button);

        // Listeners
        addButton.setOnClickListener(v -> {
            EditText newRoasterInput = getView().findViewById(R.id.new_flavor_name_input);
            String newFlavorName = newRoasterInput.getText().toString();

            Flavor newFlavor = Flavor.builder()
                    .status(Status.ACTIVE)
                    .name(newFlavorName)
                    .type("Default")
                    .build();

            FlavorActivity flavorActivity = (FlavorActivity) getActivity();
            flavorActivity.updateList(newFlavor);

            Amplify.DataStore.save(newFlavor,
                    success -> {
                        Log.i(TAG, "Saved item: " + success.item().getName());
                        dismiss();
                    },
                    error -> Log.e(TAG, "Could not save item to DataStore", error)
            );
        });

        cancelButton.setOnClickListener(v -> dismiss());

        return addFlavorView;
    }
}