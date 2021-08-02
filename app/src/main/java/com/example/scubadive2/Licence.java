package com.example.scubadive2;

public class Licence {
    private String licenceCode;
    private String licenceNo;

    public Licence() {}
    public Licence(String licenceCode, String licenceNo) {
        this.licenceCode = licenceCode;
        this.licenceNo = licenceNo;
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
}
