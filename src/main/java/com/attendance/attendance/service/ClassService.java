package com.attendance.attendance.service;
import com.attendance.attendance.entity.ClassEntity;
import jakarta.servlet.http.HttpServletRequest;
public interface ClassService {
    public String createClass(HttpServletRequest req);
    public ClassEntity closeClass(HttpServletRequest req,String classId);
    public boolean postAttendance(HttpServletRequest req,String classId);
    public ClassEntity viewAttendance(String classId);

}
