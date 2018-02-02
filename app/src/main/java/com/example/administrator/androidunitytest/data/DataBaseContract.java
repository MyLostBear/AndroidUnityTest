package com.example.administrator.androidunitytest.data;

import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ZK on 2018/1/11.
 */

public final class DataBaseContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private DataBaseContract() {}

    /**
     * The "Content authority" is a name for the entire content provider, similar to the
     * relationship between a domain name and its website.  A convenient string to use for the
     * content authority is the package name for the app, which is guaranteed to be unique on the
     * device.
     */
    public static final String CONTENT_AUTHORITY = "com.example.administrator.androidunitytest";

    public static final String CONTENT_SCHEME = "content://";

    /**
     * Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
     * the content provider.
     */
    public static final Uri BASE_CONTENT_URI = Uri.parse(CONTENT_SCHEME + CONTENT_AUTHORITY);

    /**
     * Possible path (appended to base content URI for possible URI's)
     * For instance, content://com.example.administrator.androidunitytest/command/ is a valid path for
     * looking at command data. content://com.example.administrator.androidunitytest/staff/ will fail,
     * as the ContentProvider hasn't been given any information on what to do with "staff".
     */
    public static final String PATH_COMMAND = "command";
    public static final String PATH_QUEST = "quest";
    public static final String PATH_SENTENCE = "sentence";    //整句指令表
    public static final String PATH_KEYWORDS = "keywords";    //关键词表
    public static final String PATH_RESPONDS = "responds";    //回复语表
    public static final String PATH_KEY_RES_MATCH = "key_res";   //回复语与关键词关系表



    /**
     * Inner class that defines constant values for the commands database table.
     * Each entry in the table represents a single command.
     */
    public static final class CommandEntry implements BaseColumns{

        /** The content URI to access the command data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_COMMAND);

        /** Name of database table for command */
        public final static String TABLE_NAME = "command";

        /**
         * Unique ID number for the command (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;


        /**
         * Content of the keyword.
         *
         * Type: TEXT
         */
        public final static String KEYWORD = "keyword";


        /**
         * Name of the scene.
         *
         * Type: TEXT
         */
        public final static String SCENE = "scene";

        /**
         * Content of the command.
         *
         * Type: TEXT
         */
        public final static String METHOD = "method";


        /**
         * Content of the respond word.
         *
         * Type: TEXT
         */
        public final static String RESPOND_WORD = "respond_word";

        /**
         * Times of called
         *
         * Type: INTEGER
         */
        public final static String CALLED_TIMES = "called_times";

        /**
         * Type of the pet.
         *
         * Type: INTEGER
         */
        public final static String ANIMATION_TYPE = "animation_type";

        /**
         * Possible values for the type of the animation.
         */
        public static final int ANIMATION_NONE = 0;
        public static final int ANIMATION_BOOL = 1;
        public static final int ANIMATION_TRIGGER = 2;
        public static final int ANIMATION_INT = 3;
        public static final int ANIMATION_FLOAT= 4;


        /**
         * Name of the animation.
         *
         * Type: TEXT
         */
        public final static String ANIMATION_NAME = "animation_name";

        /**
         * value of the animation.
         *
         * Type: INTEGER
         */
        public final static String ANIMATION_VALUE = "animation_value";

        /**
         * Provide link if necessary
         *
         * Type: TEXT
         */
        public final static String LINK = "link";

        /**
         * Provide comment if necessary
         *
         * Type: TEXT
         */
        public final static String COMMENT = "comment";

    }

    public static final class QuestEntry implements BaseColumns{
        /** The content URI to access the quest data in the provider */
        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_QUEST);

        /** Name of database table for quest */
        public final static String TABLE_NAME = "quest";

        /**
         * Unique ID number for the quest (only for use in the database table).
         *
         * Type: INTEGER
         */
        public final static String _ID = BaseColumns._ID;

        public final static String TYPE = "type";

        public final static String INDEX = "quest_index";

        public final static String ATTRIBUTE = "attribute";

        public final static String DATE = "quest_date";

        public final static String DAYOFWEEK = "day_of_week";

        public final static String TEXT = "quest_text";

        public final static String ISCOMPLETED = "is_completed";
    }

    //整句指令表
    public static final class SentenceEntry implements BaseColumns{
        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_SENTENCE);
        public final static String TABLE_NAME = "sentence";
        public final static String _ID = BaseColumns._ID;
        public final static String SENTENCE_TEXT = "sentence_text";
        public final static String RES_ID = "res_id";
    }

    //关键字表
    public static final class KeywordsEntry implements BaseColumns{
        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_KEYWORDS);
        public final static String TABLE_NAME = "keywords";
        public final static String _ID = BaseColumns._ID;
        public final static String KEYWORD_TEXT = "keyword_text";
        public final static String WEIGHT = "weight";
    }

    //整句指令表
    public static final class RespondsEntry implements BaseColumns{
        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_RESPONDS);
        public final static String TABLE_NAME = "responds";
        public final static String _ID = BaseColumns._ID;
        public final static String RESPOND_TEXT = "respond_text";
        public final static String CALLED_TIMES = "called_times";
        public final static String KEYWORDS_NUMS = "keywords_nums";
        public final static String RESPOND_TYPE = "respond_type";
    }

    //整句指令表
    public static final class KeyResMatchEntry implements BaseColumns{
        public final static Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_KEY_RES_MATCH);
        public final static String TABLE_NAME = "key_res";
        public final static String _ID = BaseColumns._ID;
        public final static String KEY_ID = "key_id";
        public final static String RES_ID = "res_id";
    }
}
