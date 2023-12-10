package com.zybooks.weightlogv2.Menu;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.google.android.gms.auth.api.phone.SmsRetriever;
import com.google.android.gms.auth.api.phone.SmsRetrieverClient;
import com.google.android.gms.tasks.Task;

import com.zybooks.weightlogv2.R;

public class fragment_settings extends Fragment {
    private static final String PREFS_NAME = "settings_prefs";
    private static final String SMS_NOTIFICATIONS_KEY = "sms_notifications_enabled";
    public boolean areSmsNotificationsEnabled = false;
    private Switch switchSmsNotification;
    private boolean isFirstLoad = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_settings, container, false);

        Switch switchSmsNotification = rootView.findViewById(R.id.switch_sms);

        if (!isFirstLoad) {
            loadSmsNotificationsState();
            switchSmsNotification.setChecked(areSmsNotificationsEnabled);
        } else {
            loadSmsNotificationsState();
            switchSmsNotification.setChecked(areSmsNotificationsEnabled);
            isFirstLoad = false; // Set to false after the initial load
        }


        Button buttonDone = rootView.findViewById(R.id.button_done);
        buttonDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClick();
            }
        });

        switchSmsNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                areSmsNotificationsEnabled = isChecked;
                if(areSmsNotificationsEnabled){
                    enableSmsNotifications();
                }else{
                    //Disable Notifications
                }
            }
        });

        return rootView;
    }

    private void enableSmsNotifications() {
        SmsRetrieverClient client = SmsRetriever.getClient(requireContext());
        Task<Void> task = client.startSmsUserConsent(null);

        task.addOnCompleteListener(result -> {
            if (result.isSuccessful()) {

            } else {

            }
        });
    }

    private void onSaveButtonClick() {
        saveSmsNotificationsState();
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        fragmentManager.popBackStack();
    }

    private void loadSmsNotificationsState() {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, 0);
        areSmsNotificationsEnabled = prefs.getBoolean(SMS_NOTIFICATIONS_KEY, false);
    }

    private void saveSmsNotificationsState() {
        SharedPreferences prefs = requireContext().getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(SMS_NOTIFICATIONS_KEY, areSmsNotificationsEnabled);
        editor.apply();
    }
}