package com.example.linhp.restaurantdeliveryapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import static com.example.linhp.restaurantdeliveryapp.Constants.TABLE_NAME;

public class CheckOutActivity extends ParentActivity {
    private PizzaData data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);
    }
    public void checkOut(View view) {
        // new AlertDialog.Builder(this).setTitle(R.string.checkout_title).setMessage(R.string.checkout_text).setCancelable(false)
        // .setNeutralButton("OK", new DialogInterface.OnClickListener() {

        // @Override
        //  public void onClick(DialogInterface dialog, int which) {
        //  data.getWritableDatabase().delete(TABLE_NAME, null, null);
        //  finish();
        //}
        //}).show();
        Intent i = new Intent(this, ThanksActivity.class);
        startActivity(i);
    }
}


