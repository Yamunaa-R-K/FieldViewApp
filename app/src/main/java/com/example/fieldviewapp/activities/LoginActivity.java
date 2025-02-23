package com.example.fieldviewapp.activities;

import android.content.Intent;
import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fieldviewapp.R;
import com.example.fieldviewapp.database.DatabaseHelper;
import com.example.fieldviewapp.utils.FingerprintAuthHelper;

public class LoginActivity extends AppCompatActivity {
    private EditText edtEmail, edtPassword;
    private Button btnLogin, btnGoToRegister;
    private ProgressBar progressBar;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        databaseHelper = new DatabaseHelper(this);

        edtEmail = findViewById(R.id.edtEmail);
        edtPassword = findViewById(R.id.edtPassword);
        btnLogin = findViewById(R.id.btnLogin);
        btnGoToRegister = findViewById(R.id.btnGoToRegister);
        progressBar = findViewById(R.id.progressBar);

        btnLogin.setOnClickListener(v -> loginUser());
        btnGoToRegister.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            finish();
        });

        setupFingerprintAuthentication();
    }

    private void loginUser() {
        String email = edtEmail.getText().toString().trim();
        String password = edtPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "Please enter both email and password", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean isValid = databaseHelper.checkUser(email, password);

        if (isValid) {
            Toast.makeText(this, "Login Successful!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Invalid Email or Password", Toast.LENGTH_SHORT).show();
        }
    }

    private void setupFingerprintAuthentication() {
        FingerprintManager fingerprintManager = (FingerprintManager) getSystemService(FINGERPRINT_SERVICE);

        if (fingerprintManager != null && fingerprintManager.isHardwareDetected() && fingerprintManager.hasEnrolledFingerprints()) {
            FingerprintAuthHelper fingerprintAuthHelper = new FingerprintAuthHelper(this, new FingerprintAuthHelper.AuthenticationCallback() {
                @Override
                public void onAuthenticationSuccess() {
                    Toast.makeText(LoginActivity.this, "Fingerprint Login Success!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
                    finish();
                }

                @Override
                public void onAuthenticationFailed() {
                    Toast.makeText(LoginActivity.this, "Try Again!", Toast.LENGTH_SHORT).show();
                }
            });

            FingerprintManager.CryptoObject cryptoObject = null;
            fingerprintAuthHelper.startAuthentication(fingerprintManager, cryptoObject);
        }
    }
}
