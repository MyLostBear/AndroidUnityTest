package com.example.administrator.androidunitytest.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;
import com.example.administrator.androidunitytest.data.VoiceContract.CommandEntry;

/**
 * Created by ZK on 2018/1/11.
 *
 * extends from SQLiteOpenHelper,
 * MUST override TWO method : OnCreate() and OnUpgrade()
 */

public class CommandDbHelper extends SQLiteOpenHelper {
    public static final String LOG_TAG = CommandDbHelper.class.getSimpleName();

    /** Name of the database file */
    private static final String DATABASE_NAME = "game.db";

    /**
     * Database version. If you CHANGE the database SCHEMA, you MUST increment the database version.
     */
    private static final int DATABASE_VERSION = 1;


    /**
     * Constructs a new instance of {@link CommandDbHelper}.
     *
     * @param context of the app
     */
    public CommandDbHelper(Context context)
    {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }


    /**
     * This is called when the database is created for the FIRST TIME.
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        String SQL_CREATE_COMMAND_TABLE = "create table " + CommandEntry.TABLE_NAME + " ("
                + CommandEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + CommandEntry.KEYWORD + "  TEXT NOT NULL, "
                + CommandEntry.SCENE + " TEXT NOT NULL DEFAULT \"default\", "
                + CommandEntry.METHOD + " TEXT NOT NULL DEFAULT \"unknown\", "
                + CommandEntry.RESPOND_WORD + " TEXT NOT NULL DEFAULT \"unknown\", "
                + CommandEntry.CALLED_TIMES + " INTEGER NOT NULL DEFAULT 0, "
                + CommandEntry.ANIMATION_TYPE + " INTEGER NOT NULL DEFAULT 0, "
                + CommandEntry.ANIMATION_NAME + " TEXT, "
                + CommandEntry.ANIMATION_VALUE + " INTEGER, "
                + CommandEntry.LINK + " TEXT, "
                + CommandEntry.COMMENT + " TEXT"
                +" );";

        db.execSQL(SQL_CREATE_COMMAND_TABLE);
    }


    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
