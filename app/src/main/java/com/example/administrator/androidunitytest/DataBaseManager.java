package com.example.administrator.androidunitytest;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.example.administrator.androidunitytest.data.ConstantValues;
import com.example.administrator.androidunitytest.data.DataBaseContract;
import com.example.administrator.androidunitytest.data.TempGlobalValues;
import com.unity3d.player.UnityPlayer;

/**
 * Created by Administrator on 2018/1/24.
 */

public class DataBaseManager {
    private Context currentContext;
    public DataBaseManager(Context context){
        currentContext = context;
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
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, ConstantValues.UnitySetKeyMethod , keyword);
                UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, ConstantValues.UnitySetResMethod , respond);
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

    public void insertNewQuest(String para, String text){
        ContentValues values= new ContentValues();
        values.put(DataBaseContract.QuestEntry.TYPE, Integer.parseInt(para.substring(0,1)));
        values.put(DataBaseContract.QuestEntry.INDEX, Integer.parseInt(para.substring(1,2)));
        values.put(DataBaseContract.QuestEntry.ATTRIBUTE, Integer.parseInt(para.substring(2,3)));
        values.put(DataBaseContract.QuestEntry.DATE, Integer.parseInt(para.substring(3,4)));
        values.put(DataBaseContract.QuestEntry.DAYOFWEEK, Integer.parseInt(para.substring(4,5)));
        values.put(DataBaseContract.QuestEntry.TEXT, text);
    }


    /**
     *分析句子
     */
    public void logParse(String sentenceText){
        long res_id = querySentence(sentenceText);   //整句分析
        if(res_id == ConstantValues.DATA_NOT_FOUND){   //整句分析不匹配，转入关键词分析
            BaiduLexer.sentenceLexer(sentenceText);  //交由百度分词，结果存入临时全局关键词列表
            if(TempGlobalValues.gKeywordsValues != null){
                for (String keyword:
                        TempGlobalValues.gKeywordsValues) {
                    // TODO: 2018/1/26 在数据库keywords表中查找相同数据
                    queryKeyword(keyword);
                }
            }
        }else{
            // TODO: 2018/1/26 返回句子
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
            //System.out.println("Sentence duplicated");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.SentenceEntry.SENTENCE_TEXT, sentenceText);
        values.put(DataBaseContract.SentenceEntry.RES_ID, res_id);
        currentContext.getContentResolver().insert(DataBaseContract.SentenceEntry.CONTENT_URI,values);
        //System.out.println("Sentence insert completed");
    }

    //插入关键词，最后调用插入关系表
    public void insertNewKeywords(String keywordText, int weight, long res_id){
        //查重操作
        long dup_id = querySentence(keywordText);
        if(dup_id != ConstantValues.DATA_NOT_FOUND){
            //System.out.println("Sentence duplicated");
            return;
        }
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.KeywordsEntry.KEYWORD_TEXT, keywordText);
        values.put(DataBaseContract.KeywordsEntry.WEIGHT, weight);
        currentContext.getContentResolver().insert(DataBaseContract.KeywordsEntry.CONTENT_URI, values);
        long key_id = ConstantValues.KEY_ID_INSERT;
        insertNewKeyResMatch(key_id, res_id);
        System.out.println("Keyword insert completed");
    }

    //插入回复语，返回返回语主键
    public long insertNewResponds(String respondText){
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.RespondsEntry.RESPOND_TEXT, respondText);
        values.put(DataBaseContract.RespondsEntry.CALLED_TIMES, 0);   //首次插入，调用0次
        currentContext.getContentResolver().insert(DataBaseContract.RespondsEntry.CONTENT_URI, values);
        long res_id = ConstantValues.RES_ID_INSERT;   //获得id
        System.out.println("Respond insert completed");
        return res_id;
    }

    public void insertNewKeyResMatch(long key_id, long res_id){
        ContentValues values = new ContentValues();
        values.put(DataBaseContract.KeyResMatchEntry.KEY_ID, key_id);
        values.put(DataBaseContract.KeyResMatchEntry.RES_ID, res_id);
        currentContext.getContentResolver().insert(DataBaseContract.KeyResMatchEntry.CONTENT_URI, values);
        System.out.println("Match Table insert completed");
    }

    /**
     * 查询操作
     */

    //精准匹配，输入整句，返回respond的id
    public long querySentence(String sentenceText){
        String[] projection = {
                DataBaseContract.SentenceEntry.RES_ID
        };
        String[] selectionArgs = {
                sentenceText
        };
        Cursor cursor = currentContext.getContentResolver().query(
                Uri.withAppendedPath(DataBaseContract.SentenceEntry.CONTENT_URI,sentenceText),
                        projection,
                null,
                null,
                null
                );
        try{
            System.out.println("准备查找");
            int res_id_index = cursor.getColumnIndex(DataBaseContract.SentenceEntry.RES_ID);
            if (cursor.moveToNext()){
                ConstantValues.RES_ID_QUERY = cursor.getInt(res_id_index);
                System.out.println("已经匹配到");
            }else {  //精准匹配不到，转向关键字匹配
                ConstantValues.RES_ID_QUERY = ConstantValues.DATA_NOT_FOUND;
                System.out.println("并未找到");
            }
        }finally {
            cursor.close();
            return ConstantValues.RES_ID_QUERY;
        }
    }


    public void queryKeyword(String keywordText){

    }

    public void queryKeyResMatch(long key_id){

    }
}
