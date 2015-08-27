package com.kikkersprong.dekikkersprong;

/**
 * Created by Jeroen on 27/08/2015.
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;
import com.dropbox.client2.DropboxAPI;
import com.dropbox.client2.exception.DropboxException;
import com.dropbox.client2.exception.DropboxUnlinkedException;

public class UploadFileToDropbox extends AsyncTask<Void, Void, Boolean> {

    private DropboxAPI<?> dropbox;
    private String text;
    private Context context;

    public UploadFileToDropbox(Context context, DropboxAPI<?> dropbox, String text
                               ) {
        this.context = context.getApplicationContext();
        this.dropbox = dropbox;
        this.text = text;
    }

    @Override
    protected Boolean doInBackground(Void... params) {

        try {
            uploadFile(context);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }



    public void uploadFile(Context ctx) throws IOException {
        String FILENAME = "factuur_file";
        String string = text;

        File path = ctx.getFilesDir();
        File tmpFile = new File(path, FILENAME);
        System.out.println(path);
        FileOutputStream fos = ctx.openFileOutput(FILENAME, Context.MODE_PRIVATE);
        fos.write(string.getBytes());
        fos.close();

        FileInputStream fis = new FileInputStream(tmpFile);

        try {
            DropboxAPI.Entry newEntry = dropbox.putFile("factuur.txt", fis, tmpFile.length(), null, null);
        } catch (DropboxUnlinkedException e) {
            Log.e("DbExampleLog", "User has unlinked.");
        } catch (DropboxException e) {
            Log.e("DbExampleLog", "Something went wrong while uploading.");
        }
    }
}