package com.zybooks.weightlogv2.controller.MainControllers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zybooks.weightlogv2.R;
import com.zybooks.weightlogv2.model.MainModels.DailyWeightsDataSource;
import com.zybooks.weightlogv2.model.MainModels.DailyWeightsModel;

public class AddDailyWeight extends Fragment {

    private EditText newDailyWeightEditText;
    private EditText newDateEditText;
    private Button submitButton;
    private Button exitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_add_daily_weight, container, false);

        newDailyWeightEditText = rootView.findViewById(R.id.new_daily_weight);
        newDateEditText = rootView.findViewById(R.id.new_date);
        submitButton = rootView.findViewById(R.id.submit_button);
        exitButton = rootView.findViewById(R.id.exit_button);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSubmitButtonClick();
            }
        });

        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onExitButtonClick();
            }
        });

        return rootView;
    }

    public void onSubmitButtonClick() {
        String newDailyWeight = newDailyWeightEditText.getText().toString();
        String newDate = newDateEditText.getText().toString();

        // Create a DailyWeightsModel instance with the entered data
        DailyWeightsModel newDailyWeightEntry = new DailyWeightsModel(newDailyWeight, newDate);

        // Get an instance of DailyWeightsDataSource using the context
        DailyWeightsDataSource dataSource = DailyWeightsDataSource.getInstance(getContext());

        // Insert the new daily weight data into the database
        long insertedRowId = dataSource.insertDailyWeight(newDailyWeightEntry);

        if (insertedRowId != -1) {
            // Insertion was successful
            ((MainActivity) requireActivity()).updateDataTable();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
        } else {
            // Insertion failed
        }
    }

    public void onExitButtonClick() {
        // exit button click
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}