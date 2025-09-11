package com.attendance.attendance.dto;

import com.attendance.attendance.pojo.Subject;

public class StudentAttDTO {
    private Subject subject;
    private int totalAttendance;
    private int totalClasses;
    public StudentAttDTO(Subject subject, int totalAttendance, int totalClasses) {
        this.subject = subject;
        this.totalAttendance = totalAttendance;
        this.totalClasses = totalClasses;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public int getTotalAttendance() {
        return totalAttendance;
    }

    public void setTotalAttendance(int totalAttendance) {
        this.totalAttendance = totalAttendance;
    }

    public int getTotalClasses() {
        return totalClasses;
    }

    public void setTotalClasses(int totalClasses) {
        this.totalClasses = totalClasses;
    }
}
