package com.example.scubadive2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class AddLogActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener{

    TextView logNoTv, dateTv, timeTv, purposeTv, lettTv, longTv, suitTv, gasBeyondTv;
    RadioGroup diveTypeRg, weatherRg, gasRg, tankRg;
    EditText locationEt, pointEt, buddyEt, diveMasterEt, diveTimeEt, maxDepthEt, sawLengthEt, waterTempEt,
            instructorEt, weightEt1, weightEt2, beforeGasLeftEt1, beforeGasLeftEt2, afterGasLeftEt1,
            afterGasLeftEt2, memoEt;
    Button dateBt, timeBt, mapBt, suitBt, weightEnterBt, airEnterBt, addBt;
    String weather, tank, gas, diveType;
    int insertMode = 1;
    double lati, longi;
    private DiveLog newDiveLog = new DiveLog();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_log);

        logNoTv = findViewById(R.id.logNoTv); dateTv = findViewById(R.id.dateTv);
        timeTv = findViewById(R.id.timeTv); purposeTv = findViewById(R.id.purposeTv);
        lettTv = findViewById(R.id.lettTv); longTv = findViewById(R.id.longTv);
        suitTv = findViewById(R.id.suitTv); gasBeyondTv = findViewById(R.id.gasBeyondTv);
        diveTypeRg = findViewById(R.id.diveTypeRg); weatherRg = findViewById(R.id.weatherRg);
        gasRg = findViewById(R.id.gasRg); tankRg = findViewById(R.id.tankRg);
        locationEt = findViewById(R.id.locationEt); pointEt = findViewById(R.id.pointEt);
        buddyEt = findViewById(R.id.buddyEt); diveMasterEt = findViewById(R.id.diveMasterEt);
        diveTimeEt = findViewById(R.id.diveTimeEt); maxDepthEt = findViewById(R.id.maxDepthEt);
        sawLengthEt = findViewById(R.id.sawLengthEt); waterTempEt = findViewById(R.id.waterTempEt);
        instructorEt = findViewById(R.id.instructorEt); weightEt1 = findViewById(R.id.weightEt1);
        weightEt2 = findViewById(R.id.weightEt2); beforeGasLeftEt1 = findViewById(R.id.beforeGasLeftEt1);
        beforeGasLeftEt2 = findViewById(R.id.beforeGasLeftEt2); afterGasLeftEt1 = findViewById(R.id.afterGasLeftEt1);
        afterGasLeftEt2 = findViewById(R.id.afterGasLeftEt2); memoEt = findViewById(R.id.memoEt);
        dateBt = findViewById(R.id.dateBt); timeBt = findViewById(R.id.timeBt); mapBt = findViewById(R.id.mapBt);
        suitBt = findViewById(R.id.suitBt); weightEnterBt = findViewById(R.id.weightEnterBt);
        airEnterBt = findViewById(R.id.airEnterBt); addBt = findViewById(R.id.addBt);


        logNoTv.setText((GetDataActivity.user.getAccuLog()+1)+"");
        newDiveLog.setDiveLog(GetDataActivity.user.getAccuLog()+1);
        dateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year1 = cal.get(Calendar.YEAR);
                int month1 = cal.get(Calendar.MONTH);
                int day1 = cal.get(Calendar.DATE);

                DatePickerDialog dp = new DatePickerDialog(AddLogActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String m = (month < 9)? ("0"+String.valueOf(month+1)): String.valueOf(month+1);
                        String d = (dayOfMonth < 10)? ("0"+String.valueOf(dayOfMonth)): String.valueOf(dayOfMonth);
                        dateTv.setText(String.valueOf(year) + "-" + m + "-" + d);
                        newDiveLog.setDate(String.valueOf(year) + "-" + m + "-" + d);
                    }
                }, year1, month1, day1);
                dp.setTitle("날짜 선택");
                dp.show();
            }
        });
        timeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.HOUR_OF_DAY, 9);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog tp = new TimePickerDialog(AddLogActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String h = (hourOfDay < 10)? ("0"+String.valueOf(hourOfDay)):String.valueOf(hourOfDay);
                        String m = (minute < 10)? ("0"+String.valueOf(minute)):String.valueOf(minute);
                        timeTv.setText(h + ":" + m);
                        newDiveLog.setTime(h + ":" + m);
                    }
                }, hour, min, true);
                tp.show();
            }
        });
        diveTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, final int checkedId) {
                AlertDialog.Builder ab= new AlertDialog.Builder(AddLogActivity.this);
                final String[] type = {"비치", "보트", "리브어보트", "주간", "일몰", "야간", "절벽"
                        , "난파선", "동굴", "드리프트", "사이트마운트", "사진", "영상"};
                final boolean[] checked = new boolean[type.length];
                for(int i = 0; i < checked.length; i++) checked[i] = false;

                ab.setMultiChoiceItems(type, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checked[which] = true;
                    }
                });
                ab.setPositiveButton("선택 완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        int find = 0;
                        RadioButton rd = findViewById(checkedId);
                        String str = rd.getText().toString() + "/";
                        for(int i = 0; i < checked.length; i++) {
                            if(checked[i]) {
                                diveType = type[i];
                                if(find == 0) {
                                    str += type[i];
                                    find++;
                                }
                                else str += ("," + type[i]);
                            }
                        }
                        purposeTv.setText(str);
                        newDiveLog.setPurpose(str);
                    }
                });
                ab.show();
            }
        });
        /* 날씨 radiogroup*/
        weatherRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                newDiveLog.setWeather(rb.getText().toString());
            }
        });

        mapBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(AddLogActivity.this);
                final String[] menu = {"지도실행", "이전에 입력된 좌표 가져오기"};
                final boolean[] checked = {false, false};
                ab.setMultiChoiceItems(menu, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        checked[which] = true;
                    }
                });

                ab.setPositiveButton("실행", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(checked[0] && !checked[1]) {
                            Intent intent = new Intent(AddLogActivity.this, com.example.scubadive2.MapActivity.class);
                            startActivityForResult(intent, 2080);
                        }
                        else if(!checked[0] && checked[1]) {
                            getCoordinessDB();
                        }
                    }
                });
                ab.show();
            }
        });


        /* 탱크 radiogroup*/
        tankRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton rb = findViewById(checkedId);
                newDiveLog.setTank(rb.getText().toString());
            }
        });

        /* 가스 radioGroup*/
        gasRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.gasRb3)
                    gasBeyondTv.setVisibility(View.VISIBLE);
                RadioButton rb = findViewById(checkedId);
                newDiveLog.setGas(rb.getText().toString());
            }
        });

        /* 슈트 선택 */
        findViewById(R.id.suitBt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(AddLogActivity.this);
                final String[] suit = {"래시가드", "웻슈트 3mm", "웻슈트 5mm", "웻슈트 7mm",
                        "세미드라이", "드라이슈트", "후드", "장갑", "부츠"};
                final boolean[] check = new boolean[suit.length];
                for(int i = 0; i < check.length; i++) check[i] = false;
                ab.setMultiChoiceItems(suit, check, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        check[which] = true;
                    }
                });
                ab.setPositiveButton("입력 완료", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String str = "";
                        boolean first = true;
                        for(int i = 0; i < check.length; i++) {
                            if(check[i]) {
                                if(first) {
                                    str += suit[i];
                                    first = !first;
                                }
                                else
                                    str += (", " + suit[i]);
                            }
                        }
                        suitTv.setText(str);
                        newDiveLog.setSuit(str);
                    }
                });
                ab.show();
            }
        });

        /* 웨이트 무게 입력 버튼 click*/
        findViewById(R.id.weightEnterBt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int w1 = 0, w2 = 0;
                if(!weightEt1.getText().toString().equals("") && !weightEt2.getText().toString().equals("")) {
                    w1 = Integer.parseInt(weightEt1.getText().toString());
                    w2 = Integer.parseInt(weightEt2.getText().toString());
                }
                else {
                    if((weightEt2.getText().toString()).equals("")) {
                        w1 = (weightEt1.getText().toString().equals(""))? 0: Integer.parseInt(weightEt1.getText().toString());
                        w2 = (int)((double)w1 * (0.453592));
                        weightEt2.setText(String.valueOf(w2));
                    }
                    else if((weightEt1.getText().toString()).equals("")) {
                        w2 = (weightEt2.getText().toString().equals(""))? 0: Integer.parseInt(weightEt2.getText().toString());
                        w1 = (int)((double)w2 * (2.204623));
                        weightEt1.setText(String.valueOf(w1));
                    }
                }
                newDiveLog.setBeltWeight(Integer.parseInt(weightEt1.getText().toString()));
            }
        });


        findViewById(R.id.airEnterBt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                beforeGasLeftEt1.setText(beforeGasLeftEt1.getText().toString().equals("")? "0": beforeGasLeftEt1.getText().toString());
                beforeGasLeftEt2.setText(beforeGasLeftEt2.getText().toString().equals("")? "0": beforeGasLeftEt2.getText().toString());
                afterGasLeftEt1.setText(afterGasLeftEt1.getText().toString().equals("")? "0": afterGasLeftEt1.getText().toString());
                afterGasLeftEt2.setText(afterGasLeftEt2.getText().toString().equals("")? "0": afterGasLeftEt2.getText().toString());

                int beforeAir1 = Integer.parseInt(beforeGasLeftEt1.getText().toString());
                int beforeAir2 = Integer.parseInt(beforeGasLeftEt2.getText().toString());
                int afterAir1 = Integer.parseInt(afterGasLeftEt1.getText().toString());
                int afterAir2 = Integer.parseInt(afterGasLeftEt2.getText().toString());

                if(beforeAir1 != 0 && beforeAir2 == 0) {
                    beforeAir2 = (int)((double)beforeAir1 * (14.503824));
                    beforeGasLeftEt2.setText(String.valueOf(beforeAir2));
                }
                else if(beforeAir1 == 0 && beforeAir2 != 0) {
                    beforeAir1 = (int)((double)beforeAir2 * (0.068947));
                    beforeGasLeftEt1.setText(String.valueOf(beforeAir1));
                }

                if(afterAir1 != 0 && afterAir2 == 0) {
                    afterAir2 = (int)((double)afterAir1 * (14.503824));
                    afterGasLeftEt2.setText(String.valueOf(afterAir2));
                }
                else if(afterAir1 == 0 && afterAir2 != 0) {
                    afterAir1 = (int)((double)afterAir2 * (0.068947));
                    afterGasLeftEt1.setText(String.valueOf(afterAir1));
                }
                newDiveLog.setBefGasLeft(Integer.parseInt(beforeGasLeftEt1.getText().toString()));
                newDiveLog.setAftGasLeft(Integer.parseInt(afterGasLeftEt1.getText().toString()));

            }
        });

        findViewById(R.id.addBt).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int err = 0;
                if(dateTv.getText().toString() == null || dateTv.getText().toString().equals("")) err++;
                if(timeTv.getText().toString() == null || timeTv.getText().toString().equals("")) err++;
                if(diveTimeEt.getText().toString() == null || diveTimeEt.getText().toString().equals("")) err++;
                if(lettTv.getText().toString() == null || lettTv.getText().toString().equals("")) err++;
                if(longTv.getText().toString() == null || longTv.getText().toString().equals("")) err++;
                if(weather == null || weather.equals("")) err++;
                if(tank == null || tank.equals("")) err++;
                if(gas == null || gas.equals("")) err++;

                if(err == 0) addLog1();
                else Toast.makeText(AddLogActivity.this, "필수정보가 입력되지 않았습니다.", Toast.LENGTH_LONG).show();

            }
        });


    }

    private void addLog1() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/addDiveLog1.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String buddyName = buddyEt.getText().toString() != null && !buddyEt.getText().toString().equals("") ?
                        buddyEt.getText().toString() : "NONE",
                instructor = instructorEt.getText().toString() != null && !instructorEt.getText().toString().equals("") ?
                        instructorEt.getText().toString() : "NONE",
                diveMaster = diveMasterEt.getText().toString() != null && !diveMasterEt.getText().toString().equals("") ?
                        diveMasterEt.getText().toString() : "NONE",
                purpose = purposeTv.getText().toString() != null && !purposeTv.getText().toString().equals("") ?
                        purposeTv.getText().toString() : "NONE";
                params.put("userId", GetDataActivity.user.getUserId());
                params.put("diveLog", logNoTv.getText().toString());
                params.put("buddyName", buddyName);
                params.put("instructor", instructor);
                params.put("divemaster", diveMaster);
                params.put("purpose", purpose);
                params.put("d8", dateTv.getText().toString() + " " + timeTv.getText().toString());

                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    private void addLog2() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/addDiveLog2.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String location = locationEt.getText().toString() != null && !locationEt.getText().toString().equals("") ?
                        locationEt.getText().toString() : "NONE",
                point = pointEt.getText().toString() != null && !pointEt.getText().toString().equals("") ?
                        pointEt.getText().toString() : "NONE";
                params.put("userId", GetDataActivity.user.getUserId());
                params.put("diveLog", logNoTv.getText().toString());
                params.put("location", location);
                params.put("point", point);
                params.put("lattitude", lettTv.getText().toString());
                params.put("longitude", longTv.getText().toString());
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    private void addLog3() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/addDiveLog3.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String diveTime = diveTimeEt.getText().toString() != null && !diveTimeEt.getText().toString().equals("") ?
                        diveTimeEt.getText().toString() : "0",
                maxDepth = maxDepthEt.getText().toString() != null && !maxDepthEt.getText().toString().equals("") ?
                        maxDepthEt.getText().toString() : "0",
                sawLength = sawLengthEt.getText().toString() != null && !sawLengthEt.getText().toString().equals("") ?
                        sawLengthEt.getText().toString() : "0",
                watTemp = waterTempEt.getText().toString() != null && !waterTempEt.getText().toString().equals("") ?
                        waterTempEt.getText().toString() : "0";
                params.put("userId", GetDataActivity.user.getUserId());
                params.put("diveLog", logNoTv.getText().toString());
                params.put("weather", weather);
                params.put("diveTime", diveTime);
                params.put("maxDepth", maxDepth);
                params.put("sawLength", sawLength);
                params.put("watTemp", watTemp);
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    private void addLog4() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/addDiveLog4.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String suit = suitTv.getText().toString() != null && !suitTv.getText().toString().equals("") ?
                        suitTv.getText().toString() : "NONE";
                params.put("userId", GetDataActivity.user.getUserId());
                params.put("diveLog", logNoTv.getText().toString());
                params.put("tank", tank);
                params.put("gas", gas);
                params.put("suit", suit);
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    private void addLog5() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/addDiveLog5.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String befGasLeft = beforeGasLeftEt1.getText().toString() != null && !beforeGasLeftEt1.getText().toString().equals("") ?
                        beforeGasLeftEt1.getText().toString() : "0",
                aftGasLeft = afterGasLeftEt1.getText().toString() != null && !afterGasLeftEt1.getText().toString().equals("") ?
                        afterGasLeftEt1.getText().toString() : "0",
                beltWeight = weightEt1.getText().toString() != null && !weightEt1.getText().toString().equals("") ?
                        weightEt1.getText().toString() : "0";
                params.put("userId", GetDataActivity.user.getUserId());
                params.put("diveLog", logNoTv.getText().toString());
                params.put("befGasLeft", befGasLeft);
                params.put("aftGasLeft", aftGasLeft);
                params.put("beltWeight", beltWeight);
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    private void addLog6() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/addDiveLog6.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String memo = memoEt.getText().toString() != null && !memoEt.getText().toString().equals("") ?
                        memoEt.getText().toString() : "";
                params.put("userId", GetDataActivity.user.getUserId());
                params.put("diveLog", logNoTv.getText().toString());
                params.put("memo", memo);
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    private void addAccuLog() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/addAccuLog.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", GetDataActivity.user.getUserId());

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
        Log.d("kkk", response);
        if(insertMode == 1) {
            insertMode = 2;
            addLog2();
        }
        else if(insertMode == 2) {
            insertMode = 3;
            addLog3();
        }
        else if(insertMode == 3) {
            insertMode = 4;
            addLog4();
        }
        else if(insertMode == 4) {
            insertMode = 5;
            addLog5();
        }
        else if(insertMode == 5) {
            insertMode = 6;
            addLog6();
        }
        else if(insertMode == 6) {
            insertMode = 7;
            addAccuLog();
        }
        else if(insertMode == 7) {
            Toast.makeText(AddLogActivity.this, "저장 완료", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2080) {
            lati = data.getDoubleExtra("lat", 0);
            longi = data.getDoubleExtra("long", 0);
            double[] coordiness = {lati, longi};
            newDiveLog.setCoordiness(coordiness);
            double lati1 = lati, longi1 = longi;
            lati = Math.abs(lati); longi = Math.abs(longi);
            String latitude = "", longitude = "";
            latitude += (String.valueOf((int)lati)+"°");
            lati = (lati-(int)lati)*60;
            latitude += (String.valueOf((int)lati)+"\'");
            lati = (lati-(int)lati)*60;
            latitude += (String.valueOf(lati)+"\"");
            if(lati1 > 0) latitude += "N";
            else latitude += "S";

            longitude += (String.valueOf((int)longi)+"°");
            longi = (longi - (int)longi)*60;
            longitude += (String.valueOf((int)longi)+"\'");
            longi = (longi - (int)longi)*60;
            longitude += (String.valueOf(longi)+"\"");
            if(longi1 > 0) longitude += "E";
            else longitude += "W";
            lettTv.setText(latitude);
            longTv.setText(longitude);
        }
    }

    private void getCoordinessDB() {
        ArrayList<Double> lat = new ArrayList<>();
        ArrayList<Double> lon = new ArrayList<>();
        SQLiteDatabase db = null;
        if(db == null) {
            db = openOrCreateDatabase("location", Context.MODE_PRIVATE, null);
        }
        Cursor c = db.rawQuery("SELECT * FROM location", null);
        c.moveToFirst();

        while (c.isAfterLast() == false) {
            double latt = c.getDouble(0);
            double longi = c.getDouble(1);
            lat.add(latt);
            lon.add(longi);
            c.moveToNext();
        }

        if(lat != null) {
            Double[][] coord = new Double[lat.size()][2];
            for (int i = 0; i < coord.length; i++) {
                coord[i][0] = lat.get(i);
                coord[i][1] = lon.get(i);
            }

            lettTv.setText(coord[coord.length-1][0]+"");
            longTv.setText(coord[coord.length-1][1]+"");
            double[] coordiness = {coord[coord.length-1][0], coord[coord.length-1][1]};
            newDiveLog.setCoordiness(coordiness);
            clearDB();
            for (int i = 0; i < coord.length-2; i++) saveDB(coord[i][0], coord[i][1]);
        }
        else {
            Toast.makeText(AddLogActivity.this, "저장된 좌표가 없습니다.", Toast.LENGTH_LONG).show();
        }

        c.close();
        db.close();
    }
    private void clearDB() {
        SQLiteDatabase db = null;
        if(db == null) {
            db = openOrCreateDatabase("location", Context.MODE_PRIVATE, null);
        }
        db.execSQL("DELETE FROM location") ;
        db.close();

    }
    private void saveDB(double lattitude, double longitude) {
        SQLiteDatabase db = null;
        if(db == null) {
            db = openOrCreateDatabase("location", Context.MODE_PRIVATE, null);
        }
        db.execSQL("INSERT INTO location (lattitude, longitude) VALUES ("+lattitude+", "+longitude+")");
        db.close();
    }
}
