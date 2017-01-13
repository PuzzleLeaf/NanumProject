package com.puzzleleaf.nanum;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminNumber extends AppCompatActivity {

    private EditText edit;
    private Button btn;
    ArrayList<String> check;
    FireBaseData fbd;
    DatabaseReference myRef;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_number);
        check = new ArrayList<>();
        fbd = new FireBaseData("Key");
        myRef = fbd.getRef();

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot child : dataSnapshot.getChildren())
                {
                    Log.d("qwe",child.getValue().toString());
                    check.add(child.getValue().toString());
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        edit = (EditText)findViewById(R.id.adminEdit);
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                InputMethodManager imm = (InputMethodManager) getSystemService(getApplicationContext().INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        });

        btn = (Button)findViewById(R.id.adminCheck);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(checkValue(edit.getText().toString())) {
                    MusicData.savekey(getApplicationContext(),edit.getText().toString());
                    Intent intent = new Intent(AdminNumber.this, AdminMenu.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    MusicData.savekey(getApplicationContext(),"null"); // 틀린 경우 리셋 null
                    Toast.makeText(getApplicationContext(), "올바르지 않은 Key 값", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    Boolean checkValue(String key)
    {
        for(int i=0;i<check.size();i++) {
            Log.d("qwe",check.get(i)+"123123123");
            if (check.get(i).equals(key))
                return true;
        }
        return false;
    }
}
