package com.puzzleleaf.nanum;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class AdminMenu extends AppCompatActivity {

    Button adminWrite,adminEdit,adminRemove;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_menu);

        adminWrite = (Button)findViewById(R.id.adminWriteMenu);
        adminWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenu.this,AdminWrite.class);
                startActivity(intent);
            }
        });

        adminEdit = (Button)findViewById(R.id.adminEditMenu);
        adminEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenu.this,AdminEdit.class);
                startActivity(intent);
            }
        });

        adminRemove = (Button)findViewById(R.id.adminEditRemove);
        adminRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AdminMenu.this,AdminRemove.class);
                startActivity(intent);
            }
        });

    }
}
