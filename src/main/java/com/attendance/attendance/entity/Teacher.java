package com.attendance.attendance.entity;

import com.attendance.attendance.pojo.Subject;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class Teacher {
    @Id
    private String id;
    private String courseNo;
    private String name;
    private String email;
    private String userName;
    private String password;
    private Subject subject;
    public Teacher(String courseNo, String name, String email, String userName, String password, Subject subject) {

        this.courseNo = courseNo;
        this.name = name;
        this.email = email;
        this.userName = userName;
        this.password = password;
        this.subject = subject;
    }
    public String getUserName() {
        return userName;
    }

    public void setUserName(String getUserName) {
        this.userName = getUserName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCourseNo() {
        return courseNo;
    }

    public void setCourseNo(String courseNo) {
        this.courseNo = courseNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}
