package com.example.administrator.androidunitytest;
import android.content.Context;

import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;
/**
 * Created by Administrator on 2018/1/24.
 */

public class XunFeiListener {
    public static String ID = " = 5a49e627";
    
    public static void ListenerInit(Context context){
        SpeechUtility.createUtility(context, SpeechConstant.APPID + ID);
    }


}
