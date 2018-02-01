package com.example.administrator.androidunitytest.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZK on 2018/2/1.
 */

public final class QuestsData {
    public QuestsData(int t, int i, String a, int d, int d_w, String text, int is){
        type = t;
        index = i;
        attribute = a;
        date = d;
        day_of_week = d_w;
        this.text = text;
        is_completed = is;
    }
    public int type;
    public int index;
    public String attribute;
    public int date;
    public int day_of_week;
    public String text;
    public int is_completed;
}
