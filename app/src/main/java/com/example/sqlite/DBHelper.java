package com.example.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBHelper extends SQLiteOpenHelper
{
    private String DEBUGTAG = "db";

    private static String DATABASE_NAME = "sqliteders.db";
    private static int DATABASE_VERSION = 1; // veritabanı yapısında yapılan her değişiklikten sonra güncelleme için değiştirilmelidir

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    @Override
    public void onCreate(SQLiteDatabase db)
    {
        // user
        String CREATETABLE_USER = "CREATE TABLE " + user.TABLE + " (" +
                user.COL_ID + " INTEGER PRIMARY KEY, "    +
                user.COL_USERNAME + " VARCHAR NOT NULL ," +
                user.COL_PASSWORD + " VARCHAR NOT NULL ," +
                user.COL_FULLNAME + " VARCHAR NOT NULL )";
        db.execSQL(CREATETABLE_USER);
        Log.i(DEBUGTAG, user.TABLE + " tablosu oluşturuldu");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        Log.i(DEBUGTAG, "Eski tablolar kaldırılıyor");

        db.execSQL("DROP TABLE IF EXISTS " + user.TABLE);
        Log.i(DEBUGTAG, user.TABLE + " tablosu kaldırıldı");

        onCreate(db);
    }
}