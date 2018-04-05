package com.maya.todo.fragments;

/**
 * Created by kartheekkadaru on 2018-01-31.
 */


import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by kartheekkadaru on 2018-01-31.
 */

public class Databasehelper extends SQLiteOpenHelper {


    public static final String COL_1 = "ID";
    public static final String COL_2 = "NAME";
    public static final String COL_3 = "EMAIL";
    public static final String COL_4 = "PHONENUMBER";
    public static final String DATABASE_NAME = "name.db";
    public static final String TABLE_NAME = "STUDENT_NAME";

    public Databasehelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " +  TABLE_NAME +" (ID INTEGER PRIMARY KEY AUTOINCREMENT ,NAME TEXT,EMAIL TEXT,PHONENUMBER INTEGER )");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS" + TABLE_NAME  );
        onCreate(db);
    }

    public boolean insertData(String name, String email, String phonenumber) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_2, name);
        contentValues.put(COL_3, email);
        contentValues.put(COL_4, phonenumber);
        long result = db.insert(TABLE_NAME,null,contentValues);
        return db.insert(TABLE_NAME, null, contentValues) != -1;
    }
}
