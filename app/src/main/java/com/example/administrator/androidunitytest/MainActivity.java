package com.example.administrator.androidunitytest;

import android.content.ContentValues;
import android.content.CursorLoader;
import android.database.sqlite.SQLiteDatabase;
import android.database.Cursor;
import android.net.Uri;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.administrator.androidunitytest.data.CommandDbHelper;
import com.example.administrator.androidunitytest.data.VoiceContract.*;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
import com.unity3d.player.UnityPlayer;
import com.unity3d.player.UnityPlayerActivity;

public class MainActivity extends UnityPlayerActivity {

    //instance of database;
    private CommandDbHelper mDbHelper;

    //调用unity时所要找的unity的GameObject
    String AndroidManager = "AndroidManager";

    //调用unity时所要找的unity的方法
    String UnityMethod  = "";
    String UnitySetKeyMethod = "GetKeyFromDB";
    String UnitySetResMethod = "GetResFromDB";
    String voiceResult = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化讯飞语音配置对象
        XunFeiListener.ListenerInit(getApplicationContext());
        //SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + " = 5a49e627");
        BaiduLexer.baiduClientInit();
        BaiduLexer.sentenceLexer("特朗普任期满一年，美国共和党政府停摆");
    }


    private void Comprehension(){
        XunFeiListener.VoiceComprehension(getApplicationContext());
    }

    private void Speech(String speechText){
        XunFeiListener.VoiceSpeech(getApplicationContext(), speechText);
    }
    public void ST(final String msg){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_LONG).show();
            }
        });
    }


    /**
     * 以下为数据库处理部分
     */

    private void dataBaseTest(){
        mDbHelper = new CommandDbHelper(this);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();
    }



    private void getSimpleCommand(String methodName){
        UnityMethod = methodName;
        String[] projection = {
                CommandEntry.KEYWORD,
                CommandEntry.SCENE,
                CommandEntry.RESPOND_WORD,
                CommandEntry.CALLED_TIMES,
        };

        Cursor cursor = getContentResolver().query(
                CommandEntry.CONTENT_URI,
                projection,
                null,
                null,
                CommandEntry.CALLED_TIMES
        );

        try{
            //cursor.moveToFirst();
            int KeywordIndex = cursor.getColumnIndex(CommandEntry.KEYWORD);
            int ResIndex = cursor.getColumnIndex(CommandEntry.RESPOND_WORD);
            String everyThing = "";
            while(cursor.moveToNext()){
                String keyword = cursor.getString(KeywordIndex);
                String respond = cursor.getString(ResIndex);
                everyThing += keyword;
                everyThing += keyword;
                UnityPlayer.UnitySendMessage(AndroidManager, UnitySetKeyMethod , keyword);
                UnityPlayer.UnitySendMessage(AndroidManager, UnitySetResMethod , respond);
                UnityPlayer.UnitySendMessage(AndroidManager,UnityMethod,everyThing);
            }
        } finally{
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    private void getAllQuests(String methodName){
        UnityMethod = methodName;
        String[] projection = {
                QuestEntry._ID,
                QuestEntry.TYPE,
                QuestEntry.INDEX,
                QuestEntry.ATTRIBUTE,
                QuestEntry.DATE,
                QuestEntry.DAYOFWEEK,
                QuestEntry.TEXT,
                QuestEntry.ISCOMPLETED
        };

        Cursor cursor = getContentResolver().query(
                QuestEntry.CONTENT_URI,
                projection,
                null,
                null,
                QuestEntry._ID
        );

        try{
            int iDIndex = cursor.getColumnIndex(QuestEntry._ID);
            int typeIndex = cursor.getColumnIndex(QuestEntry.TYPE);
            int inIndex = cursor.getColumnIndex(QuestEntry.INDEX);
            int attrIndex = cursor.getColumnIndex(QuestEntry.ATTRIBUTE);
            int dateIndex = cursor.getColumnIndex(QuestEntry.DATE);
            int dayofweekIndex = cursor.getColumnIndex(QuestEntry.DAYOFWEEK);
            int textIndex = cursor.getColumnIndex(QuestEntry.TEXT);
            int isCompletedIndex = cursor.getColumnIndex(QuestEntry.ISCOMPLETED);
            while(cursor.moveToNext()){
                int id = cursor.getInt(iDIndex);
                UnityPlayer.UnitySendMessage(AndroidManager, "GetQuestIDFromDb" , ""+id);
                int type = cursor.getInt(typeIndex);
                UnityPlayer.UnitySendMessage(AndroidManager, "GetQuestTypeFromDb" , ""+type);
                int index = cursor.getInt(inIndex);
                UnityPlayer.UnitySendMessage(AndroidManager, "GetQuestIndexFromDb" , ""+index);
                int attribute = cursor.getInt(attrIndex);
                UnityPlayer.UnitySendMessage(AndroidManager, "GetQuestAttributeFromDb" , ""+attribute);
                int date = cursor.getInt(dateIndex);
                UnityPlayer.UnitySendMessage(AndroidManager, "GetQuestDateFromDb" , ""+date);
                int dayOfWeek = cursor.getInt(dayofweekIndex);
                UnityPlayer.UnitySendMessage(AndroidManager, "GetQuestDayOfWeekFromDb" , ""+dayOfWeek);
                String text = cursor.getString(textIndex);
                UnityPlayer.UnitySendMessage(AndroidManager, "GetQuestTextFromDb" , text);
                int isCompleded = cursor.getInt(isCompletedIndex);
                // TODO: 2018/1/23 读取isCompleted
                // TODO: 2018/1/23 应传输JSON 数据

                UnityPlayer.UnitySendMessage(AndroidManager,UnityMethod,null);
            }

        }finally{
            cursor.close();
        }
    }

    private void displayCommand(){

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                CommandEntry._ID,
                CommandEntry.KEYWORD,
                CommandEntry.SCENE,
                CommandEntry.METHOD,
                CommandEntry.RESPOND_WORD,
                CommandEntry.CALLED_TIMES,
                CommandEntry.ANIMATION_TYPE,
                CommandEntry.ANIMATION_NAME,
                CommandEntry.ANIMATION_VALUE,
                CommandEntry.LINK,
                CommandEntry.COMMENT
        };

        // Perform a query on the provider using the ContentResolver.
        Cursor cursor = getContentResolver().query(
                CommandEntry.CONTENT_URI,
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
            ST(String.valueOf(i));
        } finally{
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }

    private void insertNewCommand(String keyword, String respondWord){
        //mDbHelper = new CommandDbHelper(this);
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CommandEntry.KEYWORD, keyword);
        values.put(CommandEntry.SCENE, "123");
        values.put(CommandEntry.METHOD, "123");
        values.put(CommandEntry.RESPOND_WORD, respondWord);
        values.put(CommandEntry.CALLED_TIMES, Integer.parseInt("123"));
        values.put(CommandEntry.ANIMATION_TYPE, Integer.parseInt("123"));
        values.put(CommandEntry.ANIMATION_NAME, "123");
        values.put(CommandEntry.ANIMATION_VALUE, Integer.parseInt("123"));
        values.put(CommandEntry.LINK, "123");
        values.put(CommandEntry.COMMENT, "123");
        Uri newUri = getContentResolver().insert(CommandEntry.CONTENT_URI, values);
    }



    private void insertNewQuest(String para, String text){
        ContentValues values= new ContentValues();
        values.put(QuestEntry.TYPE, Integer.parseInt(para.substring(0,1)));
        values.put(QuestEntry.INDEX, Integer.parseInt(para.substring(1,2)));
        values.put(QuestEntry.ATTRIBUTE, Integer.parseInt(para.substring(2,3)));
        values.put(QuestEntry.DATE, Integer.parseInt(para.substring(3,4)));
        values.put(QuestEntry.DAYOFWEEK, Integer.parseInt(para.substring(4,5)));
        values.put(QuestEntry.TEXT, text);
    }

}
