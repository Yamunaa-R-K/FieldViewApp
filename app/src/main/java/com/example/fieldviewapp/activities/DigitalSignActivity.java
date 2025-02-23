package com.example.fieldviewapp.activities;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.fieldviewapp.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DigitalSignActivity extends AppCompatActivity {

    private SignatureView signatureView;
    private Button btnClear, btnSave;
    private ImageView imgSavedSignature;
    private Bitmap signatureBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_digitalsign);

        signatureView = findViewById(R.id.signatureView);
        btnClear = findViewById(R.id.btnClear);
        btnSave = findViewById(R.id.btnSave);
        imgSavedSignature = findViewById(R.id.imgSavedSignature);

        btnClear.setOnClickListener(v -> signatureView.clearCanvas());
        btnSave.setOnClickListener(v -> saveSignature());
    }

    private void saveSignature() {
        signatureBitmap = signatureView.getSignatureBitmap();
        if (signatureBitmap == null) {
            Toast.makeText(this, "No signature detected!", Toast.LENGTH_SHORT).show();
            return;
        }

        File signatureFile = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "signature.png");
        try (FileOutputStream out = new FileOutputStream(signatureFile)) {
            signatureBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            Toast.makeText(this, "Signature saved!", Toast.LENGTH_SHORT).show();
            imgSavedSignature.setImageBitmap(signatureBitmap);
        } catch (IOException e) {
            Log.e("DigitalSignActivity", "Error saving signature", e);
        }
    }

    public static class SignatureView extends View {
        private Path path = new Path();
        private Paint paint = new Paint();

        public SignatureView(@NonNull android.content.Context context) {
            super(context);
            paint.setAntiAlias(true);
            paint.setColor(Color.BLACK);
            paint.setStyle(Paint.Style.STROKE);
            paint.setStrokeWidth(8f);
        }

        @Override
        protected void onDraw(Canvas canvas) {
            super.onDraw(canvas);
            canvas.drawPath(path, paint);
        }

        @Override
        public boolean onTouchEvent(MotionEvent event) {
            float x = event.getX();
            float y = event.getY();

            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    path.moveTo(x, y);
                    return true;
                case MotionEvent.ACTION_MOVE:
                    path.lineTo(x, y);
                    break;
                case MotionEvent.ACTION_UP:
                    break;
            }
            invalidate();
            return true;
        }

        public void clearCanvas() {
            path.reset();
            invalidate();
        }

        public Bitmap getSignatureBitmap() {
            if (path.isEmpty()) return null;
            Bitmap bitmap = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            draw(canvas);
            return bitmap;
        }
    }
}
