package com.dbManager;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.entities.Result;

public class DbManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "Qatar2022.db";
    private static final int DB_VERSION = 1;

    // Result Table
    private static final String TABLE_RESULT = "Result";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_PHASE = "Phase";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_TEAMHM = "TeamHm";
    private static final String COLUMN_GOALTMHM = "GoalTmHm";
    private static final String COLUMN_TEAMAW = "TeamAw";
    private static final String COLUMN_GOALTMAW = "GoaltmAw";

    // QUERIES
    private final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_RESULT + " (" +
            COLUMN_ID + "INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_PHASE + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_TEAMHM + " TEXT, " +
            COLUMN_GOALTMHM + " INTEGER, " +
            COLUMN_TEAMAW + " TEXT, " +
            COLUMN_GOALTMAW + " INTEGER)";
    private final String COUNT_RESULTS_BY_TEAM = "SELECT COUNT(*) FROM " +
            TABLE_RESULT + " WHERE " +
            COLUMN_PHASE + " = ? AND (" +
            COLUMN_TEAMHM + " = ? OR " +
            COLUMN_TEAMAW + " = ?)";
    private final String COUNT_RESULTS = "SELECT COUNT(*) FROM " +
            TABLE_RESULT + " WHERE " +
            COLUMN_PHASE + " = ?";


    public DbManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!checkTableExists(db, TABLE_RESULT)) {
            db.execSQL(CREATE_TABLE);
            System.out.println("TABLE CREATED SUCCESSFULLY");
        } else {
            System.out.println("TABLE ALREADY EXIST");
        }
    }

    public boolean checkTableExists(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table' AND name='" + tableName + "'", null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public void insertResult(Result result) {
        SQLiteDatabase db = getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_PHASE, result.getPhase());
        values.put(COLUMN_DATE, result.getDate());
        values.put(COLUMN_TEAMHM, result.getTeamHm());
        values.put(COLUMN_GOALTMHM, result.getGoalTmHm());
        values.put(COLUMN_TEAMAW, result.getTeamAw());
        values.put(COLUMN_GOALTMAW, result.getGoalTmAw());

        db.insert(TABLE_RESULT, null, values);
    }

    public int countResultsByTeam(String phase, String teamName) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(COUNT_RESULTS_BY_TEAM, new String[]{phase, teamName, teamName});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    public int countResults(String phase) {
        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(COUNT_RESULTS, new String[]{phase});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }


}


