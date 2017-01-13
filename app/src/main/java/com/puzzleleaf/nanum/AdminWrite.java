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
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cmtyx on 2016-11-25.
 */

public class AdminWrite extends AppCompatActivity {

    Map<String,Object> writeObject;
    HashMap<String,String> musicObject;
    EditText adminWriteTitle, adminWriteInst, adminWriteMusic;
    Button adminWriteSubmit, adminWriteTest;
    FireBaseData fbd;
    DatabaseReference myRef;


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_write);
        writeObject = new HashMap<>();
        musicObject = new HashMap<>();
        //db 연결

        fbd = new FireBaseData(MusicData.getKey(this));
        myRef = fbd.getRef();


        adminWriteTitle = (EditText)findViewById(R.id.adminWriteTitle);
        adminWriteInst = (EditText)findViewById(R.id.adminWriteInst);
        adminWriteMusic = (EditText)findViewById(R.id.adminWriteMusic);
        adminWriteSubmit = (Button)findViewById(R.id.adminWriteSubmit);
        adminWriteTest = (Button)findViewById(R.id.adminWriteTest);


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
                    Toast.makeText(getApplicationContext(), "값을 등록했습니다.", Toast.LENGTH_SHORT).show();
                    myRef.child(adminWriteTitle.getText().toString()).child(adminWriteInst.getText().toString())
                            .setValue(adminWriteMusic.getText().toString());
                    fbd.resetRef();
                    finish();
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


    }
}
