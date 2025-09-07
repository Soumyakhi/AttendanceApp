package com.attendance.attendance.service;
import jakarta.servlet.http.HttpServletRequest;
public interface ClassService {
    public String createClass(HttpServletRequest req);
    public boolean closeClass(HttpServletRequest req,String classId);

}
