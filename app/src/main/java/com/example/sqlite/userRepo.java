package com.example.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

public class userRepo
{
    private static final String DEBUGTAG = "userRepo";

    private Context context;
    private DBHelper dbHelper;

    public userRepo(Context ctx) {
        this.context = ctx;
        dbHelper = new DBHelper(this.context);
    }


    public Long insert(user item) {
        long result = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();

            values.put(user.COL_USERNAME, item.username);
            values.put(user.COL_PASSWORD, item.password);
            values.put(user.COL_FULLNAME, item.fullname);

            result = db.insert(user.TABLE, null, values);
        } catch (Exception e) {
            Log.e(DEBUGTAG, "Error : " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) {
                if (db.isOpen()) db.close();
            }
        }
        return result;
    }

    public user getLogin(String username, String password)
    {
        // result olarak dönecek olan user modelinimizi oluşturuyoruz.
        user result = new user();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor C = null;
        try {
            // sql sorgumuz ile gelen kullanıcı adı ve şifreyi doğruluyoruz.
            String sSQL = "SELECT * from user WHERE username =  '" + username + "' AND [password] = '" + password + "'";

            C = db.rawQuery(sSQL, null);
            if (C.moveToFirst())
            {
                do
                {
                    // eğer kayıt geldiyse user modeline verileri dolduruyoruz.
                    result = (new user(
                            C.getLong(C.getColumnIndex(user.COL_ID)),
                            C.getString(C.getColumnIndex(user.COL_USERNAME)),
                            C.getString(C.getColumnIndex(user.COL_PASSWORD)),
                            C.getString(C.getColumnIndex(user.COL_FULLNAME))
                    ));
                } while (C.moveToNext());
            }
        } catch (Exception e) {
            Log.e(DEBUGTAG, "Veritabanı hatası: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (C != null) {
                if (!C.isClosed()) C.close();
            }
            if (db != null) {
                if (db.isOpen()) db.close(); // Closing database connection
            }
        }
        return result;
    }

    public long update(user item) {
        long result = 0;
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();

            //values.put(trans.COL_ID, item.id); // autoincrement
            values.put(user.COL_USERNAME, item.username);
            values.put(user.COL_PASSWORD, item.password);
            values.put(user.COL_FULLNAME, item.fullname);

            // It's a good practice to use parameter ?, instead of concatenate string
            result = db.update(user.TABLE, values, user.COL_ID + "= ?", new String[]{String.valueOf(item.id)});
        } catch (Exception e) {
            Log.e(DEBUGTAG, "Veritabanı update hatası: " + e.getMessage());
            e.printStackTrace();
        } finally {
            if (db != null) {
                if (db.isOpen()) db.close(); // Closing database connection
            }
        }
        return result;
    }


}
