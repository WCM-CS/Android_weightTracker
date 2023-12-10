package com.zybooks.weightlogv2.controller.MainControllers;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zybooks.weightlogv2.R;
import com.zybooks.weightlogv2.model.MainModels.GoalWeightDataSource;
import com.zybooks.weightlogv2.model.MainModels.GoalWeightModel;



public class ChangeGoalWeight extends Fragment {
    private EditText newGoalWeightEditText;
    private Button submitButton;
    private Button exitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_change_goal_weight, container, false);

        newGoalWeightEditText = rootView.findViewById(R.id.new_goal_weight);
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
        String newGoalWeightString = newGoalWeightEditText.getText().toString();

        if (!newGoalWeightString.isEmpty()) {
            double newGoalWeight = Double.parseDouble(newGoalWeightString);

            // Create a GoalWeightModel
            GoalWeightModel goalWeightModel = new GoalWeightModel(newGoalWeight);

            // Get an instance of GoalWeightDataSource
            GoalWeightDataSource dataSource = GoalWeightDataSource.getInstance(getContext());

            // Delete the previous goal weight from the database
            dataSource.deleteAllGoalWeights();

            // insert the new goal weight
            long insertedRowId = dataSource.insertGoalWeight(goalWeightModel);

            if (insertedRowId != -1) {
                // Insertion was successful
                ((MainActivity) getActivity()).updateGoalText("Goal Weight: " + newGoalWeight);
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();
            } else {
                // Insertion failed
            }
        } else {
            // Display an error message that the field is empty
            // Handle the empty field scenario
        }

    }


    public void onExitButtonClick(){
        // exit button click
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

}