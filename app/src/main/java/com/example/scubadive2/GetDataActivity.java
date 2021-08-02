package com.example.scubadive2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GetDataActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener {

    static UserInfo user = new UserInfo();
    static ArrayList<DiveLog> logArr = new ArrayList<>();
    static ArrayList<Licence> licenceArr = new ArrayList<>();

    int mode = 1;
    static int activeMode = 0;
    static int GET_DATA = 100;
    static int MODIFY_ACCOUNT = 110;
    static int ADD_LICENCE = 120;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_data);
        if(activeMode == GET_DATA) autoLoginPost();
        else if(activeMode == MODIFY_ACCOUNT) modifyAccountPost();
        else if(activeMode == ADD_LICENCE) addLicence();
    }

    public void autoLoginPost() {
        Log.d("kkk", "자동로그인");
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/getAccount.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", user.getUserId());
                params.put("userPw", user.getUserPw());
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    public void getLogDataPost() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/getLog.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", user.getUserId());
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    public void getLicenceListPost() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/getLicence.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", user.getUserId());
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    public void modifyAccountPost() {
        Log.d("kkk", "정보수정");
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        final String uri = "http://ikey0129.woobi.co.kr/modifyAccount.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", user.getUserId());
                params.put("userPw", user.getUserPw());
                params.put("email", user.getEmail());
                params.put("userName", user.getUserName());
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    public void addLicence() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/insertLicence.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", user.getUserId());
                params.put("licenceCode", licenceArr.get(licenceArr.size()-1).getLicenceCode());
                params.put("licenceNo", licenceArr.get(licenceArr.size()-1).getLicenceNo());
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    public void updateLicenceAccount(final int mi) {
        final int depth = (int)(licenceArr.get(mi).getLicenceCode().charAt(1))-48 > 1? 30: 18;
        Log.d("kkk", "깊이: " + depth);
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/modifyLicenceAccount.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", user.getUserId());
                params.put("licenceCode", licenceArr.get(mi).getLicenceCode());
                params.put("licenceNo", licenceArr.get(mi).getLicenceNo());
                params.put("maxDepth", String.valueOf(depth));
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
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
    private void updateDB() {
        SQLiteDatabase db = null;
        if(db == null) {
            db = openOrCreateDatabase("user", Context.MODE_PRIVATE, null);
        }
        db.execSQL("UPDATE user SET userPw='"+user.getUserPw()+"' WHERE userId='"+user.getUserId()+"'");
        db.close();
    }

    @Override
    public void onErrorResponse(VolleyError error) {

    }

    @Override
    public void onResponse(String response) {
        if(activeMode == GET_DATA) {
            if(mode == 1) { // 로그인계정
                try {
                    JSONArray array = new JSONArray(response);
                    JSONObject ob = array.getJSONObject(0);
                    user.setUserName(ob.getString("userName"));
                    user.setEmail(ob.getString("email"));
                    user.setBirthD8(ob.getString("birthD8"));
                    user.setLicenceCode(ob.getString("licenceCode"));
                    user.setLicenceNo(ob.getString("licenceNo"));
                    user.setMaxDepth(ob.getInt("maxDepth"));
                    user.setAccuLog(ob.getInt("accuLog"));
                    mode = 2;
                    getLogDataPost();

                } catch (JSONException e) {
                    Log.d("kkk", "자동로그인 실패");
                    e.printStackTrace();
                }
            }
            else if(mode == 2) { // 다이브 로그
                try {
                    logArr.clear();
                    JSONArray fullArray = new JSONArray(response);
                    JSONArray arr1 = fullArray.getJSONArray(0);
                    DiveLog[][] logs = new DiveLog[arr1.length()][fullArray.length()];
                    for (int i = 0; i < fullArray.length(); i++) {
                        JSONArray arr = fullArray.getJSONArray(i);
                        for(int j = 0; j < arr.length(); j++) {
                            DiveLog log;
                            JSONObject ob = arr.getJSONObject(j);
                            switch(i) {
                                case 0:
                                    String[] d = ob.getString("d8").split(" ");
                                    log = new DiveLog(ob.getInt("diveLog"), ob.getString("buddyName"), ob.getString("instructor"), ob.getString("divemaster"), ob.getString("purpose"), d[0], d[1]);
                                    logs[j][i] = log;
                                    break;
                                case 1:
                                    double[] coordiness = {ob.getDouble("lattitude"), ob.getDouble("longitude")};
                                    log = new DiveLog(ob.getInt("diveLog"), ob.getString("location"), ob.getString("point"), coordiness);
                                    logs[j][i] = log;
                                    break;
                                case 2:
                                    log = new DiveLog(ob.getInt("diveLog"), ob.getString("weather"), ob.getInt("diveTime"), ob.getDouble("maxDepth"), ob.getInt("sawLength"), ob.getInt("watTemp"));
                                    logs[j][i] = log;
                                    break;
                                case 3:
                                    log = new DiveLog(ob.getInt("diveLog"), ob.getString("tank"), ob.getString("gas"), ob.getString("suit"));
                                    logs[j][i] = log;
                                    break;
                                case 4:
                                    log = new DiveLog(ob.getInt("diveLog"), ob.getInt("befGasLeft"), ob.getInt("aftGasLeft"), ob.getInt("beltWeight"));
                                    logs[j][i] = log;
                                    break;
                                case 5:
                                    log = new DiveLog(ob.getInt("diveLog"), ob.getString("memo"));
                                    logs[j][i] = log;
                                    break;
                            }
                        }
                    }
                    for(int i = 0; i < logs.length; i++) {
                        DiveLog dLog = new DiveLog();
                        for(int j = 0; j < logs[i].length; j++) {
                            switch (j) {
                                case 0:
                                    dLog.setDiveLog(logs[i][j].getDiveLog()); dLog.setBuddyName(logs[i][j].getBuddyName());
                                    dLog.setInstructor(logs[i][j].getInstructor()); dLog.setDiveMaster(logs[i][j].getDiveMaster());
                                    dLog.setPurpose(logs[i][j].getPurpose());
                                    dLog.setDate(logs[i][j].getDate()); dLog.setTime(logs[i][j].getTime());
                                    break;
                                case 1:
                                    dLog.setLocation(logs[i][j].getLocation()); dLog.setPoint(logs[i][j].getPoint());
                                    dLog.setCoordiness(logs[i][j].getCoordiness());
                                    break;
                                case 2:
                                    dLog.setWeather(logs[i][j].getWeather()); dLog.setDiveTime(logs[i][j].getDiveTime());
                                    dLog.setMaxDepth(logs[i][j].getMaxDepth()); dLog.setSawLength(logs[i][j].getSawLength());
                                    dLog.setWatTemp(logs[i][j].getWatTemp());
                                    break;
                                case 3:
                                     dLog.setTank(logs[i][j].getTank());
                                    dLog.setGas(logs[i][j].getGas()); dLog.setSuit(logs[i][j].getSuit());
                                    break;
                                case 4:
                                    dLog.setBefGasLeft(logs[i][j].getBefGasLeft()); dLog.setAftGasLeft(logs[i][j].getAftGasLeft());
                                    dLog.setBeltWeight(logs[i][j].getBeltWeight());
                                    break;
                                case 5:
                                    dLog.setMemo(logs[i][j].getMemo());
                                    break;
                            }
                        }
                        logArr.add(dLog);
                    }
                    mode = 3;
                    getLicenceListPost();

                } catch (JSONException e) {
                    Log.d("kkk", "데이터 요청실패");
                    e.printStackTrace();
                }
            }
            else if(mode == 3) { // 보유 자격증 리스트
                licenceArr.clear();
                try {
                    JSONArray licArr = new JSONArray(response);
                    for (int i = 0; i < licArr.length(); i++)
                        licenceArr.add(new Licence(licArr.getJSONObject(i).getString("licenceCode"), licArr.getJSONObject(i).getString("licenceNo")));
                    MainActivity.firstStart = false;
                    activeMode = 0;
                    finish();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        else if(activeMode == MODIFY_ACCOUNT) {
            if(mode == 1) {
                updateDB();
                activeMode = 0;
                finish();
            }
        }
        else if(activeMode == ADD_LICENCE) {
            if(mode == 1) {
                int[] lev = new int[licenceArr.size()];
                for(int i = 0; i < licenceArr.size(); i++) {
                    char n = licenceArr.get(i).getLicenceCode().charAt(1);
                    lev[i] = (int)n-48;
                }
                int m = 0, mi = 0;
                for(int i = 0; i < lev.length-1; i++) {
                    m = lev[i] >= lev[i + 1] ? lev[i] : lev[i + 1];
                    mi = lev[i] >= lev[i + 1]? i:i+1;
                }
                mode = 2;
                if(user.getLicenceCode() != licenceArr.get(mi).getLicenceCode()) {
                    user.setLicenceCode(licenceArr.get(mi).getLicenceCode());
                    user.setLicenceNo(licenceArr.get(mi).getLicenceNo());
                    user.setMaxDepth((int)(licenceArr.get(mi).getLicenceCode().charAt(1))-48 > 1? 30: 18);
                }
                updateLicenceAccount(mi);
            }
            else if(mode == 2) {
                activeMode = 0;
                finish();
            }

        }

    }
}
