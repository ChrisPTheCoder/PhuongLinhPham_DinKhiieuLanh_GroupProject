package com.example.linhp.restaurantdeliveryapp;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;

import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class SMSActivity extends ParentActivity {
    private static final int SMS_PERMISSION_CODE = 0;
    String SENT = "SMS_SENT";
    String DELIVERED = "SMS_DELIVERED";
    PendingIntent sentPI, deliveredPI;
    //
    BroadcastReceiver smsSentReceiver, smsDeliveredReceiver;
    //
    IntentFilter intentFilter;

    private EditText editText1;

    // receive intents sent by sendBroadcast()
    private BroadcastReceiver intentReceiver = new BroadcastReceiver() {
        //override the onReceive to receive messages
        @Override
        public void onReceive(Context context, Intent intent) {
            //ï¿½-display the SMS received in the TextViewï¿½-
            //TextView SMSes = (TextView) findViewById(R.id.textView1);
            //display the content of the received message in text view
            //SMSes.setText(intent.getExtras().getString("sms"));
        }
    };

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);

        if(!isStatePermissionGranted()) {
            requestStatePermission();
        }

        editText1 = (EditText)findViewById(R.id.editText1);
        //an action to take in the future with same permission
        //as your application
        sentPI = PendingIntent.getBroadcast(this, 0,
                new Intent(SENT), 0);

        deliveredPI = PendingIntent.getBroadcast(this, 0,
                new Intent(DELIVERED), 0);

        //ï¿½-intent to filter the action for SMS messages receivedï¿½-
        intentFilter = new IntentFilter();
        intentFilter.addAction("SMS_RECEIVED_ACTION");

        //---register the receiver---
        registerReceiver(intentReceiver, intentFilter);
    }

    @Override
    public void onResume() {
        super.onResume();

        //---register the receiver---
        //registerReceiver(intentReceiver, intentFilter);

        //---create the BroadcastReceiver when the SMS is sent---
        smsSentReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode()) //Retrieve the current result code, as set by the previous receiver
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS sent",
                                Toast.LENGTH_LONG).show();
                        break;
                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                        Toast.makeText(getBaseContext(), "Generic failure",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NO_SERVICE:
                        Toast.makeText(getBaseContext(), "No service",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_NULL_PDU:
                        Toast.makeText(getBaseContext(), "Null PDU",
                                Toast.LENGTH_SHORT).show();
                        break;
                    case SmsManager.RESULT_ERROR_RADIO_OFF:
                        Toast.makeText(getBaseContext(), "Radio off",
                                Toast.LENGTH_SHORT).show();
                        break;
                }
            }
        };

        //---create the BroadcastReceiver when the SMS is delivered---
        smsDeliveredReceiver = new BroadcastReceiver(){
            @Override
            public void onReceive(Context arg0, Intent arg1) {
                switch (getResultCode())
                {
                    case Activity.RESULT_OK:
                        Toast.makeText(getBaseContext(), "SMS delivered",
                                Toast.LENGTH_LONG).show();
                        break;
                    case Activity.RESULT_CANCELED:
                        Toast.makeText(getBaseContext(), "SMS not delivered",
                                Toast.LENGTH_LONG).show();
                        break;
                }
            }
        };

        //---register the two BroadcastReceivers---
        registerReceiver(smsDeliveredReceiver, new IntentFilter(DELIVERED));
        registerReceiver(smsSentReceiver, new IntentFilter(SENT));
    }

    @Override
    public void onPause() {
        super.onPause();

        //---unregister the receiver---
        //unregisterReceiver(intentReceiver);

        //---unregister the two BroadcastReceivers---
        unregisterReceiver(smsSentReceiver);
        unregisterReceiver(smsDeliveredReceiver);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //---unregister the receiver---
        unregisterReceiver(intentReceiver);
    }

    public void onClick(View v) {
        sendSMS("5556", editText1.getText().toString());
    }

    //ï¿½-sends an SMS message to another deviceï¿½-
    private void sendSMS(String phoneNumber, String message)
    {
        if(!isSmsPermissionGranted()) {
            requestReadAndSendSmsPermission();
        }
        SmsManager sms = SmsManager.getDefault();
        sms.sendTextMessage(phoneNumber, null, message, sentPI, deliveredPI);
    }

    public boolean isSmsPermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request runtime SMS permission
     */
    private void requestReadAndSendSmsPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SMSActivity.this, Manifest.permission.SEND_SMS)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        ActivityCompat.requestPermissions(SMSActivity.this, new String[]{Manifest.permission.SEND_SMS}, SMS_PERMISSION_CODE);
    }

    public boolean isStatePermissionGranted() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Request runtime SMS permission
     */
    private void requestStatePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(SMSActivity.this, Manifest.permission.READ_PHONE_STATE)) {
            // You may display a non-blocking explanation here, read more in the documentation:
            // https://developer.android.com/training/permissions/requesting.html
        }
        ActivityCompat.requestPermissions(SMSActivity.this, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
    }
}