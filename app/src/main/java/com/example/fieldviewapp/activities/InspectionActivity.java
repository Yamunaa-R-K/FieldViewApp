package com.example.fieldviewapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fieldviewapp.R;
import com.example.fieldviewapp.database.DatabaseHelper;

public class InspectionActivity extends AppCompatActivity {

    private EditText edtInspectionName, edtInspectionDate, edtInspectionStatus;
    private Button btnSaveInspection;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inspection);

        edtInspectionName = findViewById(R.id.edtInspectionName);
        edtInspectionDate = findViewById(R.id.edtInspectionDate);
        edtInspectionStatus = findViewById(R.id.edtInspectionStatus);
        btnSaveInspection = findViewById(R.id.btnSaveInspection);

        databaseHelper = new DatabaseHelper();

        btnSaveInspection.setOnClickListener(v -> saveInspection());
    }

    private void saveInspection() {
        String name = edtInspectionName.getText().toString().trim();
        String date = edtInspectionDate.getText().toString().trim();
        String status = edtInspectionStatus.getText().toString().trim();

        if (name.isEmpty() || date.isEmpty() || status.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = databaseHelper.addInspection(name, date, status);
        if (success) {
            Toast.makeText(this, "Inspection Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show();
        }
    }
}
