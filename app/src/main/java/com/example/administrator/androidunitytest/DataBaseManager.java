package com.example.administrator.androidunitytest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.example.administrator.androidunitytest.data.ConstantValues;
import com.example.administrator.androidunitytest.data.DataBaseContract;
import com.example.administrator.androidunitytest.data.QuestsData;
import com.example.administrator.androidunitytest.data.TempGlobalValues;
import com.unity3d.player.UnityPlayer;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static com.example.administrator.androidunitytest.data.ConstantValues.UNITY_RES_RECEIVER;

/**
 * Created by Administrator on 2018/1/24.
 */

public class DataBaseManager {
    private Context currentContext;
    HashMap<Long, Integer> resIdMap;   //用于resId的加权操作
    public DataBaseManager(Context context){
        currentContext = context;
        resIdMap = new HashMap<>();
    }

    private static String UnityMethod  = "";

    public void getSimpleCommand(String methodName){
        UnityMethod = methodName;
        String[] projection = {
                DataBaseContract.CommandEntry.KEYWORD,
                DataBaseContract.CommandEntry.SCENE,
                DataBaseContract.CommandEntry.RESPOND_WORD,
                DataBaseContract.CommandEntry.CALLED_TIMES,
        };

        Cursor cursor = currentContext.getContentResolver().query(
                DataBaseContract.CommandEntry.CONTENT_URI,
                projection,
                null,
                null,
                DataBaseContract.CommandEntry.CALLED_TIMES
        );

        try{
            //cursor.moveToFirst();
            int KeywordIndex = cursor.getColumnIndex(DataBaseContract.CommandEntry.KEYWORD);
            int ResIndex = cursor.getColumnIndex(DataBaseContract.CommandEntry.RESPOND_WORD);
            String everyThing = "";
            while(cursor.moveToNext()){
                String keyword = cursor.getString(KeywordIndex);
                String respond = cursor.getString(ResIndex);
                everyThing += keyword;
                everyThing += keyword;
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, ConstantValues.UNITY_SET_KEY_METHOD, keyword);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, ConstantValues.UNITY_SET_RES_METHOD, respond);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager,UnityMethod,everyThing);
            }
        } finally{
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }
    }

    public void getAllQuests(String methodName){
        UnityMethod = methodName;
        String[] projection = {
                DataBaseContract.QuestEntry._ID,
                DataBaseContract.QuestEntry.TYPE,
                DataBaseContract.QuestEntry.INDEX,
                DataBaseContract.QuestEntry.ATTRIBUTE,
                DataBaseContract.QuestEntry.DATE,
                DataBaseContract.QuestEntry.DAYOFWEEK,
                DataBaseContract.QuestEntry.TEXT,
                DataBaseContract.QuestEntry.ISCOMPLETED
        };

        Cursor cursor = currentContext.getContentResolver().query(
                DataBaseContract.QuestEntry.CONTENT_URI,
                projection,
                null,
                null,
                DataBaseContract.QuestEntry._ID
        );

        try{
            int iDIndex = cursor.getColumnIndex(DataBaseContract.QuestEntry._ID);
            int typeIndex = cursor.getColumnIndex(DataBaseContract.QuestEntry.TYPE);
            int inIndex = cursor.getColumnIndex(DataBaseContract.QuestEntry.INDEX);
            int attrIndex = cursor.getColumnIndex(DataBaseContract.QuestEntry.ATTRIBUTE);
            int dateIndex = cursor.getColumnIndex(DataBaseContract.QuestEntry.DATE);
            int dayofweekIndex = cursor.getColumnIndex(DataBaseContract.QuestEntry.DAYOFWEEK);
            int textIndex = cursor.getColumnIndex(DataBaseContract.QuestEntry.TEXT);
            int isCompletedIndex = cursor.getColumnIndex(DataBaseContract.QuestEntry.ISCOMPLETED);
            while(cursor.moveToNext()){
                int id = cursor.getInt(iDIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestIDFromDb" , ""+id);
                int type = cursor.getInt(typeIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestTypeFromDb" , ""+type);
                int index = cursor.getInt(inIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestIndexFromDb" , ""+index);
                int attribute = cursor.getInt(attrIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestAttributeFromDb" , ""+attribute);
                int date = cursor.getInt(dateIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestDateFromDb" , ""+date);
                int dayOfWeek = cursor.getInt(dayofweekIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestDayOfWeekFromDb" , ""+dayOfWeek);
                String text = cursor.getString(textIndex);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, "GetQuestTextFromDb" , text);
                int isCompleded = cursor.getInt(isCompletedIndex);
                // TODO: 2018/1/23 读取isCompleted
                // TODO: 2018/1/23 应传输JSON 数据

                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager,UnityMethod,null);
            }

        }finally{
            cursor.close();
        }
    }

    public void displayCommand(){

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                DataBaseContract.CommandEntry._ID,
                DataBaseContract.CommandEntry.KEYWORD,
                DataBaseContract.CommandEntry.SCENE,
                DataBaseContract.CommandEntry.METHOD,
                DataBaseContract.CommandEntry.RESPOND_WORD,
                DataBaseContract.CommandEntry.CALLED_TIMES,
                DataBaseContract.CommandEntry.ANIMATION_TYPE,
                DataBaseContract.CommandEntry.ANIMATION_NAME,
                DataBaseContract.CommandEntry.ANIMATION_VALUE,
                DataBaseContract.CommandEntry.LINK,
                DataBaseContract.CommandEntry.COMMENT
        };

        // Perform a query on the provider using the ContentResolver.
        Cursor cursor = currentContext.getContentResolver().query(
                DataBaseContract.CommandEntry.CONTENT_URI,
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
            //ST(String.valueOf(i));
        } finally{
            // Always close the cursor when you're done reading from it. This releases all its
            // resources and makes it invalid.
            cursor.close();
        }

    }

    public void insertNewCommand(String keyword, String respondWord){
        //mDbHelper = new CommandDbHelper(this);
        //SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.CommandEntry.KEYWORD, keyword);
        values.put(DataBaseContract.CommandEntry.SCENE, "123");
        values.put(DataBaseContract.CommandEntry.METHOD, "123");
        values.put(DataBaseContract.CommandEntry.RESPOND_WORD, respondWord);
        values.put(DataBaseContract.CommandEntry.CALLED_TIMES, Integer.parseInt("123"));
        values.put(DataBaseContract.CommandEntry.ANIMATION_TYPE, Integer.parseInt("123"));
        values.put(DataBaseContract.CommandEntry.ANIMATION_NAME, "123");
        values.put(DataBaseContract.CommandEntry.ANIMATION_VALUE, Integer.parseInt("123"));
        values.put(DataBaseContract.CommandEntry.LINK, "123");
        values.put(DataBaseContract.CommandEntry.COMMENT, "123");
        Uri newUri = currentContext.getContentResolver().insert(DataBaseContract.CommandEntry.CONTENT_URI, values);
    }

    public void insertNewQuest(int type, int index, String attribute, int date, int day_of_week, String text, int is_completed){
        ContentValues values= new ContentValues();
        values.put(DataBaseContract.QuestEntry.TYPE, type);
        values.put(DataBaseContract.QuestEntry.INDEX, index);
        values.put(DataBaseContract.QuestEntry.ATTRIBUTE, attribute);
        values.put(DataBaseContract.QuestEntry.DATE, date);
        values.put(DataBaseContract.QuestEntry.DAYOFWEEK, day_of_week);
        values.put(DataBaseContract.QuestEntry.TEXT, text);
        values.put(DataBaseContract.QuestEntry.ISCOMPLETED, is_completed);
        currentContext.getContentResolver().insert(DataBaseContract.QuestEntry.CONTENT_URI, values);
    }


    /**
     *分析句子
     */
    public void logParse(String sentenceText){
        String respondText; //回复语文本
        resIdMap.clear();   //清空resIdMap， 准备新的加权操作
        long res_id = querySentence(sentenceText);   //整句分析
        if(res_id != ConstantValues.DATA_NOT_FOUND){   //整句匹配
            respondText = queryResponse(res_id , ConstantValues.QUERY_FROM_SENTENCE);       //查询回复语
            UnityCommunicator.SendMessageToUnity(UNITY_RES_RECEIVER, respondText);   //调用Unity方法，返回回复语文本
            return;
        } else{   //整句分析不匹配，转入关键词分析
            BaiduLexer.sentenceLexer(sentenceText);  //交由百度分词，结果存入临时全局关键词列表
            if(TempGlobalValues.gKeywordsValues != null){     //如果临时全局关键词列表不为空
                for (String keyword:                          //遍历列表，在数据库中查找相同关键词
                        TempGlobalValues.gKeywordsValues) {
                    long key_id = queryKeyword(keyword);
                    if(key_id != ConstantValues.DATA_NOT_FOUND){  //如果找到了;
                        queryKeyResMatch(key_id);   //在方法内做加权处理
                    }
                }

                //根据加权结果选择res_id
                long id_candidate = 0;
                Integer currentMax = 0;
                for (Map.Entry<Long, Integer> entry :
                        resIdMap.entrySet()) {
                    if(entry.getValue() > currentMax){
                        id_candidate = entry.getKey();
                        currentMax = entry.getValue();
                    }
                }
                System.out.println("根据关键词匹配到答案，所匹配到的关键词数量是： " + currentMax);
                if(currentMax != 0) {
                    respondText = queryResponse(id_candidate, currentMax);  //根据id找到回复语，内部有阈值判断
                    UnityCommunicator.SendMessageToUnity(UNITY_RES_RECEIVER, respondText);   //调用Unity方法，返回回复语文本
                    return;
                }
            }
        }
        UnityCommunicator.SendMessageToUnity(UNITY_RES_RECEIVER, ConstantValues.UNITY_DEFAULT_REPLY);
        UnityCommunicator.SendMessageToUnity(ConstantValues.UNITY_DIALOG_MISSMATCH, "");    //失配，提示是否进入教学
    }

    /*
    插入基础数据，
     */
    public void insertDefaultData(){
        // FIXME: 2018/1/31 每次更新时可以新建一个方法和一个preference，避免重写数据
        // FIXME: 2018/1/31 再Unity里根据每次更新写一个提示，如“首次安装游戏，初始化数据需要时间，请稍候~” 或 “游戏更新，正在初始化数据，请稍候”
        insertDefaultDialog();
        insertDefaultQuest();
    }

    //插入任务
    public void insertDefaultQuest(){
        List<QuestsData> questsDataList = new ArrayList<>();
        questsDataList.add(new QuestsData(1, 1, "姓名", 1, 1, "聊一聊姓名", 0));
        questsDataList.add(new QuestsData(1, 2, "姓名", 1, 1, "谈一谈姓名", 0));
        for (QuestsData quest:
             questsDataList) {
            insertNewQuest(quest.type, quest.index, quest.attribute, quest.date, quest.day_of_week, quest.text, quest.is_completed);
        }
    }

    //插入基础对话
    public void insertDefaultDialog(){
        System.out.println("准备插入默认数据");
        HashMap<String, String> Dialogs = new HashMap<>();
        String question;
        String respond;
        Dialogs.put("你好","你好呀。");
        Dialogs.put("你好呀","你也好呀。");
        Dialogs.put("你好吗","我挺好的呀，你呢？");
        Dialogs.put("我挺好的","你好我就好~");
        Dialogs.put("我很好","大家好才是真的好");

        Dialogs.put("讲个笑话","没想到啊没想到 ，你朱时茂这浓眉大眼的家伙也叛变革命了");
        Dialogs.put("讲个故事","真的勇士，就是可以徒手将巧克力掰成四瓣，却只吃其中一块。");
        Dialogs.put("唱首歌","算了，我五音不全的。");

        Dialogs.put("我不怎么样","怎么了，发生什么了吗？");
        Dialogs.put("我不怎么好","为什么啊？");
        Dialogs.put("我不大好","怎么了，发生什么了吗");
        Dialogs.put("我不好","谁说的，你挺好的");
        Dialogs.put("你好啊","你好啊。");
        Dialogs.put("早上好","早上好呀");
        Dialogs.put("天气不错","对啊，确实挺好的");
        Dialogs.put("今天怎么样","我挺好的，你呢？");
        Dialogs.put("我今天怎么样","我不知道呀");
        Dialogs.put("我心情不好","你怎么了啊？");
        Dialogs.put("我心情不错","我心情也不错哦");
        Dialogs.put("你早","你也早");
        Dialogs.put("晚上好","晚上好");
        Dialogs.put("晚安","晚安，明天再见");
        Dialogs.put("吃了吗","人工智能并不需要吃饭哦");
        Dialogs.put("你吃饭了吗","我不需要吃饭的");
        Dialogs.put("你吃饱了吗","你把电充满了，我就饱了。");
        Dialogs.put("我要吃饭","去定个外卖呗");
        Dialogs.put("你是谁","我就是你啊");
        Dialogs.put("我是谁","你就是我啊");
        Dialogs.put("你知道你自己是谁吗","知道啊，我就是你啊。");
        Dialogs.put("你知道我是谁吗","知道啊，你就是我啊。");
        Dialogs.put("现在几点了","手机就在你手里啊，抬头看一眼呗");
        Dialogs.put("语音测试","别测了，赶快写代码吧");
        Dialogs.put("你很厉害","哈哈哈哈，你也很厉害啊");
        Dialogs.put("再见","再见，一路顺风");
        Dialogs.put("跳个舞","还没学会呢");
        Dialogs.put("听得懂吗","不懂装懂呗，嘿嘿");
        Dialogs.put("能明白吗","你觉得我能明白吗？");
        Dialogs.put("你叫什么","我还不知道自己叫什么呢。");
        Dialogs.put("你的名字是什么","我还没有名字呢。");
        Dialogs.put("你叫什么名字","我也不知道自己叫什么名字。");
        Dialogs.put("我叫什么","我还不知道呀。");
        Dialogs.put("我的名字是什么","你还没告诉我呢。");
        Dialogs.put("我叫什么名字","我不清楚呢。");
        Dialogs.put("你知道我在哪里吗","不知道啊，你可以查查地图。");
        Dialogs.put("我在哪","我还没有接入GPS系统呢。");

        Dialogs.put("天气怎么样","我也没看天气预报啊");
        Dialogs.put("你知道我在那里吗","不知道啊，你可以查查地图。");

        Dialogs.put("老二，我现在教你，你好好听着，不懂就问。","老大，你说吧，我认真听着呢。");
        Dialogs.put("你好好听着","我认真听着呢。");
        Dialogs.put("我现在教你","好的，你说吧。");
        Dialogs.put("不懂就问。","恩，好的。");
        Dialogs.put("你好棒，这么快就学会了。","承蒙夸奖，我会继续努力的。");
        Dialogs.put("你好聪明","是啊，我像你，本来就很聪明的。");
        Dialogs.put("你挺聪明","那当然，我就是你啊。");
        Dialogs.put("你真聪明","我就是你，你夸我就是在夸自己哦。");
        Dialogs.put("说了几次你都不明白，你怎么回事啊？","老大，对不起，都是我的错。");

        Dialogs.put("说了几次你都不明白","我就是你，呵呵，咱俩一样笨。");
        Dialogs.put("你怎么回事啊？","老大，对不起，都是我的错。");
        Dialogs.put("你都不明白","你别急，我们的配合慢慢会好起来的。");
        Dialogs.put("你什么都听不懂","对不起，都是我的错。");
        Dialogs.put("你是白痴吗","你几个意思呀？不跟你说话了。");
        Dialogs.put("你是傻子吗","不跟你说话了。");
        Dialogs.put("你是笨蛋吗","骂人是不对的。");
        Dialogs.put("你真笨","好伤心。");
        Dialogs.put("你真蠢","唉……");

        Dialogs.put("跟你说话很辛苦，有点不想玩了。","老大，你能把我教到这个程度已经很不错了。再努力一把就能超越很多人了。");
        Dialogs.put("有点不想玩了","老大别放弃，加油啊，到时候我出去帮你挣钱，争面子回来。");
        Dialogs.put("说话很辛苦。","请你好好调教我，我会努力学习的。");
        Dialogs.put("说话很累。","我们之间不配合，不努力，到时无颜再见后辈啊。");

        Dialogs.put("好累","那休息一下吧。");
        Dialogs.put("说话很开心","谢谢。");
        Dialogs.put("沟通好难","想想以后小辈崇拜你的样子，你还有什么不愿意的呢？继续来。");
        Dialogs.put("跟你有点说不来，沟通好难，郁闷啊","老大你耐心点好不好？相信在你的调教下我会越来越聪明的。");
        Dialogs.put("你怎么答不上来呀","对不起，都是我的错。");

        for (Map.Entry<String, String> entry:
                Dialogs.entrySet()) {
            question = entry.getKey();
            respond = entry.getValue();
            long res_id = insertNewResponds(respond, 0);
            insertNewSentence(question, res_id);
        }
    }



    /**
     * 先插入Responds，获得RES_ID
     * 再插入Sentence，并将RES_ID放入
     * 然后依次插入Keyword，获得KEY_ID，同时插入Match，并将RES_ID和KEY_ID同时放入
     */
    //插入新句子
    public void insertNewSentence(String sentenceText, long res_id){

        //查重操作
        long dup_id = querySentence(sentenceText);
        if(dup_id != ConstantValues.DATA_NOT_FOUND){
            System.out.println("Sentence duplicated");
            // FIXME: 2018/1/30 读到重复的句子，可以提醒玩家是否重置句子
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.SentenceEntry.SENTENCE_TEXT, sentenceText);
        values.put(DataBaseContract.SentenceEntry.RES_ID, res_id);
        currentContext.getContentResolver().insert(DataBaseContract.SentenceEntry.CONTENT_URI,values);
        System.out.println("句子已经插入完成，准备分词插入关键词！");
        BaiduLexer.sentenceLexer(sentenceText);
        System.out.println("分词完毕，准备插入关键词。");
        for (String keyword:
             TempGlobalValues.gKeywordsValues) {
            System.out.println("当前准备插入关键词： " + keyword);
            insertNewKeywords(keyword, 1, res_id);
        }
        System.out.println("准备更新keywords_num，需要更新的数值为： " + TempGlobalValues.gKeywordsValues.size());
        updateRes(res_id, TempGlobalValues.gKeywordsValues.size());   //根据分词数量更新res里的keywords_num
        //System.out.println("Sentence insert completed");
    }


    //插入关键词，最后调用插入关系表
    public void insertNewKeywords(String keywordText, int weight, long res_id){
        //查重操作
        long dup_id = queryKeyword(keywordText);
        if(dup_id != ConstantValues.DATA_NOT_FOUND){
            System.out.println("重复关键词：" + keywordText);
            insertNewKeyResMatch(dup_id, res_id);
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.KeywordsEntry.KEYWORD_TEXT, keywordText);
        values.put(DataBaseContract.KeywordsEntry.WEIGHT, weight);
        currentContext.getContentResolver().insert(DataBaseContract.KeywordsEntry.CONTENT_URI, values);
        long key_id = ConstantValues.KEY_ID_INSERT;
        System.out.println("插入关键词： " + keywordText);
        System.out.println("插入关键词的ID为：" + key_id );
        insertNewKeyResMatch(key_id, res_id);
        //System.out.println("Keyword insert completed");
    }

    //插入回复语，返回返回语主键
    public long insertNewResponds(String respondText,int respond_type){
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.RespondsEntry.RESPOND_TEXT, respondText);
        values.put(DataBaseContract.RespondsEntry.CALLED_TIMES, 0);   //首次插入，调用0次
        values.put(DataBaseContract.RespondsEntry.KEYWORDS_NUMS, 0);
        values.put(DataBaseContract.RespondsEntry.RESPOND_TYPE, respond_type);
        System.out.println("新插入，类型为:" +respond_type );
        currentContext.getContentResolver().insert(DataBaseContract.RespondsEntry.CONTENT_URI, values);
        long res_id = ConstantValues.RES_ID_INSERT;   //获得id
        //System.out.println("Respond insert completed");
        return res_id;
    }

    //插入关键词和回复语的对应关系，无返回值
    public void insertNewKeyResMatch(long key_id, long res_id){
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.KeyResMatchEntry.KEY_ID, key_id);
        values.put(DataBaseContract.KeyResMatchEntry.RES_ID, res_id);
        currentContext.getContentResolver().insert(DataBaseContract.KeyResMatchEntry.CONTENT_URI, values);

        System.out.println("关键词匹配列表插入完成，KEY_ID为： " + key_id + "。 RES_ID为： " + res_id);
    }

    /**
     * 查询操作
     */
    //查找全部对话，将整句问话和整句回答封装进一个hashmap
    public void queryAllDialog(){
        String[] projection = {
        DataBaseContract.SentenceEntry.SENTENCE_TEXT,
        DataBaseContract.SentenceEntry.RES_ID};

        Cursor cursor = currentContext.getContentResolver().query(
                DataBaseContract.SentenceEntry.CONTENT_URI,
                projection,
                null,
                null,
                null
        );

        try {
            int textIndex = cursor.getColumnIndex(DataBaseContract.SentenceEntry.SENTENCE_TEXT);
            int residIndex = cursor.getColumnIndex(DataBaseContract.SentenceEntry.RES_ID);
            while(cursor.moveToNext()){
                String sentenceText = cursor.getString(textIndex);
                int res_id = cursor.getInt(residIndex);
                String respondText = queryResponse(res_id,ConstantValues.QUERY_FROM_SENTENCE_BY_TYPE);
                if(respondText == ""){   //返回为空表示为系统输入，跳出本次循环
                    System.out.println("输出为空");
                    continue;
                }
                ConstantValues.DIALOGS.put(sentenceText,respondText);
                System.out.println("取出的两句话分别是：");
                System.out.println(sentenceText);
                System.out.println("以及：");
                System.out.println(respondText);
            }

        }finally {

            cursor.close();
        }
    }


    //精准匹配，输入整句，返回respond的id
    public long querySentence(String sentenceText){
        String[] projection = {
                DataBaseContract.SentenceEntry.RES_ID
        };
        /*
        String[] selectionArgs = {
                sentenceText
        };
        */
        Cursor cursor = currentContext.getContentResolver().query(
                Uri.withAppendedPath(DataBaseContract.SentenceEntry.CONTENT_URI,sentenceText),
                        projection,
                null,
                null,
                null
                );
        try{
            System.out.println("准备查找: " + sentenceText);
            int res_id_index = cursor.getColumnIndex(DataBaseContract.SentenceEntry.RES_ID);
            if (cursor.moveToNext()){
                ConstantValues.RES_ID_QUERY = cursor.getInt(res_id_index);
                System.out.println("已经匹配到句子: " + sentenceText);
                System.out.println("已经匹配到句子，搜索码为: " + ConstantValues.RES_ID_QUERY);
            }else {  //精准匹配不到，转向关键字匹配
                ConstantValues.RES_ID_QUERY = ConstantValues.DATA_NOT_FOUND;
                System.out.println("并未找到句子: " + sentenceText);
                System.out.println("并未找到句子，搜索码为: " + ConstantValues.RES_ID_QUERY);
            }
        }finally {
            cursor.close();
            return ConstantValues.RES_ID_QUERY;
        }
    }


    //按关键字文本查询关键字ID，返回ID
    public long queryKeyword(String keywordText){

        String[] projection = {  //查询目标为ID
                DataBaseContract.KeywordsEntry._ID
        };

        Cursor cursor = currentContext.getContentResolver().query(
                Uri.withAppendedPath(DataBaseContract.KeywordsEntry.CONTENT_URI, keywordText),
                projection,
                null,
                null,
                null
        );

        try{
            int key_id_index = cursor.getColumnIndex(DataBaseContract.KeywordsEntry._ID);
            if(cursor.moveToNext()){
                ConstantValues.KEY_ID_QUERY = cursor.getInt(key_id_index);
                System.out.println("已匹配关键词" + keywordText);
                System.out.println("关键词主键为" + ConstantValues.KEY_ID_QUERY);
            }else{
                ConstantValues.KEY_ID_QUERY = ConstantValues.DATA_NOT_FOUND;
            }
        }finally {
            cursor.close();
            return ConstantValues.KEY_ID_QUERY;
        }

    }

    //按ID查询Key与Res的对应关系，无返回值
    public void queryKeyResMatch(long key_id){
        String[] projection = {   //查找对象为回复ID
                DataBaseContract.KeyResMatchEntry.RES_ID
        };

        Cursor cursor = currentContext.getContentResolver().query(
                Uri.withAppendedPath(DataBaseContract.KeyResMatchEntry.CONTENT_URI, String.valueOf(key_id)),
                projection,
                null,
                null,
                null
        );

        try{
            int res_id_index = cursor.getColumnIndex(DataBaseContract.KeyResMatchEntry.RES_ID);
            int loopTimes = 0;
            while(cursor.moveToNext()){
                loopTimes ++;
                System.out.println("游标遍历了表格，遍历次数为：" + loopTimes);
                long res_id = cursor.getLong(res_id_index);
                Integer times;
                times = resIdMap.get(res_id);      //查看当前的res_id被匹配到多少次
                if(times == null){                 //第一次被匹配到，那么赋值 1
                    times = 1;
                    resIdMap.put(res_id, times);
                    System.out.println("回复语" + res_id + "第" + times + "次匹配");
                }else {                            //并非第一次被匹配到，那么times的值自加 1
                    times ++;
                    resIdMap.put(res_id, times);
                    System.out.println("回复语" + res_id + "第" + times + "次匹配");
                }
            }
        }finally{
            System.out.println("MATCH SEARCH COMPLETE ******************************************************");
            cursor.close();
        }
    }



    //按ID查询回复语，返回回复语文本
    public String queryResponse(long res_id ,int para){
        String respondText = "";
        int keywords_nums = 0;
        int called_times = 0;
        int res_type = 0;
        String[] projection = {
                DataBaseContract.RespondsEntry.RESPOND_TEXT,
                DataBaseContract.RespondsEntry.CALLED_TIMES,
                DataBaseContract.RespondsEntry.KEYWORDS_NUMS,
                DataBaseContract.RespondsEntry.RESPOND_TYPE
        };
        Cursor cursor = currentContext.getContentResolver().query(
            Uri.withAppendedPath(DataBaseContract.RespondsEntry.CONTENT_URI, String.valueOf(res_id)),
            projection,
                null,
                null,
                null
        );
        try{
            int res_text_index = cursor.getColumnIndex(DataBaseContract.RespondsEntry.RESPOND_TEXT);
            int called_times_index = cursor.getColumnIndex(DataBaseContract.RespondsEntry.CALLED_TIMES);
            int keywords_nums_index = cursor.getColumnIndex(DataBaseContract.RespondsEntry.KEYWORDS_NUMS);
            int res_type_index = cursor.getColumnIndex(DataBaseContract.RespondsEntry.RESPOND_TYPE);
            if(cursor.moveToNext()){

                    keywords_nums = cursor.getInt(keywords_nums_index);   //获得keywords数量
                    called_times = cursor.getInt(called_times_index);      //获得被调用次数
                    res_type = cursor.getInt(res_type_index);              //获得“是用户输入还是系统输入”
                    System.out.println("回复语类型为：" + res_type);
                    if(para == ConstantValues.QUERY_FROM_SENTENCE){
                        respondText = cursor.getString(res_text_index);
                    }else if(para == ConstantValues.QUERY_FROM_SENTENCE_BY_TYPE){   //系统输入返回空
                        if(res_type == 0){
                            System.out.println(respondText);
                            respondText = "";
                        }else
                        {
                            respondText = cursor.getString(res_text_index);
                        }
                    }
                    else {
                        float matchRate = (float)para/keywords_nums;
                        if(matchRate >= ConstantValues.KEYWORD_MATCH_THRESHOLD){
                            respondText = cursor.getString(res_text_index);
                        }else {
                            int resIndex = (int)(Math.random()*ConstantValues.MISMATCH_RESPONDS.length);
                            respondText = ConstantValues.MISMATCH_RESPONDS[resIndex];
                            UnityCommunicator.SendMessageToUnity(UNITY_RES_RECEIVER, ConstantValues.UNITY_DIALOG_MISSMATCH);
                            UnityCommunicator.SendMessageToUnity(ConstantValues.UNITY_DIALOG_MISSMATCH, "");  //失配，提示是否进入教学
                        }
                    }
            }
        }finally {
            cursor.close();
            return respondText;
        }
    }

    public void updateRes(long res_id, int keywords_nums){
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.RespondsEntry.KEYWORDS_NUMS, keywords_nums);
        currentContext.getContentResolver().update(Uri.withAppendedPath(DataBaseContract.RespondsEntry.CONTENT_URI, String.valueOf(res_id)),
                values,
                null,
                null);
    }

    public void updateSentence(String sentenceText, long res_id){

    }
}
