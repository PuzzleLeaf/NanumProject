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

public class AdminEdit extends AppCompatActivity {

    Map<String,Object> writeObject;
    ArrayList<String> instObject;
    ArrayList<String> musicObject;
    EditText adminWriteTitle, adminWriteInst, adminWriteMusic;
    Button adminWriteSubmit, adminWriteTest, adminEditSearchButton,adminEditInstSearchBtn;
    TempData tempData;
    String note= "";
    FireBaseData fbd;
    DatabaseReference myRef;
    boolean isMusicName = false;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fbd = new FireBaseData(MusicData.getKey(this));
        myRef = fbd.getRef();
        tempData = new TempData(MusicData.mdata);
        tempData.setSearchMusicName();
        setContentView(R.layout.activity_admin_edit);
        writeObject = new HashMap<>();
        instObject = new ArrayList<>();
        musicObject = new ArrayList<>();



        adminWriteTitle = (EditText)findViewById(R.id.adminWriteTitle);
        adminWriteInst = (EditText)findViewById(R.id.adminWriteInst);
        adminWriteMusic = (EditText)findViewById(R.id.adminWriteMusic);
        adminWriteSubmit = (Button)findViewById(R.id.adminWriteSubmit);
        adminWriteTest = (Button)findViewById(R.id.adminWriteTest);
        adminEditSearchButton = (Button)findViewById(R.id.adminEditSearchBtn);
        adminEditInstSearchBtn = (Button)findViewById(R.id.adminEditInstSearchBtn);


        adminWriteSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adminWriteTitle.getText().toString().equals("") || adminWriteMusic.getText().toString().equals("") ||
                        adminWriteInst.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    myRef.child(adminWriteTitle.getText().toString()).child(adminWriteInst.getText().toString())
                            .setValue(adminWriteMusic.getText().toString());
                }
            }
        });

        adminWriteTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adminWriteMusic.getText().length()>1)
                {
                    Intent intent = new Intent(getApplicationContext(),MusicPlayer.class);
                    intent.putExtra("value",adminWriteMusic.getText().toString()); //다른 액티비티로 값 넘겨주기
                    startActivity(intent);
                }
                else
                    Toast.makeText(getApplicationContext(),"값을 입력해주세요",Toast.LENGTH_SHORT).show();
            }
        });

        adminEditSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isMusicName = tempData.setMakeSearchHashMap(adminWriteTitle.getText().toString()))
                {
                    instObject = tempData.getAdminEditInst();
                    Log.d("qwe",String.valueOf(instObject.size())+"----------------------------");
                    for(int i=0;i<instObject.size();i++)
                    {
                        Toast.makeText(getApplicationContext(),instObject.get(i),Toast.LENGTH_SHORT).show();
                    }
                }else{
                    musicObject = tempData.getAdminEditMusic();
                    isMusicName =false;
                    for(int i=0;i<musicObject.size();i++)
                        Toast.makeText(getApplicationContext(),musicObject.get(i),Toast.LENGTH_SHORT).show();
                }
            }
        });

        adminEditInstSearchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                note=tempData.getAdminEditMusicNote(adminWriteInst.getText().toString());
                if(isMusicName && !note.equals(""))
                {
                    adminWriteMusic.setText(note);
                }
            }
        });

        adminWriteSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(adminWriteTitle.getText().toString().equals("") || adminWriteMusic.getText().toString().equals("") ||
                        adminWriteInst.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "값을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "값을 수정했습니다.", Toast.LENGTH_SHORT).show();
                    myRef.child(adminWriteTitle.getText().toString()).child(adminWriteInst.getText().toString())
                            .setValue(adminWriteMusic.getText().toString());
                    fbd.resetRef();
                    finish();
                }
            }
        });


    }
}
