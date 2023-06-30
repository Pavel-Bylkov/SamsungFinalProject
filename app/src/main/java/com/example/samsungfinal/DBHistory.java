package com.example.samsungfinal;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHistory {

    private static final String DATABASE_NAME = "history.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "tableHistory";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_CITY = "City";
    private static final String COLUMN_DATE_START = "DateStart";
    private static final String COLUMN_DATE_END = "DateEnd";
    private static final String COLUMN_TIMESTAMP = "LastAccess";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_CITY = 1;
    private static final int NUM_COLUMN_DATE_START = 2;
    private static final int NUM_COLUMN_DATE_END = 3;
    private static final int NUM_COLUMN_TIMESTAMP = 4;

    private SQLiteDatabase mDataBase;

    public DBHistory(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }

    public long insert(String city,String date_start, String date_end) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_CITY, city);
        cv.put(COLUMN_DATE_START, date_start);
        cv.put(COLUMN_DATE_END, date_end);
        return mDataBase.insert(TABLE_NAME, null, cv);
    }

    public int update(TempHistory md) {
        ContentValues cv=new ContentValues();
        cv.put(COLUMN_CITY, md.city);
        cv.put(COLUMN_DATE_START, md.date_start);
        cv.put(COLUMN_DATE_END, md.date_end);
        return mDataBase.update(TABLE_NAME, cv, COLUMN_ID + " = ?",new String[] { String.valueOf(md.id)});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public TempHistory select(long id) {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();
        String City = mCursor.getString(NUM_COLUMN_CITY);
        String DateStart = mCursor.getString(NUM_COLUMN_DATE_START);
        String DateEnd = mCursor.getString(NUM_COLUMN_DATE_END);
        return new TempHistory(id, City, DateStart, DateEnd);
    }

    public ArrayList<TempHistory> selectAll() {
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null, null, null, null, COLUMN_TIMESTAMP);

        ArrayList<TempHistory> arr = new ArrayList<TempHistory>();
        mCursor.moveToFirst();
        if (!mCursor.isAfterLast()) {
            do {
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String City = mCursor.getString(NUM_COLUMN_CITY);
                String DateStart = mCursor.getString(NUM_COLUMN_DATE_START);
                String DateEnd = mCursor.getString(NUM_COLUMN_DATE_END);
                arr.add(new TempHistory(id, City, DateStart, DateEnd));
            } while (mCursor.moveToNext());
        }
        return arr;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        OpenHelper(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }
        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_CITY+ " TEXT, " +
                    COLUMN_DATE_START + " TEXT, " +
                    COLUMN_DATE_END + " TEXT," +
                    COLUMN_TIMESTAMP + " DEFAULT CURRENT_TIMESTAMP);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
