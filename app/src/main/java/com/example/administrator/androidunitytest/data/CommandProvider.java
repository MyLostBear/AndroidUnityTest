package com.example.administrator.androidunitytest.data;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.administrator.androidunitytest.data.DataBaseContract.*;

/**
 * Created by ZK on 2018/1/12.
 */

public class CommandProvider extends ContentProvider {

    /** Tag for the log messages */
    public static final String LOG_TAG = CommandProvider.class.getSimpleName();

    /** URI matcher code for the content URI for the command table */
    private static final int COMMAND_ALL = 1000;

    /** URI matcher code for the content URI for a single command in the command table by ID*/
    private static final int COMMAND_ID = 1001;

    /** URI matcher code for the content URI for a single command in the command table by KEYWORD*/
    private static final int COMMAND_KEYWORD = 1002;

    private static final int QUEST_ALL = 2000;
    private static final int QUEST_ID = 2001;
    private static final int QUEST_TYPE = 2002;
    private static final int QUEST_INDEX = 2003;
    private static final int QUEST_DATE = 2004;
    private static final int QUEST_DAYOFWEEK = 2005;

    private static final int SENTENCE_ALL = 100;    //查询全部
    private static final int SENTENCE_ID = 101;     //按ID查询
    private static final int SENTENCE_TEXT = 102;    //按文本查询，用于找出回复语的ID

    private static final int KEYWORDS_ALL = 200;    //用于插入新数据
    private static final int KEYWORDS_TEXT = 202;   //按文本查询关键词

    private static final int RESPONDS_ALL = 300;    //插入新数据
    private static final int RESPONDS_ID = 301;     //按ID查询回复语

    private static final int KEY_RES_ALL = 400;      //插入新数据
    private static final int KEY_RES_KEYID = 401;    //根据keyID查询resID
    /**
     * UriMatcher object to match a content URI to a corresponding code.
     * The input passed into the constructor represents the code to return for the root URI.
     * It's common to use NO_MATCH as the input for this case.
     */
    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    // Static initializer. This is run the first time anything is called from this class.
    static{
        // The calls to addURI() go here, for all of the content URI patterns that the provider
        // should recognize. All paths added to the UriMatcher have a corresponding code to return
        // when a match is found.

        //This URI is used to provide access to MULTIPLE rows of the command table.
        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_COMMAND, COMMAND_ALL);

