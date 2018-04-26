package com.example.linhp.restaurantdeliveryapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

public class ParentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent);
    }
    //menu
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    //menu item select event
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent i = new Intent(ParentActivity.this, SMSActivity.class);
        switch (item.getItemId()) {
            case R.id.contact:
                startActivity(i);
                return true;
            case R.id.musicOn:
                startService(new Intent(this, MusicService.class));
                return true;
            case R.id.musicOff:
                stopService(new Intent(this, MusicService.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
