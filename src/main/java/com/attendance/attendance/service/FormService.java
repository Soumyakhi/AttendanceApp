package com.attendance.attendance.service;

import com.attendance.attendance.dto.CourseDTO;
import com.attendance.attendance.dto.StudentFormDTO;
import com.attendance.attendance.dto.TeacherFormDTO;

import java.io.File;

public interface FormService {
    public String createForm(CourseDTO classDto);
    public File viewForm(String courseNo);
    public File viewFormStd(String courseNo);
    public boolean addTeacherxls(TeacherFormDTO teacherFormDTO);
    public boolean addStudentxls(StudentFormDTO studentFormDTO);
    public boolean finalizeCourse(String courseNo);
}
