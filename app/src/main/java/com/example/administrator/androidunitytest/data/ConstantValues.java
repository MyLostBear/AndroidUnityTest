package com.example.administrator.androidunitytest.data;

/**
 * Created by ZK on 2018/1/25.
 * All the constantValues except DataBase contract store in this class.
 */

public class ConstantValues {

    public static final long DATA_NOT_FOUND = -101;


    /**
     * Unity
     */
    //调用unity时所要找的unity的GameObject
    public static String UnityAndroidManager = "AndroidManager";

    //调用unity时所要找的unity的方法
    public static String SHOW_LOG = "ShowLog";


    public static String UnitySetKeyMethod = "GetKeyFromDB";
    public static String UnitySetResMethod = "GetResFromDB";



    /**
     * Baidu
     */
    //百度应用ID以及密码
    public static final String BAIDU_APP_ID = "10735065";
    public static final String BAIDU_API_KEY = "90wH8shoI1rhUZ3OgrEKKKWi";
    public static final String BAIDU_SECRET_KEY = "GG1x5PTKGaYhzcMzUVT6SK2Wki10FnTN";

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

    /**
     * Xunfei
     */
    public static final String XUNFEI_APP_ID = " = 5a49e627";

    /**
     * DataBase IDs;
     */
    public static long RES_ID_INSERT = -1;
    public static long KEY_ID_INSERT = -1;

    public static long RES_ID_QUERY = -1;
    public static long KEY_ID_QUERY = -1;
}
