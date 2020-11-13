package com.toocms.tab.imageload.cache;

import android.os.AsyncTask;

import com.toocms.tab.TooCMSApplication;
import com.toocms.tab.imageload.GlideApp;
import com.toocms.tab.imageload.GlideRequests;

import java.io.File;
import java.util.concurrent.ExecutionException;

/**
 * 下载图片
 * <p>
 * Author：Zero
 * Date：2017/5/15 17:03
 *
 * @version v4.0
 */
public final class ImageCacheAsyncTask extends AsyncTask<String, Void, File> {

    private GlideRequests glideRequests;
    private CacheCallback callback;

    public ImageCacheAsyncTask(CacheCallback callback) {
        glideRequests = GlideApp.with(TooCMSApplication.getInstance());
        this.callback = callback;
    }

    @Override
    protected File doInBackground(String... params) {
        String url = params[0];
        try {
            return
                    glideRequests.download(url)
                            .submit()
                            .get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(File file) {
        if (file == null) return;
        if (null != callback) callback.onCache(file);
    }
}
