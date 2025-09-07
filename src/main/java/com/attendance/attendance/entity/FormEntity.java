package com.attendance.attendance.entity;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.util.List;

@Document
public class FormEntity {

    @Id
    private String id;
    private String courseNo;
    private String name;
    private List<String> subjects;

    private byte[] teacherXls;
    private byte[] studentXls;

    // Default constructor
    public FormEntity() {}

    public FormEntity(String name, List<String> subjects, String courseNo) {
        this.name = name;
        this.subjects = subjects;
        this.courseNo = courseNo;

        try {
            // ---------------- Teacher XLS ----------------
            HSSFWorkbook teacherWorkbook = new HSSFWorkbook();
            Sheet teacherSheet = teacherWorkbook.createSheet("Teachers");

            // Create header row
            Row headerRow = teacherSheet.createRow(0);
            String[] teacherHeaders = {"Teacher Name", "Email", "Subject Name", "Course ID", "Course Name"};
            for (int i = 0; i < teacherHeaders.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(teacherHeaders[i]);
            }

            // Convert to byte array
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                teacherWorkbook.write(bos);
                this.teacherXls = bos.toByteArray();
            }
            teacherWorkbook.close();

            // ---------------- Student XLS ----------------
            HSSFWorkbook studentWorkbook = new HSSFWorkbook();
            Sheet studentSheet = studentWorkbook.createSheet("Students");

            // Create header row
            Row studentHeader = studentSheet.createRow(0);
            String[] studentHeaders = {"Student Name", "Email", "Career Goal", "Course ID", "Course Name"};
            for (int i = 0; i < studentHeaders.length; i++) {
                Cell cell = studentHeader.createCell(i);
                cell.setCellValue(studentHeaders[i]);
            }

            // Convert to byte array
            try (ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
                studentWorkbook.write(bos);
                this.studentXls = bos.toByteArray();
            }
            studentWorkbook.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getCourseNo() { return courseNo; }
    public void setCourseNo(String courseNo) { this.courseNo = courseNo; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public List<String> getSubjects() { return subjects; }
    public void setSubjects(List<String> subjects) { this.subjects = subjects; }

    public byte[] getTeacherXls() { return teacherXls; }
    public void setTeacherXls(byte[] teacherXls) { this.teacherXls = teacherXls; }

    public byte[] getStudentXls() { return studentXls; }
    public void setStudentXls(byte[] studentXls) { this.studentXls = studentXls; }
}
