package com.example.scubadive2;

public class UserInfo {
    private String userName;
    private String userId;
    private String userPw;
    private String email;
    private String birthD8;
    private String licenceCode;
    private String licenceNo;
    private int maxDepth;
    private int accuLog;

    public UserInfo() {}
    public UserInfo(String userId, String userPw) {
        this.userId = userId;
        this.userPw = userPw;
    }
    public UserInfo(String userName, String userId, String userPw, String email, String birthD8) {
        this(userId, userPw);
        this.userName = userName;
        this.email = email;
        this.birthD8 = birthD8;
    }
    public UserInfo(String licenceCode, String licenceNo, int maxDepth, int accuLog) {
        this.licenceCode = licenceCode;
        this.licenceNo = licenceNo;
        this.maxDepth = maxDepth;
        this.accuLog = accuLog;
    }
    public UserInfo(String userName, String userId, String userPw, String email, String birthD8, String licenceCode, String licenceNo, int maxDepth, int accuLog) {
        this(userName, userId, userPw, email, birthD8);
        this.licenceCode = licenceCode;
        this.licenceNo = licenceNo;
        this.maxDepth = maxDepth;
        this.accuLog = accuLog;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public String getUserPw() {
        return userPw;
    }
    public void setUserPw(String userPw) {
        this.userPw = userPw;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getBirthD8() {
        return birthD8;
    }
    public void setBirthD8(String birthD8) {
        this.birthD8 = birthD8;
    }
    public String getLicenceCode() {
        return licenceCode;
    }
    public void setLicenceCode(String licenceCode) {
        this.licenceCode = licenceCode;
    }
    public String getLicenceNo() {
        return licenceNo;
    }
    public void setLicenceNo(String licenceNo) {
        this.licenceNo = licenceNo;
    }
    public int getMaxDepth() {
        return maxDepth;
    }
    public void setMaxDepth(int maxDepth) {
        this.maxDepth = maxDepth;
    }
    public int getAccuLog() {
        return accuLog;
    }
    public void setAccuLog(int accuLog) {
        this.accuLog = accuLog;
    }
}
