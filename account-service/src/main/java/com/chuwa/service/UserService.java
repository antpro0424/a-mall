package com.chuwa.service;

import com.chuwa.dto.LoginFormDTO;
import com.chuwa.dto.SignupFormDTO;
import com.chuwa.vo.UserLoginVO;
import com.chuwa.vo.UserSignupVO;

public interface UserService {
    UserLoginVO login(LoginFormDTO loginFormDTO);

    UserSignupVO signup(SignupFormDTO signupFormDTO);
}
