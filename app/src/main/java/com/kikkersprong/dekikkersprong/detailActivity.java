package com.kikkersprong.dekikkersprong;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class detailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        setTitle("Details voor Lotje");
        String[] details = {"Maandag 7 april 5u", "Dinsdag 8 april 6.5 u"};
        String[] rekeningen = {"April 250€", "Maart 350€","Januari 150€",};

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
}
