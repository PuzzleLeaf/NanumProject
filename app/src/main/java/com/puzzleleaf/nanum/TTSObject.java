package com.puzzleleaf.nanum;

import android.content.Context;
import android.speech.tts.TextToSpeech;

import java.util.Locale;

/**
 * Created by cmtyx on 2016-11-06.
 */

public class TTSObject {
    private TextToSpeech tts;
    //tts 생성자에 Context를 전달해야함
    TTSObject(Context ctx){
        tts = new TextToSpeech(ctx, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    tts.setLanguage(Locale.KOREAN);
                }
            }
        });
    }

    void readText(String text){
        tts.speak(text,TextToSpeech.QUEUE_FLUSH,null);
    }
    void stopText()
    {
        if(tts!=null)
            tts.stop();
    }
    void setDestroy()
    {
       tts.stop(); tts.shutdown();
    }
}
