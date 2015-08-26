package db;


import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;

import domain.CheckIn;
import domain.CheckOut;
import domain.Child;
import domain.Overview;
import domain.Visit;

public class OfflineEntryWriter implements EntryWriter {


    private static OfflineEntryWriter instance = null;
    private FileOutputStream fos;
    private ArrayList<Child> children;
    private String FILENAME1 = "children";
    private String FILENAME2 = "visits";
    private Context context;

    public OfflineEntryWriter(Context ctx){

        children = new ArrayList<Child>();
        Child child1 = new Child("Arno","Swinnen");
        Child child2 = new Child("Kathleen","Bellen");
        Child child3 = new Child("Gilles","Azaïs");
        Child child4 = new Child("Jeroen","Opdebeeck");

        children.add(child1);
        children.add(child2);
        children.add(child3);
        children.add(child4);

        this.context = ctx;


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

            for(Child child : children){
                fos.write(child.toString().getBytes());


            }

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    @Override
    public void writeAllVisits() {
        try {
            fos = context.openFileOutput(FILENAME2, Context.MODE_PRIVATE);

            for(Child child : children){
                fos.write(child.toString().getBytes());
                ArrayList<Visit> visits = child.getVisits();

                for(Visit visit : visits){
                    fos.write(visit.toString().getBytes());
                }
            }

            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void addChild(String qrcode){

        //voeg kind toe indien eerste keer bezoek aan kikkersprong
        Child child = new Child(qrcode,qrcode);
        children.add(child);

    }

    public void addVisit(String qrcode) {
        //voeg visit toe bij juiste kind
    }



}
