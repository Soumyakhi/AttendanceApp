package com.attendance.attendance.controller;


import com.attendance.attendance.dto.*;
import com.attendance.attendance.service.AuthService;
import com.attendance.attendance.service.ClassService;
import com.attendance.attendance.service.FormService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
public class AdminController {
    @Autowired
    FormService formService;

    @PostMapping("/createCourse")
    public String createCourse(@RequestBody CourseDTO classDto) {
        return formService.createForm(classDto);
    }

    @GetMapping("/View/{courseNo}")
    public File getSheet(@PathVariable String courseNo) {
        return formService.viewForm(courseNo);
    }
    @GetMapping("/ViewStd/{courseNo}")
    public File getSheetStd(@PathVariable String courseNo) {
        return formService.viewFormStd(courseNo);
    }
    @PostMapping("/EnterTeacher")
    public Boolean enterDataTeacher(@RequestBody TeacherFormDTO teacherFormDTO) {
        return formService.addTeacherxls(teacherFormDTO);
    }
    @PostMapping("/EnterStudent")
    public Boolean enterDataStudent(@RequestBody StudentFormDTO studentFormDTO) {
        return formService.addStudentxls(studentFormDTO);
    }
    @PostMapping("/Process/{courseNo}")
    public Boolean process(@PathVariable String courseNo) {
        return formService.finalizeCourse(courseNo);
    }
    @Autowired
    AuthService authService;
    @PostMapping("/loginstd")
    public ResponseEntity<Object> loginstd(@RequestBody LoginDTO loginDTO) {
        LoginResDTO loginInfoDTO = authService.doAuthenticateStd(loginDTO);;
        return new ResponseEntity<>(loginInfoDTO, HttpStatus.OK);
    }
    @PostMapping("/loginteacher")
    public ResponseEntity<Object> loginteacher(@RequestBody LoginDTO loginDTO) {
        LoginResDTO loginInfoDTO = authService.doAuthenticateTeacher(loginDTO);
        return new ResponseEntity<>(loginInfoDTO, HttpStatus.OK);
    }
    @Autowired
    ClassService classService;
    @PostMapping("/createClass")
    public ResponseEntity<Object> createClass(HttpServletRequest request) {
        return new ResponseEntity<>(classService.createClass(request), HttpStatus.OK);
    }
    @PutMapping("/closeClass/{classId}")
    public ResponseEntity<Object> closeClass(HttpServletRequest request, @PathVariable String classId) {
        return new ResponseEntity<>(classService.closeClass(request,classId), HttpStatus.OK);
    }
    @PutMapping("/postAttendance/{classId}")
    public ResponseEntity<Object> postAttendance(HttpServletRequest request, @PathVariable String classId) {
        return new ResponseEntity<>(classService.postAttendance(request,classId), HttpStatus.OK);
    }
    @GetMapping("/viewAttendanceTeacher")
    public ResponseEntity<Object> viewAttendanceTeacher(HttpServletRequest request) {
        return new ResponseEntity<>(classService.viewAttendanceTeacher(request), HttpStatus.OK);
    }
    @GetMapping("/findAllPendingForms")
    public ResponseEntity<Object> findAllPendingForms() {
        return new ResponseEntity<>(formService.findPendingForms(), HttpStatus.OK);
    }
    @GetMapping("/viewAttendanceStudent")
    public ResponseEntity<Object> viewAttendanceStd(HttpServletRequest request) {
        return new ResponseEntity<>(classService.viewAttendanceStudent(request), HttpStatus.OK);
    }

}
