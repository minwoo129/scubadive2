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

public class ModifyLogActivity extends AppCompatActivity implements Response.Listener<String>, Response.ErrorListener{

    TextView logNoTv, dateTv, timeTv, purposeTv, lettTv, longTv, suitTv, gasBeyondTv;
    RadioGroup diveTypeRg, weatherRg, gasRg, tankRg;
    EditText locationEt, pointEt, buddyEt, diveMasterEt, diveTimeEt, maxDepthEt, sawLengthEt, waterTempEt,
            instructorEt, weightEt1, weightEt2, beforeGasLeftEt1, beforeGasLeftEt2, afterGasLeftEt1,
            afterGasLeftEt2, memoEt;
    Button dateBt, timeBt, mapBt, suitBt, weightEnterBt, airEnterBt, addBt;
    String weather, tank, gas;
    int insertMode = 1, diveLog, pos;
    double lati, longi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_log);

        ArrayList<DiveLog> logs = GetDataActivity.logArr;

        logNoTv = findViewById(R.id.logNoTv8); dateTv = findViewById(R.id.dateTv8);
        timeTv = findViewById(R.id.timeTv8); purposeTv = findViewById(R.id.purposeTv8);
        lettTv = findViewById(R.id.lettTv8); longTv = findViewById(R.id.longTv8);
        suitTv = findViewById(R.id.suitTv8); gasBeyondTv = findViewById(R.id.gasBeyondTv8);
        diveTypeRg = findViewById(R.id.diveTypeRg8); weatherRg = findViewById(R.id.weatherRg8);
        gasRg = findViewById(R.id.gasRg8); tankRg = findViewById(R.id.tankRg8);
        locationEt = findViewById(R.id.locationEt8); pointEt = findViewById(R.id.pointEt8);
        buddyEt = findViewById(R.id.buddyEt8); diveMasterEt = findViewById(R.id.diveMasterEt8);
        diveTimeEt = findViewById(R.id.diveTimeEt8); maxDepthEt = findViewById(R.id.maxDepthEt8);
        sawLengthEt = findViewById(R.id.sawLengthEt8); waterTempEt = findViewById(R.id.waterTempEt8);
        instructorEt = findViewById(R.id.instructorEt8); weightEt1 = findViewById(R.id.weightEt18);
        weightEt2 = findViewById(R.id.weightEt28); beforeGasLeftEt1 = findViewById(R.id.beforeGasLeftEt18);
        beforeGasLeftEt2 = findViewById(R.id.beforeGasLeftEt28); afterGasLeftEt1 = findViewById(R.id.afterGasLeftEt18);
        afterGasLeftEt2 = findViewById(R.id.afterGasLeftEt28); memoEt = findViewById(R.id.memoEt8);
        dateBt = findViewById(R.id.dateBt8); timeBt = findViewById(R.id.timeBt8); mapBt = findViewById(R.id.mapBt8);
        suitBt = findViewById(R.id.suitBt8); weightEnterBt = findViewById(R.id.weightEnterBt8);
        airEnterBt = findViewById(R.id.airEnterBt8); addBt = findViewById(R.id.addBt8);

        Intent in = getIntent();
        pos = in.getIntExtra("pos", -1);
        if(pos == -1) {
            Toast.makeText(ModifyLogActivity.this, "오류발생!", Toast.LENGTH_LONG).show();
            finish();
        }
        else {
            int w1 = 0, w2 = 0;
            diveLog = GetDataActivity.logArr.get((int)pos).getDiveLog();
            Log.d("kkk", "로그: " + diveLog);
            logNoTv.setText(diveLog+"");
            dateTv.setText(logs.get(pos).getDate()); timeTv.setText(logs.get((int) pos).getTime());
            purposeTv.setText(GetDataActivity.logArr.get(pos).getPurpose().equals("NONE") ? "" : GetDataActivity.logArr.get((int)pos).getPurpose());
            locationEt.setText(GetDataActivity.logArr.get(pos).getLocation().equals("NONE") ? "" : GetDataActivity.logArr.get(pos).getLocation());
            pointEt.setText(GetDataActivity.logArr.get(pos).getPoint().equals("NONE") ? "" : GetDataActivity.logArr.get(pos).getPoint());
            setMapCoord(GetDataActivity.logArr.get(pos).getCoordiness()[0], GetDataActivity.logArr.get(pos).getCoordiness()[1]);
            buddyEt.setText(GetDataActivity.logArr.get(pos).getBuddyName().equals("NONE") ? "" : GetDataActivity.logArr.get(pos).getBuddyName());
            diveMasterEt.setText(GetDataActivity.logArr.get(pos).getDiveMaster().equals("NONE") ? "" : GetDataActivity.logArr.get(pos).getDiveMaster());
            diveTimeEt.setText(GetDataActivity.logArr.get(pos).getDiveTime()+"");
            maxDepthEt.setText(GetDataActivity.logArr.get(pos).getMaxDepth()+"");
            sawLengthEt.setText(GetDataActivity.logArr.get(pos).getSawLength()+"");
            waterTempEt.setText(GetDataActivity.logArr.get(pos).getWatTemp()+"");
            instructorEt.setText(GetDataActivity.logArr.get(pos).getInstructor().equals("NONE") ? "" : GetDataActivity.logArr.get(pos).getInstructor());
            suitTv.setText(GetDataActivity.logArr.get(pos).getSuit().equals("NONE") ? "" : GetDataActivity.logArr.get(pos).getSuit());
            weightEt1.setText(GetDataActivity.logArr.get(pos).getBeltWeight()+"");

            w1 = (weightEt1.getText().toString().equals(""))? 0: Integer.parseInt(weightEt1.getText().toString());
            weightEt2.setText(String.valueOf((int)((double)w1 * (0.453592))));

            beforeGasLeftEt1.setText(GetDataActivity.logArr.get(pos).getBefGasLeft()+"");
            int beforeAir1 =  GetDataActivity.logArr.get(pos).getBefGasLeft();
            beforeGasLeftEt2.setText(String.valueOf((int)((double)beforeAir1 * (14.503824))));

            afterGasLeftEt1.setText(GetDataActivity.logArr.get(pos).getAftGasLeft()+"");
            int afterAir1 = GetDataActivity.logArr.get(pos).getAftGasLeft();
            afterGasLeftEt2.setText(String.valueOf((int)((double)afterAir1 * (14.503824))));

            memoEt.setText(GetDataActivity.logArr.get(pos).getMemo());

        }

        dateBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year1 = cal.get(Calendar.YEAR);
                int month1 = cal.get(Calendar.MONTH);
                int day1 = cal.get(Calendar.DATE);

                DatePickerDialog dp = new DatePickerDialog(ModifyLogActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String m = (month < 9)? ("0"+String.valueOf(month+1)): String.valueOf(month+1);
                        String d = (dayOfMonth < 10)? ("0"+String.valueOf(dayOfMonth)): String.valueOf(dayOfMonth);
                        dateTv.setText(String.valueOf(year) + "-" + m + "-" + d);
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
                TimePickerDialog tp = new TimePickerDialog(ModifyLogActivity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String h = (hourOfDay < 10)? ("0"+String.valueOf(hourOfDay)):String.valueOf(hourOfDay);
                        String m = (minute < 10)? ("0"+String.valueOf(minute)):String.valueOf(minute);
                        timeTv.setText(h + ":" + m);
                    }
                }, hour, min, true);
                tp.show();
            }
        });
        diveTypeRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, final int checkedId) {
                AlertDialog.Builder ab= new AlertDialog.Builder(ModifyLogActivity.this);
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
                                if(find == 0) {
                                    str += type[i];
                                    find++;
                                }
                                else {
                                    str += ("," + type[i]);
                                }
                            }
                        }

                        purposeTv.setText(str);
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
                weather = rb.getText().toString();
            }
        });

        mapBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ModifyLogActivity.this);
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
                            Intent intent = new Intent(ModifyLogActivity.this, MapActivity.class);
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
                tank = rb.getText().toString();
            }
        });

        /* 가스 radioGroup*/
        gasRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if(checkedId == R.id.gasRb38)
                    gasBeyondTv.setVisibility(View.VISIBLE);
                RadioButton rb = findViewById(checkedId);
                gas = rb.getText().toString();
            }
        });

        /* 슈트 선택 */
        findViewById(R.id.suitBt8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ab = new AlertDialog.Builder(ModifyLogActivity.this);
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
                    }
                });

                ab.show();
            }
        });

        /* 웨이트 무게 입력 버튼 click*/
        findViewById(R.id.weightEnterBt8).setOnClickListener(new View.OnClickListener() {
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
            }
        });


        findViewById(R.id.airEnterBt8).setOnClickListener(new View.OnClickListener() {
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

            }
        });

        findViewById(R.id.addBt8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int err = 0;
                if(dateTv.getText().toString() == null || dateTv.getText().toString().equals("")) err++;
                if(timeTv.getText().toString() == null || timeTv.getText().toString().equals("")) err++;
                if(diveTimeEt.getText().toString() == null || diveTimeEt.getText().toString().equals("")) err++;
                if(lettTv.getText().toString() == null || lettTv.getText().toString().equals("")) err++;
                if(longTv.getText().toString() == null || longTv.getText().toString().equals("")) err++;
                if(GetDataActivity.logArr.get(pos).getWeather() == null || GetDataActivity.logArr.get(pos).getWeather().equals("")) err++;
                if(GetDataActivity.logArr.get(pos).getTank() == null || GetDataActivity.logArr.get(pos).getTank().equals("")) err++;
                if(GetDataActivity.logArr.get(pos).getGas() == null || GetDataActivity.logArr.get(pos).getGas().equals("")) err++;

                if(err == 0) {
                    setModData();
                    modLog1();
                }
                else Toast.makeText(ModifyLogActivity.this, "필수정보가 입력되지 않았습니다.", Toast.LENGTH_LONG).show();

            }
        });
    }

    private void modLog1() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/modifyLog1.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", GetDataActivity.user.getUserId());
                params.put("diveLog", GetDataActivity.logArr.get(pos).getDiveLog()+"");
                params.put("buddyName", GetDataActivity.logArr.get(pos).getBuddyName());
                params.put("instructor", GetDataActivity.logArr.get(pos).getInstructor());
                params.put("divemaster", GetDataActivity.logArr.get(pos).getDiveMaster());
                params.put("purpose", GetDataActivity.logArr.get(pos).getPurpose());
                params.put("d8", GetDataActivity.logArr.get(pos).getDate() + " " + GetDataActivity.logArr.get(pos).getTime());

                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    private void modLog2() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/modifyLog2.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", GetDataActivity.user.getUserId());
                params.put("diveLog", GetDataActivity.logArr.get(pos).getDiveLog()+"");
                params.put("location", GetDataActivity.logArr.get(pos).getLocation());
                params.put("point", GetDataActivity.logArr.get(pos).getPoint());
                params.put("lattitude", GetDataActivity.logArr.get(pos).getCoordiness()[0]+"");
                params.put("longitude", GetDataActivity.logArr.get(pos).getCoordiness()[1]+"");

                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    private void modLog3() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/modifyLog3.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", GetDataActivity.user.getUserId());
                params.put("diveLog", GetDataActivity.logArr.get(pos).getDiveLog()+"");
                params.put("weather", GetDataActivity.logArr.get(pos).getWeather());
                params.put("diveTime", GetDataActivity.logArr.get(pos).getDiveTime()+"");
                params.put("maxDepth", GetDataActivity.logArr.get(pos).getMaxDepth()+"");
                params.put("sawLength", GetDataActivity.logArr.get(pos).getSawLength()+"");
                params.put("watTemp", GetDataActivity.logArr.get(pos).getWatTemp()+"");
                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    private void modLog4() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/modifyLog4.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", GetDataActivity.user.getUserId());
                params.put("diveLog", GetDataActivity.logArr.get(pos).getDiveLog()+"");
                params.put("tank", GetDataActivity.logArr.get(pos).getTank());
                params.put("gas", GetDataActivity.logArr.get(pos).getGas());
                params.put("suit", GetDataActivity.logArr.get(pos).getSuit());

                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    private void modLog5() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/modifyLog5.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("userId", GetDataActivity.user.getUserId());
                params.put("diveLog", GetDataActivity.logArr.get(pos).getDiveLog()+"");
                params.put("befGasLeft", GetDataActivity.logArr.get(pos).getBefGasLeft()+"");
                params.put("aftGasLeft", GetDataActivity.logArr.get(pos).getAftGasLeft()+"");
                params.put("beltWeight", GetDataActivity.logArr.get(pos).getBeltWeight()+"");

                return params;
            };
        };
        myReq.setRetryPolicy(new DefaultRetryPolicy(3000, 0, 1f));
        stringRequest.add(myReq);
    }
    private void modLog6() {
        RequestQueue stringRequest = Volley.newRequestQueue(this);
        String uri = "http://ikey0129.woobi.co.kr/modifyLog6.php";
        StringRequest myReq =new StringRequest(Request.Method.POST, uri, this, this) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                String memo = memoEt.getText().toString() != null && !memoEt.getText().toString().equals("") ?
                        memoEt.getText().toString() : "";
                params.put("userId", GetDataActivity.user.getUserId());
                params.put("diveLog", GetDataActivity.logArr.get(pos).getDiveLog()+"");
                params.put("memo", GetDataActivity.logArr.get(pos).getMemo());
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
            modLog2();
        }
        else if(insertMode == 2) {
            insertMode = 3;
            modLog3();
        }
        else if(insertMode == 3) {
            insertMode = 4;
            modLog4();
        }
        else if(insertMode == 4) {
            insertMode = 5;
            modLog5();
        }
        else if(insertMode == 5) {
            insertMode = 6;
            modLog6();
        }
        else if(insertMode == 6) {
            Toast.makeText(ModifyLogActivity.this, "저장 완료", Toast.LENGTH_LONG).show();
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 2080) {
            lati = data.getDoubleExtra("lat", 0);
            longi = data.getDoubleExtra("long", 0);
            double[] coord = {lati, longi};
            GetDataActivity.logArr.get(pos).setCoordiness(coord);
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

            lettTv.setText(coord[0][0]+"");
            longTv.setText(coord[0][1]+"");
            clearDB();
            for (int i = 1; i < coord.length; i++) saveDB(coord[i][0], coord[i][1]);
        }
        else {
            Toast.makeText(ModifyLogActivity.this, "저장된 좌표가 없습니다.", Toast.LENGTH_LONG).show();
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
    private void setModData() {
        GetDataActivity.logArr.get(pos).setDate(dateTv.getText().toString());
        GetDataActivity.logArr.get(pos).setTime(timeTv.getText().toString());
        GetDataActivity.logArr.get(pos).setPurpose(purposeTv.getText().toString() != null && !purposeTv.getText().toString().equals("") ? purposeTv.getText().toString() : "NONE");
        GetDataActivity.logArr.get(pos).setWeather(weather != null && !weather.equals("") ? weather : GetDataActivity.logArr.get(pos).getWeather());
        GetDataActivity.logArr.get(pos).setLocation(locationEt.getText().toString() != null && !locationEt.getText().toString().equals("") ? locationEt.getText().toString() : "NONE");
        GetDataActivity.logArr.get(pos).setPoint(pointEt.getText().toString() != null && !pointEt.getText().toString().equals("") ? pointEt.getText().toString() : "NONE");
        GetDataActivity.logArr.get(pos).setBuddyName(buddyEt.getText().toString() != null && !buddyEt.getText().toString().equals("") ? buddyEt.getText().toString() : "NONE");
        GetDataActivity.logArr.get(pos).setDiveMaster(diveMasterEt.getText().toString() != null && !diveMasterEt.getText().toString().equals("") ? diveMasterEt.getText().toString() : "NONE");
        GetDataActivity.logArr.get(pos).setDiveTime(diveTimeEt.getText().toString() != null && !diveTimeEt.getText().toString().equals("") ? Integer.parseInt(diveTimeEt.getText().toString()) : 0);
        GetDataActivity.logArr.get(pos).setMaxDepth(maxDepthEt.getText().toString() != null && !maxDepthEt.getText().toString().equals("") ? Double.parseDouble(maxDepthEt.getText().toString()) : 0);
        GetDataActivity.logArr.get(pos).setSawLength(sawLengthEt.getText().toString() != null && !sawLengthEt.getText().toString().equals("") ? Integer.parseInt(sawLengthEt.getText().toString()) : 0);
        GetDataActivity.logArr.get(pos).setWatTemp(waterTempEt.getText().toString() != null && !waterTempEt.getText().toString().equals("") ? Integer.parseInt(waterTempEt.getText().toString()) : 0);
        GetDataActivity.logArr.get(pos).setInstructor(instructorEt.getText().toString() != null && !instructorEt.getText().toString().equals("") ? instructorEt.getText().toString() : "NONE");
        GetDataActivity.logArr.get(pos).setTank(tank != null && !tank.equals("") ? tank : GetDataActivity.logArr.get(pos).getTank());
        GetDataActivity.logArr.get(pos).setGas(gas != null && !gas.equals("") ? gas : GetDataActivity.logArr.get(pos).getGas());
        GetDataActivity.logArr.get(pos).setSuit(suitTv.getText().toString() != null && !suitTv.getText().toString().equals("") ? suitTv.getText().toString() : "NONE");
        GetDataActivity.logArr.get(pos).setBeltWeight(weightEt1.getText().toString() != null && !weightEt1.getText().toString().equals("") ? Integer.parseInt(weightEt1.getText().toString()) : 0);
        GetDataActivity.logArr.get(pos).setBefGasLeft(beforeGasLeftEt1.getText().toString() != null && !beforeGasLeftEt1.getText().toString().equals("") ? Integer.parseInt(beforeGasLeftEt1.getText().toString()) : 200);
        GetDataActivity.logArr.get(pos).setAftGasLeft(afterGasLeftEt1.getText().toString() != null && !afterGasLeftEt1.getText().toString().equals("") ? Integer.parseInt(afterGasLeftEt1.getText().toString()) : 0);
        GetDataActivity.logArr.get(pos).setMemo(memoEt.getText().toString() != null && !memoEt.getText().toString().equals("") ? memoEt.getText().toString() : "");
    }
    private void setMapCoord(double lattitude, double longitude) {
        lati = lattitude; longi = longitude;
        double lati1 = lattitude, longi1 = longitude;
        lati1 = Math.abs(lattitude); longi1 = Math.abs(longitude);
        String latitude1 = "", longitude1 = "";
        latitude1 += (String.valueOf((int)lati1)+"°");
        lati1 = (lati1-(int)lati1)*60;
        latitude1 += (String.valueOf((int)lati1)+"\'");
        lati1 = (lati1-(int)lati1)*60;
        latitude1 += (String.valueOf(lati1)+"\"");
        if(lattitude > 0) latitude1 += "N";
        else latitude1 += "S";

        longitude1 += (String.valueOf((int)longi1)+"°");
        longi1 = (longi1 - (int)longi1)*60;
        longitude1 += (String.valueOf((int)longi1)+"\'");
        longi1 = (longi1 - (int)longi1)*60;
        longitude1 += (String.valueOf(longi1)+"\"");
        if(longitude > 0) longitude1 += "E";
        else longitude1 += "W";
        lettTv.setText(latitude1);
        longTv.setText(longitude1);
    }
}
