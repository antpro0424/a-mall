package com.chuwa.service;

import com.chuwa.dto.LoginFormDTO;
import com.chuwa.vo.UserLoginVO;

public interface UserService {
    UserLoginVO login(LoginFormDTO loginFormDTO);
}
