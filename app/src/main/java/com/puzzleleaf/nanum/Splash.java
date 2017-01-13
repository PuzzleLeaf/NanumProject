package com.puzzleleaf.nanum;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.Serializable;
import java.util.HashMap;

public class Splash extends AppCompatActivity {
    //Splash 화면이 떠 있을 시간 지정
    private final int SPLASH_DISPLAY_TIME = 3000;
    FireBaseData fbd;
    DatabaseReference myRef;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        Button adminBtn = (Button)findViewById(R.id.adminBtn);
        fbd = new FireBaseData(MusicData.getKey(this));
        myRef = fbd.getRef();
        if(!MusicData.getKey(getApplicationContext()).equals("null")){
            MusicData.mdata.put("인터넷이 안됩니다.",internetErrorFix());
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    MusicData.mdata.clear();
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        Log.d("qwe",child.getKey().toString()+"간다~~~!");
                        MusicData.mdata.put(child.getKey(),(HashMap<String, String>)child.getValue());
                    }
                }
                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        final Handler handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if(!MusicData.getKey(getApplicationContext()).equals("null")) {
                    Intent intent = new Intent(Splash.this, MusicSelect.class);
                    startActivity(intent);
                }
                else{
                    Toast.makeText(getApplicationContext(), "Key를 등록 해주세요", Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        };

        //버튼 클릭시 관리자 메뉴로 넘어가도록 함
        adminBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handler.removeMessages(0); //메인 함수로 넘어가기 위한 Handler 메시지 제거
                Intent intent = new Intent(Splash.this,AdminNumber.class);
                startActivity(intent);
                finish();
            }
        });
        handler.sendEmptyMessageDelayed(0,SPLASH_DISPLAY_TIME);
    }

    HashMap<String, String> internetErrorFix()
    {
        HashMap<String, String> tem = new HashMap<>();
        tem.put("인터넷이 안돼요","도 레 미 파 솔");
        return tem;
    }


}
