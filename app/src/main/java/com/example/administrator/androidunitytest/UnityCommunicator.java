package com.example.administrator.androidunitytest;

import com.example.administrator.androidunitytest.data.ConstantValues;
import com.unity3d.player.UnityPlayer;
//不用看
/**
 * Created by Administrator on 2018/1/24.
 */

public class UnityCommunicator {

    public static void SendMessageToUnity(String methodName, String para){
        UnityPlayer.UnitySendMessage(ConstantValues.UnityAndroidManager, methodName, para);
    }
}