        // In this case, the "#" wildcard is used where "#" can be substituted for an integer.
        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_COMMAND + "/#", COMMAND_ID);

        // In this case, the "*" wildcard is used where "*" can be substituted for an string.
        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_COMMAND + "/*", COMMAND_KEYWORD);

        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_QUEST, QUEST_ALL);

        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_SENTENCE, SENTENCE_ALL);
        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_SENTENCE + "/#", SENTENCE_ID);
        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_SENTENCE + "/*", SENTENCE_TEXT);


        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_KEYWORDS, KEYWORDS_ALL);
        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_KEYWORDS+ "/*", KEYWORDS_TEXT);

        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_RESPONDS, RESPONDS_ALL);
        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_RESPONDS + "/#", RESPONDS_ID);

        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_KEY_RES_MATCH, KEY_RES_ALL);
        sUriMatcher.addURI(DataBaseContract.CONTENT_AUTHORITY, DataBaseContract.PATH_KEY_RES_MATCH + "/#", KEY_RES_KEYID);
    }



    /** Database helper object */
    private CommandDbHelper commandDbHelper;

    @Override
    public boolean onCreate() {
        commandDbHelper = new CommandDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        // Get readable database
        SQLiteDatabase db = commandDbHelper.getReadableDatabase();

        // This cursor will hold the result of the query
        Cursor cursor;

        // Figure out if the URI matcher can match the URI to a specific code
        int matchCode = sUriMatcher.match(uri);

        switch (matchCode){
            case COMMAND_ALL:

                // For the COMMAND_ALL code, query the command table directly with the given
                // projection, selection, selection arguments, and sort order. The cursor
                // could contain multiple rows of the command table.

                cursor = db.query(CommandEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case COMMAND_ID:

                // For the COMMAND_ID code, extract out the ID from the URI by ContentUris.parseId(uri)
                // For an example URI such as "content://com.example.administrator.androidunitytest/command/3",
                // the selection will be "_id=?" and the selection argument will be a
                // String array containing the actual ID of 3 in this case.
                //
                // For every "?" in the selection, we need to have an element in the selection
                // arguments that will fill in the "?". Since we have 1 question mark in the
                // selection, we have 1 String in the selection arguments' String array.
                selection = CommandEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(CommandEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case COMMAND_KEYWORD:
                selection = CommandEntry.KEYWORD + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                cursor = db.query(CommandEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case QUEST_ALL:
                cursor = db.query(QuestEntry.TABLE_NAME, projection,selection, selectionArgs,null,null,sortOrder);
                break;
            case SENTENCE_ALL:
                cursor = db.query(SentenceEntry.TABLE_NAME, projection,selection, selectionArgs,null,null,sortOrder);
                break;
            case SENTENCE_ID:
                selection = SentenceEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(SentenceEntry.TABLE_NAME, projection,selection, selectionArgs,null,null,sortOrder);
                break;
            case SENTENCE_TEXT:
                selection = SentenceEntry.SENTENCE_TEXT + "= ? ";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                cursor = db.query(SentenceEntry.TABLE_NAME, projection,selection, selectionArgs,null,null,sortOrder);
                break;
            case KEYWORDS_TEXT:
                selection = KeywordsEntry.KEYWORD_TEXT + "=?";
                selectionArgs = new String[]{uri.getLastPathSegment()};
                cursor = db.query(KeywordsEntry.TABLE_NAME, projection, selection, selectionArgs, null, null, sortOrder);
                break;
            case RESPONDS_ID:
                selection = RespondsEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(RespondsEntry.TABLE_NAME, projection,selection, selectionArgs,null,null,sortOrder);
                break;
            case KEY_RES_KEYID:
                selection = KeyResMatchEntry._ID + "=?";
                selectionArgs = new String[]{String.valueOf(ContentUris.parseId(uri))};
                cursor = db.query(KeyResMatchEntry.TABLE_NAME, projection,selection, selectionArgs,null,null,sortOrder);
                break;
            default:
                throw new IllegalArgumentException("Illegal Uri: " + uri);

        }
        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {

        // Figure out if the URI matcher can match the URI to a specific code
        int matchCode = sUriMatcher.match(uri);

        switch (matchCode){
            case COMMAND_ALL:
                break;
            case COMMAND_ID:
                break;
            case COMMAND_KEYWORD:
                break;
            default:
                throw new IllegalArgumentException("Illegal Uri: " + uri);

        }

        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        // Figure out if the URI matcher can match the URI to a specific code
        int matchCode = sUriMatcher.match(uri);

        switch (matchCode){
            case COMMAND_ALL:
                insertCommand(uri, values);
                break;
            case QUEST_ALL:
                insertQuest(uri, values);
                break;
            case SENTENCE_ALL:
                insertSentence(uri, values);
                break;
            case KEYWORDS_ALL:
                insertKeywords(uri, values);
                break;
            case RESPONDS_ALL:
                insertResponds(uri, values);
                break;
            case KEY_RES_ALL:
                insertKeyResMatch(uri, values);
                break;
            default:
                throw new IllegalArgumentException("Illegal Uri: " + uri);

        }
        return null;
    }

    private Uri insertSentence(Uri uri, ContentValues values){
        SQLiteDatabase db = commandDbHelper.getWritableDatabase();
        long id = db.insert(SentenceEntry.TABLE_NAME, null, values);
        if(id == -1){
            Log.e(LOG_TAG, "Failed to insert row for" + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertKeywords(Uri uri, ContentValues values){
        SQLiteDatabase db = commandDbHelper.getWritableDatabase();
        long id = db.insert(KeywordsEntry.TABLE_NAME, null, values);
        // FIXME: 2018/1/26 此处应通过返回值返回ID，而非给KEY_ID赋值
        ConstantValues.KEY_ID_INSERT = id;
        if(id == -1){
            Log.e(LOG_TAG, "Failed to insert row for" + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertResponds(Uri uri, ContentValues values){
        SQLiteDatabase db = commandDbHelper.getWritableDatabase();
        long id = db.insert(RespondsEntry.TABLE_NAME, null, values);
        // FIXME: 2018/1/26 此处应通过返回值返回ID，而非给RES_ID赋值
        ConstantValues.RES_ID_INSERT = id;
        if(id == -1){
            Log.e(LOG_TAG, "Failed to insert row for" + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertKeyResMatch(Uri uri, ContentValues values){
        SQLiteDatabase db = commandDbHelper.getWritableDatabase();
        long id = db.insert(KeyResMatchEntry.TABLE_NAME, null, values);
        if(id == -1){
            Log.e(LOG_TAG, "Failed to insert row for" + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }


    private Uri insertCommand(Uri uri, ContentValues values){

        // Check that the keyword is valid
        String keyword = values.getAsString(CommandEntry.KEYWORD);
        if(keyword == null){
            throw new IllegalArgumentException("Keyword can not be NULL");
        }

        //Get a writeable database
        SQLiteDatabase db = commandDbHelper.getWritableDatabase();

        //Insert the new command with the given values
        long id = db.insert(CommandEntry.TABLE_NAME,null,values);

        if(id == -1){
            Log.e(LOG_TAG, "Failed to insert row for" + uri);
            return null;
        }

        //Return the new URI with the ID(of the newly inserted row) appended at the end
        return ContentUris.withAppendedId(uri, id);
    }

    private Uri insertQuest(Uri uri, ContentValues values){
        SQLiteDatabase db = commandDbHelper.getWritableDatabase();
        Long id = db.insert(QuestEntry.TABLE_NAME, null, values);
        if(id == -1) {
            Log.e(LOG_TAG, "Failed to insert row for" + uri);
            return null;
        }
        return ContentUris.withAppendedId(uri, id);
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {

        // Figure out if the URI matcher can match the URI to a specific code
        int matchCode = sUriMatcher.match(uri);

        switch (matchCode){
            case COMMAND_ALL:
                break;
            case COMMAND_ID:
                break;
            case COMMAND_KEYWORD:
                break;
            case QUEST_TYPE:
                // TODO: 2018/1/19 按照类型删除任务 
                break;
            default:
                throw new IllegalArgumentException("Illegal Uri: " + uri);

        }
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {

        // Figure out if the URI matcher can match the URI to a specific code
        int matchCode = sUriMatcher.match(uri);

        switch (matchCode){
            case COMMAND_ALL:
                break;
            case COMMAND_ID:
                break;
            case COMMAND_KEYWORD:
                break;
            case QUEST_ID:
                // TODO: 2018/1/19 按照ID更新任务
                break;
            default:
                throw new IllegalArgumentException("Illegal Uri: " + uri);

        }
        return 0;
    }
}
