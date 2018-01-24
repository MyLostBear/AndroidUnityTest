package com.example.administrator.androidunitytest;
import android.content.Context;
import android.os.Bundle;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechSynthesizer;
import com.iflytek.cloud.SpeechUtility;
import com.iflytek.cloud.SynthesizerListener;
import com.unity3d.player.UnityPlayer;

/**
 * Created by Administrator on 2018/1/24.
 */

public class XunFeiListener {
    public static String ID = " = 5a49e627";
    public static String voiceResult = "";
    public static void ListenerInit(Context context){
        SpeechUtility.createUtility(context, SpeechConstant.APPID + ID);
    }

    public static void VoiceComprehension(Context context){


        //创建recognizer对象，第二个参数：本地听写传入一个InitListener
        SpeechRecognizer mIat = SpeechRecognizer.createRecognizer(context, null);

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
    private static RecognizerListener mRecoListener = new RecognizerListener() {
        @Override
        public void onVolumeChanged(int i, byte[] bytes) {
            UnityPlayer.UnitySendMessage(UnityCommunicator.AndroidManager, UnityCommunicator.SHOW_LOG , "正在录音");
        }

        //开始录音
        @Override
        public void onBeginOfSpeech() {
            UnityPlayer.UnitySendMessage(UnityCommunicator.AndroidManager, UnityCommunicator.SHOW_LOG , "开始录音");
        }

        //结束录音
        @Override
        public void onEndOfSpeech() {
            //UnityPlayer.UnitySendMessage(AndroidManager, UnityMethod , "结束录音");
        }

        //返回的结果
        @Override
        public void onResult(RecognizerResult recognizerResult, boolean b) {
            //public void
            voiceResult = voiceResult +JsonParser.parseIatResult(recognizerResult.getResultString());
            if(b){
                UnityPlayer.UnitySendMessage(UnityCommunicator.AndroidManager, UnityCommunicator.SHOW_LOG , voiceResult);

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


    public static void VoiceSpeech(Context context, String speechText){
        SpeechSynthesizer mTts = SpeechSynthesizer.createSynthesizer(context, null);

        // 清空参数
        mTts.setParameter(SpeechConstant.PARAMS, null);

        mTts.setParameter(SpeechConstant.ENGINE_TYPE, SpeechConstant.TYPE_CLOUD); //设置云端
        mTts.setParameter(SpeechConstant.VOICE_NAME, "xiaoping");//设置发音人
        mTts.setParameter(SpeechConstant.SPEED, "50");//设置语速
        //设置合成音调
        mTts.setParameter(SpeechConstant.PITCH, "50");
        mTts.setParameter(SpeechConstant.VOLUME, "80");//设置音量，范围0~100
        mTts.setParameter(SpeechConstant.STREAM_TYPE, "3");
        // 设置播放合成音频打断音乐播放，默认为true
        mTts.setParameter(SpeechConstant.KEY_REQUEST_FOCUS, "true");

        // 设置音频保存路径，保存音频格式支持pcm、wav，设置路径为sd卡请注意WRITE_EXTERNAL_STORAGE权限
        // 注：AUDIO_FORMAT参数语记需要更新版本才能生效
//        mTts.setParameter(SpeechConstant.AUDIO_FORMAT, "wav");
//        boolean isSuccess = mTts.setParameter(SpeechConstant.TTS_AUDIO_PATH, Environment.getExternalStorageDirectory() + "/msc/tts2.wav");
//        Toast.makeText(MainActivity.this, "语音合成 保存音频到本地：\n" + isSuccess, Toast.LENGTH_LONG).show();
        //3.开始合成
        int code = mTts.startSpeaking(speechText,mSynListener );

        if (code != ErrorCode.SUCCESS) {
            if (code == ErrorCode.ERROR_COMPONENT_NOT_INSTALLED) {
                //上面的语音配置对象为初始化时：
                //Toast.makeText(MainActivity.this, "语音组件未安装", Toast.LENGTH_LONG).show();
            } else {
                //Toast.makeText(MainActivity.this, "语音合成失败,错误码: " + code, Toast.LENGTH_LONG).show();
            }
        }
    }

    //合成监听器
    private static SynthesizerListener mSynListener = new SynthesizerListener() {
        //会话结束回调接口，没有错误时，error为null
        public void onCompleted(SpeechError error) {
        }

        //缓冲进度回调
        //percent为缓冲进度0~100，beginPos为缓冲音频在文本中开始位置，endPos表示缓冲音频在文本中结束位置，info为附加信息。
        public void onBufferProgress(int percent, int beginPos, int endPos, String info) {
        }

        //开始播放
        public void onSpeakBegin() {
        }

        //暂停播放
        public void onSpeakPaused() {
        }

        //播放进度回调
        //percent为播放进度0~100,beginPos为播放音频在文本中开始位置，endPos表示播放音频在文本中结束位置.
        public void onSpeakProgress(int percent, int beginPos, int endPos) {
        }

        //恢复播放回调接口
        public void onSpeakResumed() {
        }

        //会话事件回调接口
        public void onEvent(int arg0, int arg1, int arg2, Bundle arg3) {
        }
    };
}
