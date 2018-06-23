package edu.usc.haowu.codetestforpheramor.customview;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.os.Environment;
/**
 * @author Hao Wu
 * @time 2018/06/23
 * @since
 */
public class BitmapUtil
{

    public static void saveBitmap2file(Bitmap bmp, String filename)
    {
        CompressFormat format = Bitmap.CompressFormat.JPEG;
        int quality = 100;
        OutputStream stream = null;
        try
        {

            stream = new FileOutputStream(Environment
                    .getExternalStorageDirectory().getPath()
                    + "/"
                    + filename
                    + ".jpg");
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        bmp.compress(format, quality, stream);
        try
        {
            stream.flush();
            stream.close();
        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static Bitmap getBitmapFromFile(String filename)

    {
        try
        {
            return BitmapFactory.decodeStream(new FileInputStream(Environment
                    .getExternalStorageDirectory().getPath()
                    + "/"
                    + filename
                    + ".jpg"));
        } catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}