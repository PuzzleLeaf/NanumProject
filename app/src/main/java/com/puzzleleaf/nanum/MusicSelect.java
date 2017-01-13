package com.puzzleleaf.nanum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

/**
 * Created by cmtyx on 2016-11-06.
 */

public class MusicSelect extends AppCompatActivity {

    private TTSObject tts;
    private TempData tempData;
    private String recentSelectMusic ="";
    private int touchMusic = 0;
    private int touchInstrument = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tts = new TTSObject(this);
        tempData = new TempData(MusicData.mdata);
        tempData.setSearchMusicName();
        setContentView(R.layout.activity_music_select);
    }
    
    public void onClickSelectNextMusic(View v)
    {
        if (touchMusic==0)
            tts.readText("다음 곡 ,"+tempData.searchMusic(true));
        else {
            tts.readText("다음 악기 ," + tempData.searchInstrument(true));
            touchInstrument =0;
        }
    }
    public void onClickSelectPrevMusic(View v)
    {
        if (touchMusic==0)
            tts.readText("이전 곡 ,"+tempData.searchMusic(false));
        else {
            tts.readText("이전 악기 ," + tempData.searchInstrument(false));
            touchInstrument =0;
        }
    }
    public void onClickSelectMusic(View v)
    {
        recentSelectMusic = tempData.selectMusic();
        if(touchMusic ==0) {
            tts.readText(tempData.selectMusic() + "를 선택했습니다.");
            touchMusic = 1;
        }
        else
        {
            tts.readText(tempData.selectMusic() + "를 해제했습니다.");
            tempData.resetInstCount();
            touchMusic =0;
            touchInstrument =0;
        }
        tempData.setSearchInstrumentName();
    }

    public void onClickRecentMusic(View v)
    {
        if(touchMusic==1 && touchInstrument ==1) {
            tts.readText(recentSelectMusic + "를 재생합니다.");
            Intent intent = new Intent(this,MusicPlayer.class);
            intent.putExtra("value",tempData.getMusicInstNote()); //다른 액티비티로 값 넘겨주기
            startActivity(intent);
        }
        else if(touchMusic ==0)
        {
            tts.readText("음악을 선택 해주세요");
        }
        else if(touchInstrument ==0){
            tts.readText("악기를 선택 해주세요");
        }
    }
    public void onClickSelectInstrument(View v)
    {
        if(touchMusic ==1) {
            if(touchInstrument ==1)
            {
                tts.readText(tempData.selectInstrument() + "를 해제 했습니다.");
                touchInstrument =0;
            }
            else {
                tts.readText(tempData.selectInstrument() + "를 선택했습니다.");
                touchInstrument = 1;
            }
        }
        else if(touchMusic ==0)
        {
            tts.readText("곡을 선택해주세요.");
        }


    }
    @Override
    protected void onDestroy() {
        tts.setDestroy();
        super.onDestroy();
    }
}
