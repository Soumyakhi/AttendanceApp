package com.attendance.attendance.serviceImpl;

import com.attendance.attendance.dto.CourseDTO;
import com.attendance.attendance.dto.StudentFormDTO;
import com.attendance.attendance.dto.TeacherFormDTO;
import com.attendance.attendance.entity.FormEntity;
import com.attendance.attendance.repo.FormRepo;
import com.attendance.attendance.service.FormService;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.attendance.attendance.utils.FileUtils;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.UUID;
@Service
public class FormServiceImpl implements FormService {
    @Autowired
    FormRepo formRepo;
    @Override
    public String createForm(CourseDTO classDto) {
        String courseNo = UUID.randomUUID().toString();
        FormEntity formEntity = new FormEntity(classDto.getCourseName(),classDto.getSubjects(), courseNo);
        formRepo.save(formEntity);
        return courseNo;
    }
    @Autowired
    FileUtils fileUtil;
    @Override
    public File viewForm(String courseNo) {
        try{
            return fileUtil.convert(formRepo.findByCourseNo(courseNo).getTeacherXls(),"Teacher.xls");
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public File viewFormStd(String courseNo) {
        try{
            return fileUtil.convert(formRepo.findByCourseNo(courseNo).getStudentXls(),"Student.xls");
        }
        catch(Exception e){
            return null;
        }
    }

    @Override
    public boolean addTeacherxls(TeacherFormDTO teacherFormDTO) {
        FormEntity formEntity= formRepo.findByCourseNo(teacherFormDTO.getCourseId());
        if(formEntity==null){
            return false;
        }
        try {
            // 1. Load workbook from byte[]
            ByteArrayInputStream bis = new ByteArrayInputStream(formEntity.getTeacherXls());
            HSSFWorkbook workbook = new HSSFWorkbook(bis);
            Sheet sheet = workbook.getSheetAt(0);

            // 2. Find next empty row
            int lastRow = sheet.getLastRowNum();
            Row row = sheet.createRow(lastRow + 1);

            // 3. Fill teacher details
            row.createCell(0).setCellValue(teacherFormDTO.getName());
            row.createCell(1).setCellValue(teacherFormDTO.getEmail());
            row.createCell(2).setCellValue(teacherFormDTO.getSubject());
            row.createCell(3).setCellValue(teacherFormDTO.getCourseId());
            row.createCell(4).setCellValue(formEntity.getName());

            // 4. Convert workbook back to byte[]
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            workbook.close();

            formEntity.setTeacherXls(bos.toByteArray());

            // 5. Save back into MongoDB
            formRepo.save(formEntity);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    @Override
    public boolean addStudentxls(StudentFormDTO studentFormDTO) {
        FormEntity formEntity= formRepo.findByCourseNo(studentFormDTO.getCourseId());
        if(formEntity==null){
            return false;
        }
        try {
            // 1. Load workbook from byte[]
            ByteArrayInputStream bis = new ByteArrayInputStream(formEntity.getStudentXls());
            HSSFWorkbook workbook = new HSSFWorkbook(bis);
            Sheet sheet = workbook.getSheetAt(0);

            // 2. Find next empty row
            int lastRow = sheet.getLastRowNum();
            Row row = sheet.createRow(lastRow + 1);

            // 3. Fill teacher details
            row.createCell(0).setCellValue(studentFormDTO.getName());
            row.createCell(1).setCellValue(studentFormDTO.getEmail());
            row.createCell(2).setCellValue(studentFormDTO.getCareerGoals());
            row.createCell(3).setCellValue(studentFormDTO.getCourseId());
            row.createCell(4).setCellValue(formEntity.getName());

            // 4. Convert workbook back to byte[]
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            workbook.write(bos);
            workbook.close();

            formEntity.setStudentXls(bos.toByteArray());

            // 5. Save back into MongoDB
            formRepo.save(formEntity);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    @Override
    public boolean finalizeCourse(String courseNo){
        if (fileUtil.process(formRepo.findByCourseNo(courseNo))){
            formRepo.deleteByCourseNo(courseNo);
            return true;
        }
        return false;
    }

    @Override
    public List<FormEntity> findPendingForms() {
        return formRepo.findAll();
    }

}
