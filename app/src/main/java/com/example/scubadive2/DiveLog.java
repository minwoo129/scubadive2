package com.example.scubadive2;

public class DiveLog {
    private int diveLog;
    private String buddyName; // 버디 이름
    private String instructor; // 강사(인솔자)명
    private String diveMaster; // 다이브 마스터
    private String purpose;
    private String date; // 날짜
    private String time; // 시간
    private String location; // 위치(리조트 등)
    private String point; // 다이빙 포인트
    private double[] coordiness; // 위치 좌표
    private String weather; // 날씨
    private int diveTime; // 잠수시간
    private double maxDepth; // 최대잠수수심
    private int sawLength; // 가시거리
    private int watTemp; // 수온
    private String tank; // 탱크 종류
    private String gas; // 가스 종류
    private String suit; // 슈트
    private int befGasLeft; // 입수 전 공기 잔압
    private int aftGasLeft; // 출수 후 공기 잔압
    private int beltWeight; // 웨이트(벨트, 단위 lb)
    private String memo;

    public DiveLog() {}
    public DiveLog(int diveLog, String buddyName, String instructor, String diveMaster, String purpose, String date, String time) {
        this.diveLog = diveLog;
        this.buddyName = buddyName;
        this.instructor = instructor;
        this.diveMaster = diveMaster;
        this.purpose = purpose;
        this.date = date;
        this.time = time;
    }
    public DiveLog(int diveLog, String location, String point, double[] coordiness) {
        this.diveLog = diveLog;
        this.location = location;
        this.point = point;
        this.coordiness = coordiness;
    }
    public DiveLog(int diveLog, String weather, int diveTime, double maxDepth, int sawLength, int watTemp) {
        this.diveLog = diveLog;
        this.weather = weather;
        this.diveTime = diveTime;
        this.maxDepth = maxDepth;
        this.sawLength = sawLength;
        this.watTemp = watTemp;
    }
    public DiveLog(int diveLog, String tank, String gas, String suit) {
        this.diveLog = diveLog;
        this.tank = tank;
        this.gas = gas;
        this.suit = suit;
    }
    public DiveLog(int diveLog, int befGasLeft, int aftGasLeft, int beltWeight) {
        this.diveLog = diveLog;
        this.befGasLeft = befGasLeft;
        this.aftGasLeft = aftGasLeft;
        this.beltWeight = beltWeight;
    }
    public DiveLog(int diveLog, String memo) {
        this.diveLog = diveLog;
        this.memo = memo;
    }
    public DiveLog(String date, String time, String point) {
        this.date = date;
        this.time = time;
        this.point = point;
    }
    public DiveLog(int diveLog, String buddyName, String instructor, String diveMaster, String purpose, String date, String time, String location, String point, double[] coordiness, String weather, int diveTime, double maxDepth,
                   int sawLength, int watTemp, String tank, String gas, String suit, int befGasLeft, int aftGasLeft, int beltWeight, String memo) {
        this(diveLog, buddyName, instructor, diveMaster, purpose, date, time);
        this.location = location;
        this.point = point;
        this.coordiness = coordiness;
        this.weather = weather;
        this.diveTime = diveTime;
        this.maxDepth = maxDepth;
        this.sawLength = sawLength;
        this.watTemp = watTemp;
        this.tank = tank;
        this.gas = gas;
        this.suit = suit;
        this.befGasLeft = befGasLeft;
        this.aftGasLeft = aftGasLeft;
        this.beltWeight = beltWeight;
        this.memo = memo;
    }

    public int getDiveLog() {
        return diveLog;
    }
    public void setDiveLog(int diveLog) {
        this.diveLog = diveLog;
    }
    public String getBuddyName() {
        return buddyName;
    }
    public void setBuddyName(String buddyName) {
        this.buddyName = buddyName;
    }
    public String getInstructor() {
        return instructor;
    }
    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }
    public String getDiveMaster() {
        return diveMaster;
    }
    public void setDiveMaster(String diveMaster) {
        this.diveMaster = diveMaster;
    }
    public String getPurpose() {
        return purpose;
    }
    public void setPurpose(String purpose) {
        this.purpose = purpose;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getPoint() {
        return point;
    }
    public void setPoint(String point) {
        this.point = point;
    }
    public double[] getCoordiness() {
        return coordiness;
    }
    public void setCoordiness(double[] coordiness) {
        this.coordiness = coordiness;
    }
    public String getWeather() {
        return weather;
    }
    public void setWeather(String weather) {
        this.weather = weather;
    }
    public int getDiveTime() {
        return diveTime;
    }
    public void setDiveTime(int diveTime) {
        this.diveTime = diveTime;
    }
    public double getMaxDepth() {
        return maxDepth;
    }
    public void setMaxDepth(double maxDepth) {
        this.maxDepth = maxDepth;
    }
    public int getSawLength() {
        return sawLength;
    }
    public void setSawLength(int sawLength) {
        this.sawLength = sawLength;
    }
    public int getWatTemp() {
        return watTemp;
    }
    public void setWatTemp(int watTemp) {
        this.watTemp = watTemp;
    }
    public String getTank() {
        return tank;
    }
    public void setTank(String tank) {
        this.tank = tank;
    }
    public String getGas() {
        return gas;
    }
    public void setGas(String gas) {
        this.gas = gas;
    }
    public String getSuit() {
        return suit;
    }
    public void setSuit(String suit) {
        this.suit = suit;
    }
    public int getBefGasLeft() {
        return befGasLeft;
    }
    public void setBefGasLeft(int befGasLeft) {
        this.befGasLeft = befGasLeft;
    }
    public int getAftGasLeft() {
        return aftGasLeft;
    }
    public void setAftGasLeft(int aftGasLeft) {
        this.aftGasLeft = aftGasLeft;
    }
    public int getBeltWeight() {
        return beltWeight;
    }
    public void setBeltWeight(int beltWeight) {
        this.beltWeight = beltWeight;
    }
    public String getMemo() {
        return memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
}
