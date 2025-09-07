package com.attendance.attendance.pojo;

import java.util.List;

public class Course {
    private String courseNo;
    private String courseName;
    private List<Subject> subjectList;
    public Course(String courseNo, String courseName, List<Subject> subjectList) {
        this.courseNo = courseNo;
        this.courseName = courseName;
        this.subjectList = subjectList;
    }
    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public List<Subject> getSubjectList() {
        return subjectList;
    }
    public void setSubjectList(List<Subject> subjectList) {
        this.subjectList = subjectList;
    }
}
