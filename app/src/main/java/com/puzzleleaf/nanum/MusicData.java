package com.puzzleleaf.nanum;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by cmtyx on 2016-11-15.
 */

public class MusicData {
    public static HashMap<String,HashMap<String,String>> mdata = new HashMap<>();
    public static ArrayList<String> musicName = new ArrayList<>();


    static void savekey(Context ctx, String b)
    {
        SharedPreferences prefs = ctx.getSharedPreferences("Key", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("key", b);
        editor.commit();
    }

    static String getKey(Context ctx){
        SharedPreferences prefs = ctx.getSharedPreferences("Key", Context.MODE_PRIVATE);
        return prefs.getString("key","nanum"); //원래는 null
    }



}
