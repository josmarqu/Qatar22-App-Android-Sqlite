package com.dbManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DbManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "Qatar2022.db";
    private static final int DB_VERSION = 1;

    // Result Table
    private static final String TABLE_RESULT = "Result";
    private static final String COLUMN_PHASE = "Phase";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_TEAMHM = "TeamHm";
    private static final String COLUMN_GOALTMHM = "GoalTmHm";
    private static final String COLUMN_TEAMAW = "TeamAw";
    private static final String COLUMN_GOALTMAW = "GoaltmAw";


    public DbManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!checkTableExists(db, TABLE_RESULT)) {
            String createTable = "CREATE TABLE " + TABLE_RESULT + " (" +
                    COLUMN_PHASE + " TEXT, " +
                    COLUMN_DATE + " TEXT, " +
                    COLUMN_TEAMHM + " TEXT, " +
                    COLUMN_GOALTMHM + " INTEGER, " +
                    COLUMN_TEAMAW + " TEXT, " +
                    COLUMN_GOALTMAW + " INTEGER)";
            db.execSQL(createTable);
        }
    }

    public boolean checkTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'", null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}


