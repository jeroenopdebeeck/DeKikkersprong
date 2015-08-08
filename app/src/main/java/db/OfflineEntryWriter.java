package db;


import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import domain.CheckIn;
import domain.CheckOut;
import domain.Overview;

public class OfflineEntryWriter implements EntryWriter {

    private FileOutputStream fos;
    private String FILENAME1 = "entries";
    private String FILENAME2 = "overview";
    private Context context;

    public OfflineEntryWriter(Context ctx){

        this.context = ctx;

    }


    @Override
    public void writeCheckIn(CheckIn checkIn) {

        try {
            fos = context.openFileOutput(FILENAME1, Context.MODE_PRIVATE);
            fos.write(checkIn.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void writeCheckOut(CheckOut checkOut) {
        try {
            fos = context.openFileOutput(FILENAME1, Context.MODE_PRIVATE);
            fos.write(checkOut.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void writeOverview(Overview overview) {
        try {
            fos = context.openFileOutput(FILENAME2, Context.MODE_PRIVATE);
            fos.write(overview.toString().getBytes());
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
