package com.example.administrator.androidunitytest;

import com.baidu.aip.nlp.AipNlp;
import com.unity3d.player.UnityPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
/**
 * Created by Administrator on 2018/1/24.
 */

public class BaiduLexer {
    //百度应用ID以及密码
    public static final String APP_ID = "10735065";
    public static final String API_KEY = "90wH8shoI1rhUZ3OgrEKKKWi";
    public static final String SECRET_KEY = "GG1x5PTKGaYhzcMzUVT6SK2Wki10FnTN";

    //百度JSON参数名称
    public static final String ITEMS = "items";     //词汇数组，每个元素对应结果中的一个词
    public static final String ITEM = "item";       //词汇的字符串
    public static final String POS = "pos";         //词性，词性标注算法使用。命名实体识别算法中，此项为空串
    public static final String BASIC_WORD = "basic_words"; //基本词成分

    public static final String SENTIMENT = "sentiment";
    public static final String POSITIVE_PROB = "positive_prob";
    public static final String NEGATIVE_PROB = "negative_prob";
    //百度JSON数据词性参数详情
    public static final String PUNCTUATION = "w";       //标点符号


    public static AipNlp client = new AipNlp(APP_ID, API_KEY, SECRET_KEY);
    public static String text = "";


    static Runnable baiduLexerTask = new Runnable() {
        @Override
        public void run() {
            JSONObject lexerRes = client.lexer(text, null);
            System.out.println("用户输入文本： "+ text);
            baiduLexerPaser(lexerRes);


            // 传入可选参数调用接口
            HashMap<String, Object> options = new HashMap<String, Object>();

            JSONObject res02 = client.sentimentClassify(text, options);
            baiduSentimentParser(res02);
            //System.out.println(res02.toString(2));
        }
    };


    public static void baiduClientInit(){
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        text = "";
    }

    public static void sentenceLexer(String sentence) {
        text = sentence;
        new Thread(baiduLexerTask).start();
    }




    //情感分析
    public static void baiduSentimentParser(JSONObject jsonObject){
        try{
            JSONArray items = jsonObject.optJSONArray(ITEMS);
            JSONObject item = items.getJSONObject(0);
            //0负向  1中性   2正向
            int sentiment = item.optInt(SENTIMENT);
            String sentimentText = "";
            switch(sentiment){
                case 0:
                    sentimentText = "负面";
                    break;
                case 1:
                    sentimentText = "中性";
                    break;
                case 2:
                    sentimentText = "正面";
                    break;
                default:
                    sentimentText = "无结果";
                    break;
            }
            int positive_prob = item.optInt(POSITIVE_PROB);
            int negative_prob = item.optInt(NEGATIVE_PROB);

            // TODO: 2018/1/24 添加数据库处理
            UnityPlayer.UnitySendMessage("AndroidManager", "ShowLog" , sentimentText);
            System.out.println(sentiment);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //分词字符串解析
    public static void baiduLexerPaser(JSONObject jsonObject){
        try{
            JSONArray itemsArray = jsonObject.optJSONArray(ITEMS);
            for(int i = 0; i < itemsArray.length(); i++){
                JSONObject itemsObject = itemsArray.optJSONObject(i);

                //获得词性
                String pos = itemsObject.optString(POS);
                String item = itemsObject.optString(ITEM);
                // TODO: 2018/1/24 添加数据库处理
                System.out.println("一级划分词汇： " + item);
                //if的作用是屏蔽标点符号
                if(!pos.equals(PUNCTUATION)) {
                    JSONArray basic_words = itemsObject.optJSONArray(BASIC_WORD);

                    //if的作用是避免为空
                    if (basic_words != null) {
                        for (int j = 0; j < basic_words.length(); j++) {
                            String basic_word = basic_words.getString(j);
                            // TODO: 2018/1/24 添加数据库处理
                            System.out.println("二级划分词汇： "+basic_word);
                        }
                    }
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }
}
