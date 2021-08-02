package com.example.scubadive2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JoinActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    TextView sameCheckTv, joinBirthD8Tv;
    EditText joinIdEt, joinPwEt, joinPwEt1, joinEmailEt, joinNameEt;
    Button sameCheckBtn, joinD8Btn, joinAddBtn;

    int mode = 0;
    boolean idCheck = false;
    Date birthD8 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        sameCheckTv = findViewById(R.id.sameCheckTv); joinBirthD8Tv = findViewById(R.id.joinBirthD8Tv);
        joinIdEt = findViewById(R.id.joinIdEt); joinPwEt = findViewById(R.id.joinPwEt);
        joinPwEt1 = findViewById(R.id.joinPwEt1); joinEmailEt = findViewById(R.id.joinEmailEt);
        joinNameEt = findViewById(R.id.joinNameEt); sameCheckBtn = findViewById(R.id.sameCheckBtn);
        joinD8Btn = findViewById(R.id.joinD8Btn); joinAddBtn = findViewById(R.id.joinAddBtn);

        sameCheckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSameCheckPost(joinIdEt.getText().toString());
            }
        });

        joinD8Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year1 = cal.get(Calendar.YEAR);
                int month1 = cal.get(Calendar.MONTH);
                int day1 = cal.get(Calendar.DATE);
                DatePickerDialog dp = new DatePickerDialog(JoinActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String m = month<9? ("0"+String.valueOf(month+1)): String.valueOf(month+1);
                        String d = dayOfMonth < 10? ("0"+String.valueOf(dayOfMonth)): String.valueOf(dayOfMonth);
                        joinBirthD8Tv.setText(String.valueOf(year) + "-"+ m +"-"+ d);

                        Calendar cal1 = Calendar.getInstance();
                        cal1.set(Calendar.YEAR, year);
                        cal1.set(Calendar.MONTH, month-1);
                        cal1.set(Calendar.DATE, dayOfMonth);
                        birthD8 = cal1.getTime();
                        Log.d("kkk", "BirthDay: " + cal1);
                    }
                },year1, month1, day1);
                dp.setTitle("생년월일 선택");
                dp.show();
            }
        });

        joinAddBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pw = joinPwEt.getText().toString();
                String pw1 = joinPwEt1.getText().toString();

                if(pw.equals(pw1)) {
                    if(idCheck) {
                        String userId = joinIdEt.getText().toString();
                        String userPw = encoder(joinPwEt.getText().toString());
                        String email = joinEmailEt.getText().toString();
                        String userName = joinNameEt.getText().toString();
                        String birthD8 = joinBirthD8Tv.getText().toString();
                        joinAccountPost(userId, userPw, email, userName, birthD8);

                    }
                    else {
                        Toast.makeText(JoinActivity.this, "ID중복확인이 완료되지 않았습니다", Toast.LENGTH_LONG).show();
                    }
                }
                else {
                    Toast.makeText(JoinActivity.this, "비밀번호가 일치하지 않습니다!", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void getSameCheckPost(final String userId) {
        mode = 1;
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/diveIdSameCheck.php?";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }

    private void joinAccountPost(final String userId, final String userPw, final String email, final String userName, final String birthD8) {
        mode = 2;
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/diveJoinAccount.php?";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", userId);
                params.put("userPw", userPw);
                params.put("email", email);
                params.put("userName", userName);
                params.put("birthD8", birthD8);
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
        if(mode == 1) {
            Log.d("kkk", response);
            if(response.equals("0")) {
                sameCheckTv.setText("사용가능한 ID입니다.");
                idCheck = true;
            }
            else {
                sameCheckTv.setText("이미 사용중인 ID입니다");
                idCheck = false;
            }
        }
        else if(mode == 2) {
            Toast.makeText(JoinActivity.this, "가입완료", Toast.LENGTH_LONG).show();
            finish();
        }
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
