package com.attendance.attendance.utils;

import com.attendance.attendance.entity.FormEntity;
import com.attendance.attendance.entity.Student;
import com.attendance.attendance.entity.Teacher;
import com.attendance.attendance.pojo.Course;
import com.attendance.attendance.pojo.Subject;
import com.attendance.attendance.repo.FormRepo;
import com.attendance.attendance.repo.StudentRepo;
import com.attendance.attendance.repo.TeacherRepo;
import com.attendance.attendance.service.FormService;
import com.attendance.attendance.service.MailService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Component
public class FileUtils {
    public File convert(byte[] data, String fileName) {
        try {
            File file = new File(fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(data);
            }
            return file;
        }
        catch (IOException e) {
            return null;
        }
    }
    @Autowired
    private TeacherRepo teacherRepo;
    @Autowired
    private StudentRepo studentRepo;
    @Autowired
    private MailService mailService;
    public boolean process(FormEntity formEntity) {
            try {
                if (formEntity == null) {
                    return false;
                }
                List<Subject> subjects=new ArrayList<>();
                Map<String,String> subjectsMap=new HashMap<>();
                int count=1;
                for(String sub: formEntity.getSubjects()) {
                    String subId=Integer.toString(count);
                    subjectsMap.put(sub,subId);
                    subjects.add(new Subject(subId,sub));
                    count++;
                }
                Course course = new Course(formEntity.getCourseNo(), formEntity.getName(), subjects);
                ByteArrayInputStream teacherStream = new ByteArrayInputStream(formEntity.getTeacherXls());
                HSSFWorkbook teacherWorkbook = new HSSFWorkbook(teacherStream);
                Sheet teacherSheet = teacherWorkbook.getSheetAt(0);
                for (Row row : teacherSheet) {
                    if (row.getRowNum() == 0) continue; // skip heading
                    String teacherName = row.getCell(0).getStringCellValue();
                    String teacherEmail = row.getCell(1).getStringCellValue();
                    String subject = row.getCell(2).getStringCellValue();
                    String courseId = row.getCell(3).getStringCellValue();
                    Teacher teacher=new Teacher(courseId,teacherName,teacherEmail, UUID.randomUUID().toString(),UUID.randomUUID().toString(),
                            new Subject(subjectsMap.get(subject),subject));
                    teacherRepo.save(teacher);
                    mailService.sendEmail(teacherEmail,"Teacher login info for course "+course.getCourseName(),
                            " UserName is "+teacher.getUserName()+ " Password is "+teacher.getPassword());
                }
                // --- Student workbook ---
                ByteArrayInputStream studentStream = new ByteArrayInputStream(formEntity.getStudentXls());
                HSSFWorkbook studentWorkbook = new HSSFWorkbook(studentStream);
                Sheet studentSheet = studentWorkbook.getSheetAt(0);
                for (Row row : studentSheet) {
                    if (row.getRowNum() == 0) continue; // skip heading
                    String studentName = row.getCell(0).getStringCellValue();
                    String studentEmail = row.getCell(1).getStringCellValue();
                    String careerGoals = row.getCell(2).getStringCellValue();
                    Student student=new Student(studentName,studentEmail,UUID.randomUUID().toString(),UUID.randomUUID().toString(),course,careerGoals);
                    studentRepo.save(student);
                    mailService.sendEmail(studentEmail,"Student login info for course "+course.getCourseName(),
                            " UserName is "+student.getUserName()+ " Password is "+student.getPassword());
                }

                teacherWorkbook.close();
                studentWorkbook.close();

                return true;

            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

    }
}
