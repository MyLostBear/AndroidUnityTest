package com.example.administrator.androidunitytest;

import com.unity3d.player.UnityPlayer;

/**
 * Created by Administrator on 2018/1/24.
 */

public class UnityCommunicator {
    //调用unity时所要找的unity的GameObject
    static String AndroidManager = "AndroidManager";

    //调用unity时所要找的unity的方法
    public static String SHOW_LOG = "ShowLog";

    public static String UnityMethod  = "";
    public static String UnitySetKeyMethod = "GetKeyFromDB";
    public static String UnitySetResMethod = "GetResFromDB";



    public static void SendMessageToUnity(String methodName, String para){
        UnityPlayer.UnitySendMessage(AndroidManager, methodName, para);
    }
}
