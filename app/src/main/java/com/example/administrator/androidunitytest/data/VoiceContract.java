package com.example.administrator.androidunitytest.data;

import android.provider.BaseColumns;

/**
 * Created by ZK on 2018/1/11.
 */

public final class VoiceContract {
    // To prevent someone from accidentally instantiating the contract class,
    // give it an empty constructor.
    private VoiceContract() {}

    /**
     * Inner class that defines constant values for the commands database table.
     * Each entry in the table represents a single command.
     */
    public static final class CommandEntry implements BaseColumns{

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
}
