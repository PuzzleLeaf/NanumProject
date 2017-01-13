package com.puzzleleaf.nanum;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by cmtyx on 2016-11-06.
 */

public class TempData implements Serializable{
    private HashMap<String,HashMap<String,String>> musicName;
    private int selectMusicNumber;
    private int selectInstNumber;
    HashMap<String,String> instMap;
    Iterator<String> searchMusic;
    ArrayList<String> searchMusicName;
    Iterator<String> searchInst;
    ArrayList<String> searchInstName;

    TempData(HashMap<String,HashMap<String,String>> temp)
    {

        selectMusicNumber =0;
        selectInstNumber =0;
        searchMusicName = new ArrayList<>();
        searchInstName = new ArrayList<>();
        instMap = new HashMap<>();
        musicName = new HashMap<>(); //메인이 되는 HashMap
        musicName = temp;

//        //------------------------------------------------------------------------
//        //악기, 연주의 값을 temp 임시 변수에 넣어준다.
//        temp.put("바이올린 일번",
//                "라  라   라 /시b  시b  /솔  도2 시b 시b 라 /레2 레2  미2 파2 솔2 /도2 도2  /" +
//                        "라 솔 라 솔 파 /파   /라  라   라 /시b 시b   /솔  도2 시b /시b 라  /레2 레2  /" +
//                        "도2 도2  /라 솔 라 솔 파 /파   /라 파2  레2/ 시b 시b  /레2 솔2 미2/ 도2 도2 /" +
//                        "미2 레2 미2  레2 도2 /도2  미 파 솔 /라 라  라 /시b 시b  /솔 도2 시b /시b 라  /레2 레2  /" +
//                        "도2 도2  /라 솔 라 솔 파 /파   /라  라   라 /시b  시b  /솔  도2  솔 /시b 라  /" +
//                        "레2 레2  미2 파2 솔2 /도2 도2  파2 /라 솔 라 솔 파 /파   /라 레2  미2 /도2# 라  /라 레2  미2 /" +
//                        "파2 파2  /시  시b /시  라  /라 라 시b /솔 솔# 라  /라 라  /미2  솔#  레2 /" +
//                        "도2 시 시b   /라   /라 라  라 /시b 시b  /솔 도2  시b /시b 라  /레2 레2  /" +
//                        "도2 도2  /라 솔 라 솔 파 /파    /라 파2  레2 /시b 시b  /레2 솔2  미2 /도2 도2  파2 /" +
//                        "미2 레2 미2 레2 도2 /도2  미 파 솔 /라  라   라 /시b 시b  /솔 도2  시b /시b 라  /레2 레2  /" +
//                        "도2 도2  /라 솔 라 솔 파 /파    /라 라  라 /시b 시b  /솔 도2 시b /시b 라  /" +
//                        "레2 레2  미2 파2 솔2 /도2 도2  파2 /라 솔 라 솔 파 /파   /라 레2  미2 /도2# 라  /라 레2  미2 /" +
//                        "파2 파2  /시  시b /시  라  /라 라 시b /솔 솔# 라  /라 라  /미2  솔#  레2  /" +
//                        "도2 시 시b  /라   /라 라  라 /시b 시b  /솔 도2  시b /시b 라  /레2 레2  " +
//                        "도2 도2  /라 솔 라 솔 파  /라2 라2  라2 /시2b 시2b  /솔2 도3 시2b /시2b 라2  /" +
//                        "레3 레3 미3 파3 파3 /도3 도3  라2 /도3 시2b 도3 시2b 라2 /라2  레3");
//
//        musicName.put("울게 하소서",temp);
//
//        temp = new HashMap<>();
//        temp.put("피아노","도 레 미 파 솔 라 시 도2 레2 미2 파2 솔2 라2 시2 도3 레3 미3 파3");
//        musicName.put("비발디 사계",temp);


        //데이터 베이스에서 값을 모두 읽어옴
    }
    //시작시 미리 수행해야 함
    void setSearchMusicName()
    {
        searchInstName.clear();
        searchMusic = musicName.keySet().iterator();
        while (searchMusic.hasNext())
        {
            String tem = searchMusic.next();
            searchMusicName.add(tem);
        }
    }
    //----------------------- 곡 선택
    String searchMusic(boolean flag)
    {
        if(flag)
        {
            if(searchMusicName.size()-1>selectMusicNumber)
                selectMusicNumber++;
            else
                selectMusicNumber =0;
        }
        else
        {
            if(selectMusicNumber>0)
                selectMusicNumber--;
            else
                selectMusicNumber = searchMusicName.size()-1;
        }

        return selectMusic();
    }
    String selectMusic()
    {
        return searchMusicName.get(selectMusicNumber);
    }
    //------------------------
    void setSearchInstrumentName()
    {
        Log.d("qwe",Integer.toString(selectMusicNumber));
        instMap = musicName.get(searchMusicName.get(selectMusicNumber));
        searchInst = instMap.keySet().iterator();
        searchInstName.clear();
        while(searchInst.hasNext())
        {
            searchInstName.add(searchInst.next());
        }
    }

    //관리자 메뉴용
    //곡 이름에 대한 값 찾기, 곡 명이 존재하면 1 return
    boolean setMakeSearchHashMap(String name)
    {
        int temp = findMusicNameIndex(name);
        Log.d("qwe",temp+"--------------");
        if(temp != -1) {
            instMap = musicName.get(searchMusicName.get(temp));
            searchInst = instMap.keySet().iterator();
            searchInstName.clear();
            while(searchInst.hasNext())
            {
                searchInstName.add(searchInst.next());
            }
            return true;
        }
        return false;
    }

    ArrayList<String> getAdminEditMusic(){ return searchMusicName; }
    ArrayList<String> getAdminEditInst()
    {
        return searchInstName;
    }

    String getAdminEditMusicNote(String inst)
    {
        for(int i=0;i<searchInstName.size();i++)
        {
            if(searchInstName.get(i).equals(inst))
            {
                return instMap.get(searchInstName.get(i));
            }
        }
        return "";
    }

    int findMusicNameIndex(String name)
    {
        for(int i=0;i<searchMusicName.size();i++)
        {
            if(searchMusicName.get(i).equals(name))
            {
                return i;
            }
        }

        return -1;
    }
    //---------------------------------------------------------------------
    String searchInstrument(boolean flag)
    {
        if(flag)
        {
            if(searchInstName.size()-1>selectInstNumber)
                selectInstNumber++;
            else
                selectInstNumber =0;
        }
        else
        {
            if(selectInstNumber>0)
                selectInstNumber--;
            else
                selectInstNumber = searchInstName.size()-1;
        }

        return selectInstrument();
    }
    void resetInstCount()
    {
        selectInstNumber =0;
    }
    String selectInstrument()
    {
        return searchInstName.get(selectInstNumber);
    }

    String getMusicInstNote()
    {
        Log.d("qwe",instMap.get(searchInstName.get(selectInstNumber)));
        return instMap.get(searchInstName.get(selectInstNumber));
    }
}
