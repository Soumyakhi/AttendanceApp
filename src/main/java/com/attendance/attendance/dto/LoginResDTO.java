package com.attendance.attendance.dto;

public class LoginResDTO {
    private String jwt;
    private String uname;
    public String getJwt() {
        return jwt;
    }
    public void setJwt(String jwt) {
        this.jwt = jwt;
    }

    public String getUname() {
        return uname;
    }
    public void setUname(String uname) {
        this.uname = uname;
    }

}
