package com.example.administrator.androidunitytest;

import com.example.administrator.androidunitytest.data.ConstantValues;
import com.baidu.aip.nlp.AipNlp;
import com.example.administrator.androidunitytest.data.TempGlobalValues;
import com.unity3d.player.UnityPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
/**
 * Created by Administrator on 2018/1/24.
 */

public class BaiduLexer {



    public static AipNlp client = new AipNlp(ConstantValues.BAIDU_APP_ID, ConstantValues.BAIDU_API_KEY, ConstantValues.BAIDU_SECRET_KEY);
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
            JSONArray items = jsonObject.optJSONArray(ConstantValues.ITEMS);
            JSONObject item = items.getJSONObject(0);
            //0负向  1中性   2正向
            int sentiment = item.optInt(ConstantValues.SENTIMENT);
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
            int positive_prob = item.optInt(ConstantValues.POSITIVE_PROB);
            int negative_prob = item.optInt(ConstantValues.NEGATIVE_PROB);

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
            JSONArray itemsArray = jsonObject.optJSONArray(ConstantValues.ITEMS);
            TempGlobalValues.gKeywordsValues = null;   //将全局关键词列表清空，准备接收新数据
            for(int i = 0; i < itemsArray.length(); i++){
                JSONObject itemsObject = itemsArray.optJSONObject(i);

                //获得词性
                String pos = itemsObject.optString(ConstantValues.POS);
                String item = itemsObject.optString(ConstantValues.ITEM);

                //如果不重复，将一级分词加入临时全局关键词列表，为接下来的数据库操作做准备
                if(!TempGlobalValues.gKeywordsValues.contains(item)){
                    TempGlobalValues.gKeywordsValues.add(item);
                }

                // TODO: 2018/1/24 添加数据库处理
                System.out.println("一级划分词汇： " + item);
                //if的作用是屏蔽标点符号
                if(!pos.equals(ConstantValues.PUNCTUATION)) {
                    JSONArray basic_words = itemsObject.optJSONArray(ConstantValues.BASIC_WORD);

                    //if的作用是避免为空
                    if (basic_words != null) {
                        for (int j = 0; j < basic_words.length(); j++) {
                            String basic_word = basic_words.getString(j);
                            // TODO: 2018/1/24 添加数据库处理
                            //如果不重复，将二级分词加入临时全局关键词列表，为接下来的数据库操作做准备
                            if(!TempGlobalValues.gKeywordsValues.contains(basic_word)){
                                TempGlobalValues.gKeywordsValues.add(basic_word);
                            }
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
