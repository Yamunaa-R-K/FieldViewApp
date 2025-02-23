package com.example.fieldviewapp.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fieldviewapp.R;
import com.example.fieldviewapp.database.DatabaseHelper;

public class ReportActivity extends AppCompatActivity {

    private EditText edtReportName, edtReportDate, edtReportDetails;
    private Button btnSaveReport;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);

        edtReportName = findViewById(R.id.edtReportName);
        edtReportDate = findViewById(R.id.edtReportDate);
        edtReportDetails = findViewById(R.id.edtReportDetails);
        btnSaveReport = findViewById(R.id.btnSaveReport);

        databaseHelper = new DatabaseHelper();

        btnSaveReport.setOnClickListener(v -> saveReport());
    }

    private void saveReport() {
        String name = edtReportName.getText().toString().trim();
        String date = edtReportDate.getText().toString().trim();
        String details = edtReportDetails.getText().toString().trim();

        if (name.isEmpty() || date.isEmpty() || details.isEmpty()) {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean success = databaseHelper.addReport(name, date, details);
        if (success) {
            Toast.makeText(this, "Report Saved", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Failed to save", Toast.LENGTH_SHORT).show();
        }
    }
}
