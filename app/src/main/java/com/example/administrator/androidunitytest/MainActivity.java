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
import com.example.administrator.androidunitytest.data.VoiceContract;
import com.example.administrator.androidunitytest.data.VoiceContract.CommandEntry;
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

    String AndroidManager = "AndroidManager";
    String voiceResult = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化讯飞语音配置对象
        SpeechUtility.createUtility(getApplicationContext(), SpeechConstant.APPID + " = 5a49e627");
    }

    public void VoiceComprehension(){


        //创建recognizer对象，第二个参数：本地听写传入一个InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(getApplicationContext(), null);

        //设置参数
        mIat.setParameter(SpeechConstant.DOMAIN, "iat");
        mIat.setParameter(SpeechConstant.LANGUAGE, "zh_cn");
        mIat.setParameter(SpeechConstant.ACCENT, "mandarin");

        // 设置听写引擎
        mIat.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD);

        // 设置返回结果格式
        mIat.setParameter(SpeechConstant.RESULT_TYPE, "json");

        // 设置语音前端点:静音超时时间，即用户多长时间不说话则当做超时处理
        mIat.setParameter(SpeechConstant.VAD_BOS,  "4000");

        // 设置语音后端点:后端点静音检测时间，即用户停止说话多长时间内即认为不再输入， 自动停止录音
        mIat.setParameter(SpeechConstant.VAD_EOS, "1000");

        mIat.setParameter(SpeechConstant.ASR_PTT, "0");
        //是否动态修正结果， 1为动态
        mIat.setParameter(SpeechConstant.ASR_DWA, "0");
        mIat.startListening(mRecoListener);
    }


    //监听器
    private RecognizerListener mRecoListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            UnityPlayer.UnitySendMessage(AndroidManager, "ShowLog" , "音量变化");
        }

        //开始录音
        @Override
        public void onBeginOfSpeech() {
            UnityPlayer.UnitySendMessage(AndroidManager, "ShowLog" , "开始录音");
        }

        //结束录音
        @Override
        public void onEndOfSpeech() {
            //UnityPlayer.UnitySendMessage(AndroidManager, "ShowLog" , "结束录音");
        }

        //返回的结果
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            //public void
            voiceResult = voiceResult +JsonParser.parseIatResult(recognizerResult.getResultString());
            if(b){
                UnityPlayer.UnitySendMessage(AndroidManager, "ShowLog" , voiceResult);

                //TODO: 2018/1/9 在这里加入语句中断
                voiceResult= "";
            }


        }

        @Override
        public void onError(SpeechError speechError) {

        }

        @Override
        public void onEvent(int i, int i1, int i2, Bundle bundle) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };






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

    private void getCommandKeywords(){
        String[] projecion = {
                CommandEntry.KEYWORD,
                CommandEntry.CALLED_TIMES
        };

        //CursorLoader cursorLoader = new CursorLoader(this, );
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


    private void insertNewCommand(){
        //mDbHelper = new CommandDbHelper(this);
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CommandEntry.KEYWORD, "123");
        values.put(CommandEntry.SCENE, "123");
        values.put(CommandEntry.METHOD, "123");
        values.put(CommandEntry.RESPOND_WORD, "123");
        values.put(CommandEntry.CALLED_TIMES, Integer.parseInt("123"));
        values.put(CommandEntry.ANIMATION_TYPE, Integer.parseInt("123"));
        values.put(CommandEntry.ANIMATION_NAME, "123");
        values.put(CommandEntry.ANIMATION_VALUE, Integer.parseInt("123"));
        values.put(CommandEntry.LINK, "123");
        values.put(CommandEntry.COMMENT, "123");

        //long insertId = db.insert(CommandEntry.TABLE_NAME, null, values);

        Uri newUri = getContentResolver().insert(CommandEntry.CONTENT_URI, values);
    }
    /*
    private void insertNewCommand(final String[] properties){
        mDbHelper = new CommandDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CommandEntry.KEYWORD, properties[0]);
        values.put(CommandEntry.SCENE, properties[1]);
        values.put(CommandEntry.METHOD, properties[2]);
        values.put(CommandEntry.RESPOND_WORD, properties[3]);
        values.put(CommandEntry.CALLED_TIMES, Integer.parseInt(properties[4]));
        values.put(CommandEntry.ANIMATION_TYPE, Integer.parseInt(properties[5]));
        values.put(CommandEntry.ANIMATION_NAME, properties[6]);
        values.put(CommandEntry.ANIMATION_VALUE, Integer.parseInt(properties[7]));
        values.put(CommandEntry.LINK, properties[8]);
        values.put(CommandEntry.COMMENT, properties[9]);

        long insertId = db.insert(CommandEntry.TABLE_NAME, null, values);
    }
    */
}
