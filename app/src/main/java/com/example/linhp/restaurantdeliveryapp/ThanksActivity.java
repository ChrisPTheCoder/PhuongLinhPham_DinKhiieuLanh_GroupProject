package com.example.linhp.restaurantdeliveryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

public class ThanksActivity extends ParentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        WebView wv = findViewById(R.id.wv);

        wv.loadUrl("http://images.birthdayexpress.com/mgen/itzza-pizza-party-thank-you-notes-bx-93897.jpg?zm=1600,1600,1,0,0");
        wv.setInitialScale(46);
    }
    public void back(View view){
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}
