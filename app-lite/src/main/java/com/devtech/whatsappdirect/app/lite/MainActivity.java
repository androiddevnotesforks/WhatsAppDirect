package com.devtech.whatsappdirect.app.lite;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.devtech.whatsappdirect.core.WhatsAppDirectLauncher;

public class MainActivity extends Activity {

    private static final String KEY_PHONE_TEXT = "key_phone_text";
    private static final String KEY_MSG_TEXT = "key_msg_text";

    private EditText phoneNumberEditText;
    private EditText messageEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        phoneNumberEditText = findViewById(R.id.phoneNumberEditText);
        messageEditText = findViewById(R.id.messageEditText);
        Button launchWhatsAppButton = findViewById(R.id.launchWhatsAppButton);


        String number = WhatsAppDirectLauncher.parsePhoneNumber(getIntent());
        phoneNumberEditText.setText(number);

        launchWhatsAppButton.setOnClickListener(v -> WhatsAppDirectLauncher.launch(
                this,
                phoneNumberEditText.getText().toString(),
                messageEditText.getText().toString()
        ));
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        String phoneTxt = phoneNumberEditText.getText().toString().trim();
        String messageText = messageEditText.getText().toString().trim();
        outState.putString(KEY_PHONE_TEXT, phoneTxt);
        outState.putString(KEY_MSG_TEXT, messageText);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        phoneNumberEditText.setText(savedInstanceState.getString(KEY_PHONE_TEXT));
        messageEditText.setText(savedInstanceState.getString(KEY_MSG_TEXT));
    }
}
