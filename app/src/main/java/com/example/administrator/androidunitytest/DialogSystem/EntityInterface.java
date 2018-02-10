package com.example.administrator.androidunitytest.DialogSystem;

import com.example.administrator.androidunitytest.data.ConstantValues;

/**
 * Created by ZK on 2018/2/9.
 */

public interface EntityInterface {
    public int type = ConstantValues.ENTITY_TYPE_UNKNOW;
    public abstract void setEntityType();   //设置类型
    public abstract int getEntityType(int Type);   //获得类型
    public abstract boolean compareEntityType(int Type);   //比较类型

    public abstract void setEntityText();  //设置文本
    public abstract String getEntityText(String inputText);  //获得文本
    public abstract boolean compareEntityText(String inputText);   //比较文本
}
