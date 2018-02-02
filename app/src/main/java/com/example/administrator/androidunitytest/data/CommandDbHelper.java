package com.example.administrator.androidunitytest.data;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.Context;

import com.example.administrator.androidunitytest.DataBaseManager;
import com.example.administrator.androidunitytest.MainActivity;
import com.example.administrator.androidunitytest.data.DataBaseContract.*;

/**
 * Created by ZK on 2018/1/11.
 *
 * extends from SQLiteOpenHelper,
 * MUST override TWO method : OnCreate() and OnUpgrade()
 * Manages database creation and version management.
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
    public void onCreate(SQLiteDatabase DataBase) {
        createCommandTable(DataBase);
        createQuestTable(DataBase);
        createSentenceTable(DataBase);
        createKeywordsTable(DataBase);
        createRespondsTable(DataBase);
        createKeyResMatchTable(DataBase);
        //MainActivity.mainActivity.insertDefauleData();
    }


    /**
     * This is called when the database needs to be upgraded.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }



    public void createCommandTable(SQLiteDatabase db){
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
        System.out.println("指令表建立完成");
    }

    public void createQuestTable(SQLiteDatabase db){
        String SQL_CREATE_QUEST_TABLE = "create table " + QuestEntry.TABLE_NAME + " ("
                + QuestEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + QuestEntry.TYPE +  " INTEGER NOT NULL DEFAULT 0, "
                + QuestEntry.INDEX + " INTEGER NOT NULL DEFAULT 0, "
                + QuestEntry.ATTRIBUTE + " TEXT NOT NULL DEFAULT 0, "
                + QuestEntry.DATE + " INTEGER NOT NULL DEFAULT 0, "
                + QuestEntry.DAYOFWEEK + " INTEGER NOT NULL DEFAULT 0, "
                + QuestEntry.TEXT +  " TEXT NOT NULL, "
                + QuestEntry.ISCOMPLETED + " INTEGER NOT NULL DEFAULT 0 "
                +" );";
        db.execSQL(SQL_CREATE_QUEST_TABLE);
        System.out.println("任务表建立完成");
    }

    public void createSentenceTable(SQLiteDatabase db){
        String SQL_CREATE_SENTENCE_TABLE = "create table " + SentenceEntry.TABLE_NAME + " ("
                + SentenceEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + SentenceEntry.SENTENCE_TEXT +  " TEXT NOT NULL, "
                + SentenceEntry.RES_ID + " INTEGER NOT NULL "
                +" );";
        db.execSQL(SQL_CREATE_SENTENCE_TABLE);
        System.out.println("整句表建立完成");
    }

    public void createKeywordsTable(SQLiteDatabase db){
        String SQL_CREATE_KEYWORDS_TABLE = "create table " + KeywordsEntry.TABLE_NAME + " ("
                + KeywordsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KeywordsEntry.KEYWORD_TEXT +  " TEXT NOT NULL, "
                + KeywordsEntry.WEIGHT + " INTEGER NOT NULL DEFAULT 1 "
                +" );";
        db.execSQL(SQL_CREATE_KEYWORDS_TABLE);
        System.out.println("关键字表建立完成");
    }

    public void createRespondsTable(SQLiteDatabase db){
        String SQL_CREATE_RESPONDS_TABLE = "create table " +RespondsEntry.TABLE_NAME + " ("
                + RespondsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RespondsEntry.RESPOND_TEXT +  " TEXT NOT NULL, "
                + RespondsEntry.CALLED_TIMES + " INTEGER NOT NULL DEFAULT 1, "
                + RespondsEntry.KEYWORDS_NUMS + " INTEGER NOT NULL DEFAULT 1, "
                + RespondsEntry.RESPOND_TYPE + " INTEGER NOT NULL DEFAULT 0 "
                +" );";
        db.execSQL(SQL_CREATE_RESPONDS_TABLE);
        System.out.println("回复表建立完成");
    }

    public void createKeyResMatchTable(SQLiteDatabase db){
        String SQL_CREATE_KEY_RES_MATCH_TABLE = "create table " + KeyResMatchEntry.TABLE_NAME + " ("
                + KeyResMatchEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + KeyResMatchEntry.KEY_ID +  " INTEGER NOT NULL, "
                + KeyResMatchEntry.RES_ID + " INTEGER NOT NULL "
                +" );";
        db.execSQL(SQL_CREATE_KEY_RES_MATCH_TABLE);
        System.out.println("对照表建立完成");
    }

}
