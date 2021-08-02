package com.example.scubadive2;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Simulation {
    private ArrayList<DepthTimeLevel> chart1_3 = new ArrayList<>();
    private ArrayList<BreakTimeLevel> chart2 = new ArrayList<>();
    int[] deep10 = {10, 20, 26, 30, 34, 37, 41, 45, 50, 54, 59, 64, 70, 75, 82, 88, 95, 104, 112, 122, 133, 145, 160, 178, 199, 219};
    int[] deep12 = {9, 17, 23, 26, 29, 32, 35, 38, 42, 45, 49, 53, 57, 62, 66, 71, 76, 82, 88, 94, 101, 108, 116, 125, 134, 147};
    int[] deep14 = {8, 15, 19, 22, 24, 27, 29, 32, 35, 37, 40, 43, 47, 50, 53, 57, 61, 64, 68, 73, 77, 82, 87, 92, 98};
    int[] deep16 = {7, 13, 17, 19, 21, 23, 25, 27, 29, 32, 34, 37, 39, 42, 45, 48, 50, 53, 56, 60, 63, 67, 70, 72};
    int[] deep18 = {6, 11, 15, 16, 18, 20, 22, 24, 26, 28, 30, 32, 34, 36, 39, 41, 43, 46, 48, 51, 53, 55, 56};
    int[] deep20 = {6, 10, 13, 15, 16, 18, 20, 21, 23, 25, 26, 28, 30, 32, 34, 36, 38, 40, 42, 44, 45};
    int[] deep22 = {5, 9, 12, 13, 15, 16, 18, 19, 21, 22, 24, 25, 27, 29, 30, 32, 34, 36, 37};
    int[] deep25 = {4, 8, 10, 11, 13, 14, 15, 17, 18, 19, 21, 22, 23, 25, 26, 28, 29};
    int[] deep30 = {3, 6, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20};
    int[] breaktimeB = {0, 48, 228};
    int[] breaktimeC = {0, 22, 70, 250};
    int[] breaktimeD = {0, 9, 31, 79, 259};
    int[] breaktimeE = {0, 8, 17, 39, 88, 268};
    int[] breaktimeF = {0, 8, 16, 25, 47, 95, 275};
    int[] breaktimeG = {0, 7, 14, 23, 32, 54, 102, 282};
    int[] breaktimeH = {0, 6, 13, 21, 29, 38, 60, 108, 288};
    int[] breaktimeI = {0, 6, 12, 19, 27, 35, 44, 66, 114, 294};
    int[] breaktimeJ = {0, 6, 12, 18, 25, 32, 41, 50, 72, 120, 300};
    int[] breaktimeK = {0, 5, 11, 17, 23, 30, 38, 46, 55, 77, 125, 305};
    int[] breaktimeL = {0, 5, 10, 16, 22, 28, 35, 43, 51, 60, 82, 130, 310};
    int[] breaktimeM = {0, 5, 10, 15, 20, 26, 33, 40, 47, 56, 65, 86, 135, 315};
    int[] breaktimeN = {0, 4, 9, 14, 19, 25, 31, 37, 44, 52, 60, 69, 91, 139, 319};
    int[] breaktimeO = {0, 4, 9, 13, 18, 24, 29, 35, 42, 48, 56, 64, 73, 95, 144, 324};
    int[] breaktimeP = {0, 4, 8, 13, 17, 22, 28, 33, 39, 46, 52, 60, 68, 77, 99, 148, 328};
    int[] breaktimeQ = {0, 4, 8, 12, 17, 21, 26, 31, 37, 43, 49, 56, 64, 72, 81, 103, 151, 331};
    int[] breaktimeR = {0, 4, 8, 12, 16, 20, 25, 30, 35, 41, 47, 53, 60, 68, 76, 85, 107, 155, 335};
    int[] breaktimeS = {0, 4, 7, 11, 15, 19, 24, 28, 33, 39, 44, 50, 57, 64, 71, 79, 88, 110, 159, 339};
    int[] breaktimeT = {0, 3, 7, 11, 14, 18, 23, 27, 32, 37, 42, 48, 54, 60, 67, 74, 83, 92, 114, 162, 342};
    int[] breaktimeU = {0, 3, 7, 10, 14, 18, 22, 26, 30, 35, 40, 45, 51, 57, 63, 70, 78, 86, 95, 177, 165, 345};
    int[] breaktimeV = {0, 3, 6, 10, 13, 17, 21, 25, 29, 34, 38, 43, 48, 54, 60, 66, 73, 81, 89, 98, 120, 168, 348};
    int[] breaktimeW = {0, 3, 6, 9, 13, 16, 20, 24, 28, 32, 37, 41, 46, 51, 57, 63, 69, 76, 84, 92, 101, 123, 171, 351};
    int[] breaktimeX = {0, 3, 6, 9, 12, 16, 19, 23, 27, 31, 35, 40, 44, 49, 54, 60, 66, 72, 79, 87, 95, 104, 126, 174, 354};
    int[] breaktimeY = {0, 3, 6, 9, 12, 15, 19, 22, 26, 30, 34, 38, 42, 47, 52, 57, 63, 69, 75, 82, 90, 98, 107, 129, 177, 357};
    int[] breaktimeZ = {0, 3, 6, 9, 12, 15, 18, 21, 25, 29, 32, 36, 41, 45, 50, 55, 60, 66, 72, 78, 85, 92, 101, 110, 132, 180, 360};

    public Simulation() { }

    public int calcNextTime(int diveTime, int breaktime, int lastDepth, int simDepth) {
        chart1_3.add(new DepthTimeLevel(10, deep10)); chart1_3.add(new DepthTimeLevel(12, deep12)); chart1_3.add(new DepthTimeLevel(14, deep14));
        chart1_3.add(new DepthTimeLevel(16, deep16)); chart1_3.add(new DepthTimeLevel(18, deep18)); chart1_3.add(new DepthTimeLevel(20, deep20));
        chart1_3.add(new DepthTimeLevel(22, deep22)); chart1_3.add(new DepthTimeLevel(25, deep25)); chart1_3.add(new DepthTimeLevel(30, deep30));

        chart2.add(new BreakTimeLevel('B', breaktimeB)); chart2.add(new BreakTimeLevel('C', breaktimeC));
        chart2.add(new BreakTimeLevel('D', breaktimeD)); chart2.add(new BreakTimeLevel('E', breaktimeE));
        chart2.add(new BreakTimeLevel('F', breaktimeF)); chart2.add(new BreakTimeLevel('G', breaktimeG));
        chart2.add(new BreakTimeLevel('H', breaktimeH)); chart2.add(new BreakTimeLevel('I', breaktimeI));
        chart2.add(new BreakTimeLevel('J', breaktimeJ)); chart2.add(new BreakTimeLevel('K', breaktimeK));
        chart2.add(new BreakTimeLevel('L', breaktimeL)); chart2.add(new BreakTimeLevel('M', breaktimeM));
        chart2.add(new BreakTimeLevel('N', breaktimeN)); chart2.add(new BreakTimeLevel('O', breaktimeO));
        chart2.add(new BreakTimeLevel('P', breaktimeP)); chart2.add(new BreakTimeLevel('Q', breaktimeQ));
        chart2.add(new BreakTimeLevel('R', breaktimeR)); chart2.add(new BreakTimeLevel('S', breaktimeS));
        chart2.add(new BreakTimeLevel('T', breaktimeT)); chart2.add(new BreakTimeLevel('U', breaktimeU));
        chart2.add(new BreakTimeLevel('V', breaktimeV)); chart2.add(new BreakTimeLevel('W', breaktimeW));
        chart2.add(new BreakTimeLevel('X', breaktimeX)); chart2.add(new BreakTimeLevel('Y', breaktimeY));
        chart2.add(new BreakTimeLevel('Z', breaktimeZ));
        return chart3Calc(chart2Calc(chart1Calc(diveTime, lastDepth), breaktime), simDepth);
    }

    public String nextDiveTimeCalc_planeTv(int diveTime, int breaktime, int lastDepth, int simDepth) {
        String str = "";
        int nextTime = calcNextTime(diveTime, breaktime, lastDepth, simDepth);

        if(breaktime < 3) {
            str = "휴식시간 부족";
        }
        else if(breaktime >= 60*24) {
            str = "분석결과: 다이빙 가능";
        }
        else {
            if(simDepth >= 40) {
                str = "위험! 한계수심초과";
            }
            else if(simDepth >= 30 && simDepth < 40) {
                str = "위험! 한계수심초과";
            }
            else {
                if(nextTime < 3)
                    str = "위험! DCS(감압병) 경고";
                else
                    str = "분석결과: 다이빙 가능";
            }
        }

        return str;
    }

    public String nextDiveTimeCalc_resultTv(int diveTime, int breaktime, int lastDepth, int simDepth) {
        String str = "";
        int nextTime = chart3Calc(chart2Calc(chart1Calc(diveTime, lastDepth), breaktime), simDepth);
        if(breaktime < 3) {
            str = "휴식시간이 너무 짧습니다 \n 최소 30분 이상 휴식을 취하세요";
        }
        else if(breaktime >= 60*24) {
            str = "마지막 다이빙 후 24시간 이상 경과하였습니다!";
        }
        else {
            if(simDepth >= 30) {
                str = "한계수심을 초과하였습니다. \n 가능하면 30m 이상 내려가지 마세요";
            }
            else {
                if(nextTime < 3) {
                    str = "휴식시간이 너무 짧습니다. \n "+simDepth+"m 깊이에서 "+ nextTime + "분 이상 잠수하면 \n 감압병(DCS)를 일으키게 되니 충분한 휴식을 취하세요";
                }
                else {
                    str = simDepth + "m 깊이에서 \n 최대 "+nextTime+"분간 잠수 가능합니다.";
                }
            }
        }


        return str;
    }

    public long getPlaneTime(String lastDiveDate, String lastDiveTime) {
        return planeTimeCalc(lastDiveDate, lastDiveTime);
    }

    public String planeEnableCalc_planeTv(String lastDiveDate, String lastDiveTime) {
        String str = "";

        long planeTime = getPlaneTime(lastDiveDate, lastDiveTime);
        if((planeTime/60) < 16) {
            str = "위험! DCS(감압병) 경고";
        }
        else {
            str = "비행기 탑승가능";
        }

        return str;
    }

    public String planeEnableCalc_resultTv(String lastDiveDate, String lastDiveTime) {
        String str = "";

        long planeTime = getPlaneTime(lastDiveDate, lastDiveTime);
        if((planeTime/60) < 16) {
            str = "지금 비행기에 탑승하면 감압병(DCS)의 \n 위험이 발생합니다" +
            "앞으로 " + ((16*60 - (int)planeTime)/60) + "시간 " + ((16*60 - (int)planeTime)%60)
            + "분 후 비행기에 탑승해야 안전합니다";
        }
        else {
            str = "지금 비행기에 탑승할 수 있습니다";
        }

        return str;
    }

    private char chart1Calc(int diveTime, int lastDepth) {
        char ret = 'A';
        int idx1 = 0;

        for(int i = 0; i < chart1_3.size(); i++) {
            if(lastDepth <= chart1_3.get(i).depth) {
                idx1 = i;
                break;
            }
        }
        int[] timeLevel = chart1_3.get(idx1).timeLevel;
        for(int i = 0; i < timeLevel.length; i++) {
            if(diveTime <= timeLevel[i]) {
                ret = (char)((int)'A'+i);
                break;
            }
        }

        return ret;
    }
    private char chart2Calc(char pressLevel, int breaktime) {
        char ret = pressLevel;
        int idx = 0;

        if(pressLevel == 'A') {
            ret = 'A';
        }
        else {
            for(int i = 0; i < chart2.size(); i++) {
                boolean isEnd = false;
                if(chart2.get(i).pressLevel == pressLevel) {
                    idx = i;
                    break;
                }
            }
            int[] breakTimeArr = chart2.get(idx).breaktime;
            for(int j = 0; j < breakTimeArr.length-1; j++) {
                if(breaktime >= breakTimeArr[j] && breaktime < breakTimeArr[j+1]) {
                    ret = (char)(pressLevel-j);
                    break;
                }
            }
        }
        return ret;
    }
    private int chart3Calc(char pressLevel, int simDepth) {
        int dif = (int)(pressLevel-'A');
        int time = 0;

        for(int i = 0; i < chart1_3.size(); i++) {
            if(simDepth <= chart1_3.get(i).depth) {
                time = (chart1_3.get(i).timeLevel[chart1_3.get(i).timeLevel.length-1] - chart1_3.get(i).timeLevel[dif]);
                break;
            }
        }

        return time;
    }
    private long planeTimeCalc(String lastDiveDate, String lastDiveTime) {
        String[] dateArr = lastDiveDate.split("-");
        String[] timeArr = lastDiveTime.split(":");
        int[] ld = new int[dateArr.length+timeArr.length];
        for(int i = 0; i < ld.length; i++) {
            if(i < dateArr.length) ld[i] = Integer.parseInt(dateArr[i]);
            else ld[i] = Integer.parseInt(timeArr[i-dateArr.length]);
        }

        Calendar ldc = new GregorianCalendar(ld[0], ld[1]-1, ld[2]);
        ldc.set(Calendar.HOUR_OF_DAY, ld[3]);
        ldc.set(Calendar.MINUTE, ld[4]);

        Calendar cdc = Calendar.getInstance();
        cdc.add(Calendar.HOUR_OF_DAY, 9);

        long dif = (cdc.getTimeInMillis() - ldc.getTimeInMillis())/1000;
        return dif/60;
    }
}
