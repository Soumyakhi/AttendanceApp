package com.attendance.attendance.dto;

import com.attendance.attendance.entity.ClassEntity;
import com.attendance.attendance.pojo.Subject;

import java.util.Map;

public class TeacherAttDTO {
    private ClassEntity classEntity;
    private Map<String, Boolean> studentAttendance;
    public TeacherAttDTO( ClassEntity classEntity, Map<String, Boolean> studentAttendance) {
        this.classEntity = classEntity;
        this.studentAttendance = studentAttendance;
    }
    public ClassEntity getClassEntity() {
        return classEntity;
    }

    public void setClassEntity(ClassEntity classEntity) {
        this.classEntity = classEntity;
    }

    public Map<String, Boolean> getStudentAttendance() {
        return studentAttendance;
    }

    public void setStudentAttendance(Map<String, Boolean> studentAttendance) {
        this.studentAttendance = studentAttendance;
    }
}
