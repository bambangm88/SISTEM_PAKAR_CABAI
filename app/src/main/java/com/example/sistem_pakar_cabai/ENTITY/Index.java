package com.example.sistem_pakar_cabai.ENTITY;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.sistem_pakar_cabai.MainActivity;
import com.example.sistem_pakar_cabai.R;



public class Index extends AppCompatActivity {

    Button btn_login, btn_register;
    SharedPreferences sharedpreferences;
    Boolean session = false;
    String id, username;
    public static final String my_shared_preferences = "my_shared_preferences";
    public static final String session_status = "session_status";
    public final static String TAG_USERNAME = "username";
    public final static String TAG_ID = "id";
    public final static String TAG_LEVEL = "status";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        btn_login = findViewById(R.id.btnLogin);
        btn_register = findViewById(R.id.btnRegister);


        // Cek session login jika TRUE maka langsung
        sharedpreferences = getSharedPreferences(my_shared_preferences, Context.MODE_PRIVATE);
        session = sharedpreferences.getBoolean(session_status, false);
        id = sharedpreferences.getString(TAG_ID, null);
        username = sharedpreferences.getString(TAG_USERNAME, null);

        if (session) {
            Intent intent = new Intent(Index.this, MainActivity.class);
            intent.putExtra(TAG_ID, id);
            intent.putExtra(TAG_USERNAME, username);
            finish();
            startActivity(intent);
        }


        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Index.this,Login.class));
                finish();
            }
        });


        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Index.this,Register.class));
                finish();
            }
        });


    }
}
