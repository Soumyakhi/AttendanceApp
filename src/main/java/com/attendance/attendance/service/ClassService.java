package com.attendance.attendance.service;
import com.attendance.attendance.dto.StudentAttDTO;
import com.attendance.attendance.dto.TeacherAttDTO;
import com.attendance.attendance.entity.ClassEntity;
import jakarta.servlet.http.HttpServletRequest;

import java.util.List;

public interface ClassService {
    public String createClass(HttpServletRequest req);
    public ClassEntity closeClass(HttpServletRequest req,String classId);
    public boolean postAttendance(HttpServletRequest req,String classId);
    public List<TeacherAttDTO> viewAttendanceTeacher(HttpServletRequest request);
    public List<StudentAttDTO> viewAttendanceStudent(HttpServletRequest request);

}
