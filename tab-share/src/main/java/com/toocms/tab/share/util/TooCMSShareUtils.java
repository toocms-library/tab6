package com.toocms.tab.share.util;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.DisplayMetrics;
import android.view.View;

import com.toocms.tab.configs.FileManager;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * 工具类
 * <p>
 * Author：Zero
 * Date：2020/5/26
 *
 * @version v3.0
 */
public class TooCMSShareUtils {

    /**
     * 将view转换成bitmap,并将bitmap转换成图片文件存储到sd卡中
     *
     * @param activity
     * @param view
     * @return
     */
    public static File loadImageFromView(Activity activity, View view) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        int width = metric.widthPixels;
        int height = metric.heightPixels;
        view.layout(0, 0, width, height);
        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(10000, View.MeasureSpec.AT_MOST);
        view.measure(measuredWidth, measuredHeight);
        view.layout(0, 0, view.getMeasuredWidth(), view.getMeasuredHeight());
        int w = view.getWidth();
        int h = view.getHeight();
        Bitmap bmp = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmp);
        c.drawColor(Color.WHITE);
        view.layout(0, 0, w, h);
        view.draw(c);
        return savePhotoToSDCard(bmp);
    }

    /**
     * 将bitmap转换成png格式存储到sd卡中
     *
     * @param bitmap
     * @return
     */
    public static File savePhotoToSDCard(Bitmap bitmap) {
        if (FileManager.hasSDCard()) {
            String imageFilePath = FileManager.getImageFilePath();
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddhhmmss", Locale.getDefault());
            File photoFile = new File(imageFilePath, dateFormat.format(new Date()) + ".png");
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (bitmap != null) {
                    if (bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                }
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return photoFile;
        }
        return null;
    }
}
