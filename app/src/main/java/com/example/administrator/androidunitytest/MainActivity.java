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
import com.unity3d.player.UnityPlayerActivity;

public class MainActivity extends UnityPlayerActivity {

    private DataBaseManager DBM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化讯飞语音配置对象
        XunFeiListener.ListenerInit(getApplicationContext());

        BaiduLexer.baiduClientInit();

        DBM = new DataBaseManager(this);
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

    private void getSimpleCommand(String methodName){
       DBM.getSimpleCommand(methodName);
    }

    private void getAllQuests(String methodName){
        DBM.getAllQuests(methodName);
    }

    private void displayCommand(){
        DBM.displayCommand();
    }

    private void insertNewCommand(String keyword, String respondWord){
        DBM.insertNewQuest(keyword,respondWord);
    }

    private void insertNewQuest(String para, String text){
        DBM.insertNewQuest(para, text);
    }


    /**
     * 先插入Responds，获得RES_ID
     * 再插入Sentence，并将RES_ID放入
     * 然后依次插入Keyword，获得KEY_ID
     * 同时插入Match，并将RES_ID和KEY_ID同时放入
     */
    private void insertNewSentence(String sentenceText){
        DBM.insertNewSentence(sentenceText);
    }

    private void insertNewKeywords(String keywordText){
        DBM.insertNewKeywords(keywordText);
    }

    private void insertNewResponds(String respondText){
        DBM.insertNewResponds(respondText);
    }

    private void insertNewKeyResMatch(int keyID, int resId){
        DBM.insertNewKeyResMatch(keyID, resId);
    }

}
