package com.zybooks.weightlogv2.controller.LoginControllers;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.zybooks.weightlogv2.R;
import com.zybooks.weightlogv2.model.LoginModels.LoginInfoDataSource;
import com.zybooks.weightlogv2.model.LoginModels.LoginInfoModel;

public class CreateNewAccount extends Fragment {
    private EditText usernameEditText;
    private EditText passwordEditText;
    private Button submitButton;
    private Button exitButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_create_new_account, container, false);

        usernameEditText = rootView.findViewById(R.id.username_edittext);
        passwordEditText = rootView.findViewById(R.id.password_edittext);
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
        //return root since it's a fragment not a layout
        return rootView;
    }

    private void onSubmitButtonClick() {
        String username = usernameEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        // Create a LoginInfoModel instance with the entered username and password
        LoginInfoModel newUser = new LoginInfoModel(username, password);

        // Get an instance of LoginInfoDataSource using the context
        LoginInfoDataSource dataSource = LoginInfoDataSource.getInstance(getContext());

        // Insert the new user data into the database
        long insertedRowId = dataSource.insertUser(newUser);

        if (insertedRowId != -1) {
            // Insertion was successful
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.popBackStack();
        } else {
            // Insertion failed
        }
    }

    private void onExitButtonClick() {
        // exit button click
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }
}