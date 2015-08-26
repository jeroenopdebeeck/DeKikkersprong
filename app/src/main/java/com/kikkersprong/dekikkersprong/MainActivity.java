package com.kikkersprong.dekikkersprong;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Scanner;

import db.OfflineEntryWriter;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {


    private Button scanBtn;
    private TextView contentTxt;
    private OfflineEntryWriter db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = OfflineEntryWriter.getInstance(getApplicationContext());

        scanBtn = (Button)findViewById(R.id.scan_button);
        contentTxt = (TextView)findViewById(R.id.scan_content);

        scanBtn.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.scan_button){
            IntentIntegrator scanIntegrator;
            scanIntegrator = new IntentIntegrator(this);
            scanIntegrator.initiateScan();
        }
    }



    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        IntentResult scanningResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, intent);

        if (scanningResult != null) {
            String scanContent = scanningResult.getContents();


            contentTxt.setText("CONTENT: " + scanContent);

            Scanner scanner = new Scanner(scanContent);

            int id = Integer.parseInt(scanner.nextLine());
            String firstname = scanner.nextLine();
            String lastname = scanner.nextLine();
            scanner.close();

            if(id == -1) {
                openFacturation();
            } else {
                openGreet(id,firstname,lastname);
            }
        }

        else{
            Toast toast = Toast.makeText(getApplicationContext(),
                    "No scan data received!", Toast.LENGTH_SHORT);
            toast.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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



    public void openFacturation() {

        Intent intent = new Intent(this, FacturationActivity.class);
        startActivity(intent);
    }

    public void openGreet(int id, String firstname, String lastname) {

        Intent intent = new Intent(getApplicationContext(), GreetActivity.class);
        intent.putExtra("id", id);
        intent.putExtra("firstname",firstname);
        intent.putExtra("lastname",lastname);
        startActivity(intent);

    }

}
