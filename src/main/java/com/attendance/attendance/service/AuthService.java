package com.attendance.attendance.service;

import com.attendance.attendance.dto.LoginDTO;
import com.attendance.attendance.dto.LoginResDTO;

public interface AuthService {
    public LoginResDTO doAuthenticateStd(LoginDTO loginDTO);
    public LoginResDTO doAuthenticateTeacher(LoginDTO loginDTO);

}
