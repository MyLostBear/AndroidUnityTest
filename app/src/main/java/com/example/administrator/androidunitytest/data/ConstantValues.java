package com.example.administrator.androidunitytest.data;

import java.util.HashMap;

/**
 * Created by ZK on 2018/1/25.
 * All the constantValues except DataBase contract store in this class.
 */

public class ConstantValues {

    public static final long DATA_NOT_FOUND = -101;
    public static final float KEYWORD_MATCH_THRESHOLD = 0.8f;   //关键词匹配阈值，大于此值认为匹配

    /**
     * Unity
     */
    //调用unity时所要找的unity的GameObject
    public static String UnityAndroidManager = "AndroidManager";

    //unity获取一条dialog
    public static String UNITY_GET_ONE_DIALOG = "GetOneDialog";

    //unity填充dialogView
    public static String UNITY_POP_DIALOG_VIEW = "PopDialogView";

    //调用unity时所要找的unity的方法
    public static String SHOW_LOG = "ShowLog";

    //unity DialogData类的两个字段
    public static String UNITY_DIALOG_SPEECH = "speechText";
    public static String UNITY_DIALOG_RESPOND = "respondText";

    //对话missmatch时所需要调用的Unity方法名
    public static String UNITY_DIALOG_MISSMATCH = "MissMatch";

    public static String UNITY_SET_KEY_METHOD = "GetKeyFromDB";
    public static String UNITY_SET_RES_METHOD = "GetResFromDB";

    public static String UNITY_RES_RECEIVER = "RespondShow";



    public static String[] MISMATCH_RESPONDS = {
            "听不明白你说的话，要教我怎么回答吗？",
            "嘿嘿，不好意思，没听明白你说的是什么。",
            "对不起啦，我没听懂。",
            "唉，我还没办法理解这么复杂的人类语言，要不你再教教我？",
            "真的不明白，抱歉。",
            "又没听懂，对不起啦。",
            "人工智能表示，自己还没有这么智能，请继续调教。",
            "信息量太大，不明白。",
            "不懂啊，真的不懂……"
    };
    public static String UNITY_DEFAULT_REPLY = "听不明白你说的话，要教我怎么回答吗？";

    public static String UNITY_JSON_QUESTION = "speechText";   //Unity传来的Dialog Json中，问句的名字
    public static String UNITY_JSON_RESPOND = "respondText";     //Unity传来的Dialog Json中，回答的名字

    public static String UNITY_RECORD_NOTHING = "NoSoundListened";


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

    public static int QUERY_FROM_SENTENCE = -1;   //表示整句匹配，不需要进行阈值计算
    public static int QUERY_FROM_SENTENCE_BY_TYPE = -2;  //表示整句匹配，不需要进行阈值计算，且丢弃掉非用户输入的数据

    /**
     * Data
     */
    public static HashMap<String, String> DIALOGS = new HashMap<>();   //用于储存所有从数据库里取出的对话数据


    /**
     * Entity Type
     */
    public static int ENTITY_TYPE_UNKNOW = 0;     //entity类型不明
    public static int ENTITY_TYPE_PERSON_NAME = 1;   //entity类型为“人名”
    public static int ENTITY_TYPE_ORG_NAME = 2;    //entity类型为“机构名”
}
