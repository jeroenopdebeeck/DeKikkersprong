package com.kikkersprong.dekikkersprong;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.android.AndroidAuthSession;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxUnlinkedException;
import com.dropbox.client2.session.AccessTokenPair;
import com.dropbox.client2.session.AppKeyPair;
import com.dropbox.client2.session.Session;
import com.dropbox.client2.session.TokenPair;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import db.OfflineEntryWriter;
import domain.Child;

public class FacturationActivity extends AppCompatActivity {

    private OfflineEntryWriter db;

    private DropboxAPI<AndroidAuthSession> dropbox;
    private final static String FILE_DIR = "/DropboxSample/";
    private final static String DROPBOX_NAME = "DeKikkersprong";
    private final static String APP_KEY = "d2keznd0yda8ws5";
    private final static String APP_SECRET = "e7qpod9s10dydhz";
    private boolean isLoggedIn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facturation);

        db = OfflineEntryWriter.getInstance(getApplicationContext());


        ArrayList<Child> kids = db.getAllChildren();

        Spinner spinner = (Spinner) findViewById(R.id.kids_spinner);
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, kids);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);




        AppKeyPair appKeys = new AppKeyPair(APP_KEY, APP_SECRET);
        AndroidAuthSession session = new AndroidAuthSession(appKeys);
        dropbox = new DropboxAPI<AndroidAuthSession>(session);
        dropbox.getSession().startOAuth2Authentication(FacturationActivity.this);

    }

    protected void onResume() {
        super.onResume();

        if (dropbox.getSession().authenticationSuccessful()) {
            try {
                // Required to complete auth, sets the access token on the session
                dropbox.getSession().finishAuthentication();

                String accessToken = dropbox.getSession().getOAuth2AccessToken();
            } catch (IllegalStateException e) {
                Log.i("DbAuthLog", "Error authenticating", e);
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_facturation, menu);
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

    public void showList(View view) {


        Spinner mySpinner=(Spinner) findViewById(R.id.kids_spinner);
        Child child = (Child) mySpinner.getSelectedItem();
        HashMap<Integer,Double> factuurPerMonth = child.generateFacturationPerMonth();
        ArrayList<String> rekeningenList = new ArrayList<String>();

        for (HashMap.Entry<Integer,Double> entry : factuurPerMonth.entrySet())
        {
            String factuur = entry.getKey().toString() + " " + entry.getValue().toString();
            rekeningenList.add(factuur);
        }
        String[] rekeningen = rekeningenList.toArray(new String[rekeningenList.size()]);

        ListView listview = (ListView) findViewById(R.id.detailViewFactuur);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, rekeningen);
        listview.setAdapter(adapter);

        adapter.notifyDataSetChanged();


    }

    public void writeFactuur(View view) throws DropboxException, IOException {

        Spinner mySpinner=(Spinner) findViewById(R.id.kids_spinner);
        Child child = (Child) mySpinner.getSelectedItem();

        String factuur = child.generateTotalFacturation();

        UploadFileToDropbox upload = new UploadFileToDropbox(getApplicationContext(),dropbox,factuur);
        upload.execute();


    }
}
