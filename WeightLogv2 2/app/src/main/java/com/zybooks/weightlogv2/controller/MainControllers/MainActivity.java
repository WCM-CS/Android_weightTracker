package com.zybooks.weightlogv2.controller.MainControllers;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.zybooks.weightlogv2.Menu.fragment_settings;
import com.zybooks.weightlogv2.R;
import com.zybooks.weightlogv2.model.MainModels.DailyWeightsDataSource;
import com.zybooks.weightlogv2.model.MainModels.DailyWeightsModel;
import com.zybooks.weightlogv2.model.MainModels.GoalWeightDataSource;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private Button dailyButton;
    private Button goalButton;
    private TextView goalText;
    private GoalWeightDataSource dataSource;
    private Toolbar toolbar; // Declare a Toolbar variable

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dailyButton = findViewById(R.id.daily_button);
        goalText = findViewById(R.id.goal_text);
        goalButton = findViewById(R.id.goal_button);

        setSupportActionBar(findViewById(R.id.tool_bar));
        getSupportActionBar().setTitle("Weight Journal");

        dataSource = GoalWeightDataSource.getInstance(this);
        double currentGoalWeight = dataSource.getCurrentGoalWeight();

        updateGoalText("Goal Weight: " + currentGoalWeight);

        populateDataTable();

        dailyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onAddDailyWeightButtonClick();
            }
        });

        goalButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onChangeGoalButtonClick();
            }
        });
    }


    private void populateDataTable() {
        DailyWeightsDataSource dataSource = DailyWeightsDataSource.getInstance(this);
        List<DailyWeightsModel> dailyWeightsList = dataSource.getAllDailyWeights();

        TableLayout tableLayout = findViewById(R.id.tableLayout);
        tableLayout.removeAllViews();

        // Add column headers
        TableRow headerRow = new TableRow(this);
        TextView headerDateTextView = new TextView(this);
        TextView headerWeightTextView = new TextView(this);

        headerDateTextView.setText("Date");
        headerWeightTextView.setText("Weight");

        headerDateTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));
        headerWeightTextView.setLayoutParams(new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1));

        headerDateTextView.setBackgroundColor(getResources().getColor(R.color.black)); // Set heaeder background color
        headerWeightTextView.setBackgroundColor(getResources().getColor(R.color.black)); // Set header background color

        headerDateTextView.setGravity(Gravity.CENTER); // Center align header text
        headerWeightTextView.setGravity(Gravity.CENTER); // Center align header text

        headerDateTextView.setTypeface(null, Typeface.BOLD); // Make header text bold
        headerWeightTextView.setTypeface(null, Typeface.BOLD); // Make header tex bold


        headerDateTextView.setTextColor(getResources().getColor(R.color.gamma)); // Set header text color
        headerWeightTextView.setTextColor(getResources().getColor(R.color.gamma)); // Set header text color

        headerRow.addView(headerDateTextView);
        headerRow.addView(headerWeightTextView);

        tableLayout.addView(headerRow);

        for (DailyWeightsModel dailyWeight : dailyWeightsList) {
            TableRow tableRow = new TableRow(this);
            TextView dateTextView = new TextView(this);
            TextView weightTextView = new TextView(this);

            // Set layout parameters for dateTextView
            TableRow.LayoutParams dateLayoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
            dateTextView.setLayoutParams(dateLayoutParams);

            // Set layout parameters for weightTextView
            TableRow.LayoutParams weightLayoutParams = new TableRow.LayoutParams(0, TableRow.LayoutParams.WRAP_CONTENT, 1);
            weightTextView.setLayoutParams(weightLayoutParams);

            dateTextView.setText(formatDate(dailyWeight.getDate())); // Implement formatDate as needed
            weightTextView.setText(String.valueOf(dailyWeight.getWeight()));

            tableRow.addView(dateTextView);
            tableRow.addView(weightTextView);

            tableLayout.addView(tableRow);
        }
    }

    public void updateDataTable(){
        populateDataTable();
    }

    private String formatDate(String dateString) {
        SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("MMM dd, yyyy");

        try {
            Date date = inputFormat.parse(dateString);
            return outputFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateString; // Return original string if parsing fails
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Fragment settingsFragment = new fragment_settings();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.fragment_container, settingsFragment);
            transaction.addToBackStack(null);
            transaction.commit();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void onAddDailyWeightButtonClick() {
        AddDailyWeight addDailyWeightFragment = new AddDailyWeight();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, addDailyWeightFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    public void updateGoalText(String text) {
        goalText.setText(text);
    }

    public void onChangeGoalButtonClick() {
        ChangeGoalWeight changeGoalWeightFragment = new ChangeGoalWeight();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        fragmentTransaction.replace(R.id.fragment_container, changeGoalWeightFragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }


}