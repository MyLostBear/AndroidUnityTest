package com.example.administrator.androidunitytest;

import android.os.Bundle;
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


    private void sentenceParse(String sentenceText){
        DBM.logParse(sentenceText);
    }







    /**
     * 先插入Responds，获得RES_ID
     * 再插入Sentence，并将RES_ID放入
     * 然后依次插入Keyword，获得KEY_ID, 同时插入Match，并将RES_ID和KEY_ID同时放入
     */
    private void insertNewSentence(String sentenceText, long res_id){
        DBM.insertNewSentence(sentenceText, res_id);
    }

    private void insertNewKeywords(String keywordText, int weight, long res_id){
        DBM.insertNewKeywords(keywordText, weight, res_id);
    }

    private long insertNewResponds(String respondText){
        return DBM.insertNewResponds(respondText);
    }

    //New Database Test
    private void insertDummyData(){

        String resText = "It's just a drill~";
        String sentence = "THIS IS NOT A DRILL";
        long res_id = insertNewResponds(resText);
        insertNewSentence(sentence, res_id);
        insertNewKeywords("THIS", 1, res_id);
        insertNewKeywords("IS", 1, res_id);
        insertNewKeywords("NOT", 1, res_id);
        insertNewKeywords("A", 1, res_id);
        insertNewKeywords("DRILL", 1, res_id);

        sentenceParse("THIS IS NOT A DRILL");
        sentenceParse("好好学习天天向上，快使用双截棍哼哼哈嘿！");

    }


}
