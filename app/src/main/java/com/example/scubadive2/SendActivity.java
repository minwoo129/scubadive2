package com.example.scubadive2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class SendActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    private String id = "";
    private String pw = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        Intent intent = getIntent();
        String userId = intent.getStringExtra("userId");
        String userPw = intent.getStringExtra("userPw");
        id = userId;
        pw = userPw;
        getLoginPost( userId, userPw);
    }

    public void getLoginPost(final String userId, final String userPw) {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/diveLogin.php?";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("userPw", userPw);
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        Intent intent = new Intent();
        intent.putExtra("count", response);
        setResult(100, intent);
        initDB();
        if(loadDB() > 0) clearDB();
        saveDB(id, pw);
        finish();
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

    private void saveDB(String userId, String userPw) {
        SQLiteDatabase db = null;
        if(db == null) {
            db = openOrCreateDatabase("user", Context.MODE_PRIVATE, null);
        }
        db.execSQL("INSERT INTO user (userId, userPw) VALUES ('"+userId+"', '"+userPw+"')");
        db.close();
    }

    private int loadDB() {
        SQLiteDatabase db = null;
        if(db == null) {
            db = openOrCreateDatabase("user", Context.MODE_PRIVATE, null);
        }
        Cursor c =db.rawQuery("select * from user", null);
        c.moveToFirst();
        int count = 0;
        while (c.isAfterLast() == false) {
            count++;
        }
        c.close();
        db.close();
        return count;
    }

    private void clearDB() {
        SQLiteDatabase db = null;
        if(db == null) {
            db = openOrCreateDatabase("user", Context.MODE_PRIVATE, null);
        }
        db.execSQL("DELETE FROM user");
        db.close();
    }
}
