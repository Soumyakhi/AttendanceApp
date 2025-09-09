package com.attendance.attendance.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Set;

@Document
public class ClassEntity {
    @Id
    private String id;
    private String classId;
    private Teacher teacher;
    private Set<String> attendedStudents;
    private boolean isOpen;
    public ClassEntity( String classId, Teacher teacher, Set<String> attendedStudents, boolean isOpen) {
        this.classId = classId;
        this.teacher = teacher;
        this.attendedStudents = attendedStudents;
        this.isOpen = isOpen;
    }
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Set<String> getAttendedStudents() {
        return attendedStudents;
    }

    public void setAttendedStudents(Set<String> attendedStudents) {
        this.attendedStudents = attendedStudents;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }
}
