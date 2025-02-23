package com.example.fieldviewapp.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.fieldviewapp.R;
import com.example.fieldviewapp.database.DatabaseHelper;
import com.example.fieldviewapp.models.User;
import com.example.fieldviewapp.utils.SharedPrefManager;

public class ProfileActivity extends AppCompatActivity {

    private TextView txtName, txtEmail, txtPhone;
    private DatabaseHelper databaseHelper;
    private SharedPrefManager sharedPrefManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        txtName = findViewById(R.id.txtName);
        txtEmail = findViewById(R.id.txtEmail);
        txtPhone = findViewById(R.id.txtPhone);

        databaseHelper = new DatabaseHelper();
        sharedPrefManager = new SharedPrefManager(this);

        loadUserData();
    }

    private void loadUserData() {
        String email = sharedPrefManager.getUserEmail();
        User user = databaseHelper.getUserDetails(email);

        if (user != null) {
            txtName.setText(user.getName());
            txtEmail.setText(user.getEmail());
            txtPhone.setText(user.getPhone());
        } else {
            txtName.setText("N/A");
            txtEmail.setText("N/A");
            txtPhone.setText("N/A");
        }
    }
}
