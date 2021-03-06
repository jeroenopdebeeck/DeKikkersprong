package com.kikkersprong.dekikkersprong;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import db.OfflineEntryWriter;
import domain.Child;
import domain.Visit;

public class GreetActivity extends AppCompatActivity {


    private static final int TIMEOUT_MILLISEC = 10000 ;
    private int id;
    private String firstname;
    private String lastname;
    private OfflineEntryWriter db;
    private Child child;
    final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_greet);


        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                try {
                    showMain();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
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


        //add child to db
        child = db.addChild(id, firstname, lastname);
        if(!child.getPresent()) {
            message.setText(getString(R.string.greeting) + " " + firstname + " " + lastname + "!");
            ImageView img= (ImageView) findViewById(R.id.imageViewGreet);
            img.setImageResource(R.drawable.welcome);
        }else if(child.getPresent()) {
            message.setText(getString(R.string.goodbye) + " " + firstname + " " + lastname + "!");
            ImageView img= (ImageView) findViewById(R.id.imageViewGreet);
            img.setImageResource(R.drawable.bye);
        }
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

    public void showMain() throws IOException, JSONException {
        handler.removeCallbacksAndMessages(null);
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    flushData();
                    flushDataVisits();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void flushData() throws IOException, JSONException {


        HashMap<Integer,Child> map = db.getChildrenMap();

        for (HashMap.Entry<Integer, Child> entry : map.entrySet()) {
            int key = entry.getKey();
            Child value = entry.getValue();

            int id = key;
            String voornaam = value.getFirstName();
            String naam = value.getLastName();

            String url = "https://r0307913.webontwerp.khleuven.be/android/writeAllChildren.php?+json=" + URLEncoder.encode("{\"id\":" + "\"" +id+ "\"" + ",\"voornaam\":" + "\"" +voornaam+ "\"" + ",\"naam\":" +  "\"" +naam+ "\"" + "}");
            System.out.println(url);
            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(new HttpGet(url));
            StatusLine statusLine = response.getStatusLine();
            if(statusLine.getStatusCode() == HttpStatus.SC_OK){
                ByteArrayOutputStream out = new ByteArrayOutputStream();
                response.getEntity().writeTo(out);
                String responseString = out.toString();
                out.close();
                //..more logic
            } else{
                //Closes the connection.
                response.getEntity().getContent().close();
                throw new IOException(statusLine.getReasonPhrase());
            }

        }


    }

    private void flushDataVisits() throws IOException, JSONException {


        HashMap<Integer,Child> map = db.getChildrenMap();

        for(Map.Entry<Integer, Child> entry : map.entrySet()){
            ArrayList<Visit> visits = entry.getValue().getVisits();
            int id = entry.getKey();

            for(Visit visit : visits) {

            int day = visit.getDay();
            int month = visit.getMonth();
            String begin = visit.getBegin();
            String end = visit.getEnd();
            double hours = visit.getHours();
                if(!end.equals("")) {
                    String url = "https://r0307913.webontwerp.khleuven.be/android/writeAllVisits.php?+json=" + URLEncoder.encode("{\"id\":" + "\"" + id + "\"" + ",\"day\":" + "\"" + day + "\"" + ",\"month\":" + "\"" + month + "\"" + ",\"begin\":" + "\"" + begin + "\"" + ",\"end\":" + "\"" + end + "\"" + ",\"hours\":" + "\"" + hours + "\"" + "}");
                    System.out.println(url);
                    HttpClient httpclient = new DefaultHttpClient();
                    HttpResponse response = httpclient.execute(new HttpGet(url));

                    StatusLine statusLine = response.getStatusLine();

                    if (statusLine.getStatusCode() == HttpStatus.SC_OK) {
                        ByteArrayOutputStream out = new ByteArrayOutputStream();
                        response.getEntity().writeTo(out);
                        String responseString = out.toString();
                        out.close();
                        //..more logic
                    } else {
                        //Closes the connection.
                        response.getEntity().getContent().close();
                        throw new IOException(statusLine.getReasonPhrase());
                    }
                }


            }
        }


    }



}
