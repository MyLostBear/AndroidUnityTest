package com.example.administrator.androidunitytest;

import android.app.Activity;
import android.app.Dialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import com.example.administrator.androidunitytest.data.ConstantValues;
import com.example.administrator.androidunitytest.data.TempGlobalValues;
import com.unity3d.player.UnityPlayerActivity;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static com.example.administrator.androidunitytest.UnityCommunicator.SendMessageToUnity;

public class MainActivity extends UnityPlayerActivity {

    private DataBaseManager DBM;
    public static MainActivity mainActivity = null;
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainActivity = this;
        preferences = getPreferences(Activity.MODE_PRIVATE);
        //初始化讯飞语音配置对象
        XunFeiListener.ListenerInit(getApplicationContext());

        BaiduLexer.baiduClientInit();

        DBM = new DataBaseManager(this);

        System.out.println("这里是MainActivity的OnCreate方法！！");
    }



    //由Unity调用，使用讯飞进行听写，参数表示结果交还给哪个Unity方法
    private void Comprehension(String unityMethodName){
        XunFeiListener.VoiceComprehension(getApplicationContext(), unityMethodName);
    }

    //由Unity调用，使用数据库进行语意分析
    private void sentenceParse(String sentenceText, String indexString){
//        insertDefauleData();   //查看是否需要插入默认数据
        int propertyIndex = Integer.parseInt(indexString);
        System.out.println("已经接收到属性关键词，其类别为： " + propertyIndex);
        DBM.logParse(sentenceText);
    }


    //暂时由Unity调用，使用讯飞对回复语进行朗读
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
        //DBM.insertNewQuest(keyword,respondWord);
    }

    /*暂时无用
    private void insertNewQuest(String para, String text){
        DBM.insertNewQuest(para, text);
    }
    */



    private void PopDialogView(){
        DBM.queryAllDialog(); //获取所有对话
        System.out.println("已获取全部对话");
        //依次打包为Json，并转存给Unity
        for (Map.Entry dialog:
             ConstantValues.DIALOGS.entrySet()) {
            System.out.println("问句是： "+dialog.getKey().toString());
            System.out.println("回答是： "+dialog.getValue().toString());
            String dialogJson = JsonParser.packDialog(dialog.getKey().toString(), dialog.getValue().toString());
            System.out.println(dialogJson);
            SendMessageToUnity(ConstantValues.UNITY_GET_ONE_DIALOG,dialogJson);
            //System.out.println("存入一句");
        }
        //SendMessageToUnity(ConstantValues.UNITY_POP_DIALOG_VIEW,null);
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

    //新插入，type为1
    private long insertNewResponds(String respondText){
        return DBM.insertNewResponds(respondText, 1);
    }

    /*
    本方法用于第一次打开游戏时安装默认数据
    */
    public void insertDefauleData(){
        Boolean isDefaultDataInserted = preferences.getBoolean("isInserted", false);
        if(!isDefaultDataInserted){   //如果尚未安装默认数据

            DBM.insertDefaultData();   //则安装默认数据
            editor = preferences.edit();
            editor.putBoolean("isInserted", true);    //并告知系统“已经安装默认数据”
            editor.apply();  //确认提交
        }
    }

    //学习新的语句
    private void learnNewDialog(String dialogText){
        String[] dialog = JsonParser.parseUnityDialog(dialogText);
        String queText = dialog[0]; //玩家问句
        String resText = dialog[1]; //应答语句
        System.out.println("已经完成Json分析,提取出两句: " + queText);
        System.out.println("以及： " + resText);
        long res_id = insertNewResponds(resText);
        insertNewSentence(queText, res_id);
    }
}
