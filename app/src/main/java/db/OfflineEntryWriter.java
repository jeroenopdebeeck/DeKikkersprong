package db;


import android.content.Context;

import com.google.zxing.integration.android.IntentIntegrator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import domain.CheckIn;
import domain.CheckOut;
import domain.Child;
import domain.Overview;
import domain.Visit;

public class OfflineEntryWriter implements EntryWriter {


    private static OfflineEntryWriter instance = null;
    private FileOutputStream fos;
    private HashMap<Integer,Child> children;
    private String FILENAME1 = "children.txt";
    private String FILENAME2 = "visits.txt";
    private Context context;
    private File path;
    private File file1;
    private File file2;

    public OfflineEntryWriter(Context ctx){

        children = new HashMap<Integer,Child>();

        Child child1 = new Child(4,"Arno","Swinnen");
        Child child2 = new Child(3,"Kathleen","Bellen");
        Child child3 = new Child(2,"Gilles","Azaïs");


        children.put(4, child1);
        children.put(3, child2);
        children.put(2, child3);


        this.context = ctx;
        path = context.getFilesDir();
        file1 = new File(path, FILENAME1);
        file2 = new File(path, FILENAME2);


    }

    public static OfflineEntryWriter getInstance(Context context) {
        if(instance == null) {
            instance = new OfflineEntryWriter(context);
        }
        return instance;
    }


    @Override
    public void writeAllChildren() {

        try {
            fos = context.openFileOutput(FILENAME1, Context.MODE_PRIVATE);

            for(Map.Entry<Integer, Child> entry : children.entrySet()){
                fos.write(entry.getValue().toString().getBytes());
                fos.write(System.getProperty("line.separator").getBytes());
            }

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    public void readAllChildren() throws IOException {
        int length = (int) file1.length();

        byte[] bytes = new byte[length];

        FileInputStream in = new FileInputStream(file1);
        try {
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }

        String contents = new String(bytes);
        System.out.println(contents);
    }


    @Override
    public void writeAllVisits() {
        try {
            fos = context.openFileOutput(FILENAME2, Context.MODE_PRIVATE);

            for(Map.Entry<Integer, Child> entry : children.entrySet()){
                fos.write(entry.getValue().toString().getBytes());
                fos.write(System.getProperty("line.separator").getBytes());
                ArrayList<Visit> visits = entry.getValue().getVisits();

                for(Visit visit : visits){
                    fos.write(visit.toString().getBytes());
                    fos.write(System.getProperty("line.separator").getBytes());
                }
            }

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void readAllVisits() throws IOException {
        int length = (int) file2.length();

        byte[] bytes = new byte[length];

        FileInputStream in = new FileInputStream(file2);
        try {
            in.read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            in.close();
        }

        String contents = new String(bytes);
        System.out.println(contents);
    }




    public Child addChild(int id, String firstname, String lastname){

        //voeg kind toe indien eerste keer bezoek aan kikkersprong
        if(children.get(id) == null) {
            Child child = new Child(id,firstname,lastname);
            children.put(id, child);
            return children.get(id);
        } else {
            return children.get(id);
        }

    }

    public ArrayList<Child> getAllChildren() {

        ArrayList<Child> valuesList = new ArrayList<Child>(children.values());
        return valuesList;
    }

    public HashMap<Integer,Child> getChildrenMap() {
        return children;
    }





}
