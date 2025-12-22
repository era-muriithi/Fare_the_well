package com.blackgoose.fare_the_well;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.Adapters.ProgramAdapter;
import com.blackgoose.fare_the_well.Adapters.ProgramSetAdapter;
import com.blackgoose.fare_the_well.Models.ProgramModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class ProgramSetActivity extends AppCompatActivity {

    private EditText startTimeEditText, actionEditText;
    private Button addButton, previewButton;
    private RecyclerView recyclerView;

    private ArrayList<ProgramModel> programList = new ArrayList<>();
    private ProgramSetAdapter programAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_set);

        startTimeEditText = findViewById(R.id.StarttimeEditText);
        actionEditText = findViewById(R.id.actionEditText);
        addButton = findViewById(R.id.addButton);
        previewButton = findViewById(R.id.previewButton);
        recyclerView = findViewById(R.id.recyclerView);

        // RecyclerView setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        programAdapter = new ProgramSetAdapter(programList);
        recyclerView.setAdapter(programAdapter);

        // Time picker for start time only
        startTimeEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(
                    ProgramSetActivity.this,
                    (view, hourOfDay, minute1) -> {
                        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                        startTimeEditText.setText(formattedTime);
                    },
                    hour,
                    minute,
                    false
            );
            timePickerDialog.show();
        });

        // Add button logic
        addButton.setOnClickListener(v -> {

            String startTime = startTimeEditText.getText().toString().trim();
            String action = actionEditText.getText().toString().trim();

            if (!startTime.isEmpty() && !action.isEmpty()) {

                programList.add(new ProgramModel(startTime, action));
                programAdapter.notifyItemInserted(programList.size() - 1);

                // Clear inputs
                startTimeEditText.setText("");
                actionEditText.setText("");

            } else {
                Toast.makeText(this, "Please enter both time and action", Toast.LENGTH_SHORT).show();
            }
        });

        previewButton.setOnClickListener(v -> gotoPreviewActivity());
    }

    private void gotoPreviewActivity() {
        Intent intent = new Intent(this, PreviewActivity.class);

        // Carry forward previous activity data
        intent.putExtras(getIntent());

        // Pass the program list
        intent.putExtra("programs", programList);

        startActivity(intent);
    }
}
