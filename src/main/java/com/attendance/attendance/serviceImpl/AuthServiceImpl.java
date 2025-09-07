package com.attendance.attendance.serviceImpl;

import com.attendance.attendance.dto.LoginDTO;
import com.attendance.attendance.dto.LoginResDTO;
import com.attendance.attendance.entity.Student;
import com.attendance.attendance.entity.Teacher;
import com.attendance.attendance.repo.StudentRepo;
import com.attendance.attendance.repo.TeacherRepo;
import com.attendance.attendance.service.AuthService;
import com.attendance.attendance.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    JwtUtil jwtUtil;
    @Autowired
    StudentRepo studentRepo;
    @Autowired
    TeacherRepo teacherRepo;
    @Override
    public LoginResDTO doAuthenticateStd(LoginDTO loginDTO) {
        Student student=studentRepo.findByUserNameAndPassword(loginDTO.getUsername(),loginDTO.getPassword());
        if(student==null){
            throw new BadCredentialsException("bad credentials");
        }
        LoginResDTO loginResDTO=new LoginResDTO();
        loginResDTO.setUname(loginDTO.getUsername());
        loginResDTO.setJwt(this.jwtUtil.generateToken(loginDTO.getUsername()));
        return loginResDTO;
    }
    @Override
    public LoginResDTO doAuthenticateTeacher(LoginDTO loginDTO) {
        Teacher teacher=teacherRepo.findByUserNameAndPassword(loginDTO.getUsername(),loginDTO.getPassword());
        if(teacher==null){
            throw new BadCredentialsException("bad credentials");
        }
        LoginResDTO loginResDTO=new LoginResDTO();
        loginResDTO.setUname(loginDTO.getUsername());
        loginResDTO.setJwt(this.jwtUtil.generateToken(loginDTO.getUsername()));
        return loginResDTO;
    }
}
