package com.example.lenovo.notepadapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lenovo on 07-08-2016.
 */
public class DBHelper extends SQLiteOpenHelper {

    private Context context;
    public static final String DATABASE_NAME="notepadDB";
    public static final int DATABASE_VERSION=2;
    public static String TABLE_NAME="notepad";
    public static final String COL_ID="id";
    public static final String COL_TITLE="title";
    public static final String COL_NOTE="note";


    public DBHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
        Log.d("DBHelper","Database created successfully");

    }
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_NOTEPAD_TABLE="create table notepad(title TITLE,note TEXT)";
        db.execSQL(CREATE_NOTEPAD_TABLE);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //Add Note and Title into Database
    public boolean addNote(Main2Activity nc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        try {
            values.put(COL_TITLE, nc.getTitle());
            values.put(COL_NOTE, nc.getNote());
            db.insert(TABLE_NAME, null, values);
        } finally {
            db.close();
        }
        Toast.makeText(context, "" + nc.getTitle() + "  " + nc.getNote(), Toast.LENGTH_SHORT).show();
        return true;
    }

    public List<String> getTitlesOnly() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("select title from " + TABLE_NAME, null);
        List<String> titlestr = new ArrayList<String>();
        if (cursor.moveToFirst()) {
            do {
                titlestr.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
            db.close();
        } else {
            titlestr = null;
            cursor.close();
            db.close();
        }
        return titlestr;

    }
