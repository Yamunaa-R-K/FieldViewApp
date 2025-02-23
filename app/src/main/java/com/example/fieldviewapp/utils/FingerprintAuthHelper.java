package com.example.fieldviewapp.utils;

import android.content.Context;
import android.hardware.fingerprint.FingerprintManager;
import android.os.CancellationSignal;
import android.widget.Toast;

public class FingerprintAuthHelper extends FingerprintManager.AuthenticationCallback {
    private Context context;
    private AuthenticationCallback callback;

    public interface AuthenticationCallback {
        void onAuthenticationSuccess();
        void onAuthenticationFailed();
    }

    public FingerprintAuthHelper(Context context, AuthenticationCallback callback) {
        this.context = context;
        this.callback = callback;
    }

    public void startAuthentication(FingerprintManager fingerprintManager, FingerprintManager.CryptoObject cryptoObject) {
        CancellationSignal cancellationSignal = new CancellationSignal();
        fingerprintManager.authenticate(cryptoObject, cancellationSignal, 0, this, null);
    }

    @Override
    public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
        Toast.makeText(context, "Fingerprint Auth Successful!", Toast.LENGTH_SHORT).show();
        callback.onAuthenticationSuccess();
    }

    @Override
    public void onAuthenticationFailed() {
        Toast.makeText(context, "Fingerprint Auth Failed!", Toast.LENGTH_SHORT).show();
        callback.onAuthenticationFailed();
    }
}
