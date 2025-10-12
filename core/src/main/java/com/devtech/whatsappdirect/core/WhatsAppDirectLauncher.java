package com.devtech.whatsappdirect.core;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import java.util.Objects;

public final class WhatsAppDirectLauncher {

    private static final String TAG = "WhatsAppDirectLauncher";

    public static String parsePhoneNumber(Intent intent) {
        if (Objects.equals(intent.getAction(), Intent.ACTION_DIAL)) {
            String data = intent.getDataString();
            if (data == null) return null;
            return data.substring(4);
        } else if (Objects.equals(intent.getAction(), Intent.ACTION_PROCESS_TEXT)) {
            return intent.getStringExtra(Intent.EXTRA_PROCESS_TEXT);
        }
        return null;
    }

    public static void launch(Context context, String phoneNumber, String message) {
        try {
            String processedNumber = getProcessedNumber(phoneNumber);
            if (processedNumber == null || processedNumber.isEmpty()) {
                toast(context, "Please enter a valid phone number");
                return;
            }
            String processedMessage = message.trim();
            log("launch: processedNumber: " + processedNumber);
            log("launch: processedMessage: " + processedMessage);

            Uri launchUri = getLaunchUri(processedNumber, processedMessage);
            log("launch: launchUri: " + launchUri);

            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(launchUri);

            context.startActivity(intent);
        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.e(TAG, "launch: " + e.getMessage(), e);
            }
            toast(context, "WhatsApp is not installed!!");
        }
    }

    private static Uri getLaunchUri(String phoneNumber, String message) {
        Uri.Builder uriBuilder = new Uri.Builder()
                .scheme("https")
                .authority("wa.me")
                .path(phoneNumber);
        if (message != null && !message.isEmpty()) {
            uriBuilder.appendQueryParameter("text", message);
        }
        return uriBuilder.build();
    }

    private static void toast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    private static String getProcessedNumber(String phoneNumber) {
        if (phoneNumber == null) return null;
        return phoneNumber.replaceAll("[^0-9]", "");
    }

    private static void log(String message) {
        if (BuildConfig.DEBUG) {
            Log.d(TAG, message);
        }
    }
}
