package com.toocms.tab.expand.history;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class HistorySQLiteHelper extends SQLiteOpenHelper {

    private final static int CURRENT_VERSION = 1;
    private static final String DB_NAME = "HistoryDB";

    public HistorySQLiteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, CURRENT_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(createTableSql());
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

    private String createTableSql() {
        return "create table " + Constants.TABLE_NAME + "(" + Constants.PRIMARY_KEY + " text primary key," + Constants.KEY_HISTORY + " text)";
    }
}
