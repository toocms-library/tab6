package com.toocms.tab.expand.history;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.GsonUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class History<T> {

    public static final String TAG = History.class.getSimpleName();
    public static final int MESSAGE_SEARCH = 1;
    public static final int MESSAGE_SAVE = 2;
    public static final int MESSAGE_DELETE = 3;

    private HistoryHandler handler;
    private HistorySQLiteHelper mSqLiteHelper;
    private ExecutorService mExecutorService;
    private OnSaveResultListener mOnSaveResultListener;
    private OnDeleteResultListener mOnDeleteResultListener;
    private OnSearchResultListener<T> mOnSearchResultListener;
    private Class<T> mCls;
    private String fKey;

    public History(Context context, @NonNull String key, Class<T> cls) {
        handler = new HistoryHandler();
        mExecutorService = Executors.newCachedThreadPool();
        mSqLiteHelper = new HistorySQLiteHelper(context);
        fKey = key;
        mCls = cls;
    }

    /**
     * 保存历史记录
     *
     * @param history
     */
    public History<T> saveHistory(final T history) {
        Log.e(TAG, "save history");
        final Message message = handler.obtainMessage(MESSAGE_SAVE);

        if (Looper.myLooper() != Looper.getMainLooper()) {
            Looper.prepare();
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Looper.loop();
        }
        if (TextUtils.isEmpty(fKey) || null == history) {
            message.obj = false;
            handler.sendMessage(message);
            return this;
        }
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase readableDatabase = mSqLiteHelper.getReadableDatabase();
                SQLiteDatabase writableDatabase = mSqLiteHelper.getWritableDatabase();
                Cursor query = readableDatabase.query(Constants.TABLE_NAME, null, Constants.PRIMARY_KEY + "=?", new String[]{fKey}, null, null, null);
                List<String> rawHistorysStrList = new ArrayList<>();
                while (query.moveToNext()) {
                    rawHistorysStrList.add(query.getString(query.getColumnIndex(Constants.KEY_HISTORY)));
                }
                query.close();
                List<T> rawHistoryList;
                if (0 < rawHistorysStrList.size()) {
                    String historyListStr = rawHistorysStrList.get(0);
                    rawHistoryList = GsonUtils.fromJson(historyListStr, GsonUtils.getListType(mCls));
                } else {
                    rawHistoryList = new ArrayList<>();
                }
                //判断要保存的历史记录是否存在或者是否超过了最大条数
                //这里将对象转成JSON字符串进行比较是为了防止对象未实现equals方法和hashCode方法,而照成对比失败
                //存在超过则删除原有的记录
                String saveHistoryJson = GsonUtils.toJson(history);
                for (int i = 0; i < rawHistoryList.size(); i++) {
                    String itemJson = GsonUtils.toJson(rawHistoryList.get(i));
                    if (saveHistoryJson.equals(itemJson) || 1 >= Constants.SINGLE_HISTORY_MAX_COUNT - i) {
                        rawHistoryList.remove(i);
                        i--;
                    }
                }
                rawHistoryList.add(0, history);
                String newestHistorysStr = GsonUtils.toJson(rawHistoryList, GsonUtils.getListType(mCls));
                ContentValues contentValues = new ContentValues();
                contentValues.put(Constants.PRIMARY_KEY, fKey);
                contentValues.put(Constants.KEY_HISTORY, newestHistorysStr);
                writableDatabase.replace(Constants.TABLE_NAME, null, contentValues);
                message.obj = true;
                handler.sendMessage(message);
            }
        });
        return this;
    }

    /**
     * 获取历史记录
     */
    public History<T> getHistory() {
        final Message message = handler.obtainMessage(MESSAGE_SEARCH);
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Looper.prepare();
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Looper.loop();
        }
        if (TextUtils.isEmpty(fKey)) {
            message.obj = new ArrayList<T>();
            handler.sendMessage(message);
            return this;
        }
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase readableDatabase = mSqLiteHelper.getReadableDatabase();
                Cursor query = readableDatabase.query(Constants.TABLE_NAME, null, Constants.PRIMARY_KEY + "=?", new String[]{fKey}, null, null, null);
                List<String> historysStrList = new ArrayList<>();
                while (query.moveToNext()) {
                    historysStrList.add(query.getString(query.getColumnIndex(Constants.KEY_HISTORY)));
                }
                ArrayList<T> history;
                if (0 == historysStrList.size()) {
                    history = new ArrayList<>();
                } else {
                    Log.e(TAG, historysStrList.get(0));
                    history = GsonUtils.fromJson(historysStrList.get(0), GsonUtils.getListType(mCls));
                }
                Log.e(TAG, history.toString());
                message.obj = history;
                handler.sendMessage(message);
                query.close();
//                readableDatabase.close();
            }
        });
        return this;
    }

    /**
     * 删除历史记录
     */
    public History<T> deleteHistory() {
        final Message message = handler.obtainMessage(MESSAGE_DELETE);
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Looper.prepare();
        }
        if (Looper.myLooper() != Looper.getMainLooper()) {
            Looper.loop();
        }
        if (TextUtils.isEmpty(fKey)) {
            message.obj = false;
            handler.sendMessage(message);
            return this;
        }
        mExecutorService.execute(new Runnable() {
            @Override
            public void run() {
                SQLiteDatabase writableDatabase = mSqLiteHelper.getWritableDatabase();
                int delete = writableDatabase.delete(Constants.TABLE_NAME, Constants.PRIMARY_KEY + "=?", new String[]{fKey});
                message.obj = 0 < delete;
                handler.sendMessage(message);
            }
        });
        return this;
    }

    public History<T> setOnSaveResultListener(OnSaveResultListener onSaveResultListener) {
        this.mOnSaveResultListener = onSaveResultListener;
        return this;
    }

    public History<T> setOnSearchResultListener(OnSearchResultListener<T> onSearchResultListener) {
        this.mOnSearchResultListener = onSearchResultListener;
        return this;
    }

    public History<T> setOnDeleteResultListener(OnDeleteResultListener onDeleteResultListener) {
        this.mOnDeleteResultListener = onDeleteResultListener;
        return this;
    }

    /**
     * 释放数据库
     */
    public History<T> release() {
        if (null == mSqLiteHelper) {
            return this;
        }
        mSqLiteHelper.getWritableDatabase().close();
        mSqLiteHelper.getReadableDatabase().close();
        mSqLiteHelper.close();
        return this;
    }

    public class HistoryHandler extends Handler {

        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_SEARCH:
                    mOnSearchResultListener.onResult(fKey, null == msg.obj ? new ArrayList<T>() : (ArrayList<T>) msg.obj);
                    break;
                case MESSAGE_SAVE:
                    mOnSaveResultListener.onResult(null != msg.obj && (boolean) msg.obj);
                    break;
                case MESSAGE_DELETE:
                    mOnDeleteResultListener.onResult(null != msg.obj && (boolean) msg.obj);
                    break;
            }
        }
    }
}
