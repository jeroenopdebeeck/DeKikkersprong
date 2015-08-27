package com.kikkersprong.dekikkersprong;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

import db.OfflineEntryWriter;
import domain.Child;
import domain.Visit;

public class detailActivity extends AppCompatActivity {

    private int id;
    private String firstname;
    private String lastname;
    private OfflineEntryWriter db;
    private Child child;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        db = OfflineEntryWriter.getInstance(getApplicationContext());

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            firstname = extras.getString("firstname");
            lastname = extras.getString("lastname");
        }

        child = db.addChild(id, firstname, lastname);

        setTitle("Details voor " + child.getFirstName() + " " + child.getLastName());
        Visit[] details = child.getVisits().toArray(new Visit[child.getVisits().size()]);

        HashMap<Integer,Double> factuurPerMonth = child.generateFacturationPerMonth();
        ArrayList<String> rekeningenList = new ArrayList<String>();

        for (HashMap.Entry<Integer,Double> entry : factuurPerMonth.entrySet())
        {
           String factuur = entry.getKey().toString() + " " + entry.getValue().toString();
            rekeningenList.add(factuur);
        }
        String[] rekeningen = rekeningenList.toArray(new String[rekeningenList.size()]);


        final ListView listview = (ListView) findViewById(R.id.detailViewAanwezigheden);
        final ListView listview2 = (ListView) findViewById(R.id.detailViewRekeningen);
        final ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, details);
        final ArrayAdapter adapter2 = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, rekeningen);
        listview.setAdapter(adapter);
        listview2.setAdapter(adapter2);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
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

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
