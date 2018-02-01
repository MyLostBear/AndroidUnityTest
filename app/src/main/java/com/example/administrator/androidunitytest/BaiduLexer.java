package com.example.administrator.androidunitytest;

import com.example.administrator.androidunitytest.data.ConstantValues;
import com.baidu.aip.nlp.AipNlp;
import com.example.administrator.androidunitytest.data.TempGlobalValues;
import com.unity3d.player.UnityPlayer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
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
            System.out.println("分词进程已经建立");
            JSONObject lexerRes = client.lexer(text, null);
            System.out.println("百度服务器应答完成，返回的Json码为： " + lexerRes.toString());
            //System.out.println("用户输入文本： "+ text);
            baiduLexerPaser(lexerRes);;
            /*
            *暂不调用情感分析
            // 传入可选参数调用接口
            HashMap<String, Object> options = new HashMap<String, Object>();

            JSONObject res02 = client.sentimentClassify(text, options);
            baiduSentimentParser(res02);
            //System.out.println(res02.toString(2));
            */


            System.out.println("线程结束位！！！！！");
        }
    };

    public static void baiduClientInit(){
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);
        text = "";
    }

    //此处为分词入口
    public static void sentenceLexer(String sentence) {
        text = sentence;
        System.out.println("已进入百度分词管理器，传入的句子为： " + sentence);
        // FIXME: 2018/1/27 此处的run需要改为start，否则会有明显卡顿
        new Thread(baiduLexerTask).run();
        System.out.println("线程跳出");
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

            // TODO: 2018/1/26 此处为情感分析，后续需要处理
            //UnityPlayer.UnitySendMessage("AndroidManager", "ShowLog" , sentimentText);
            //System.out.println(sentiment);
        }catch (JSONException e) {
            e.printStackTrace();
        }
    }

    //分词字符串解析
    public static void baiduLexerPaser(JSONObject jsonObject){
        try{
            System.out.println("已经入百度管理器Json分析");
            JSONArray itemsArray = jsonObject.optJSONArray(ConstantValues.ITEMS);
            TempGlobalValues.gKeywordsValues.clear();   //将全局关键词列表清空，准备接收新数据
            for(int i = 0; i < itemsArray.length(); i++){
                JSONObject itemsObject = itemsArray.optJSONObject(i);

                //获得词性
                String pos = itemsObject.optString(ConstantValues.POS);
                String item = itemsObject.optString(ConstantValues.ITEM);

                //if的作用是屏蔽标点符号
                if(!pos.equals(ConstantValues.PUNCTUATION)) {
                    System.out.println("一级划分词汇： "+ item);
                    //如果不重复，将一级分词加入临时全局关键词列表，为接下来的数据库操作做准备
                    if(!TempGlobalValues.gKeywordsValues.contains(item)){
                        TempGlobalValues.gKeywordsValues.add(item);
                        System.out.println("一级未重复词汇： " + item);
                    }
                    

                    JSONArray basic_words = itemsObject.optJSONArray(ConstantValues.BASIC_WORD);

                    //if的作用是避免为空
                    if (basic_words != null) {
                        for (int j = 0; j < basic_words.length(); j++) {
                            String basic_word = basic_words.getString(j);
                            System.out.println("二级划分词汇： "+basic_word);
                            //如果不重复，将二级分词加入临时全局关键词列表，为接下来的数据库操作做准备
                            if(!TempGlobalValues.gKeywordsValues.contains(basic_word)){
                                TempGlobalValues.gKeywordsValues.add(basic_word);
                                System.out.println("二级未重复词汇： "+basic_word);
                            }

                        }
                    }
                }
            }
        }catch (JSONException e){
            e.printStackTrace();
        }
    }

}
