package com.dbManager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteStatement;
import com.R;
import com.entities.Result;



public class DbManager extends SQLiteOpenHelper {
    private static final String DB_NAME = "Qatar2022.db";
    private static final int DB_VERSION = 1;
    private Context context;
    private SQLiteDatabase rdb;
    private SQLiteDatabase wdb;

    // Result Table
    private static final String TABLE_NAME = "Result";
    private static final String COLUMN_ID = "Id";
    private static final String COLUMN_PHASE = "Phase";
    private static final String COLUMN_DATE = "Date";
    private static final String COLUMN_TEAMHM = "TeamHm";
    private static final String COLUMN_GOALTMHM = "GoalTmHm";
    private static final String COLUMN_TEAMAW = "TeamAw";
    private static final String COLUMN_GOALTMAW = "GoaltmAw";

    // QUERIES
    private final String CHECK_TABLE_EXISTS = "SELECT name FROM sqlite_master WHERE type='table' AND name='" + TABLE_NAME + "'";
    private final String CREATE_TABLE = "CREATE TABLE " +
            TABLE_NAME + " (" +
            COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_PHASE + " TEXT, " +
            COLUMN_DATE + " TEXT, " +
            COLUMN_TEAMHM + " TEXT, " +
            COLUMN_GOALTMHM + " INTEGER, " +
            COLUMN_TEAMAW + " TEXT, " +
            COLUMN_GOALTMAW + " INTEGER)";
    private final String COUNT_RESULTS_BY_TEAM_IN_PHASE = "SELECT COUNT(*) FROM " +
            TABLE_NAME + " WHERE " +
            COLUMN_PHASE + " = ? AND (" +
            COLUMN_TEAMHM + " = ? OR " +
            COLUMN_TEAMAW + " = ?)";
    private final String COUNT_RESULTS_BY_PHASE = "SELECT COUNT(*) FROM " +
            TABLE_NAME + " WHERE " +
            COLUMN_PHASE + " = ?";
    private final String COUNT_RESULTS_BY_TEAM = "SELECT COUNT(*) FROM " +
            TABLE_NAME + " WHERE " +
            COLUMN_TEAMHM + " = ? OR " +
            COLUMN_TEAMAW + " = ?";
    private final String INSERT_RESULT = "INSERT INTO " +
            TABLE_NAME + "(" +
            COLUMN_PHASE + "," +
            COLUMN_DATE + "," +
            COLUMN_TEAMHM + "," +
            COLUMN_GOALTMHM + "," +
            COLUMN_TEAMAW + "," +
            COLUMN_GOALTMAW + ") VALUES (?,?,?,?,?,?)";
    private final String GET_RESULT = "SELECT * FROM " +
            TABLE_NAME + " WHERE ("+
            COLUMN_TEAMHM + "= ? OR " +
            COLUMN_TEAMAW + "= ?) " +
            "LIMIT 1 OFFSET ?";


    public DbManager(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        if (!checkTableExists()) {
            db.execSQL(CREATE_TABLE);
            System.out.println("TABLE CREATED SUCCESSFULLY");
        } else {
            System.out.println("TABLE ALREADY EXIST");
        }
    }

    private boolean checkTableExists() {
        rdb = getReadableDatabase();
        Cursor cursor = rdb.rawQuery(CHECK_TABLE_EXISTS, null);
        boolean exists = (cursor.getCount() > 0);
        cursor.close();
        return exists;
    }

    public void insertResult(Result result) {
        rdb = getReadableDatabase();
        wdb = getWritableDatabase();
        checkValidResult(result.getTeamHm(), result.getTeamAw(), result.getPhase());
        SQLiteStatement statement = wdb.compileStatement(INSERT_RESULT);
        statement.bindString(1, result.getPhase());
        statement.bindString(2, result.getDate());
        statement.bindString(3, result.getTeamHm());
        statement.bindLong(4, result.getGoalTmHm());
        statement.bindString(5, result.getTeamAw());
        statement.bindLong(6, result.getGoalTmAw());
        statement.executeInsert();
    }

    private void checkValidResult(String hmTm, String awTm, String phase) {
        String[] phases = context.getResources().getStringArray(R.array.phases);
        int nbResByPh = countResultsByPhase(phase);
        int nbResByPhTmHm = countResultsByCountryInPhase(phase, hmTm);
        int nbResByPhTmAw = countResultsByCountryInPhase(phase, awTm);

        //group stage
        if (phase.contains(phases[0])){
            if (nbResByPhTmHm > 2){
                throwException(hmTm, phase);
            } else if (nbResByPhTmAw > 2){
                throwException(awTm, phase);
            }
        }

        //round of sixteen
        if (phase.contains(phases[1])){
            if (nbResByPh > 7){
                throwException(null, phase);
            } else if (nbResByPhTmHm == 1) {
                throwException(hmTm, phase);
            } else if (nbResByPhTmAw == 1) {
                throwException(awTm, phase);
            }
        }

        //quarterfinal
        if (phase.contains(phases[2])){
            if (nbResByPh > 3){
                throwException(null, phase);
            } else if (nbResByPhTmHm == 1) {
                throwException(hmTm, phase);
            } else if (nbResByPhTmAw == 1) {
                throwException(awTm, phase);
            }
        }

        //semifinal
        if (phase.contains(phases[3])){
            if (nbResByPh > 1){
                throwException(null, phase);
            } else if (nbResByPhTmHm == 1) {
                throwException(hmTm, phase);
            } else if (nbResByPhTmAw == 1) {
                throwException(awTm, phase);
            }
        }

        //final
        if (phase.contains(phases[4])){
            if (nbResByPh > 0) {
                throwException(null, phase);
            }
        }
    }

    private int countResultsByCountryInPhase(String phase, String country) {
        Cursor cursor = rdb.rawQuery(COUNT_RESULTS_BY_TEAM_IN_PHASE, new String[]{phase, country, country});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    private int countResultsByPhase(String phase) {
        Cursor cursor = rdb.rawQuery(COUNT_RESULTS_BY_PHASE, new String[]{phase});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    public int countResultsByCountry(String country) {
        rdb = getReadableDatabase();
        Cursor cursor = rdb.rawQuery(COUNT_RESULTS_BY_TEAM, new String[]{country});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();

        return count;
    }

    public Result getResult(String country, int matchNb) {
        rdb = getReadableDatabase();
        String[] selectionArgs = {country, country, String.valueOf(matchNb)};
        Cursor cursor = rdb.rawQuery(GET_RESULT, selectionArgs);
        Result result = new Result();
        result.setPhase(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_PHASE)));
        result.setDate(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_DATE)));
        result.setTeamHm(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEAMHM)));
        result.setGoaltmHm(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_GOALTMHM)));
        result.setTeamAw(cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TEAMAW)));
        result.setGoaltmAw(cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_TEAMAW)));

        return result;
    }

    private void throwException(String tm, String phase) {
        if (tm == null){
            throw new IllegalArgumentException(context.getResources().getString(R.string.error) + phase +
                    context.getResources().getString(R.string.resError));
        } else {
            throw new IllegalArgumentException(context.getResources().getString(R.string.error) + tm + " " + phase +
                    context.getResources().getString(R.string.resError));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
    }
}


