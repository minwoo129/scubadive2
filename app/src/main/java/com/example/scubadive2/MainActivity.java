package com.example.scubadive2;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Response;
import com.android.volley.VolleyError;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, Response.Listener<String>, Response.ErrorListener {

    LogFragment fragment1 = new LogFragment();
    SimulationFragment fragment2 = new SimulationFragment();
    MapFragment fragment3 = new MapFragment();
    AccountFragment fragment4 = new AccountFragment();
    LoginFragment fragment5 = new LoginFragment();

    Button menuBtn1, menuBtn2, menuBtn3, menuBtn4;
    UserInfo user;
    int page = 1;
    static boolean firstStart = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        menuBtn1 = findViewById(R.id.menuBtn1);
        menuBtn2 = findViewById(R.id.menuBtn2);
        menuBtn3 = findViewById(R.id.menuBtn3);
        menuBtn4 = findViewById(R.id.menuBtn4);

        menuBtn1.setOnClickListener(this);
        menuBtn2.setOnClickListener(this);
        menuBtn3.setOnClickListener(this);
        menuBtn4.setOnClickListener(this);

        initDB();
        loadDB();
    }

    @Override
    public void onClick(View v) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        switch (v.getId()) {
            case R.id.menuBtn1:
                ft.replace(R.id.frameLayout, fragment1);
                ft.commit();
                break;
            case R.id.menuBtn2:
                ft.replace(R.id.frameLayout, fragment2);
                ft.commit();
                break;
            case R.id.menuBtn3:
                ft.replace(R.id.frameLayout, fragment3);
                ft.commit();
                break;
            case R.id.menuBtn4:
                ft.replace(R.id.frameLayout, fragment4);
                ft.commit();
                break;

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2020) {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameLayout, fragment1);
            ft.commit();
        }
    }

    public void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayout, fragment).commit();
    }


    private void initDB() {
        SQLiteDatabase db = null;
        if(db == null) {
            db = openOrCreateDatabase("user", Context.MODE_PRIVATE, null);
        }

        db.execSQL("CREATE TABLE IF NOT EXISTS user("
                +"userId TEXT,"
                +"userPw TEXT)");

        db.close();
    }

    private void loadDB() {
        SQLiteDatabase db = null;
        if(db == null) {
            db = openOrCreateDatabase("user", Context.MODE_PRIVATE, null);
        }
        Cursor c =db.rawQuery("select * from user", null);
        c.moveToFirst();
        while (c.isAfterLast() == false) {
            String userId = c.getString(0);
            String userPw = c.getString(1);
            user = new UserInfo(userId, userPw);
            c.moveToNext();
        }
        c.close();
        db.close();

        if(user != null) {
            GetDataActivity.user.setUserId(user.getUserId());
            GetDataActivity.user.setUserPw(user.getUserPw());

            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameLayout, fragment1);
            ft.commit();
        }
        else {
            FragmentManager fm = getSupportFragmentManager();
            FragmentTransaction ft = fm.beginTransaction();
            ft.replace(R.id.frameLayout, fragment5);
            ft.commit();
        }
    }


    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {

    }

    public static String encoder(String data) {
        String retVal = "";
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-1");
            md.update(data.getBytes());

            byte byteData[] = md.digest();

            StringBuffer sb = new StringBuffer();
            for(int i=0; i<byteData.length; i++) {
                sb.append(Integer.toString((byteData[i]&0xff) + 0x100, 16).substring(1));
            }

            retVal = sb.toString();

        } catch(NoSuchAlgorithmException e){
            e.printStackTrace();
        }

        return retVal;

    }
}
