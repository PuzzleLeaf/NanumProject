package com.puzzleleaf.nanum;

import android.content.Context;
import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

/**
 * Created by cmtyx on 2016-11-09.
 */

public class SoundPoolObject {
    private SoundPool sp;
    private Integer[] note;
    private Context context;
    private int temp;
    SoundPoolObject(Context context)
    {
        this.context = context;
        temp = 0;
        note = new Integer[30];
        sp = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);//최대 스트림 수, 오디오 스트림 타입, 샘플링 품질
    }
    void playNote(int num)
    {
        temp = num;
        sp.play(note[temp],1,1,0,0,1.0f);
    }
    void stopNote() {
        sp.stop(temp);
    }

    int getCurrentAt(String edText,int ed)
    {
        int temp =0;
        for(int i=0;i<=ed;i++)
        {
            if(edText.charAt(i)=='/')
                temp++;
        }
        return temp;
    }
    int searchCurrentAt(int at,String edText,int flag)
    {
        int temp =0;
        if(flag == 1) {//우측 한마디 탐색{
            for (int i = at; i < edText.length(); i++) {
                if (edText.charAt(i) == '/' && i != at) {
                    Log.d("qwe", String.valueOf(edText.charAt(i)));
                    return i;
                }
            }
            return -1;
        }
        else if(flag==2)//좌측 한마디 탐색
            for(int i=at;i>=0;i--)
            {
                if( (edText.charAt(i)=='/' && i!= at) || (i==0)&&(i!=at) )
                    return i;

            }
        else if(flag==3) //우측 4마디 탐색
        {
            for(int i = at; i<edText.length();i++)
            {
                if(edText.charAt(i)=='/' && i!=at)
                    temp++;
                if(temp==4)
                    return i;
            }
        }
        else if(flag==4) //좌측 4마디 탐색
        {
            for(int i = at; i>=0;i--)
            {
                if( (edText.charAt(i)=='/' && i!= at)  || (i==0)&&(i!=at)  )
                    temp++;
                if(temp==4)
                    return i;
            }
        }
        return -1;
    }

    //음성 데이터 초기화
    void initNote()
    {
        note[0] = sp.load(context,R.raw.c,1); note[1] = sp.load(context,R.raw.cup,1);
        note[2] = sp.load(context,R.raw.d,1);
        note[3] = sp.load(context,R.raw.eb,1); note[4] = sp.load(context,R.raw.e,1);
        note[5] = sp.load(context,R.raw.f,1); note[6] = sp.load(context,R.raw.fup,1);
        note[7] = sp.load(context,R.raw.g,1); note[8] = sp.load(context,R.raw.gup,1);
        note[9] = sp.load(context,R.raw.a,1);
        note[10] = sp.load(context,R.raw.bb,1); note[11] = sp.load(context,R.raw.b,1); //1옥
        note[12] = sp.load(context,R.raw.c2,1); note[13] = sp.load(context,R.raw.c2up,1);
        note[14] = sp.load(context,R.raw.d2,1);
        note[15] = sp.load(context,R.raw.e2b,1); note[16] = sp.load(context,R.raw.e2,1);
        note[17] = sp.load(context,R.raw.f2,1); note[18] = sp.load(context,R.raw.f2up,1);
        note[19] = sp.load(context,R.raw.g2up,1); note[20] = sp.load(context,R.raw.g2,1);
        note[21] = sp.load(context,R.raw.a2,1);
        note[22] = sp.load(context,R.raw.b2b,1); note[23] = sp.load(context,R.raw.b2,1);//2옥
        note[24] = sp.load(context,R.raw.c3,1); note[25] = sp.load(context,R.raw.c3up,1);
        note[26] = sp.load(context,R.raw.d3,1);
        note[27] = sp.load(context,R.raw.e3b,1); note[28] = sp.load(context,R.raw.e3,1);
        note[29] = sp.load(context,R.raw.f3,1);
    }
    //음성 출력 함수
    int playSound(String edText,int at)
    {
        //음계가 아닌 경우 빠른 리턴

        if(edText.charAt(at) == '/')
            return 1;

        if(edText.charAt(at) == ' '||edText.charAt(at) == '#'||edText.charAt(at) == 'b'||
                edText.charAt(at) == '2'||edText.charAt(at) == '3')
            return 0;

        if(edText.charAt(at)=='도')
        {
            if(at+1<edText.length() && edText.charAt(at+1)=='#')
                playNote(1); //도#
            else if(at+1<edText.length() && edText.charAt(at+1)=='2')
            {//2옥타브
                if(at+2<edText.length() && edText.charAt(at+2)=='#') {
                    playNote(13); //도2#
                }
                else
                    playNote(12); //도2
            }
            else if(at+1<edText.length() && edText.charAt(at+1)=='3')
            {//3옥타브
                if(at+2<edText.length() && edText.charAt(at+2)=='#') {
                    playNote(25); //도3#
                }
                else
                    playNote(24); //도3
            }
            else
                playNote(0); //도
        }
        else if(edText.charAt(at)=='레')
        {
            if(at+1<edText.length() && edText.charAt(at+1)=='2')
            {//2옥타브
                    playNote(14); //레2
            }
            else if(at+1<edText.length() && edText.charAt(at+1)=='3')
            {//3옥타브
                    playNote(26); //레3
            }
            else
                playNote(2); //레
        }
        else if(edText.charAt(at)=='미')
        {
            if(at+1<edText.length() && edText.charAt(at+1)=='b')
                playNote(3); //미b
            else if(at+1<edText.length() && edText.charAt(at+1)=='2')
            {//2옥타브
                if(at+2<edText.length() && edText.charAt(at+2)=='b') {
                    playNote(15); //미2b
                }
                else
                    playNote(16); //미2
            }
            else if(at+1<edText.length() && edText.charAt(at+1)=='3')
            {//3옥타브
                if(at+2<edText.length() && edText.charAt(at+2)=='b') {
                    playNote(27); //미3b
                }
                else
                    playNote(28); //미3
            }
            else
                playNote(4); //미
        }
        else if(edText.charAt(at)=='파')
        {
            if(at+1<edText.length() && edText.charAt(at+1)=='#')
                playNote(6); //파#
            else if(at+1<edText.length() && edText.charAt(at+1)=='2')
            {//2옥타브
                if(at+2<edText.length() && edText.charAt(at+2)=='#') {
                    playNote(18); //파2#
                }
                else
                    playNote(17); //파2
            }
            else if(at+1<edText.length() && edText.charAt(at+1)=='3')
            {//3옥타브
                if(at+2<edText.length() && edText.charAt(at+2)=='#') {
                    playNote(29); //파3#
                }
                else
                    playNote(29); //파3
            }
            else
                playNote(5); //파
        }
        else if(edText.charAt(at)=='솔')
        {
            if(at+1<edText.length() && edText.charAt(at+1)=='#')
                playNote(8); //파#
            else if(at+1<edText.length() && edText.charAt(at+1)=='2')
            {//2옥타브
                if(at+2<edText.length() && edText.charAt(at+2)=='#') {
                    playNote(19); //솔2#
                }
                else
                    playNote(20); //솔2
            }
            else
                playNote(7); //솔
        }
        else if(edText.charAt(at)=='라')
        {
            if(at+1<edText.length() && edText.charAt(at+1)=='2')
            {//2옥타브
                playNote(21); //라2
            }
            else
                playNote(9); //라
        }
        else if(edText.charAt(at)=='시')
        {
            if(at+1<edText.length() && edText.charAt(at+1)=='b')
                playNote(10); //시b
            else if(at+1<edText.length() && edText.charAt(at+1)=='2')
            {//2옥타브
                if(at+2<edText.length() && edText.charAt(at+2)=='b') {
                    playNote(22); //시2b
                }
                else
                    playNote(23); //시2
            }
            else
                playNote(11); //시
        }

        return 0;
    }


}
