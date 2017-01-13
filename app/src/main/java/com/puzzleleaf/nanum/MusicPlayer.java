package com.puzzleleaf.nanum;

import android.content.Intent;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

/**
 * Created by cmtyx on 2016-11-06.
 */
public class MusicPlayer extends AppCompatActivity {
    private TTSObject tts;
    private String edText = "";
    private int i=0;
    private int speed = 500;
    private SoundPoolObject sp;
    boolean isSearching = false;
    int startPlay; int endPlay; boolean repeatFlag =false;
    private boolean playFlag = false;
    private boolean playSearchFlag = false;
    private int interruptNumber = 0;
    private int currentAt; // 현재 마디 탐색
    // 0 기본상태  1이면 일시정지    3은 종료
    Stream thread = new Stream();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON); //화면 켜짐 유지
        setContentView(R.layout.activity_music_player);
        Intent intent = getIntent();
        edText = intent.getStringExtra("value");
        Log.d("qwe",edText+"qweqeqw-------------------");
        tts = new TTSObject(this);
        sp = new SoundPoolObject(this);
        sp.initNote();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tts.setDestroy();
        interruptNumber =3; thread.interrupt();
        // 종료시 스레드 인터럽트 발생
    }

    //구간반복 영역 해제
    void repeatMusicReset()
    {
        startPlay =-1;
        endPlay =-1;
        repeatFlag =false;
    }

    //속도 감소 이벤트
    public void onClickSpeedDown(View v)
    {
        if(speed<1000)
        {
            tts.readText("속도 느리게");
            speed +=200;
        }
        else
            tts.readText("더이상 느려질 수 없음");
    }
    //정상 속도 이벤트
    public void onClickSpeedReset(View v)
    {
        tts.readText("정상 속도");
        speed = 500;
    }
    //속도 빠르게 이벤트
    public void onClickSpeedUp(View v)
    {
        if(speed>100)
        {
            tts.readText("속도 빠르게");
            speed -=40;
        }
        else{
            tts.readText("더이상 빨라질 수 없음");
        }
    }

    public void onClickSearchNextOne(View v)
    {
        int temp =0;
        if(playFlag && playSearchFlag) {
            interruptNumber =1;
            if((temp = sp.searchCurrentAt(i, edText, 1))!=-1)
            {
                isSearching = true;
                currentAt = sp.getCurrentAt(edText,temp);
                i=temp;
                Log.d("qwe",Integer.toString(temp)+ " + "+String.valueOf(edText.charAt(i)));
            }
            Log.d("qwe",Integer.toString(i));
            tts.readText(Integer.toString(currentAt+1) + " 번 마디");
        }
    }
    public void onClickSearchPrevOne(View v)
    {
        int temp =0;
        if(playFlag && playSearchFlag) {
            repeatMusicReset();
            interruptNumber =1;
            if((temp = sp.searchCurrentAt(i, edText, 2))!=-1 && currentAt>0)
            {
                isSearching = true;
                currentAt = sp.getCurrentAt(edText,temp);
                Log.d("qwe",Integer.toString(temp));
                i=temp;
            }
            if (currentAt==0)
                i=0;
            Log.d("qwe",Integer.toString(i));
            tts.readText(Integer.toString(currentAt+1) + " 번 마디");
        }
    }
    public void onClickSearchNextFour(View v)
    {
        int temp =0;
        if(playFlag && playSearchFlag) {
            interruptNumber =1;
            if((temp = sp.searchCurrentAt(i, edText, 3))!=-1)
            {
                isSearching = true;
                i=temp;
                currentAt+=4;
            }
            Log.d("qwe",Integer.toString(i));
            tts.readText(Integer.toString(currentAt+1) + " 번 마디");
        }
    }
    public void onClickSearchPrevFour(View v)
    {
        int temp =0;
        if(playFlag && playSearchFlag) {
            repeatMusicReset();
            interruptNumber =1;
            if((temp = sp.searchCurrentAt(i, edText, 4))!=-1 && currentAt>3)
            {
                isSearching = true;
                i=temp;
                currentAt-=4;

            }
            if(currentAt<4)
            {
                currentAt =0;
                i=0;
            }
            Log.d("qwe",Integer.toString(i));
            tts.readText(Integer.toString(currentAt+1) + " 번 마디");
        }
    }
    //구간 반복
    public void onClickRepeatMusic(View v)
    {
        if(playFlag && playSearchFlag)
        {
            if(repeatFlag == false) {
                if (startPlay == -1) {
                    tts.readText("구간반복 설정");
                    startPlay = i;
                } else if (endPlay == -1) {
                    tts.readText("반복 시작");
                    interruptNumber = 0;
                    endPlay = i;
                    repeatFlag = true;
                }
            }
            else
            {
                tts.readText("구간반복 해제");
                repeatFlag = false;
            }
        }
    }
    //재생 버튼 이벤트
    public void onClickPlayMusic(View v)
    {
        if(playFlag == false) {
            playFlag = true;
            currentAt = 0; // 현재 마디
            repeatMusicReset();
            thread = new Stream();
            thread.start();
        }
        else{
            if(interruptNumber==0) {
                tts.readText("일시정지");
                interruptNumber = 1;
            }
            else{
                tts.readText("재생");
                interruptNumber =0;
            }
        }
    }

    class Stream extends Thread{  // Thread 를 상속받은 작업스레드 생성
        @Override
        public void run() {
            try {
                tts.readText("음악을 재생합니다....");
                Thread.sleep(3000);
                playSearchFlag = true;
                for(i=0;i<edText.length();)
                {
                    //스레드를 자연스럽게 종료시키기 위한 인터럽트 문구
                    if(Thread.currentThread().isInterrupted()) {
                        if(interruptNumber == 3)
                            break;
                    }
                    if(interruptNumber!=1) {
                        if(repeatFlag == true) {
                            while (repeatFlag)
                            {
                                for(int temp = startPlay;temp<=endPlay;)
                                {
                                    sp.playSound(edText, temp);

                                    if (edText.charAt(temp) == ' ') {
                                        Thread.sleep(speed);
                                        sp.stopNote();
                                    }

                                    if(Thread.currentThread().isInterrupted()) {
                                        if(interruptNumber == 3)
                                            break;
                                    }

                                    if(interruptNumber ==1) {
                                    }
                                    else
                                        temp++;
                                }
                            }
                        }
                        else {
                            if (!isSearching)
                                currentAt += sp.playSound(edText, i);
                            else
                                sp.playSound(edText, i);

                            if (edText.charAt(i) == ' ') {
                                Thread.sleep(speed);
                                sp.stopNote();
                            }
                            i++;
                        }
                    }
                }
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }finally {
                playFlag =false;
                playSearchFlag = false;
                interruptNumber =0;
            }
        }
    }
}
