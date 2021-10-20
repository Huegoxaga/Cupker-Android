package com.cupker;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.DialogFragment;

import com.amplifyframework.core.Amplify;
import com.amplifyframework.datastore.generated.model.Dealer;
import com.amplifyframework.datastore.generated.model.Flavor;
import com.amplifyframework.datastore.generated.model.Roaster;
import com.amplifyframework.datastore.generated.model.Status;

/**
 * This is the add roaster fragment for the roaster drop down in create new cupping session page
 */
public class NewDealerProfileFragment extends DialogFragment {

    // Init Keys
    private static final String TAG = "===New Roaster Frag===";

    // Init UI objects
    private View addRoasterView;
    private Button addButton;
    private Button cancelButton;

    public NewDealerProfileFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Init
        addRoasterView = inflater.inflate(R.layout.fragment_new_roaster, container, false);
        addButton = addRoasterView.findViewById(R.id.new_roaster_add_button);
        cancelButton = addRoasterView.findViewById(R.id.new_roaster_cancel_button);

        // Listeners
        addButton.setOnClickListener(v -> {
            EditText newRoasterInput = getView().findViewById(R.id.new_roaster_name_input);
            String newDealerName = newRoasterInput.getText().toString();
            String newDealerEmail = "example@gamil.com";

            Dealer newDealer = Dealer.builder()
                    .name(newDealerName)
                    .email(newDealerEmail)
                    .status(Status.ACTIVE)
                    .build();

            DealerActivity dealerActivity = (DealerActivity) getActivity();
            dealerActivity.updateList(newDealer);

            Amplify.DataStore.save(newDealer,
                    success -> {
                        Log.i(TAG, "Saved item: " + success.item().getName());
                        dismiss();
                    },
                    error -> Log.e(TAG, "Could not save item to DataStore", error)
            );
        });

        cancelButton.setOnClickListener(v -> dismiss());

        return addRoasterView;
    }
}