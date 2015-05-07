package net.kreid.decisiontree;

import android.app.Activity;
import android.content.Context;
import android.graphics.Path;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.util.List;

/**
 * Created by Kevin on 26/02/2015.
 */
public class FileManager {

    private static final String TAG = "MEDIA";

    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            return true;
        }
        return false;
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            return true;
        }
        return false;
    }

    public static void writeToFile(Context ctx, String appName, String fileName, String content)
    {
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/net.kreid.android/" + appName);
        Writer writer = null;

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName);

        try {

            if (!file.exists()) {
                file.createNewFile();
            }

            writer = new FileWriter(file);
            writer.write(content);

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void writeToFile(Context ctx, String appName, String fileName, List<String> content)
    {
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/net.kreid.android/" + appName);
        Writer writer = null;

        if (!dir.exists()) {
            dir.mkdirs();
        }

        File file = new File(dir, fileName);

        try
        {
            if (!file.exists()) {
                file.createNewFile();
            }

            writer = new BufferedWriter(new OutputStreamWriter(
            new FileOutputStream(file), "utf-8"));

            for (String line : content) {
                line += System.getProperty("line.separator");
                writer.write(line);
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String readFile(Context ctx, String appName, String fileName) throws FileNotFoundException {
        File root = android.os.Environment.getExternalStorageDirectory();
        File dir = new File(root.getAbsolutePath() + "/net.kreid.android/" + appName);
        File file = new File(dir, fileName);
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }

            br.close();

            return text.toString();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw e;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    /**
     * Method to read in a text file placed in the res/raw directory of the
     * application. The method reads in all lines of the file sequentially.
     */
    /*
    private void readRaw()
    {
        tv.append("\nData read from res/raw/textfile.txt:");
        InputStream is = this.getResources().openRawResource(R.raw.textfile);
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr, 8192); // 2nd arg is buffer
        // size
        // More efficient (less readable) implementation of above is the
        // composite expression

        // BufferedReader br = new BufferedReader(new InputStreamReader(
         //this.getResources().openRawResource(R.raw.textfile)), 8192);

        try {
            String test;
            while (true) {
                test = br.readLine();
                // readLine() returns null if no more lines in the file
                if (test == null) break;
                tv.append("\n" + "    " + test);
            }
            isr.close();
            is.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        tv.append("\n\nThat is all");
    }
*/
}
