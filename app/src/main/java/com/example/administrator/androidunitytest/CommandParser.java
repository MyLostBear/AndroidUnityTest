package com.example.administrator.androidunitytest;

/**
 * Created by ZK on 2018/1/10.
 */


/**
 * Parse command
 */
public class CommandParser {

    public final static int NOT_FOUND = -1;
    public static String[] ParseCommand(String command){
        String[] commandResult = new String[]{String.valueOf(NOT_FOUND)};

        commandResult = ParseCommandLoc(command);
        if(commandResult[0] == ""){
            commandResult = ParseCommandDB(command);
        }if(commandResult[0] == ""){
            commandResult = ParseCommandRemote(command);
        }
        return commandResult;
    }


    public static String[] ParseCommandLoc(String command){
        String[] commandResult = new String[]{String.valueOf(NOT_FOUND)};

        switch (command){
            case "你好":
                commandResult = new String[]{"你好"};
                break;
            case "你好呀":
                commandResult = new String[]{"你好"};
                break;
            case "早上好":
                commandResult = new String[]{"你好"};
                break;
            case "谢谢":
                commandResult = new String[]{"你好"};
                break;
            case "谢谢你":
                commandResult = new String[]{"你好"};
                break;
            case "再见":
                commandResult = new String[]{"你好"};
                break;
            case "确定":
                commandResult = new String[]{"你好"};
                break;

            case "返回":
                commandResult = new String[]{"你好"};
                break;

            case "完成":
                commandResult = new String[]{"你好"};
                break;
            case "跳个舞":
                commandResult = new String[]{"你好"};
                break;
            case "你是谁":
                commandResult = new String[]{"你好"};
                break;
            case "我是谁":
                commandResult = new String[]{"你好"};
                break;

            case "吃了没":
                commandResult = new String[]{"你好"};
                break;
            case "笨蛋":
                commandResult = new String[]{"你好"};
                break;
            case "晚上好":
                commandResult = new String[]{"你好"};
                break;
            default:
                break;
        }

        return commandResult;
    }

    public static String[] ParseCommandDB(String command){
        String[] commandResult = new String[]{String.valueOf(NOT_FOUND)};

        return commandResult;
    }

    public static String[] ParseCommandRemote(String command){
        String[] commandResult = new String[]{String.valueOf(NOT_FOUND)};

        return commandResult;
    }
}
