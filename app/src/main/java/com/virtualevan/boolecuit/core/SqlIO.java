package com.virtualevan.boolecuit.core;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by VirtualEvan on 29/11/2016.
 */

public class SqlIO extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "boolecuit";
    private static final int DATABASE_VERSION = 1;

    public SqlIO(Context context){
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        try {
            db.execSQL( "CREATE TABLE IF NOT EXISTS history("
                    + "expression string(255) NOT NULL"
                    + ")" );
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w( SqlIO.class.getName(),
                "Upgrading database from version "
                        + oldVersion
                        + " to " + newVersion
                        + ", which will destroy all old data" );
        db.beginTransaction();
        try {
            db.execSQL( "DROP TABLE IF EXISTS history" );
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
        this.onCreate( db );
    }
}
