package com.toocms.tab.imageload.progress;

/**
 * Author：Zero
 * Date：2018/4/27 9:46
 */
public interface OnProgressListener {

    void onProgress(boolean isComplete, int percentage, long bytesRead, long totalBytes);
}
