package com.blackgoose.fare_the_well;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.blackgoose.fare_the_well.Adapters.ProgramAdapter;
import com.blackgoose.fare_the_well.Models.ProgramModel;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ProgramSetActivity extends AppCompatActivity {
    private EditText StarttimeEditText,CompletiontimeEditText, actionEditText;
    private Button addButton, previewButton;
    private RecyclerView recyclerView;

    private ArrayList<ProgramModel> programList = new ArrayList<>();
    private ProgramAdapter programAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_program_set);
        StarttimeEditText = findViewById(R.id.StarttimeEditText);
        CompletiontimeEditText = findViewById(R.id.CompletiontimeEditText);
        actionEditText = findViewById(R.id.actionEditText);
        addButton = findViewById(R.id.addButton);
        previewButton = findViewById(R.id.previewButton);
        recyclerView = findViewById(R.id.recyclerView);

        // RecyclerView setup
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        programAdapter = new ProgramAdapter(programList);
        recyclerView.setAdapter(programAdapter);

        // TimePicker logic
        StarttimeEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(ProgramSetActivity.this,
                    (view, hourOfDay, minute1) -> {
                        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                        StarttimeEditText.setText(formattedTime);
                    }, hour, minute, false);
            timePickerDialog.show();
        });

        // TimePicker logic
        CompletiontimeEditText.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(ProgramSetActivity.this,
                    (view, hourOfDay, minute1) -> {
                        String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute1);
                        CompletiontimeEditText.setText(formattedTime);
                    }, hour, minute, false);
            timePickerDialog.show();
        });

        // Add button logic
        addButton.setOnClickListener(v -> {
            String start_time = StarttimeEditText.getText().toString().trim();
            String completion_time = CompletiontimeEditText.getText().toString().trim();
            String action = actionEditText.getText().toString().trim();

            if (!start_time.isEmpty() &&!completion_time.isEmpty() && !action.isEmpty()) {
                programList.add(new ProgramModel(start_time, completion_time,action));
                programAdapter.notifyItemInserted(programList.size() - 1);

                // Clear inputs
                StarttimeEditText.setText("");
                CompletiontimeEditText.setText("");
                actionEditText.setText("");
            } else {
                Toast.makeText(this, "Please enter both time and action", Toast.LENGTH_SHORT).show();
            }
        });
        
        previewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                gotoPreviewActivity();
            }
        });
    }

    private void gotoPreviewActivity() {
        Intent intent = new Intent(this, PreviewActivity.class);
        intent.putExtras(getIntent()); // Pass previous data
        intent.putExtra("programs", programList);
        startActivity(intent);
    }

}