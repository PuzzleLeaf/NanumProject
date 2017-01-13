package com.puzzleleaf.nanum;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cmtyx on 2016-11-25.
 */
public class AdminRemove extends AppCompatActivity {

    Map<String,Object> writeObject;
    ArrayList<String> instObject;
    ArrayList<String> musicObject;
    TempData tempData;
    EditText adminWriteTitle, adminWriteInst;
    Button adminEditSearchButton,adminEditInstSearchBtn,adminEditRemove;
    boolean isMusicName = false;
    boolean isMusicInst = false;
    FireBaseData fbd;
    DatabaseReference myRef;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fbd = new FireBaseData(MusicData.getKey(this));
        myRef = fbd.getRef();
        setContentView(R.layout.activity_admin_remove);
        tempData = new TempData(MusicData.mdata);
        tempData.setSearchMusicName();
        writeObject = new HashMap<>();
        instObject = new ArrayList<>();
        musicObject = new ArrayList<>();


        adminWriteTitle = (EditText)findViewById(R.id.adminWriteTitle);
        adminWriteInst = (EditText)findViewById(R.id.adminWriteInst);
        adminEditSearchButton = (Button)findViewById(R.id.adminEditSearchBtn);
        adminEditInstSearchBtn = (Button)findViewById(R.id.adminEditInstSearchBtn);
        adminEditRemove = (Button)findViewById(R.id.adminEditRemove);

        adminEditSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMusicName = tempData.setMakeSearchHashMap(adminWriteTitle.getText().toString()))
                {
                    instObject = tempData.getAdminEditInst();
                    isMusicInst = false;
                    Log.d("qwe",String.valueOf(instObject.size())+"----------------------------");
                    for(int i=0;i<instObject.size();i++)
                    {
                        Toast.makeText(getApplicationContext(),instObject.get(i),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    isMusicName =false;
                    isMusicInst = false;
                    musicObject = tempData.getAdminEditMusic();
                    for(int i=0;i<musicObject.size();i++)
                        Toast.makeText(getApplicationContext(),musicObject.get(i),Toast.LENGTH_SHORT).show();
                }
            }
        });
        adminEditInstSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMusicName)
                {
                    isMusicInst = true;
                    Toast.makeText(getApplicationContext(),"삭제 버튼을 눌러주세요.",Toast.LENGTH_SHORT).show();
                }
                else{
                    isMusicInst = false;
                    Toast.makeText(getApplicationContext(),"존재하지 않는 악기입니다.",Toast.LENGTH_SHORT).show();
                }
            }
        });

        adminEditRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMusicInst)
                {
                    myRef.child(adminWriteTitle.getText().toString()).child(adminWriteInst.getText().toString()).removeValue();
                    Toast.makeText(getApplicationContext(),"삭제가 완료 되었습니다.",Toast.LENGTH_SHORT).show();
                    adminWriteInst.setText("");
                    adminWriteTitle.setText("");
                    isMusicInst = false;
                    isMusicName =false;
                    fbd.resetRef();
                    finish();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"입력 값을 확인해주세요.",Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}
