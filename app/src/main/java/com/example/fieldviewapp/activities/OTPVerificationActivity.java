package com.example.fieldviewapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.fieldviewapp.R;

import java.util.Random;

public class OTPVerificationActivity extends AppCompatActivity {

    private EditText edtOTP;
    private Button btnVerifyOTP, btnResendOTP;
    private TextView txtTimer;
    private ProgressBar progressBar;

    private String generatedOTP;
    private String userPhoneNumber;
    private static final int OTP_VALIDITY_TIME = 60000; // 60 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otpverification);

        edtOTP = findViewById(R.id.edtOTP);
        btnVerifyOTP = findViewById(R.id.btnVerifyOTP);
        btnResendOTP = findViewById(R.id.btnResendOTP);
        txtTimer = findViewById(R.id.txtTimer);
        progressBar = findViewById(R.id.progressBar);

        // Get phone number from intent (Passed from Register/Login)
        userPhoneNumber = getIntent().getStringExtra("phone_number");

        generateAndSendOTP();

        btnVerifyOTP.setOnClickListener(v -> verifyOTP());

        btnResendOTP.setOnClickListener(v -> {
            generateAndSendOTP();
            btnResendOTP.setEnabled(false);
        });
    }

    private void generateAndSendOTP() {
        Random random = new Random();
        generatedOTP = String.format("%04d", random.nextInt(10000)); // Generate 4-digit OTP

        // Simulating OTP sending (In a real app, integrate SMS API)
        Toast.makeText(this, "OTP Sent: " + generatedOTP, Toast.LENGTH_LONG).show();

        // Start countdown timer
        startTimer();
    }

    private void verifyOTP() {
        String enteredOTP = edtOTP.getText().toString().trim();

        if (enteredOTP.isEmpty()) {
            Toast.makeText(this, "Please enter the OTP", Toast.LENGTH_SHORT).show();
            return;
        }

        if (enteredOTP.equals(generatedOTP)) {
            Toast.makeText(this, "OTP Verified Successfully!", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(OTPVerificationActivity.this, DashboardActivity.class));
            finish();
        } else {
            Toast.makeText(this, "Invalid OTP. Try again.", Toast.LENGTH_SHORT).show();
        }
    }

    private void startTimer() {
        new CountDownTimer(OTP_VALIDITY_TIME, 1000) {
            public void onTick(long millisUntilFinished) {
                txtTimer.setText("Resend OTP in " + millisUntilFinished / 1000 + " sec");
            }

            public void onFinish() {
                txtTimer.setText("Didn't receive OTP?");
                btnResendOTP.setEnabled(true);
            }
        }.start();
    }
}
