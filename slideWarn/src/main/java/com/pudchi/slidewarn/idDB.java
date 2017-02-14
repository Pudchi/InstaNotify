package com.pudchi.slidewarn;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class idDB extends SQLiteOpenHelper{

    private final static int _DBVersion = 1;
    private final static String _DBName = "idDB.db";
    private final static String _TableName = "Login_Id";

    public idDB(Context context) {
        super(context, _DBName, null, _DBVersion);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL = "CREATE TABLE IF NOT EXISTS " + _TableName + " ( " +
                "_serial INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "_id INTEGER UNIQUE " + ")";

        db.execSQL(SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + _TableName);
        onCreate(db);

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }
}
