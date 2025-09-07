package com.attendance.attendance.serviceImpl;

import com.attendance.attendance.entity.ClassEntity;
import com.attendance.attendance.entity.Teacher;
import com.attendance.attendance.repo.ClassRepo;
import com.attendance.attendance.repo.TeacherRepo;
import com.attendance.attendance.security.TeacherAuthenticationFilter;
import com.attendance.attendance.service.ClassService;
import com.attendance.attendance.utils.JwtUtil;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.UUID;

@Service
public class ClassServiceImpl implements ClassService {
    @Autowired
    TeacherRepo teacherRepo;
    @Autowired
    ClassRepo classRepo;
    @Autowired
    JwtUtil jwtUtil;
    @Override
    public String createClass(HttpServletRequest req) {
        Teacher teacher=teacherRepo.findByUserName(jwtUtil.extractUserNameFromRequest(req));
        if(teacher==null)
            throw new RuntimeException("Teacher Not Found");
        ClassEntity classEntity=new ClassEntity(UUID.randomUUID().toString(),teacher,new ArrayList<>(),true);
        classRepo.save(classEntity);
        return classEntity.getClassId();
    }

    @Override
    public boolean closeClass(HttpServletRequest req, String classId) {
        ClassEntity classEntity=classRepo.findByClassIdAndTeacher_UserName(classId,jwtUtil.extractUserNameFromRequest(req));
        if(classEntity==null)
            return false;
        classEntity.setOpen(false);
        classRepo.save(classEntity);
        return true;
    }
}
