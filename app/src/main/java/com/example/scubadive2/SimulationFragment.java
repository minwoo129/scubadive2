package com.example.scubadive2;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class SimulationFragment extends Fragment {


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.simulationfragment, container, false);

        Button dateInputBt = (Button)view.findViewById(R.id.dateInputBt),
                timeInputBt = (Button)view.findViewById(R.id.timeInputBt),
                analizeBt = (Button)view.findViewById(R.id.analizeBt),
                planeBt = (Button)view.findViewById(R.id.planeBt);
        final TextView lastDiveDateTv = (TextView)view.findViewById(R.id.lastDiveDateTv),
                lastDiveTimeTv = (TextView)view.findViewById(R.id.lastDiveTimeTv),
                lastDiveDepthTv = (TextView)view.findViewById(R.id.lastDiveDepthTv),
                dateTv2 = (TextView)view.findViewById(R.id.dateTv2),
                timeTv1 = (TextView)view.findViewById(R.id.timeTv1),
                planeResultTv = (TextView)view.findViewById(R.id.planeResultTv),
                allResultTv = (TextView)view.findViewById(R.id.allResultTv);
        final EditText depthEt = (EditText)view.findViewById(R.id.depthEt);

        lastDiveDateTv.setText(GetDataActivity.logArr.get(GetDataActivity.logArr.size()-1).getDate());
        lastDiveTimeTv.setText(calculateTime(GetDataActivity.logArr));
        lastDiveDepthTv.setText(String.valueOf(GetDataActivity.logArr.get(GetDataActivity.logArr.size()-1).getMaxDepth()));
        calculateTime(GetDataActivity.logArr);
        dateInputBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                int year1 = cal.get(Calendar.YEAR);
                int month1 = cal.get(Calendar.MONTH);
                int day1 = cal.get(Calendar.DATE);

                DatePickerDialog dp = new DatePickerDialog((MainActivity)getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        String m = (month < 9)? ("0"+String.valueOf(month+1)): String.valueOf(month+1);
                        String d = (dayOfMonth < 10)? ("0"+String.valueOf(dayOfMonth)): String.valueOf(dayOfMonth);
                        dateTv2.setText(String.valueOf(year) + "-" + m + "-" + d);
                    }
                }, year1, month1, day1);
                dp.setTitle("날짜 선택");
                dp.show();
            }
        });

        timeInputBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal = Calendar.getInstance();
                cal.add(Calendar.HOUR_OF_DAY, 9);
                int hour = cal.get(Calendar.HOUR_OF_DAY);
                int min = cal.get(Calendar.MINUTE);
                TimePickerDialog tp = new TimePickerDialog((MainActivity)getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String h = (hourOfDay < 10)? ("0"+String.valueOf(hourOfDay)):String.valueOf(hourOfDay);
                        String m = (minute < 10)? ("0"+String.valueOf(minute)):String.valueOf(minute);
                        timeTv1.setText(h + ":" + m);
                    }
                }, hour, min, true);
                tp.show();
            }
        });

        analizeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Simulation simulation = new Simulation();
                if(!depthEt.getText().toString().equals("") && depthEt.getText().toString() != null) {
                    int diveTime = GetDataActivity.logArr.get(GetDataActivity.logArr.size()-1).getDiveTime();
                    int breaktime = calculateBreakTime(lastDiveDateTv.getText().toString(),lastDiveTimeTv.getText().toString(), dateTv2.getText().toString(),timeTv1.getText().toString());
                    int lastDepth = (int)Math.round(GetDataActivity.logArr.get(GetDataActivity.logArr.size()-1).getMaxDepth());
                    int simDepth = Integer.parseInt(depthEt.getText().toString());

                    Log.d("kkk", ""+simulation.calcNextTime(diveTime, breaktime, lastDepth, simDepth));
                    if(breaktime != -1) {
                        int lev = (GetDataActivity.user.getLicenceCode().charAt(1))-48;
                        if(((lev == 1)&&(simDepth <= 18))||((lev > 1)&&(simDepth <= 30))) {
                            planeResultTv.setVisibility(View.VISIBLE);
                            allResultTv.setVisibility(View.VISIBLE);

                            if(breaktime < 3)
                                planeResultTv.setBackgroundColor(Color.rgb(255, 0, 0));
                            else {
                                if(simDepth >= 40)
                                    planeResultTv.setBackgroundColor(Color.rgb(255, 0, 0));
                                else if(simDepth >= 30 && simDepth < 40)
                                    planeResultTv.setBackgroundColor(Color.rgb(255, 0, 0));
                                else {
                                    int nextTime = simulation.calcNextTime(diveTime, breaktime, lastDepth, simDepth);
                                    if(nextTime < 3)
                                        planeResultTv.setBackgroundColor(Color.rgb(255, 0, 0));
                                    else
                                        planeResultTv.setBackgroundColor(Color.rgb(0, 255, 0));
                                }
                            }
                            planeResultTv.setText(simulation.nextDiveTimeCalc_planeTv(diveTime, breaktime, lastDepth, simDepth));
                            allResultTv.setText(simulation.nextDiveTimeCalc_resultTv(diveTime, breaktime, lastDepth, simDepth));
                        }
                        else {
                            int dep = lev == 1? 18: 30;
                            planeResultTv.setBackgroundColor(Color.rgb(255, 0, 0));
                            planeResultTv.setText("위험! 한계수심초과");
                            allResultTv.setText("한계수심을 초과하였습니다. " + dep +"m 이상 하강할 수 없습니다!");
                        }

                    }
                }
                else
                    Toast.makeText((MainActivity)getActivity(), "희망 잠수수심(단위:m)을 입력하세요", Toast.LENGTH_LONG).show();
            }
        });

        planeBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                planeResultTv.setVisibility(View.VISIBLE);
                allResultTv.setVisibility(View.VISIBLE);
                String lastDiveDate = lastDiveDateTv.getText().toString();
                String lastDiveTime = lastDiveTimeTv.getText().toString();

                Simulation simulation = new Simulation();
                long planeTime = simulation.getPlaneTime(lastDiveDate, lastDiveTime);

                if((planeTime/60) < 16) planeResultTv.setBackgroundColor(Color.rgb(255, 0, 0));
                else planeResultTv.setBackgroundColor(Color.rgb(0, 255, 0));
                planeResultTv.setText(simulation.planeEnableCalc_planeTv(lastDiveDate, lastDiveTime));
                allResultTv.setText(simulation.planeEnableCalc_resultTv(lastDiveDate, lastDiveTime));
            }
        });

        return view;
    }

    private String calculateTime(ArrayList<DiveLog> arr) {
        String[] time1 = arr.get(arr.size()-1).getTime().split(":");
        int diveTime = arr.get(arr.size()-1).getDiveTime();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR_OF_DAY, Integer.parseInt(time1[0]));
        cal.set(Calendar.MINUTE, Integer.parseInt(time1[1]));
        cal.add(Calendar.MINUTE, diveTime);
        String ret = "";
        int h = cal.get(Calendar.HOUR_OF_DAY);
        int m = cal.get(Calendar.MINUTE);
        ret += (h >9? h: "0"+h);
        ret += ":";
        ret += (m > 9 ? m : "0"+m);
        return ret;
    }

    private int calculateBreakTime(String ldd, String ldt, String ndd, String ndt) {
        String[] s11 = ldd.split("-");
        String[] s12 = ldt.split(":");
        String[] s21 = ndd.split("-");
        String[] s22 = ndt.split(":");
        int[] ld = new int[s11.length+s12.length];
        int[] nd = new int[s21.length+s22.length];

        for(int i = 0; i < ld.length; i++) {
            if(i < s11.length) ld[i] = Integer.parseInt(s11[i]);
            else ld[i] = Integer.parseInt(s12[i-s11.length]);
        }
        for(int i = 0; i < nd.length; i++) {
            if(i < s21.length) nd[i] = Integer.parseInt(s21[i]);
            else nd[i] = Integer.parseInt(s22[i-s21.length]);
        }

        Calendar ldc = new GregorianCalendar(ld[0], ld[1]-1, ld[2]);
        ldc.set(Calendar.HOUR_OF_DAY, ld[3]);
        ldc.set(Calendar.MINUTE, ld[4]);

        Calendar ndc = new GregorianCalendar(nd[0], nd[1]-1, nd[2]);
        ndc.set(Calendar.HOUR_OF_DAY, nd[3]);
        ndc.set(Calendar.MINUTE, nd[4]);

        long diff = (ndc.getTimeInMillis()-ldc.getTimeInMillis())/1000;
        return (int)diff/60;
    }

}
