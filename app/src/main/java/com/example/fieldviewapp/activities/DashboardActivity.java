package com.example.fieldviewapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fieldviewapp.R;
import com.example.fieldviewapp.database.DatabaseHelper;
import com.example.fieldviewapp.models.User;
import com.example.fieldviewapp.utils.SharedPrefManager;

public class DashboardActivity extends AppCompatActivity {

    private TextView txtWelcome;
    private Button btnInspection, btnReports, btnDigitalSign, btnProfile, btnLogout;
    private DatabaseHelper databaseHelper;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        txtWelcome = findViewById(R.id.txtWelcome);
        btnInspection = findViewById(R.id.btnInspection);
        btnReports = findViewById(R.id.btnReports);
        btnDigitalSign = findViewById(R.id.btnDigitalSign);
        btnProfile = findViewById(R.id.btnProfile);
        btnLogout = findViewById(R.id.btnLogout);

        databaseHelper = new DatabaseHelper(this);
        sharedPrefManager = new SharedPrefManager(this);


        loadUserData();

        // Button click listeners
        btnInspection.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, InspectionActivity.class)));
        btnReports.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, ReportActivity.class)));
        btnDigitalSign.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, DigitalSignActivity.class)));
        btnProfile.setOnClickListener(v -> startActivity(new Intent(DashboardActivity.this, ProfileActivity.class)));

        btnLogout.setOnClickListener(v -> logoutUser());
    }

    private void loadUserData() {
        String email = sharedPrefManager.getUserEmail();
        User user = databaseHelper.getUserDetails(email);

        if (user != null) {
            txtWelcome.setText("Welcome, " + user.getName());
        } else {
            txtWelcome.setText("Welcome, User");
        }
    }

    private void logoutUser() {
        sharedPrefManager.logout();
        startActivity(new Intent(DashboardActivity.this, LoginActivity.class));
        finish();
    }
}
