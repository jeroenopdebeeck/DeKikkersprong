package com.kikkersprong.dekikkersprong;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import db.OfflineEntryWriter;
import domain.Child;

public class GreetActivity extends AppCompatActivity {


    private int id;
    private String firstname;
    private String lastname;
    private OfflineEntryWriter db;
    private Child child;
    final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greet);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                showMain();
            }
        }, 5000);

        db = OfflineEntryWriter.getInstance(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            firstname = extras.getString("firstname");
            lastname = extras.getString("lastname");
        }

        final TextView message = (TextView) findViewById(R.id.textViewGreeting);
        message.setText(firstname + " " + lastname);

        //add child to db
        child = db.addChild(id, firstname, lastname);

        //register visit
        child.scanCard();


        db.writeAllChildren();
        db.writeAllVisits();
        try {
            db.readAllVisits();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_greet, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void showDetail(View view) {
        handler.removeCallbacksAndMessages(null);

        Intent intent = new Intent(getApplicationContext(), detailActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("firstname",firstname);
        intent.putExtra("lastname",lastname);
        startActivity(intent);
    }

    public void showMain() {
        handler.removeCallbacksAndMessages(null);

        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
