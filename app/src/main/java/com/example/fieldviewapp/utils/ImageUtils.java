package com.example.fieldviewapp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ImageUtils {

    // Compress an image to reduce size
    public static File compressImage(Context context, Uri imageUri, int quality) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            bitmap = rotateImageIfRequired(context, imageUri, bitmap);

            File compressedFile = new File(context.getCacheDir(), "compressed.jpg");
            FileOutputStream fos = new FileOutputStream(compressedFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, quality, fos);
            fos.close();

            return compressedFile;
        } catch (Exception e) {
            Log.e("ImageUtils", "Compression Error: " + e.getMessage());
            return null;
        }
    }

    // Resize image to a specific width & height
    public static Bitmap resizeImage(Bitmap bitmap, int newWidth, int newHeight) {
        return Bitmap.createScaledBitmap(bitmap, newWidth, newHeight, true);
    }

    // Rotate image based on EXIF data
    public static Bitmap rotateImageIfRequired(Context context, Uri imageUri, Bitmap bitmap) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            ExifInterface exif = new ExifInterface(inputStream);

            int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);
            int rotationAngle = 0;

            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    rotationAngle = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    rotationAngle = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    rotationAngle = 270;
                    break;
            }

            if (rotationAngle != 0) {
                Matrix matrix = new Matrix();
                matrix.postRotate(rotationAngle);
                return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            } else {
                return bitmap;
            }
        } catch (IOException e) {
            Log.e("ImageUtils", "Rotation Error: " + e.getMessage());
            return bitmap;
        }
    }

    // Extract Latitude & Longitude from image metadata
    public static double[] getGeoTagging(Context context, Uri imageUri) {
        try {
            InputStream inputStream = context.getContentResolver().openInputStream(imageUri);
            ExifInterface exif = new ExifInterface(inputStream);

            float[] latLong = new float[2];
            if (exif.getLatLong(latLong)) {
                return new double[]{latLong[0], latLong[1]};
            }
        } catch (IOException e) {
            Log.e("ImageUtils", "GeoTagging Error: " + e.getMessage());
        }
        return null;
    }
}
