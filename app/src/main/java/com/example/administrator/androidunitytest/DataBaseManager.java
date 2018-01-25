package com.example.administrator.androidunitytest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;

import com.example.administrator.androidunitytest.data.CommandDbHelper;
import com.example.administrator.androidunitytest.data.ConstantValues;
import com.example.administrator.androidunitytest.data.VoiceContract;
import com.unity3d.player.UnityPlayer;

/**
 * Created by Administrator on 2018/1/24.
 */

public class DataBaseManager {
    private Context currentContext;
    public DataBaseManager(Context context){
        currentContext = context;
    }

    private static String UnityMethod  = "";

    public void getSimpleCommand(String methodName){
        UnityMethod = methodName;
        String[] projection = {
                VoiceContract.CommandEntry.KEYWORD,
                VoiceContract.CommandEntry.SCENE,
                VoiceContract.CommandEntry.RESPOND_WORD,
                VoiceContract.CommandEntry.CALLED_TIMES,
        };

        Cursor cursor = currentContext.getContentResolver().query(
                VoiceContract.CommandEntry.CONTENT_URI,
                projection,
                null,
                null,
                VoiceContract.CommandEntry.CALLED_TIMES
        );

        try{
            //cursor.moveToFirst();
            int KeywordIndex = cursor.getColumnIndex(VoiceContract.CommandEntry.KEYWORD);
            int ResIndex = cursor.getColumnIndex(VoiceContract.CommandEntry.RESPOND_WORD);
            String everyThing = "";
            while(cursor.moveToNext()){
                String keyword = cursor.getString(KeywordIndex);
                String respond = cursor.getString(ResIndex);
                everyThing += keyword;
                everyThing += keyword;
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, ConstantValues.UnitySetKeyMethod , keyword);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, ConstantValues.UnitySetResMethod , respond);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager,UnityMethod,everyThing);
            }
        } finally{
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    public void getAllQuests(String methodName){
        UnityMethod = methodName;
        String[] projection = {
                VoiceContract.QuestEntry._ID,
                VoiceContract.QuestEntry.TYPE,
                VoiceContract.QuestEntry.INDEX,
                VoiceContract.QuestEntry.ATTRIBUTE,
                VoiceContract.QuestEntry.DATE,
                VoiceContract.QuestEntry.DAYOFWEEK,
                VoiceContract.QuestEntry.TEXT,
                VoiceContract.QuestEntry.ISCOMPLETED
        };

        Cursor cursor = currentContext.getContentResolver().query(
                VoiceContract.QuestEntry.CONTENT_URI,
                projection,
                null,
                null,
                VoiceContract.QuestEntry._ID
        );

        try{
            int iDIndex = cursor.getColumnIndex(VoiceContract.QuestEntry._ID);
            int typeIndex = cursor.getColumnIndex(VoiceContract.QuestEntry.TYPE);
            int inIndex = cursor.getColumnIndex(VoiceContract.QuestEntry.INDEX);
            int attrIndex = cursor.getColumnIndex(VoiceContract.QuestEntry.ATTRIBUTE);
            int dateIndex = cursor.getColumnIndex(VoiceContract.QuestEntry.DATE);
            int dayofweekIndex = cursor.getColumnIndex(VoiceContract.QuestEntry.DAYOFWEEK);
            int textIndex = cursor.getColumnIndex(VoiceContract.QuestEntry.TEXT);
            int isCompletedIndex = cursor.getColumnIndex(VoiceContract.QuestEntry.ISCOMPLETED);
            while(cursor.moveToNext()){
                int id = cursor.getInt(iDIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestIDFromDb" , ""+id);
                int type = cursor.getInt(typeIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestTypeFromDb" , ""+type);
                int index = cursor.getInt(inIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestIndexFromDb" , ""+index);
                int attribute = cursor.getInt(attrIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestAttributeFromDb" , ""+attribute);
                int date = cursor.getInt(dateIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestDateFromDb" , ""+date);
                int dayOfWeek = cursor.getInt(dayofweekIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestDayOfWeekFromDb" , ""+dayOfWeek);
                String text = cursor.getString(textIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestTextFromDb" , text);
                int isCompleded = cursor.getInt(isCompletedIndex);
                // TODO: 2018/1/23 读取isCompleted
                // TODO: 2018/1/23 应传输JSON 数据

                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager,UnityMethod,null);
            }

        }finally{
            cursor.close();
        }
    }

    public void displayCommand(){

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                VoiceContract.CommandEntry._ID,
                VoiceContract.CommandEntry.KEYWORD,
                VoiceContract.CommandEntry.SCENE,
                VoiceContract.CommandEntry.METHOD,
                VoiceContract.CommandEntry.RESPOND_WORD,
                VoiceContract.CommandEntry.CALLED_TIMES,
                VoiceContract.CommandEntry.ANIMATION_TYPE,
                VoiceContract.CommandEntry.ANIMATION_NAME,
                VoiceContract.CommandEntry.ANIMATION_VALUE,
                VoiceContract.CommandEntry.LINK,
                VoiceContract.CommandEntry.COMMENT
        };

        // Perform a query on the provider using the ContentResolver.
        Cursor cursor = currentContext.getContentResolver().query(
                VoiceContract.CommandEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        if(cursor == null){
        }
        int i = 0;

        try{

            while(cursor.moveToNext()){
                i++;
            }
            //ST(String.valueOf(i));
        } finally{
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }

    public void insertNewCommand(String keyword, String respondWord){
        //mDbHelper = new CommandDbHelper(this);
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(VoiceContract.CommandEntry.KEYWORD, keyword);
        values.put(VoiceContract.CommandEntry.SCENE, "123");
        values.put(VoiceContract.CommandEntry.METHOD, "123");
        values.put(VoiceContract.CommandEntry.RESPOND_WORD, respondWord);
        values.put(VoiceContract.CommandEntry.CALLED_TIMES, Integer.parseInt("123"));
        values.put(VoiceContract.CommandEntry.ANIMATION_TYPE, Integer.parseInt("123"));
        values.put(VoiceContract.CommandEntry.ANIMATION_NAME, "123");
        values.put(VoiceContract.CommandEntry.ANIMATION_VALUE, Integer.parseInt("123"));
        values.put(VoiceContract.CommandEntry.LINK, "123");
        values.put(VoiceContract.CommandEntry.COMMENT, "123");
        Uri newUri = currentContext.getContentResolver().insert(VoiceContract.CommandEntry.CONTENT_URI, values);
    }

    public void insertNewQuest(String para, String text){
        ContentValues values= new ContentValues();
        values.put(VoiceContract.QuestEntry.TYPE, Integer.parseInt(para.substring(0,1)));
        values.put(VoiceContract.QuestEntry.INDEX, Integer.parseInt(para.substring(1,2)));
        values.put(VoiceContract.QuestEntry.ATTRIBUTE, Integer.parseInt(para.substring(2,3)));
        values.put(VoiceContract.QuestEntry.DATE, Integer.parseInt(para.substring(3,4)));
        values.put(VoiceContract.QuestEntry.DAYOFWEEK, Integer.parseInt(para.substring(4,5)));
        values.put(VoiceContract.QuestEntry.TEXT, text);
    }



    /**
     * 先插入Responds，获得RES_ID
     * 再插入Sentence，并将RES_ID放入
     * 然后依次插入Keyword，获得KEY_ID
     * 同时插入Match，并将RES_ID和KEY_ID同时放入
     */
    public void insertNewSentence(String sentenceText){
// TODO: 2018/1/25 向数据库插入句子
    }

    public void insertNewKeywords(String keywordText){
// TODO: 2018/1/25 向数据库插入关键词
    }

    public void insertNewResponds(String respondText){
// TODO: 2018/1/25 向数据库插入回复语
    }

    public void insertNewKeyResMatch(int keyID, int resId){
        // TODO: 2018/1/25 向数据库插入关键词和回复语的匹配资料
    }
}
